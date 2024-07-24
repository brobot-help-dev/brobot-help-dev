package com.example.mrdoob.controller;

import com.example.mrdoob.model.about.AboutTransitions;
import com.example.mrdoob.model.harmony.HarmonyTransitions;
import com.example.mrdoob.model.homepage.HomepageTransitions;
import com.example.mrdoob.model.source.SourceTransitions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class TestController {

    private final HomepageTransitions homepageTransitions;
    private final HarmonyTransitions harmonyTransitions;
    private final AboutTransitions aboutTransitions;
    private final SourceTransitions sourceTransitions;

    public TestController(HomepageTransitions homepageTransitions,
                          HarmonyTransitions harmonyTransitions,
                          AboutTransitions aboutTransitions,
                          SourceTransitions sourceTransitions) {
        this.homepageTransitions = homepageTransitions;
        this.harmonyTransitions = harmonyTransitions;
        this.aboutTransitions = aboutTransitions;
        this.sourceTransitions = sourceTransitions;
    }

    // Realize only one specific test
    @GetMapping("/startTest")
    public Map<String, Object> startTest(@RequestParam String testName) {
        boolean result = false;
        // Check which test was asked by the user
        switch (testName) {
            case "homepage":
                result = checkHomepage();
                updateToDatabase("homepage","Only",result);
                break;
            case "harmony":
                result = checkHarmony();
                updateToDatabase("harmony","Only",result);
                break;
            case "about":
                result = checkAbout();
                updateToDatabase("about","Only",result);
                break;
            case "source":
                result = checkSource();
                updateToDatabase("source","Only",result);
                break;
        };
        return createResponse(result);
    }

    // Realize all the test from the start to the chosen test
    @GetMapping("/startTestsFromStart")
    public Map<String, Object> startTestsFromStart(@RequestParam String testName,@RequestParam List<String> listStates) {
        Map<String, Object> response = new HashMap<>();
        int index = listStates.indexOf(testName);

        // loop to realize all the test asked
        for (int i = 0; i <= index; i++) {
            String step = listStates.get(i);
            boolean stepResult = false;
            // Check which test was asked by the user
            switch (step) {
                case "homepage":
                    stepResult = checkHomepage();
                    updateToDatabase("homepage","All",stepResult);
                    break;
                case "harmony":
                    stepResult = checkHarmony();
                    updateToDatabase("harmony","All",stepResult);
                    break;
                case "about":
                    stepResult = checkAbout();
                    updateToDatabase("about","All",stepResult);
                    break;
                case "source":
                    stepResult = checkSource();
                    updateToDatabase("source","All",stepResult);
                    break;
            };
            response.put(step, stepResult);
        }

        return response;
    }

    // Realize all the action for Homepage test and return result
    private boolean checkHomepage() {
        return homepageTransitions.finishTransition() && homepageTransitions.gotoHarmony() && harmonyTransitions.finishTransition();
    }

    // Realize all the action for Harmony test and return result
    private boolean checkHarmony() {
        return harmonyTransitions.finishTransition() && harmonyTransitions.gotoAbout() && aboutTransitions.finishTransition();
    }

    // Realize all the action for About test and return result
    private boolean checkAbout() {
        if (aboutTransitions.finishTransition() && aboutTransitions.gotoSourcePage()) {
            // Add delay because change of page
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return sourceTransitions.finishTransition();
        } else {
            return false;
        }
    }

    // Realize all the action for Source test and return result
    private boolean checkSource() {
        return sourceTransitions.finishTransition();
    }

    // Create response object to print it
    private Map<String, Object> createResponse(boolean result) {
        Map<String, Object> response = new HashMap<>();
        response.put("result", result);
        return response;
    }

    // Get the date and call the function to update the test results to the database
    private void updateToDatabase(String name, String status, Boolean check){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        DatabaseController.addTest(name,status,check,dtf.format(now));
    }
}
