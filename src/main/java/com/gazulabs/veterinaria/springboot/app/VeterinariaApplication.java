package com.gazulabs.veterinaria.springboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class VeterinariaApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(VeterinariaApplication.class, args);
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(VeterinariaApplication.class);
    }


    @Override
    public void run(String... args) throws Exception {

      /*  String password = "12345";
        String bpass = passwordEncoder.encode(password);
        System.out.println("-- Password: ".concat(bpass)); */

    }
}
