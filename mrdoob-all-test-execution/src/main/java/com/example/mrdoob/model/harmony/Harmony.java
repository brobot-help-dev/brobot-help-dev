package com.example.mrdoob.model.harmony;

import io.github.jspinak.brobot.database.services.AllStatesInProjectService;
import io.github.jspinak.brobot.datatypes.state.state.State;
import io.github.jspinak.brobot.datatypes.state.stateObject.stateImage.StateImage;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Harmony {

    private StateImage about = new StateImage.Builder()
            .addPattern("aboutButton")
            .build();

    private State state = new State.Builder("harmony")
            .withImages(about)
            .build();

    public Harmony(AllStatesInProjectService stateService) {
        stateService.save(state);
    }
}
