package tech.test.apiservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class DataResourceConfiguration {
    @Value("${paging.default.size}")
    private Integer defaultPageSize;
}
