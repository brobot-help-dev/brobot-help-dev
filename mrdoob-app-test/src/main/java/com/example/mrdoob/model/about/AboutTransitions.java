package com.example.mrdoob.model.about;

import io.github.jspinak.brobot.actions.actionExecution.Action;
import io.github.jspinak.brobot.manageStates.StateTransition;
import io.github.jspinak.brobot.manageStates.StateTransitions;
import io.github.jspinak.brobot.services.StateTransitionsRepository;
import org.springframework.stereotype.Component;

import static io.github.jspinak.brobot.actions.actionOptions.ActionOptions.Action.CLICK;
import static io.github.jspinak.brobot.actions.actionOptions.ActionOptions.Action.FIND;

@Component
public class AboutTransitions {

    private final Action action;
    private final About about;

    public AboutTransitions(StateTransitionsRepository stateTransitionsRepository,
                              Action action, About about) {
        this.action = action;
        this.about = about;
        StateTransitions transitions = new StateTransitions.Builder("about")
                .addTransitionFinish(this::finishTransition)
                .addTransition(new StateTransition.Builder()
                        .addToActivate("source")
                        .setFunction(this::gotoSourcePage)
                        .build())
                .build();
        stateTransitionsRepository.add(transitions);
    }

    public boolean finishTransition() {
        return action.perform(FIND, about.getAboutText()).isSuccess();
    }

    public boolean gotoSourcePage() {
        return action.perform(CLICK, about.getAboutText()).isSuccess();
    }

}
