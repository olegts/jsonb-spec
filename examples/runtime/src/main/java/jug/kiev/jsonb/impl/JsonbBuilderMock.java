package jug.kiev.jsonb.impl;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import static org.mockito.Mockito.mock;

/**
 * @author Oleg Tsal-Tsalko
 */
public class JsonbBuilderMock implements JsonbBuilder{

    @Override
    public JsonbBuilder withConfig(JsonbConfig config) {
        return this;
    }

    @Override
    public Jsonb build() {
        return mock(Jsonb.class);
    }
}
