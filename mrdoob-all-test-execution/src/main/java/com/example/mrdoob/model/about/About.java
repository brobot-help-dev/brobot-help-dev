package com.example.mrdoob.model.about;

import io.github.jspinak.brobot.database.services.AllStatesInProjectService;
import io.github.jspinak.brobot.datatypes.state.state.State;
import io.github.jspinak.brobot.datatypes.state.stateObject.stateImage.StateImage;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class About {

    private StateImage aboutText = new StateImage.Builder()
            .addPattern("aboutText")
            .build();

    private State state = new State.Builder("about")
            .withImages(aboutText)
            .build();

    public About(AllStatesInProjectService stateService) {
        stateService.save(state);
    }
}
