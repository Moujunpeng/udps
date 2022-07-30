package com.hikvision.idatafusion.udps.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name="enabled",prefix = "udps.swagger",havingValue = "true")
public class SwaggerConfig {

    @Value("${udps.swagger.enabled}")
    private boolean enabled;

}
