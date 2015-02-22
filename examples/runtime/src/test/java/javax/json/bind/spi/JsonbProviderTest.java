package javax.json.bind.spi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

/**
 * @author Oleg Tsal-Tsalko
 */
@RunWith(MockitoJUnitRunner.class)
public class JsonbProviderTest {

    @Mock
    private JsonbBuilder builder;

    @Test
    public void lambdasFriendlyProvider() throws Exception {
        JsonbProvider provider;

        provider = () -> builder;
        provider = () -> Mockito.mock(JsonbBuilder.class);
        provider = CustomJsonbBuilder::new;
    }

    @Test
    public void lookupDefaultProvider() throws Exception {
        JsonbProvider provider = JsonbProvider.lookupProvider();
    }

    @Test
    public void lookupCustomProvider() throws Exception {
        JsonbProvider provider = JsonbProvider.lookupProvider("jug.kiev.jsonb.impl.CustomJsonbProvider");
    }

    class CustomJsonbBuilder implements JsonbBuilder{

        @Override
        public JsonbBuilder withConfig(JsonbConfig config) {
            return null;
        }

        @Override
        public Jsonb build() {
            return null;
        }
    }
}
