package lt.viko.eif.groupproject.musicapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Ši klasė konfigūruoja Swagger muzikos API projektui.
 * Swagger naudojamas interaktyviai API dokumentacijai generuoti,
 * taip palengvinant programuotojams API supratimą ir sąveiką su juo.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Konfigūruoja Docket bean, kuris naudojamas Swagger interaktyviai API dokumentacijai generuoti.
     *
     * @return sukonfigūruotas Docket objektas Swagger 2
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // Nurodo paketą, kurį reikia nuskenuoti dėl kontrolerių
                .apis(RequestHandlerSelectors.basePackage("lt.viko.eif.groupproject.musicapi.controller"))
                // Nurodo kelius, kurie turi būti įtraukti į API dokumentaciją
                .paths(PathSelectors.any())
                .build();
    }
}
