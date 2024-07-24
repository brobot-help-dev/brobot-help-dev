package com.example.mrdoob.old;

import io.github.jspinak.brobot.manageStates.InitialStates;
import org.sikuli.script.ImagePath;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"io.github.jspinak.brobot", "com.example.mrdoob"})
public class MrdoobApplication {

    public static void main(String[] args) {


        ImagePath.setBundlePath("brobot-test-ui/images");

        SpringApplicationBuilder builder = new SpringApplicationBuilder(MrdoobApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        // find initial active States
        InitialStates initialStates = context.getBean(InitialStates.class);
        initialStates.addStateSet(100, "homepage");
        initialStates.findIntialStates();

        AutomationInstructions automationInstructions = context.getBean(AutomationInstructions.class);
        automationInstructions.doAutomation();

        System.exit(0);
    }
}
