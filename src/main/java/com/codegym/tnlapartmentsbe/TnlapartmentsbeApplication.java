package com.codegym.tnlapartmentsbe;

import com.codegym.tnlapartmentsbe.service.ApartmentStatusService;
import com.codegym.tnlapartmentsbe.service.Impl.ApartmentStatusServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TnlapartmentsbeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TnlapartmentsbeApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TnlapartmentsbeApplication.class);
    }

    @Bean
    public ApartmentStatusService statusHouseService(){
        return  new ApartmentStatusServiceImpl();
    }

}
