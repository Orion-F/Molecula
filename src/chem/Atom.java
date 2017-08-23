package chem;

import javafx.scene.paint.Color;

public class Atom {
    
    private int number;
    private String symbol;
    private String name;
    private double mass;
    private double radius;
    private int valence;
    
    public Atom(int number) {
        Atom atom = AtomData.getAtom(number);
        this.number = atom.number;
        this.symbol = atom.symbol;
        this.name = atom.name;
        this.mass = atom.mass;
        this.radius = atom.radius;
    }
    
    public Atom(String symbolOrName) {
        Atom atom;
        if (symbolOrName.length() < 3) {
            atom = AtomData.getAtomBySymbol(symbolOrName);
        } else {
            atom = AtomData.getAtomByName(symbolOrName);
        }
        this.number = atom.number;
        this.symbol = atom.symbol;
        this.name = atom.name;
        this.mass = atom.mass;
        this.radius = atom.radius;
    }

    public Atom(int number, String symbol, String name, double mass,
         double radius, int valence) {
        this.number = number;
        this.symbol = symbol;
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.valence = valence;
    }
    
//    public int getNumValence() {
//        if (number == 1) return 1;
//        if (number == 2) return 8;
//        if (number > 2 && number < 21) {
//            int num = ((number - 2) % 8);
//            if (num == 0) num = 8;
//            return num;
//        } if (number > 30 && number < 39) {
//            int num = ((number - 28) % 8);
//            if (num == 0) num = 8;
//            return num;
//        } if (number > 48 && number < 57) {
//            int num = ((number - 46) % 8);
//            if (num == 0) num = 8;
//            return num;
//        } if (number > 80 && number < 89) {
//            int num = ((number - 78) % 8);
//            if (num == 0) num = 8;
//            return num;
//        }
//        return -1;
//    }

    public Color getAtomColorRepresentation() {
        return AtomData.getColorRepresentation(number);
    }

    public int getNumber() {
        return number;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public int getValence() {return valence; }
}