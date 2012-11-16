package com.spaziodati.datatxt;

import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Michele Mostarda (mostarda@spaziodati.eu)
 */
public class DataTXTClientTest {

    @Test
    public void testAnnotate() throws IOException, DataTXTClientException {
        final DataTXTClient dataTXTClient = new DataTXTClient();
        final Annotation[] annotations = dataTXTClient.annotate("Il calcio e' uno sport molto sopravvalutato.");

        Assert.assertEquals(2, annotations.length);
        final Annotation soccerAnnotation = annotations[0];
        Assert.assertEquals(3       , soccerAnnotation.getStart());
        Assert.assertEquals(9       , soccerAnnotation.getEnd());
        Assert.assertEquals("calcio", soccerAnnotation.getSpot());
        final Entity soccerEntity = soccerAnnotation.getEntity();
        Assert.assertTrue( soccerEntity.hasProperty("http://purl.org/dc/terms/subject") );
        Assert.assertEquals(
                "http://it.dbpedia.org/resource/Categoria:Calcio",
                soccerEntity.getValues("http://purl.org/dc/terms/subject")[0]
        );
    }

    @Test(expected = DataTXTClientException.class)
    public void testAnnotationError() throws IOException, DataTXTClientException {
        final DataTXTClient dataTXTClient = new DataTXTClient(
                DataTXTClient.DEFAULT_API_URL,
                "xxx",
                DataTXTClient.DEFAULT_APP_ID,
                DataTXTClient.DEFAULT_LANG,
                DataTXTClient.DEFAULT_RHO,
                DataTXTClient.DEFAULT_EPSILON
        );
        dataTXTClient.annotate("Il calcio e' uno sport molto sopravvalutato.");
    }

}
