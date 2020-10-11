package br.com.henrique.teste.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Classe responsavel por concentrar configuracao referente ao Swagger
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.henrique.teste.dev"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Teste para desenvolvedor(a) Backend")
                .description("Este teste é composto pela construção de um backend REST simples, composto por dois endpoints.")
                .version("1.0.0")
                .contact(new Contact("Henrique Lemes Baron", "https://github.com/henriquelbaron/api-test-spring", "hike.lemes@gmail.com"))
                .build();
    }
}