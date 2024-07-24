package com.example.mrdoob.model.source;

import io.github.jspinak.brobot.database.services.AllStatesInProjectService;
import io.github.jspinak.brobot.datatypes.state.state.State;
import io.github.jspinak.brobot.datatypes.state.stateObject.stateImage.StateImage;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Source {

    private StateImage sourceTitle = new StateImage.Builder()
            .addPattern("sourceTitle")
            .build();

    private State state = new State.Builder("source")
            .withImages(sourceTitle)
            .build();

    public Source(AllStatesInProjectService stateService) {
        stateService.save(state);
    }
}
