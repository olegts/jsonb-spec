package org.eclipse.persistence.json.bind;

import jug.kiev.jsonb.impl.JsonbBuilderMock;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.spi.JsonbProvider;

/**
 * @author Oleg Tsal-Tsalko
 */
public class JsonBindingProvider extends JsonbProvider{
    @Override
    public JsonbBuilder create() {
        return new JsonbBuilderMock();
    }
}
