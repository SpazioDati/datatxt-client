package com.spaziodati.datatxt;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Michele Mostarda (mostarda@fbk.eu)
 */
public class Entity {

    private final URL entityURL;
    private final Map<String,List<String>> propertyValues;

    public Entity(URL entityURL, Map<String, List<String>> propertyValues) {
        this.entityURL      = entityURL;
        this.propertyValues = propertyValues;
    }

    public URL getEntityURL() {
        return entityURL;
    }

    public int getPropertiesCount() {
        return propertyValues.size();
    }

    public boolean hasProperty(String property) {
        return propertyValues.containsKey(property);
    }

    public String[] getProperties() {
        final Set<String> properties = propertyValues.keySet();
        return properties.toArray( new String[properties.size()] );
    }

    public String[] getValues(String property) {
        final List<String> values = propertyValues.get(property);
        if(values == null) throw new IllegalArgumentException("Cannot find property " + property);
        return values.toArray( new String[values.size()] );
    }

}
