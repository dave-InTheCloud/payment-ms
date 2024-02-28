package lu.dave.finance.payment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ServletConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // depreacated but  @GetMapping({"", "/"}) generate two endpoints in swagger)
        configurer.setUseTrailingSlashMatch(true);
    }
}
