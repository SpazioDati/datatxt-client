package com.spaziodati.datatxt;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Michele Mostarda (mostarda@spaziodati.eu)
 */
public class DataTXTClient {

    public static final String DEFAULT_API_URL   = "http://spaziodati.eu/datatxt/v3/";
    public static final String DEFAULT_APP_KEY   = "2e57de2ce47dcc1e82613b16017efbf2";
    public static final String DEFAULT_APP_ID    = "1191439d";
    public static final String DEFAULT_LANG      = "it";
    public static final double DEFAULT_RHO       = 0.1;
    public static final double DEFAULT_EPSILON   = 0.3;

    public static final int    LONG_TEXT = 0;

    private final String apiURL;
    private final String appKey;
    private final String appId;
    private final String lang;
    private final double rho;
    private final double epsilon;


    private static String toParamsString(Map<String,String> params) throws UnsupportedEncodingException {
        final StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String> param : params.entrySet()) {
            sb.append(param.getKey()).append('=').append(URLEncoder.encode(param.getValue(), "utf-8"));
            sb.append('&');
        }
        return sb.toString();
    }

    private static Map<String,URL> parseReferences(JsonNode refs) throws MalformedURLException {
        final Map<String, URL> result = new HashMap<String, URL>();
        Map.Entry<String, JsonNode> entry;
        for (JsonNode ref : refs) {
            final Iterator<Map.Entry<String, JsonNode>> fieldIterator = ref.getFields();
            while (fieldIterator.hasNext()) {
                entry = fieldIterator.next();
                result.put(entry.getKey(), new URL(entry.getValue().asText()));
            }
        }
        return result;
    }

    private static Annotation[] parseAnnotations(JsonNode response) throws MalformedURLException {
        final List<Annotation> annotations = new ArrayList<Annotation>();
        for(JsonNode annotation : response.get("annotations")) {
            annotations.add( new Annotation(
                    annotation.get("id").asInt(),
                    annotation.get("title").asText(),
                    annotation.get("start").asInt(),
                    annotation.get("end").asInt(),
                    annotation.get("spot").asText(),
                    parseReferences( annotation.get("ref") )

            ) );
        }
        return annotations.toArray( new Annotation[annotations.size()] );
    }

    public DataTXTClient(String apiURL, String appKey, String appId, String lang, double rho, double epsilon) {
        this.apiURL  = apiURL;
        this.appKey  = appKey;
        this.appId   = appId;
        this.lang    = lang;
        this.rho     = rho;
        this.epsilon = epsilon;
    }

    public DataTXTClient() {
        this(DEFAULT_API_URL, DEFAULT_APP_KEY, DEFAULT_APP_ID, DEFAULT_LANG, DEFAULT_RHO, DEFAULT_EPSILON);
    }

    public Annotation[] annotate(String text) throws IOException, DataTXTClientException {
        Map<String,String> params = new HashMap<String, String>();
        params.put("app_key"  , appKey);
        params.put("app_id"   , appId);
        params.put("lang"     , lang);
        params.put("rho"      , Double.toString(rho));
        params.put("epsilon"  , Double.toString(epsilon));
        params.put("long_text", Integer.toString(LONG_TEXT));
        params.put("text"     , text);

        final String request = String.format("%s?%s", apiURL, toParamsString(params));
        final URL requestURL = new URL(request);
        final URLConnection connection = requestURL.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        final String response = Utils.readContent(connection.getInputStream());

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode node = mapper.readTree(response);
        manageError(node);
        return parseAnnotations(node);
    }

    private void manageError(JsonNode node) throws DataTXTClientException {
        final int status = node.get("status").asInt();
        if(status != 200) {
            throw new DataTXTClientException(status, node.get("error").asText());
        }
    }

}
