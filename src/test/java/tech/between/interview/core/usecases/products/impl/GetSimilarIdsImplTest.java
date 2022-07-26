package tech.between.interview.core.usecases.products.impl;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tech.between.interview.core.repositories.GetProductSimilarIdsRepository;
import tech.between.interview.core.usecases.exceptions.UseCaseException;
import tech.between.interview.core.usecases.products.GetSimilarIds;
import tech.between.interview.repositories.exceptions.RepositoryException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class GetSimilarIdsImplTest implements WithAssertions {

    private GetSimilarIds instance;

    @Mock
    private GetProductSimilarIdsRepository similarIdsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new GetSimilarIdsImpl(similarIdsRepository);
    }

    @Test
    void whenProductHaveSimilar_thenReturnIds() {
        final int productId = 1;
        final Set<Integer> similarProductIds = new HashSet<>();
        similarProductIds.add(2);
        similarProductIds.add(3);
        similarProductIds.add(4);

        when(similarIdsRepository.getSimilarIds(productId)).thenReturn(similarProductIds);
        final Set<Integer> result = instance.apply(productId);
        assertThat(result).isNotEmpty().contains(2, 3, 4);
    }

    @Test
    void whenProductHaveNotSimilar_thenReturnEmpty() {
        final int productId = 1;
        when(similarIdsRepository.getSimilarIds(productId)).thenReturn(Collections.emptySet());
        final Set<Integer> result = instance.apply(productId);
        assertThat(result).isEmpty();
    }

    @Test
    void whenProductRepositoryException_thenReturnUseCaseException() {
        final int productId = 1;
        when(similarIdsRepository.getSimilarIds(productId)).thenThrow(RepositoryException.class);
        assertThrows(UseCaseException.class,
                () -> instance.apply(productId));
    }
}