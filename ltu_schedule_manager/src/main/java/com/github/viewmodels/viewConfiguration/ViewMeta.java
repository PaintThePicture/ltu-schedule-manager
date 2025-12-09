package com.github.viewmodels.viewConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewMeta {
    private static final ViewMeta instance = new ViewMeta();

    private final Map<SceneId, ViewMeta.SceneConfig> sceneData = new HashMap<>();

    private ViewMeta() {}

    public void register(SceneId key, ViewMeta.SceneConfig data) {
        sceneData.put(key, data);
    }

    public static class SceneConfig {
        private Stylesheets[] stylesheets;
        public SceneConfig(Stylesheets... stylesheets) {
            this.stylesheets = stylesheets;
        }

        public String[] getStyleSheetsUrls() {
            return Arrays.stream(stylesheets)
                         .map(s -> getClass().getResource(s.getPath()).toExternalForm())
                         .toArray(String[]::new);
        }
    }

    public static enum SceneId {
        PRIMARY("Primary window", "primary.fxml", new Stylesheets[] {
            Stylesheets.CUSTOM_BUTTON,  Stylesheets.CUSTOM_GRID_PANE,
            Stylesheets.CUSTOM_ICON_BUTTON, Stylesheets.CUSTOM_MENU_BUTTON,
            Stylesheets.CUSTOM_ROOT, Stylesheets.CUSTOM_TABLE_VIEW,
            Stylesheets.CUSTOM_TEXT_FIELD,
            Stylesheets.FONT,
            Stylesheets.CUSTOM_COMBO_BOX
        }),
        SECONDARY("Secondary Window", "secondary.fxml", new Stylesheets[] {
            Stylesheets.CUSTOM_BUTTON,  Stylesheets.CUSTOM_GRID_PANE,
            Stylesheets.CUSTOM_ICON_BUTTON, Stylesheets.CUSTOM_MENU_BUTTON,
            Stylesheets.CUSTOM_ROOT, Stylesheets.CUSTOM_TABLE_VIEW,
            Stylesheets.CUSTOM_TEXT_FIELD
        }),
        DASHBOARD("Dashboard window", "dashboard.fxml", new Stylesheets[] {
            Stylesheets.CUSTOM_BUTTON,  Stylesheets.CUSTOM_GRID_PANE,
            Stylesheets.CUSTOM_ICON_BUTTON, Stylesheets.CUSTOM_MENU_BUTTON,
            Stylesheets.CUSTOM_ROOT, Stylesheets.CUSTOM_TABLE_VIEW,
            Stylesheets.CUSTOM_TEXT_FIELD,
            Stylesheets.FONT,
            Stylesheets.CUSTOM_COMBO_BOX
        });
        
        private final String folder = "/com/github/views/";
        private final String path;
        private final String title;
        private final Stylesheets[] stylesheets;

        private SceneId(String title, String path, Stylesheets[] stylesheets) {
            this.title = title;
            this.path = folder + path;
            this.stylesheets = stylesheets;
        }

        public String getTitle() {
            return this.title;
        }

        public String getPath() {
            return this.path;
        }
        public Stylesheets[] getStylesheets() {
            return this.stylesheets;
        }
    }

    public static enum Stylesheets {
        //Example
        CUSTOM_BUTTON("CustomButton.css"),
        CUSTOM_GRID_PANE("CustomGridPane.css"),
        CUSTOM_ICON_BUTTON("CustomIconButton.css"),
        CUSTOM_MENU_BUTTON("CustomMenuButton.css"),
        CUSTOM_RECTANGLE("CustomRectangle.css"),
        CUSTOM_ROOT("CustomRoot.css"),
        CUSTOM_TABLE_VIEW("CustomTableView.css"),
        CUSTOM_TEXT_FIELD("CustomTextField.css"),
        CUSTOM_COMBO_BOX("CustomComboBox.css"),
        FONT("Font.css");

        private final String folder = "/com/github/stylesClasses/";
        private final String path;

        private Stylesheets(String path) {
            this.path = folder + path;
        }

        public String getPath() {
            return this.path;
        }
    }

    public SceneConfig getSceneConfig(SceneId key) {
        return sceneData.get(key);
    }

    public static ViewMeta getInstance() {
        return instance;
    }
}
