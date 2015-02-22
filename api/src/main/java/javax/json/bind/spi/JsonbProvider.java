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

package javax.json.bind.spi;

import java.util.Iterator;
import java.util.ServiceLoader;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;

/**
 * Service lookupProvider for JSON Binding implementations.
 *
 * Provider implementors must implement all abstract methods.
 *
 * API clients can obtain instance of default lookupProvider by calling:
 * <pre>
 * {@code
 JsonbProvider lookupProvider = JsonbProvider.lookupProvider();
 }}</pre>
 *
 * Specific lookupProvider instance lookup:
 * <pre>
 * {@code
 * JsonbProvider lookupProvider;
 * try {
 *   JsonbProvider.lookupProvider("foo.bar.ProviderImpl");
 * } catch (JsonbException e) {
 *   // lookupProvider not found or could not be instantiated
 * }}</pre>
 * where '{@code foo.bar.ProviderImpl}' is a vendor implementation class extending
 * {@link javax.json.bind.spi.JsonbProvider} and identified to service loader as
 * specified in {@link java.util.ServiceLoader} documentation.
 * <br>
 * All the methods in this class are allowed to be called by multiple concurrent
 * threads.
 *
 * @author Martin Grebac
 * @see javax.json.bind.Jsonb
 * @see java.util.ServiceLoader
 * @since JSON Binding 1.0
 */
@FunctionalInterface
public interface JsonbProvider {

    /**
     * A constant representing the name of the default
     * {@link javax.json.bind.spi.JsonbProvider JsonbProvider} implementation class.
     */
    static final String DEFAULT_PROVIDER = "org.eclipse.persistence.json.bind.JsonBindingProvider";

    /**
     * Creates a JSON Binding lookupProvider object by using the
     * {@link java.util.ServiceLoader#load(Class)} method. The first lookupProvider of
     * {@code JsonbProvider} class from list of providers returned by
     * {@code ServiceLoader.load} call is returned. If there are no available
     * service providers, this method tries to load the default service lookupProvider using
     * {@link Class#forName(String)} method.
     *
     * @see java.util.ServiceLoader
     *
     * @throws JsonbException if there is no lookupProvider found, or there is a problem
     *         instantiating the lookupProvider instance.
     *
     * @return {@code JsonbProvider} instance
     */
    @SuppressWarnings("UseSpecificCatch")
    public static JsonbProvider lookupProvider() {
        ServiceLoader<JsonbProvider> loader = ServiceLoader.load(JsonbProvider.class);
        Iterator<JsonbProvider> it = loader.iterator();
        if (it.hasNext()) {
            return it.next();
        }

        return lookupProvider(DEFAULT_PROVIDER);
    }

    /**
     * Creates a JSON Binding lookupProvider object by using the
     * {@link java.util.ServiceLoader#load(Class)} method, matching {@code providerName}.
     * The first lookupProvider of {@code JsonbProvider} class from list of providers returned by
     * {@code ServiceLoader.load} call, matching providerName is returned.
     * If no such lookupProvider is found, JsonbException is thrown.
     *
     * @param providerName
     *      Class name ({@code class.getName()}) to be chosen from the list of providers
     *      returned by {@code ServiceLoader.load(JsonbProvider.class)} call.
     *
     * @throws JsonbException if there is no lookupProvider found, or there is a problem
     *         instantiating the lookupProvider instance.
     *
     * @throws IllegalArgumentException if providerName is null.
     *
     * @see java.util.ServiceLoader
     *
     * @return {@code JsonbProvider} instance
     */
    @SuppressWarnings("UseSpecificCatch")
    public static JsonbProvider lookupProvider(final String providerName) {
        if (providerName == null) {
            throw new IllegalArgumentException();
        }

        try {
            Class<?> clazz = Class.forName(providerName);
            return (JsonbProvider) clazz.newInstance();
        } catch (ClassNotFoundException x) {
            throw new JsonbException("JSON Binding lookupProvider " + DEFAULT_PROVIDER + " not found", x);
        } catch (Exception x) {
            throw new JsonbException("JSON Binding lookupProvider " + DEFAULT_PROVIDER
                    + " could not be instantiated: " + x, x);
        }
    }

    /**
     * Returns a new instance of {@link javax.json.bind.JsonbBuilder JsonbBuilder} class.
     *
     * {@link javax.json.bind.JsonbBuilder JsonbBuilder} provides necessary getter
     * methods to access required parameters.
     *
     * @return JsonbBuilder
     *      A new instance of class implementing {@link javax.json.bind.JsonbBuilder}.
     *      Always a non-null valid object.
     *
     * @see javax.json.bind.Jsonb
     * @see javax.json.bind.JsonbBuilder
     *
     * @throws JsonbException
     *      If an error was encountered while creating the {@link JsonbBuilder} instance.
     */
    JsonbBuilder newBuilder();

}
