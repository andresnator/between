package tech.between.interview.core.usecases.products.impl;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tech.between.interview.core.entities.ProductDescription;
import tech.between.interview.core.usecases.exceptions.UseCaseException;
import tech.between.interview.core.usecases.products.CacheProducts;
import tech.between.interview.core.usecases.products.GetDescription;
import tech.between.interview.core.usecases.products.GetSimilarIds;
import tech.between.interview.core.usecases.products.GetSimilarProducts;
import tech.between.interview.core.usecases.utils.Mocks;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class GetSimilarProductsImplTest implements WithAssertions {

    private GetSimilarProducts instance;
    @Mock
    private CacheProducts cacheProducts;
    @Mock
    private GetSimilarIds getSimilarIds;
    @Mock
    private GetDescription getDescription;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new GetSimilarProductsImpl(cacheProducts, getSimilarIds, getDescription);
    }

    @Test
    void whenGetSimilarProductsDonHaveAnySimilarProducts_thenEmpty() {
        when(getSimilarIds.apply(anyInt())).thenReturn(Collections.emptySet());

        final List<ProductDescription> result = instance.apply(1);

        assertThat(result).isEmpty();
    }

    @Test
    void whenGetSimilarProductsDonHaveAnyDescription_thenEmpty() {
        final Set<Integer> similarProductIds = new HashSet<>();
        similarProductIds.add(2);
        similarProductIds.add(3);
        similarProductIds.add(4);

        when(getSimilarIds.apply(anyInt())).thenReturn(similarProductIds);

        final List<ProductDescription> result = instance.apply(1);

        assertThat(result).isEmpty();
    }

    @Test
    void whenGetSimilarProductsSuccess_thenOk() {

        final Set<Integer> similarProductIds = new HashSet<>();
        similarProductIds.add(2);
        similarProductIds.add(3);
        similarProductIds.add(4);

        when(getSimilarIds.apply(1)).thenReturn(similarProductIds);
        when(getSimilarIds.apply(2)).thenThrow(UseCaseException.class);
        when(getSimilarIds.apply(3)).thenReturn(similarProductIds);
        when(getSimilarIds.apply(4)).thenReturn(similarProductIds);
        when(getDescription.apply(2)).thenThrow(UseCaseException.class);
        when(getDescription.apply(3)).thenReturn(Mocks.productDescription());

        final List<ProductDescription> result = instance.apply(1);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo("1");
    }
}