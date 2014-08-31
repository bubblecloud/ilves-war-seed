/**
 * Copyright 2013 Tommi S.E. Laukkanen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bubblecloud.ilves;

import com.vaadin.ui.Label;
import org.vaadin.addons.sitekit.site.AbstractViewlet;

/**
 * Custom viewlet.
 */
public class CustomViewlet extends AbstractViewlet {

    /**
     * Default constructor.
     */
    public CustomViewlet() {
        // Configure the Vaadin user interface.
        final Label customWelcomeLabel = new Label("Welcome to Ilves seed project!");
        customWelcomeLabel.setStyleName("custom-welcome-label");
        setCompositionRoot(customWelcomeLabel);
    }

    /**
     * Invoked each time this view is visited by the user.
     * @param parameters the Vaadin view parameters
     */
    @Override
    public void enter(final String parameters) {
        // This method can be used for example to read data on demand from database.
    }
}
