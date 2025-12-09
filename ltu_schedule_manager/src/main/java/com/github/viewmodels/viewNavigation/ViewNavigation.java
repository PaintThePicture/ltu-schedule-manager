package com.github.viewmodels.viewNavigation;

import java.io.IOException;

import com.github.App;
import com.github.viewmodels.viewConfiguration.ViewMeta;
import com.github.viewmodels.viewConfiguration.ViewProperties;
import com.github.viewmodels.viewEntities.ViewStage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

interface Navigator {
    void navigate(ViewMeta.SceneId sceneId);
}

public class ViewNavigation implements Navigator {
    private static ViewNavigation instance = new ViewNavigation();

    private ViewNavigation() {}

    @Override
    public void navigate(ViewMeta.SceneId sceneId) {
        ViewStage sceneStage = ViewStage.getInstance();

        try {
            // execution before leaving view
            unLoadController(sceneStage.getController());

            // Create loader
            FXMLLoader loader = new FXMLLoader(App.class.getResource(sceneId.getPath()));

            // Load new instance of FXML from loader
            Parent root = loader.load();
            // Execution before loading view
            sceneStage.setController(loadController(loader.getController()));
            // Create and configure new scene
            sceneStage.setScene(createScene(root, sceneId, sceneStage.getProperties()));
            // Show
            sceneStage.show(sceneId.getTitle());

        } catch (IOException e) {
            System.err.println("\n\nError: failed to load scene '" + sceneId.getPath() + "'\n\n");
            e.printStackTrace();
        }
    } 

    private void unLoadController(ViewController controller) {
        if (controller != null) {
            controller.onUnload();
        }
    }

    private ViewController loadController(Object controller) {
        if (controller instanceof ViewController) {
            ((ViewController) controller).initialize();
            return ((ViewController) controller);
        } else {
            return null;
        }
    }

    private void applyStyleSheets(Parent root, ViewMeta.SceneId sceneId) {
        ViewMeta.SceneConfig config = ViewMeta.getInstance().getSceneConfig(sceneId);

        if (config != null && config.getStyleSheetsUrls() != null) {
            root.getStylesheets().addAll(config.getStyleSheetsUrls());
        }
    }

    private Scene createScene(Parent root, ViewMeta.SceneId sceneId, ViewProperties properties) {
        applyStyleSheets(root, sceneId);
        return new Scene(root, properties.getWidth(), properties.getHeight());
    }

    public static ViewNavigation getInstance() {
        return instance;
    }
}
