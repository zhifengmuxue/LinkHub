package top.zfmx.linkhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 允许所有路径
                        .allowedOrigins("http://localhost:5173") // 允许前端域名
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
                        .allowCredentials(true); // 允许携带凭证（如 Cookie）
            }
        };
    }

}
