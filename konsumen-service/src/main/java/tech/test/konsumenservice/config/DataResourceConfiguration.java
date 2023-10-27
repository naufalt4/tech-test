package tech.test.konsumenservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class DataResourceConfiguration {
    @Value("${api.url}")
    private String apiUrl;

    @Value("${paging.default.size}")
    private String defaultPageSize;
}
