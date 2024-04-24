package edu.java;

import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.PerClientRateLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class ScrapperApplication implements WebMvcConfigurer {

    @Autowired
    private PerClientRateLimitInterceptor interceptor;

    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
