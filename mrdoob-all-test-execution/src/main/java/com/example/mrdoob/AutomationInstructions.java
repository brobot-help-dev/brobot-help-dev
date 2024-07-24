package com.example.mrdoob;

import io.github.jspinak.brobot.manageStates.StateTransitionsManagement;
import org.springframework.stereotype.Component;


@Component
public class AutomationInstructions {

    private final StateTransitionsManagement stateTransitionsManagement;

    public AutomationInstructions(StateTransitionsManagement stateTransitionsManagement) {
        this.stateTransitionsManagement = stateTransitionsManagement;
    }

    public void doAutomation() {
        stateTransitionsManagement.openState("source");
    }
}
