package tech.between.interview.configuration.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String DESCRIPTION_PRODUCT = "description_product";
    public static final String SIMILAR_PRODUCT_IDS = "similar_product_ids";
    public static final String SIMILAR_PRODUCT = "similar_product";
    @Value("${cache.maximum.size}")
    private int descriptionProductCacheMaxSize;
    @Value("${cache.duration.minutes}")
    private int descriptionProductCacheDuration;
    private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;

    @Bean
    public CacheManager cacheManager() {
        final CaffeineCacheManager cacheManager = new CaffeineCacheManager(DESCRIPTION_PRODUCT, SIMILAR_PRODUCT_IDS, SIMILAR_PRODUCT);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(descriptionProductCacheMaxSize)
                .recordStats()
                .expireAfterAccess(descriptionProductCacheDuration, TIME_UNIT));
        return cacheManager;
    }
}