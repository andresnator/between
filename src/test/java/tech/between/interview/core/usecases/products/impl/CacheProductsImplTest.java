package tech.between.interview.core.usecases.products.impl;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tech.between.interview.core.usecases.exceptions.UseCaseException;
import tech.between.interview.core.usecases.products.CacheProducts;
import tech.between.interview.core.usecases.products.GetDescription;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CacheProductsImplTest implements WithAssertions {


    private CacheProducts instance;
    @Mock
    public GetDescription description;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new CacheProductsImpl(description);
    }

    @Test
    void whenGetDescriptionWithUseCaseException_thenOk() {
        final Set<Integer> similarProductIds = new HashSet<>();
        similarProductIds.add(2);
        similarProductIds.add(3);
        similarProductIds.add(4);

        when(description.apply(anyInt())).thenThrow(UseCaseException.class);
        instance.accept(similarProductIds);
        verify(description, timeout(5)).apply(2);
    }

    @Test
    void whenGetDescription_thenOk() {
        final Set<Integer> similarProductIds = new HashSet<>();
        similarProductIds.add(2);
        similarProductIds.add(3);
        similarProductIds.add(4);

        instance.accept(similarProductIds);

        verify(description).apply(2);
        verify(description).apply(3);
        verify(description).apply(4);
        verify(description, times(3)).apply(anyInt());
    }
}