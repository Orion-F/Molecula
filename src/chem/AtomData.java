package chem;

import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AtomData {
    
    private static final int NUMBER = 109;

    private static Atom[] data = null;
    private static Color[] colorData = null;
    
    private AtomData() {}
    
    public static Atom getAtom(int number) {
        return data[number - 1];
    }
    
    public static Atom getAtomBySymbol(String symbol) {
        for (int i = 0; i < data.length; i++) {
            if (data[i].getSymbol().equalsIgnoreCase(symbol)) {
                return data[i];
            }
        }
        return null;
    }
    
    public static Atom getAtomByName(String name) {
        for (int i = 0; i < data.length; i++) {
            if (data[i].getName().equalsIgnoreCase(name)) {
                return data[i];
            }
        }
        return null;
    }
    
    public static Color getColor(int number) {
        return colorData[number - 1];
    }
    
    public static void initData() {
        if (data == null) {
            data = new Atom[NUMBER];
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        AtomData.class.getResourceAsStream("/chem/atoms.txt")));
                for (int i = 0; i < NUMBER; i++) {
                    String[] dataLine = reader.readLine().split(" ");
                    data[i] = new Atom(Integer.parseInt(dataLine[0]),
                            dataLine[1], dataLine[2], 
                            Double.parseDouble(dataLine[3]), 
                            Double.parseDouble(dataLine[4]));
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (colorData == null) {
            colorData = new Color[NUMBER];
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        AtomData.class.getResourceAsStream("/chem/atomColor.txt")));
                for (int i = 0; i < NUMBER; i++) {
                    String[] dataLine = reader.readLine().split(" ");
                    String rgbRaw = dataLine[2];
                    rgbRaw = rgbRaw.replaceAll("\\[", "");
                    rgbRaw = rgbRaw.replaceAll("\\]", "");
                    String[] rgbDataRaw = rgbRaw.split(",");
                    int[] rgbData = new int[3];
                    for (int j = 0; j < rgbDataRaw.length; j++) {
                        rgbData[j] = Integer.parseInt(rgbDataRaw[j]);
                    }
                    colorData[i] = Color.rgb(rgbData[0], rgbData[1], rgbData[2]);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
