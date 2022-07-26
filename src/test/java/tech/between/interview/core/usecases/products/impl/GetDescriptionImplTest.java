package tech.between.interview.core.usecases.products.impl;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tech.between.interview.core.entities.ProductDescription;
import tech.between.interview.core.repositories.GetProductDescriptionRepository;
import tech.between.interview.core.usecases.exceptions.UseCaseException;
import tech.between.interview.core.usecases.products.GetDescription;
import tech.between.interview.core.usecases.utils.Mocks;
import tech.between.interview.repositories.exceptions.RepositoryException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class GetDescriptionImplTest implements WithAssertions {

    private GetDescription instance;

    @Mock
    private GetProductDescriptionRepository descriptionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instance = new GetDescriptionImpl(descriptionRepository);
    }

    @Test
    void whenGetDescription_thenReturnOk() {
        final int productId = 1;
        when(descriptionRepository.getDescription(productId)).thenReturn(Optional.of(Mocks.productDescription()));
        final ProductDescription result = instance.apply(productId);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("1");
    }

    @Test
    void whenGetDescription_thenReturnUseCaseException() {
        final int productId = 1;
        when(descriptionRepository.getDescription(productId)).thenReturn(Optional.empty());
        assertThrows(UseCaseException.class,
                () -> instance.apply(productId));
    }

    @Test
    void whenGetDescription_thenReturnRepositoryException() {
        final int productId = 1;
        when(descriptionRepository.getDescription(productId)).thenThrow(RepositoryException.class);
        assertThrows(UseCaseException.class,
                () -> instance.apply(productId));
    }

}
