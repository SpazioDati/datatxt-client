package com.spaziodati.datatxt;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michele Mostarda (mostarda@fbk.eu)
 */
public class EntityFetcher {

    private static final String SPARQL_ENDPOINT = "http://it.dbpedia.org/sparql";
    private static final String SPARQL_QRY      = "SELECT DISTINCT * WHERE { <%s> ?p ?o }";

    public static Entity fetchEntity(URL entityURL) throws IOException {
        final String entity = entityURL.toExternalForm().replace("wikipedia.org/wiki", "dbpedia.org/resource");
        final String query = String.format("%s?format=%s&query=%s",
                SPARQL_ENDPOINT,
                URLEncoder.encode("application/sparql-results+json", "utf-8"),
                URLEncoder.encode(String.format(SPARQL_QRY, entity), "utf-8")
        );
        final URL queryURL = new URL(query);
        final String jsonContent = Utils.readContent( queryURL.openStream() );
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode node = mapper.readTree(jsonContent);
        return new Entity(entityURL, extractBindings(node.get("results").get("bindings")));
    }

    private static Map<String,List<String>> extractBindings(JsonNode bindings) {
        final Map<String,List<String>> map = new HashMap<String, List<String>>();
        String key, val;
        for(JsonNode binding : bindings) {
            key = binding.get("p").get("value").asText();
            val = binding.get("o").get("value").asText();
            List<String> values = map.get(key);
            if(values == null) {
                values = new ArrayList<String>();
                map.put(key, values);
            }
            values.add(val);
        }
        return map;
    }

}
