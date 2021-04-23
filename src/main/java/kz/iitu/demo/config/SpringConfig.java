package kz.iitu.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "kz.iitu.demo")
@PropertySource("application.properties")
@EnableJpaRepositories(basePackages = "kz.iitu.demo.dao")
public class SpringConfig {
}
