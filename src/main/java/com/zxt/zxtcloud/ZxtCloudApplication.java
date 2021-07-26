package com.zxt.zxtcloud;

import com.zxt.zxtcloud.common.distributedlock.CacheKeyGenerator;
import com.zxt.zxtcloud.common.distributedlock.LockKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@SpringBootApplication
public class ZxtCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZxtCloudApplication.class, args);
    }

    @Bean
    public CacheKeyGenerator cacheKeyGenerator() {
        return new LockKeyGenerator();
    }
}
