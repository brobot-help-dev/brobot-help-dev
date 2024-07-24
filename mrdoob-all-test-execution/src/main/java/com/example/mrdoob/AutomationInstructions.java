package com.example.mrdoob.old;

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
