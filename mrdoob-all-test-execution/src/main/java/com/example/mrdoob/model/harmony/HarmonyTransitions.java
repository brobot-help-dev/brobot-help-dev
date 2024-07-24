package com.example.mrdoob.model.harmony;

import io.github.jspinak.brobot.actions.actionExecution.Action;
import io.github.jspinak.brobot.manageStates.StateTransition;
import io.github.jspinak.brobot.manageStates.StateTransitions;
import io.github.jspinak.brobot.services.StateTransitionsRepository;
import org.springframework.stereotype.Component;

import static io.github.jspinak.brobot.actions.actionOptions.ActionOptions.Action.CLICK;
import static io.github.jspinak.brobot.actions.actionOptions.ActionOptions.Action.FIND;

@Component
public class HarmonyTransitions {

    private final Action action;
    private final Harmony harmony;

    public HarmonyTransitions(StateTransitionsRepository stateTransitionsRepository,
                               Action action, Harmony harmony) {
        this.action = action;
        this.harmony = harmony;
        StateTransitions transitions = new StateTransitions.Builder("harmony")
                .addTransitionFinish(this::finishTransition)
                .addTransition(new StateTransition.Builder()
                        .addToActivate("about")
                        .setFunction(this::gotoAbout)
                        .build())
                .build();
        stateTransitionsRepository.add(transitions);
    }

    public boolean finishTransition() {
        return action.perform(FIND, harmony.getAbout()).isSuccess();
    }

    public boolean gotoAbout() {
        return action.perform(CLICK, harmony.getAbout()).isSuccess();
    }

}
