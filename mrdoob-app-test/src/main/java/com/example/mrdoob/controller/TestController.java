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
        // Check which test was asked by the user
        boolean result = switch (testName) {
            case "homepage" -> checkHomepage();
            case "harmony" -> checkHarmony();
            case "about" -> checkAbout();
            case "source" -> checkSource();
            default -> false;
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
            // Check which test was asked by the user
            boolean stepResult = switch (step) {
                case "homepage" -> checkHomepage();
                case "harmony" -> checkHarmony();
                case "about" -> checkAbout();
                case "source" -> checkSource();
                default -> false;
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
}
