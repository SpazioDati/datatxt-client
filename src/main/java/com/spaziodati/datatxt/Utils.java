package com.spaziodati.datatxt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Michele Mostarda (mostarda@fbk.eu)
 */
public class Utils {

    private Utils() {}

    public static String readContent(InputStream is) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        final StringBuilder out = new StringBuilder();
        String line;
        try {
            while (true) {
                line = reader.readLine();
                if (line == null) break;
                out.append(line);
            }
        } finally {
            reader.close();
        }
        return out.toString();
    }

}
