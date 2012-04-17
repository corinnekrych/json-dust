/*
 * Copyright 2004-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.json.dust

import grails.converters.JSON

import java.util.Collection

import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException
import org.codehaus.groovy.grails.web.converters.marshaller.ObjectMarshaller
import org.codehaus.groovy.grails.web.json.JSONWriter
import org.codehaus.groovy.grails.web.json.JSONWriter.Mode


class CollectionMarshaller implements ObjectMarshaller<JSON> {

	boolean supports(Object object) {
		return object instanceof Collection
	}

	void marshalObject(Object o, JSON converter) {
		JSONWriter writer = converter.getWriter()
		Collection collection = (Collection) o
		if (!collection.isEmpty()) {
			String clazzName = collection[0].class
			int i = clazzName.lastIndexOf(".");
			if (i > -1) {
				clazzName = clazzName.substring(i + 1, clazzName.length());
			}
			writer.object()
			writer.key(clazzName.toLowerCase().concat("s"))
		}
		writer.array()
		for (Object o1 : ((Collection) o)) {
			converter.convertAnother(o1)
		}
		writer.endArray()
		writer.endObject()
	}
}
