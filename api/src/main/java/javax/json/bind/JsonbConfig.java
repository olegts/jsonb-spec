/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package javax.json.bind;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <a name="supportedProps"></a>
 * <b>Supported Properties</b><br>
 * <blockquote>
 * <p>
 * All JSON Binding providers are required to support the following set of properties.
 * Some providers may support additional properties.
 * <dl>
 *   <dt><tt>jsonb.tojson.formatted</tt> - java.lang.Boolean
 *   <dd>Controls whether or not the {@link javax.json.bind.Jsonb Jsonb} {@code toJson()}
 *       methods will format the resulting JSON data with line breaks and indentation. A
 *       true value for this property indicates human readable indented
 *       data, while a false value indicates unformatted data.
 *       Default value is false (unformatted) if this property is not specified.
 * </dl>
 * <dl>
 *   <dt><tt>jsonb.tojson.encoding</tt> - java.lang.String
 *   <dd>The {@link javax.json.bind.Jsonb Jsonb} marshalling {@code toJson()} methods
 *       will default to this property for encoding of output JSON data. Default
 *       value is 'UTF-8' if this property is not specified.
 * </dl>
 * <dl>
 *   <dt><tt>jsonb.fromjson.encoding</tt> - java.lang.String
 *   <dd>The {@link javax.json.bind.Jsonb Jsonb} unmarshalling {@code fromJson()}
 *       methods will default to this property encoding of input JSON data if the
 *       encoding cannot be detected.
 * </dl>
 * </blockquote>
 *
 * @see JsonbConfigException
 * @author Martin Grebac, Przemyslaw Bielicki, Eugen Cepoi
 * @since JSON Binding 1.0
 */
public final class JsonbConfig {

    /**
     * Property used to specify whether or not the marshaled
     * JSON data is formatted with linefeeds and indentation.
     */
    public static final String TO_JSON_FORMATTING = "to.json.formatting";

    /**
     * The Jsonb marshalling {@code toJson()} methods will default to this property
     * for encoding of output JSON data. Default value is 'UTF-8'.
     */
    public static final String TO_JSON_ENCODING = "to.json.encoding";

    /**
     * The Jsonb unmarshalling {@code fromJson()} methods will default to this
     * property encoding of input JSON data if the encoding cannot be detected
     * automatically.
     */
    public static final String FROM_JSON_ENCODING = "from.json.encoding";

    public static JsonbConfigBuilder jsonbConfig(){
        return new JsonbConfigBuilder();
    }

    private final Map<String, Object> configuration;

    private JsonbConfig(Map<String, Object> configuration) {
        this.configuration = configuration;
    }

    /**
     * Return value of particular configuration property.
     *
     * @param name
     *      The name of the property to retrieve
     * @return The value of the requested property
     * @throws IllegalArgumentException if the name parameter is null.
     */
    public Object getProperty(String name) throws JsonbConfigException {
        return configuration.get(name);
    }

    public static class JsonbConfigBuilder{

        private final Map<String, Object> configuration;

        public JsonbConfigBuilder() {
            configuration = new HashMap<>();
            configuration.put(TO_JSON_FORMATTING, false);
        }

        public JsonbConfigBuilder withOutputEncoding(Charset encoding){
            configuration.put(TO_JSON_ENCODING, encoding);
            return this;
        }

        public JsonbConfigBuilder withInputEncoding(Charset encoding){
            configuration.put(FROM_JSON_ENCODING, encoding);
            return this;
        }

        public JsonbConfigBuilder withPrettyOutput(){
            configuration.put(TO_JSON_FORMATTING, true);
            return this;
        }

        public JsonbConfig build(){
            return new JsonbConfig(configuration);
        }
    }

}
