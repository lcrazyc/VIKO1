package lt.viko.vvasylieva.springrest.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class for Swagger documentation.
 * This class enables and configures Swagger for the Spring Boot application,
 * allowing API documentation to be generated and accessible.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Creates and configures a Docket bean for Swagger 2.
     * The Docket bean is configured to document the API endpoints within the specified base package.
     * @return a {@link Docket} configured for Swagger 2 documentation
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("lt.viko.vasylieva.spring-rest"))
                .paths(PathSelectors.any())
                .build();
    }
}