package sg.nus.iss.team5.javaCA.team5LAPS.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import sg.nus.iss.team5.javaCA.team5LAPS.interceptor.SecurityInterceptor;

@Component
public class WebAppConfig implements WebMvcConfigurer{
  @Autowired
  SecurityInterceptor securityInterceptor;
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(securityInterceptor);
  }
}