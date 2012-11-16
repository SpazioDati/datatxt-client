package com.spaziodati.datatxt;

import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

/**
 * @author Michele Mostarda (mostarda@fbk.eu)
 */
public class EntityFetcherTest {

    @Test
    public void testFetchEntity() throws IOException {
        final Entity entity = EntityFetcher.fetchEntity( new URL("http://it.wikipedia.org/wiki/La_grande_guerra") );
        Assert.assertTrue(entity.getPropertiesCount() > 50);
    }

}
