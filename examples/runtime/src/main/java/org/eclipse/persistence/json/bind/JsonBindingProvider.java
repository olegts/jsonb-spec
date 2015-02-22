package org.eclipse.persistence.json.bind;

import jug.kiev.jsonb.impl.JsonbBuilderMock;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.spi.JsonbProvider;

/**
 * @author Oleg Tsal-Tsalko
 */
public class JsonBindingProvider implements JsonbProvider{
    @Override
    public JsonbBuilder newBuilder() {
        return new JsonbBuilderMock();
    }
}
