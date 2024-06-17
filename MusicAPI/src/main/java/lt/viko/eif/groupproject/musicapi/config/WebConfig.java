package lt.viko.eif.groupproject.musicapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Ši klasė konfigūruoja resursų tvarkytuvą muzikos API projektui.
 * Ji naudojama norint apibrėžti, kaip statiniai resursai, tokie kaip garso failai, yra pasiekiami per URL.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Konfigūruoja resursų tvarkytuvą, nurodantį, kad visiems užklausoms su keliu
     * prasidedančiu "/audio/**" turi būti pasiekiami iš "file:audio/" katalogo.
     *
     * @param registry resursų tvarkytuvų registras, kuriame yra saugomi keliai ir jų atitikmenys
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/audio/**")
                .addResourceLocations("file:audio/");
    }
}
