package org.clematis.web.elearning.client.ui.input.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface CheckBoxResources extends ClientBundle {
    public static final CheckBoxResources INSTANCE = GWT.create(CheckBoxResources.class);

    @Source("checkbox.css")
    public CheckBoxStyle css();

}
