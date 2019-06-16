package tech.ftchekure.documentslostandfound.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.sql.DataSource;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Configuration
public class ConfigBeans {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurationSupport corsConfigurer() {
        return new WebMvcConfigurationSupport() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String[] names = new String[HttpMethod.values().length];
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods(Stream.of(HttpMethod.values()).map(Enum::name).collect(toSet()).toArray(names));
            }
        };
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }


}
