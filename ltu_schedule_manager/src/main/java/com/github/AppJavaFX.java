/*
 * ltu-schedule-manager: Ett verktyg för att hantera och exportera scheman 
 * från TimeEdit till Canvas för studenter vid Luleå tekniska universitet.
 *
 * Copyright (C) 2025  Alexander Edemalm, 
 * Ronak Olyaee, Therese Henriksson, Jakob Nilsson
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA 02110-1301 USA.
 */
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
