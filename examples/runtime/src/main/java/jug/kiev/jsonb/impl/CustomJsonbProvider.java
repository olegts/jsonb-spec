package jug.kiev.jsonb.impl;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.spi.JsonbProvider;

/**
 * @author Oleg Tsal-Tsalko
 */
public class CustomJsonbProvider implements JsonbProvider{
    @Override
    public JsonbBuilder newBuilder() {
        return new JsonbBuilderMock();
    }
}
