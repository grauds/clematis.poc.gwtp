package org.clematis.web.elearning.client.ui.input.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface InputResources extends ClientBundle {
    public static final InputResources INSTANCE = GWT.create(InputResources.class);

    @Source("input.css")
    InputStyle css();

}
