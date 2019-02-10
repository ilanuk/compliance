package com.company.compliance.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.List;

@Configuration
@EntityScan(basePackages = {"com.company.compliance.domain"})
@EnableJpaRepositories(basePackages = {"com.company.compliance.repository"})
@EnableTransactionManagement
@EnableAutoConfiguration
public class RepositoryConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setBasePath("/api");
    }

    @Override
    public void configureConversionService(ConfigurableConversionService configurableConversionService) {

    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingRepositoryEventListener) {

    }

    @Override
    public void configureExceptionHandlerExceptionResolver(ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver) {

    }

    @Override
    public void configureHttpMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {

    }
}
