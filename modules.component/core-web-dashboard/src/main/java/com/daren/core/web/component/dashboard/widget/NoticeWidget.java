/*
 * Copyright 2012 Decebal Suiu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.daren.core.web.component.dashboard.widget;


import com.daren.core.web.component.dashboard.AbstractWidget;
import com.daren.core.web.component.dashboard.Widget;
import com.daren.core.web.component.dashboard.web.WidgetView;
import org.apache.wicket.model.Model;

/**
 * @author Decebal Suiu
 */
public class NoticeWidget extends AbstractWidget {

	private static final long serialVersionUID = 1L;
	
	public NoticeWidget() {
		super();
		
		title = "Text";
	}

	public String getText() {
		return "hello world! \ndfdfd\n\nfdfdf\n" +
                "everyy ";
	}

	public WidgetView createView(String viewId) {
		return new NoticeWidgetView(viewId, new Model<Widget>(this));
	}

}
