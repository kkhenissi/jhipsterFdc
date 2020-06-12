package com.kkhenissi.fdc.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.kkhenissi.fdc.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.kkhenissi.fdc.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.kkhenissi.fdc.domain.User.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Authority.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.User.class.getName() + ".authorities");
            createCache(cm, com.kkhenissi.fdc.domain.Region.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Country.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Location.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Department.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Department.class.getName() + ".fdcUsers");
            createCache(cm, com.kkhenissi.fdc.domain.Item.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Item.class.getName() + ".jobs");
            createCache(cm, com.kkhenissi.fdc.domain.FdcUser.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.FdcUser.class.getName() + ".jobs");
            createCache(cm, com.kkhenissi.fdc.domain.Job.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Job.class.getName() + ".items");
            createCache(cm, com.kkhenissi.fdc.domain.JobHistory.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Product.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.ProductItem.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Category.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Photo.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.UserFdc.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Item.class.getName() + ".photos");
            createCache(cm, com.kkhenissi.fdc.domain.Brand.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.Brand.class.getName() + ".products");
            createCache(cm, com.kkhenissi.fdc.domain.Product.class.getName() + ".subcategories");
            createCache(cm, com.kkhenissi.fdc.domain.Category.class.getName() + ".subcategories");
            createCache(cm, com.kkhenissi.fdc.domain.SubCategory.class.getName());
            createCache(cm, com.kkhenissi.fdc.domain.SubCategory.class.getName() + ".products");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
