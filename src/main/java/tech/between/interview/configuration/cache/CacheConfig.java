package tech.between.interview.configuration.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
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
    private static final int DESCRIPTION_PRODUCT_CACHE_MAX_SIZE = 30;
    private static final int DESCRIPTION_PRODUCT_CACHE_DURATION = 10;
    private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;

    @Bean
    public CacheManager cacheManager() {
        final CaffeineCacheManager cacheManager = new CaffeineCacheManager(DESCRIPTION_PRODUCT, SIMILAR_PRODUCT_IDS, SIMILAR_PRODUCT);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(DESCRIPTION_PRODUCT_CACHE_MAX_SIZE)
                .recordStats()
                .expireAfterAccess(DESCRIPTION_PRODUCT_CACHE_DURATION, TIME_UNIT));
        return cacheManager;
    }
}