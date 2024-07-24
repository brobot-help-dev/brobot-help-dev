package com.example.mrdoob.model.homepage;

import io.github.jspinak.brobot.actions.actionExecution.Action;
import io.github.jspinak.brobot.manageStates.StateTransition;
import io.github.jspinak.brobot.manageStates.StateTransitions;
import io.github.jspinak.brobot.services.StateTransitionsRepository;
import org.springframework.stereotype.Component;

import static io.github.jspinak.brobot.actions.actionOptions.ActionOptions.Action.CLICK;
import static io.github.jspinak.brobot.actions.actionOptions.ActionOptions.Action.FIND;

@Component
public class HomepageTransitions {

    private final Action action;
    private final Homepage homepage;

    public HomepageTransitions(StateTransitionsRepository stateTransitionsRepository,
                               Action action, Homepage homepage) {
        this.action = action;
        this.homepage = homepage;
        StateTransitions transitions = new StateTransitions.Builder("homepage")
                .addTransitionFinish(this::finishTransition)
                .addTransition(new StateTransition.Builder()
                        .addToActivate("harmony")
                        .setFunction(this::gotoHarmony)
                        .build())
                .build();
        stateTransitionsRepository.add(transitions);
    }

    public boolean finishTransition() {
        return action.perform(FIND, homepage.getHarmony()).isSuccess();
    }

    public boolean gotoHarmony() {
        return action.perform(CLICK, homepage.getHarmony()).isSuccess();
    }
}
