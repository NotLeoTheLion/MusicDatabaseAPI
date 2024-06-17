package lt.viko.eif.groupproject.musicapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;

/**
 * Pagrindinė aplikacijos klasė, kuri paleidžia Spring Boot aplikaciją ir konfigūruoja
 * failų įkėlimo (multipart) nustatymus bei automatiškai atidaro pagrindinį puslapį.
 */
@SpringBootApplication
@ServletComponentScan
public class MusicApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicApiApplication.class, args);
        openHomePage();
    }

    /**
     * Konfigūruoja failų įkėlimo nustatymus.
     *
     * @return MultipartConfigElement su nustatytais maksimaliais failų dydžiais
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        factory.setMaxRequestSize(DataSize.ofMegabytes(10));
        return factory.createMultipartConfig();
    }

    /**
     * Atidaro pagrindinį puslapį naršyklėje, priklausomai nuo operacinės sistemos.
     */
    private static void openHomePage() {
        String url = "http://localhost:8080";
        try {
            // Patikrina operacinę sistemą
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows atvejis
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                // macOS atvejis
                Runtime.getRuntime().exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux/Unix atvejis
                Runtime.getRuntime().exec("xdg-open " + url);
            } else {
                System.err.println("Nepalaikoma operacinė sistema. Prašome atidaryti URL rankiniu būdu: " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
