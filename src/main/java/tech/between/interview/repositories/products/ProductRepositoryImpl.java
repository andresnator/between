package tech.between.interview.repositories.products;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import tech.between.interview.core.entities.ProductDescription;
import tech.between.interview.core.repositories.GetProductDescriptionRepository;
import tech.between.interview.core.repositories.GetProductSimilarIdsRepository;
import tech.between.interview.repositories.AbstractRepository;

import java.util.*;
import java.util.stream.Collectors;

import static tech.between.interview.configuration.cache.CacheConfig.DESCRIPTION_PRODUCT;
import static tech.between.interview.configuration.cache.CacheConfig.SIMILAR_PRODUCT_IDS;

@Component
@Log4j2
public class ProductRepositoryImpl extends AbstractRepository implements GetProductDescriptionRepository, GetProductSimilarIdsRepository {

    private static final String SIMILAR_IDS = "/similarids";

    @Value("${restclient.url.product}")
    private String urlProduct;

    @Override
    @Cacheable(DESCRIPTION_PRODUCT)
    public Optional<ProductDescription> getDescription(int productId) {
        final RestTemplate restTemplate = new RestTemplate();
        final String url = urlProduct.concat(String.valueOf(productId));
        ProductDescription productDescription = null;

        try {
            final ResponseEntity<ProductDescription> responseEntity = restTemplate.getForEntity(url, ProductDescription.class);
            productDescription = responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            launchHttpStatusCodeException(e, url);
        } catch (Exception e) {
            launchRepositoryException(e, "Error getting description product");
        }
        return Optional.ofNullable(productDescription);
    }

    @Override
    @Cacheable(SIMILAR_PRODUCT_IDS)
    public Set<Integer> getSimilarIds(int productId) {
        final RestTemplate restTemplate = new RestTemplate();
        final String url = urlProduct.concat(String.valueOf(productId)).concat(SIMILAR_IDS);
        Set<Integer> similarIds = Collections.emptySet();

        try {
            final ResponseEntity<Integer[]> responseEntity = restTemplate.getForEntity(url, Integer[].class);
            similarIds = Arrays.stream(Objects.requireNonNull(responseEntity.getBody())).collect(Collectors.toSet());
        } catch (HttpStatusCodeException e) {
            launchHttpStatusCodeException(e, url);
        } catch (Exception e) {
            launchRepositoryException(e, "Error getting similar ids");
        }
        return similarIds;
    }
}
