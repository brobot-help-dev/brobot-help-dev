package com.example.mrdoob.model.homepage;

import io.github.jspinak.brobot.database.services.AllStatesInProjectService;
import io.github.jspinak.brobot.datatypes.state.state.State;
import io.github.jspinak.brobot.datatypes.state.stateObject.stateImage.StateImage;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Homepage {

    private StateImage harmony = new StateImage.Builder()
            .addPattern("harmonyIcon")
            .build();

    private State state = new State.Builder("homepage")
            .withImages(harmony)
            .build();

    public Homepage(AllStatesInProjectService stateService) {
        stateService.save(state);
    }
}
