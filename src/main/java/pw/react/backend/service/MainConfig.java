package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.stream.Collectors.toSet;

@Configuration
public class MainConfig {

    @Value(value = "${cors-urls}")
    private String corsUrls;
    @Value(value = "${cors-mappings}")
    private String corsMappings;

    @Value("${carly-url}")
    private String carlyUrl;
    @Value("${flatly-url}")
    private String flatlyUrl;
    @Value("${parkly-url}")
    private String parklyUrl;
    @Value("${integration-url}")
    private String integrationUrl;

    @PostConstruct
    private void init() {
        Logger logger = LoggerFactory.getLogger(MainConfig.class);
        logger.info("************** Environment variables **************");
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            logger.info("[{}] : [{}]", entry.getKey(), entry.getValue());
        }
        logger.info("Injected CORS URL: [{}]", corsUrls);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public HttpClient httpClient(SecurityProvider securityProvider, RestTemplate restTemplate) {
        return new HttpService(securityProvider, restTemplate)
                .withCarlyUrl(carlyUrl)
                .withFlatlyUrl(flatlyUrl)
                .withParklyUrl(parklyUrl)
                .withIntegrationUrl(integrationUrl);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                final Set<String> mappings = getCorsMapings();
                if (mappings.isEmpty()) {
                    registry.addMapping("/**");
                } else {
                    for (String mapping : mappings) {
                        registry.addMapping(mapping).allowedOrigins(getCorsUrls());

                    }
                }
            }
        };
    }

    private String[] getCorsUrls() {
        return Optional.ofNullable(corsUrls)
                .map(value -> value.split(","))
                .orElseGet(() -> new String[0]);
    }

    private Set<String> getCorsMapings() {
        return Optional.ofNullable(corsMappings)
                .map(value -> Arrays.stream(value.split(",")))
                .map(stream -> stream.collect(toSet()))
                .orElseGet(HashSet::new);
    }
}
