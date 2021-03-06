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
package com.daren.core.web.component.dashboard.web.util;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * @author Decebal Suiu
 */
public abstract class CascadingLoadableDetachableModel<M, P> extends LoadableDetachableModel<M> {

	private static final long serialVersionUID = 1L;

	protected IModel<P> parentModel;

	public CascadingLoadableDetachableModel(IModel<P> parentModel) {
		super();
		
		this.parentModel = parentModel;
	}

	@Override
	public void detach() {
		super.detach();
		
		parentModel.detach();
	}

	public abstract M load(P parentObject);
	
	@Override
	protected M load() {
		return load(parentModel.getObject());
	}
	
}
