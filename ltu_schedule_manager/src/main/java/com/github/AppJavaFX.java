package com.github;

import com.github.viewmodels.viewConfiguration.ViewMeta;
import com.github.viewmodels.viewEntities.ViewStage;
import com.github.viewmodels.viewNavigation.ViewNavigation;

import javafx.stage.Stage;

public class AppJavaFX {

    public static final AppJavaFX instance = new AppJavaFX(); 

    @SuppressWarnings("exports")
    public void run(Stage stage) {
        AppInitializer initializer = new AppInitializer();
        initializer.registerScenes();
        initializer.setStage(stage);
    }

    private static class AppInitializer {

        private void registerScenes() {
            ViewMeta sceneMeta = ViewMeta.getInstance();

            for(ViewMeta.SceneId sceneId : ViewMeta.SceneId.values()) {
                sceneMeta.register(sceneId, new ViewMeta.SceneConfig(sceneId.getStylesheets()));
            }
        }

        private void setStage(Stage stage) {
            ViewNavigation sceneNavigation = ViewNavigation.getInstance();   
            ViewStage sceneStage = ViewStage.getInstance();
            sceneStage.setPrimStage(stage);
            sceneNavigation.navigate(ViewMeta.SceneId.PRIMARY);
        }
    }

    public static AppJavaFX getInstance() {
        return instance;
    }
}
