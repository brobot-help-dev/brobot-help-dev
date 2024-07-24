package com.example.mrdoob;

import org.sikuli.script.ImagePath;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Connection;

@SpringBootApplication
@ComponentScan(basePackages = {"io.github.jspinak.brobot", "com.example.mrdoob"})
public class MrdoobTestApplication {

    public static void main(String[] args) {
        ImagePath.setBundlePath("brobot-test-ui/images");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(MrdoobTestApplication.class);
        builder.headless(false);
        builder.run(args);
    }

}
