package darek9k.config;

import darek9k.tracking.TrackingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Trackingconfig {
    @Bean
    FilterRegistrationBean<TrackingFilter> registerTrackingFilter(){
        FilterRegistrationBean<TrackingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TrackingFilter());
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
