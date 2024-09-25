package org.clematis.web.elearning.client.classes;


import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class ClassSelected {
	@Order(1)
	StudentsClass studentClass;
	@Order(2)
	Boolean selected;
}
