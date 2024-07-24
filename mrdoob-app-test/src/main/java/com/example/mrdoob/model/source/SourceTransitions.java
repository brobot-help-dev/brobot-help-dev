package com.example.mrdoob.model.source;

import io.github.jspinak.brobot.actions.actionExecution.Action;
import io.github.jspinak.brobot.manageStates.StateTransitions;
import io.github.jspinak.brobot.services.StateTransitionsRepository;
import org.springframework.stereotype.Component;

import static io.github.jspinak.brobot.actions.actionOptions.ActionOptions.Action.FIND;

@Component
public class SourceTransitions {

    private final Action action;
    private final com.example.mrdoob.model.source.Source source;

    public SourceTransitions(StateTransitionsRepository stateTransitionsRepository,
                             Action action, Source source) {
        this.action = action;
        this.source = source;
        StateTransitions transitions = new StateTransitions.Builder("source")
                .addTransitionFinish(this::finishTransition)
                .build();
        stateTransitionsRepository.add(transitions);
    }

    public boolean finishTransition() {
        return action.perform(FIND, source.getSourceTitle()).isSuccess();
    }
}
