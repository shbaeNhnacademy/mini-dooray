package com.nhnacademy.edu.minidooraygateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "com.nhnacademy.security")
public class SecurityProperties {
    private String clientId;
    private String clientSecret;
    private String scope;
}
