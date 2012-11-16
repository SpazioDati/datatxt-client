package com.spaziodati.datatxt;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

/**
 * @author Michele Mostarda (mostarda@spaziodati.eu)
 */
public class Annotation {

    public static final String WIKIPEDIA_REF = "wikipedia";

    private int id;
    private String title;
    private int start;
    private int end;
    private String spot;
    private Map<String,URL> refs;

    public Annotation(int id, String title, int start, int end, String spot, Map<String,URL> refs) {
        if(title == null) throw new NullPointerException();
        if(spot  == null) throw new NullPointerException();
        if(refs  == null) throw new NullPointerException();

        this.id    = id;
        this.title = title;
        this.start = start;
        this.end   = end;
        this.spot  = spot;
        this.refs  = Collections.unmodifiableMap(refs);
    }

    public Entity getEntity() throws IOException {
        final URL wikipediaEntity = refs.get(WIKIPEDIA_REF);
        return EntityFetcher.fetchEntity(wikipediaEntity);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getSpot() {
        return spot;
    }

    public Map<String,URL> getRefs() {
        return refs;
    }

}
