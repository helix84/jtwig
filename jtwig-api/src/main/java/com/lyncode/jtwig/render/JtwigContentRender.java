/**
 * Copyright 2012 Lyncode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lyncode.jtwig.render;

import java.util.Map;

import com.lyncode.jtwig.exceptions.JtwigRenderException;
import com.lyncode.jtwig.tree.JtwigContent;
import com.lyncode.jtwig.tree.JtwigElement;

/**
 * @author "João Melo <jmelo@lyncode.com>"
 *
 */
public class JtwigContentRender extends JtwigRender<JtwigContent> {

	public JtwigContentRender(Map<String, Object> model, JtwigContent c) {
		super(model, c);
	}

	@Override
	public String render() throws JtwigRenderException {
		String res = "";
		for (JtwigElement e : this.getElement().getChilds()) {
			res += e.renderer(this.getModel()).render();
		}
		return res;
	}

}