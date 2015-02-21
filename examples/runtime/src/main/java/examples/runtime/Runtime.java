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

package examples.runtime;

import examples.model.Author;
import examples.model.Book;
import examples.model.Language;
import jug.kiev.jsonb.impl.CustomJsonbProvider;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbConfig;

import static java.nio.charset.StandardCharsets.UTF_16;
import static javax.json.bind.JsonbBuilder.*;
import static javax.json.bind.JsonbConfig.jsonbConfig;

/**
 *
 * @author Martin Grebac
 */
public class Runtime {

    public static void main(String[] args) {

        Book book = new Book();
        book.id = 101L;
        book.lang = Language.CZECH;

        book.author = new Author();
        book.author.firstName = "Jara";
        book.author.lastName = "Cimrman";

/**
 * Shortcut use
 */
{
        Jsonb jsonb = newJsonb();

        /**
         * Write an object content tree using default JSON mapping
            {
              "id" : 101,
              "author" : {
                "firstName" : "Jara",
                "lastName" : "Cimrman"
              },
              "lang" : "CZECH"
            }
        */
        String json = jsonb.toJson(book);

        /**
         * Read JSON document (from above) into an object content tree using default mapping
         */
        Book b = jsonb.fromJson(json, Book.class);
}

/**
 * Default, reuse
 */
{
        Jsonb jsonb = newJsonb();
        String json = jsonb.toJson(book);
        Book b = jsonb.fromJson(json, Book.class);
}

/**
 * Custom providers
 */
{
        Jsonb jsonb;
        // Lookup different provider by provider class name
        jsonb = jsonBuilderFrom("jug.kiev.jsonb.impl.CustomJsonbProvider").build();

        // Use an explicit implementation of JsonbProvider
        jsonb = jsonBuilderFrom(new CustomJsonbProvider()).build();
}

/**
 * Configuration
 */
    /*
        JsonConfig is thread safe and immutable
        More builder like with more fluent naming
        Uses Charset class for encoding instead of simple String
     */
    JsonbConfig config = jsonbConfig().withInputEncoding(UTF_16).withPrettyOutput().build();
{
    // Default configuration
    Jsonb jsonb = newJsonbWith(config);
    String json = jsonb.toJson(book);
}
{
    // Default configuration with specific builder
    Jsonb jsonb = jsonBuilderFrom("jug.kiev.jsonb.impl.CustomJsonbProvider")
                    .withConfig(config)
                    .build();
    jsonb.toJson(book);
}

    }
}
