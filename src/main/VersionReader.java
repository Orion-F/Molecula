package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VersionReader {
    
    private static String version = "???";
    private static boolean foundVersion = false;
    
    private VersionReader() {}

    private static void findVersion() {
        Class<VersionReader> anchor = VersionReader.class;
        InputStream versionStream = anchor.getResourceAsStream("/" + anchor.getPackage().getName() + "/VERSION.txt");
        if (versionStream != null) {
            BufferedReader versionReader = new BufferedReader(new InputStreamReader(versionStream));
            try {
                version = versionReader.readLine();
                foundVersion = true;
            } catch (Exception e) {
                version = "ERROR";
            }
        }
    }

    public static String get() {
        if (!foundVersion) {
            findVersion();
        }
        return version;
    }
}
