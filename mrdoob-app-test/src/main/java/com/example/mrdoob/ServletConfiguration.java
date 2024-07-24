package com.example.mrdoob;

import io.github.jspinak.brobot.database.services.AllStatesInProjectService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfiguration {
    private final AllStatesInProjectService allStatesInProjectService;

    public ServletConfiguration(AllStatesInProjectService allStatesInProjectService) {
        this.allStatesInProjectService = allStatesInProjectService;
    }

    @Bean
    public ServletRegistrationBean<TestInterfaceServlet> servletRegistrationBean() {
        return new ServletRegistrationBean<>(new TestInterfaceServlet(allStatesInProjectService), "/test-interface");
    }
}
