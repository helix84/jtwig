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
package com.lyncode.jtwig.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.lyncode.jtwig.exceptions.JtwigRenderException;
import com.lyncode.jtwig.expression.JtwigExpressionEvaluator;
import com.lyncode.jtwig.manager.ResourceManager;
import com.lyncode.jtwig.render.Calculable;
import com.lyncode.jtwig.render.Renderable;

/**
 * @author "João Melo <jmelo@lyncode.com>"
 *
 */
public class FastExpression implements Renderable {
	private static Logger log = LogManager.getLogger(FastExpression.class);
	private Object value;
	private List<FunctionExpr> functions;
	
	public FastExpression (Object value) {
		this.value = value;
		this.functions = new ArrayList<FunctionExpr>();
		log.debug(this);
	}

	public Object getValue() {
		return value;
	}
	
	public boolean add (FunctionExpr f) {
		this.functions.add(f);
		return true;
	}

	public List<FunctionExpr> getFunctions() {
		return functions;
	}

	public String render(HttpServletRequest req, Map<String, Object> model, ResourceManager manager)
			throws JtwigRenderException {
		JtwigExpressionEvaluator evaluator = new JtwigExpressionEvaluator(model);
		Object result = null;
		if (this.value instanceof Calculable) {
			result = ((Calculable) this.value).calculate(req, model);
		} else result = this.value;
		
		for (FunctionExpr f : functions) {
			List<Object> args = new ArrayList<Object>();
			args.add(result);
			for (Object obj : f.getArguments()) {
				obj = evaluator.evaluate(req, obj);
				args.add(obj);
			}
			result = f.calculate(req, args);
		}
		
		if (result == null) {
			log.debug("No value for "+this.value);
			return "";
		}
		else {
			log.debug("Value for "+this.value+" = "+result);
			return result.toString();
		}
	}
	

	public String toString () {
		return "FastExpression: "+value;
	}
}
