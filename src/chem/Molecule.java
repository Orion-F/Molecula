package chem;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Molecule {
    
    private static final double WIDTH = 0.1; //Bond cylinder width
    private static final double BL = 1; //Bond length
    
    private Shape shape;
    private Atom centralAtom;
    private Atom[] outerAtoms;
    
    public Molecule(String formula) {
        ArrayList<Atom> tempOuterAtoms = new ArrayList<>();

        int place = 2;
        if (formula.length() > 1) {
            centralAtom = AtomData.getAtomBySymbol(formula.substring(0, 2));
        }
        if (centralAtom == null) {
            centralAtom = AtomData.getAtomBySymbol(formula.substring(0, 1));
            place--;
        }
        while (place < formula.length()) {
            Atom curAtom = null;
            if (place < formula.length() - 1) {
                curAtom = AtomData.getAtomBySymbol(formula.substring(place, place + 2));
            }
            if (curAtom == null) {
                curAtom = AtomData.getAtomBySymbol(formula.substring(place, place + 1));
                place++;
            } else {
                place += 1;
            }
            tempOuterAtoms.add(curAtom);
        }
        outerAtoms = tempOuterAtoms.toArray(new Atom[tempOuterAtoms.size()]);
        shape = Shape.getShape(outerAtoms.length, (centralAtom.getValence() - outerAtoms.length)/2);
    }
    
    public Molecule(Atom centralAtom) {
        shape = Shape.A;
        this.centralAtom = centralAtom;
    }
    
    public Molecule(Shape shape, Atom centralAtom, Atom[] outerAtoms) {
        this.shape = shape;
        this.centralAtom = centralAtom;
        this.outerAtoms = outerAtoms;
    }
    
    public Molecule(Atom centralAtom, Atom[] outerAtoms, int lonePairs) {
        shape = Shape.getShape(outerAtoms.length, lonePairs);
        this.centralAtom = centralAtom;
        this.outerAtoms = outerAtoms;
    }
    
    public ArrayList<Node> get3D() {
    	ArrayList<Node> nodes = new ArrayList<Node>();
    	
    	Sphere central = new Sphere(centralAtom.getRadius());
    	central.setMaterial(new PhongMaterial(centralAtom.getAtomColorRepresentation()));
    	nodes.add(central);
    	
    	ArrayList<Atom> outer = new ArrayList<Atom>(Arrays.asList(outerAtoms));
    	double radius, trans;
    	Sphere atom;
    	Cylinder bond;
    	Color color;
    	if (shape != Shape.A && shape != Shape.AX4) {
    	    color = outer.get(0).getAtomColorRepresentation();
    	    radius = outer.remove(0).getRadius();
    	    trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateX(trans);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setRotate(90);
            bond.setTranslateX(trans/2);
            nodes.add(bond);
    	} if (shape == Shape.AX2 || shape == Shape.AX5 || shape == Shape.AX4E1 
    	        || shape == Shape.AX3E2 || shape == Shape.AX2E3 || shape == Shape.AX6 
    	        || shape == Shape.AX5E1 || shape == Shape.AX4E2) {
    	    color = outer.get(0).getAtomColorRepresentation();
    	    radius = outer.remove(0).getRadius();
            trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateX(-trans);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setRotate(90);
            bond.setTranslateX(-trans/2);
            nodes.add(bond);
    	} if (shape == Shape.AX5 || shape == Shape.AX3E2 || shape == Shape.AX6 || shape == Shape.AX5E1) {
    	    color = outer.get(0).getAtomColorRepresentation();
    	    radius = outer.remove(0).getRadius();
            trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateY(-trans);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setTranslateY(-trans/2);
            nodes.add(bond);
    	} if (shape == Shape.AX6) {
    	    color = outer.get(0).getAtomColorRepresentation();
    	    radius = outer.remove(0).getRadius();
            trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateY(trans);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setTranslateY(trans/2);
            nodes.add(bond);
    	} if (shape == Shape.AX6 || shape == Shape.AX5E1 || shape == Shape.AX4E2) {
    	    color = outer.get(0).getAtomColorRepresentation();
    	    radius = outer.remove(0).getRadius();
            trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateZ(trans);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setTranslateZ(trans/2);
            bond.setRotationAxis(Rotate.X_AXIS);
            bond.setRotate(90);
            nodes.add(bond);
            
            color = outer.get(0).getAtomColorRepresentation();
            radius = outer.remove(0).getRadius();
            trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateZ(-trans);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setTranslateZ(-trans/2);
            bond.setRotationAxis(Rotate.X_AXIS);
            bond.setRotate(90);
            nodes.add(bond);
    	} if (shape == Shape.AX5 || shape == Shape.AX4E1) {
    	    color = outer.get(0).getAtomColorRepresentation();
    	    radius = outer.remove(0).getRadius();
            trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateZ(trans);
            atom.setTranslateY(trans/2);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setTranslateZ(trans/2);
            bond.setTranslateY(trans/4);
            bond.setRotationAxis(Rotate.X_AXIS);
            bond.setRotate(60);
            nodes.add(bond);
            
            color = outer.get(0).getAtomColorRepresentation();
            radius = outer.remove(0).getRadius();
            trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateZ(-trans);
            atom.setTranslateY(trans/2);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setTranslateZ(-trans/2);
            bond.setTranslateY(trans/4);
            bond.setRotationAxis(Rotate.X_AXIS);
            bond.setRotate(-60);
            nodes.add(bond);
    	} if (shape == Shape.AX4) {
    	    //TODO: Update AX4 for the case where the outer atoms' radiuses are different from one another
    	    radius = outer.remove(0).getRadius();
    	    trans = centralAtom.getRadius() + BL + radius;
    	    double scale = trans/Math.sqrt(3);
    	    
    	    color = outer.get(0).getAtomColorRepresentation();
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.getTransforms().add(new Translate(scale, scale, scale));
            nodes.add(atom);
            
            color = outer.get(0).getAtomColorRepresentation();
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.getTransforms().add(new Translate(-scale, -scale, scale));
            nodes.add(atom);
            
            color = outer.get(0).getAtomColorRepresentation();
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.getTransforms().add(new Translate(-scale, scale, -scale));
            nodes.add(atom);
            
            color = outer.get(0).getAtomColorRepresentation();
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.getTransforms().add(new Translate(scale, -scale, -scale));
            nodes.add(atom);
    	} if (shape == Shape.AX3) {
    		color = outer.get(0).getAtomColorRepresentation();
    	    radius = outer.remove(0).getRadius();
            trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateX(-trans/2);
            atom.setTranslateY(trans);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setTranslateX(-trans/4);
            bond.setTranslateY(trans/2);
            bond.setRotate(30);
            nodes.add(bond);
            
            color = outer.get(0).getAtomColorRepresentation();
    	    radius = outer.remove(0).getRadius();
            trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateX(-trans/2);
            atom.setTranslateY(-trans);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setRotate(150);
            bond.setTranslateX(-trans/4);
            bond.setTranslateY(-trans/2);
            nodes.add(bond);
    	} if (shape == Shape.AX2E1) {
    		color = outer.get(0).getAtomColorRepresentation();
    	    radius = outer.remove(0).getRadius();
            trans = centralAtom.getRadius() + BL + radius;
            atom = new Sphere(radius);
            atom.setMaterial(new PhongMaterial(color));
            atom.setTranslateX(-trans/2);
            atom.setTranslateY(trans);
            nodes.add(atom);
            bond = new Cylinder(WIDTH, trans);
            bond.setTranslateX(-trans/4);
            bond.setTranslateY(trans/2);
            bond.setRotate(30);
            nodes.add(bond);
    	}
        return nodes;
    }
    
    public Shape getShape() {
        return shape;
    }

    public Atom getCentralAtom() {
        return centralAtom;
    }

    public Atom[] getOuterAtoms() {
        return outerAtoms;
    }
    
    public String getFormula() {
    	String formula = centralAtom.getSymbol();
    	
    	int numOuter = outerAtoms.length;
    	if (numOuter > 0) {
//    		if (numOuter > 1) {
//    			ArrayList<String> symbols = new ArrayList<String>();
//    	    	ArrayList<Integer> symbolsCount = new ArrayList<Integer>();
//    	    	
//    	    	symbols.add(outerAtoms[0].getSymbol());
//    	    	symbolsCount.add(1);
//    	    	
//    	    	for (int i = 0; i < numOuter; i++) {
//    				for (int j = 0; j < symbols.size(); j++) {
//						
//					}
//    			}
//        	} else {
//        		formula += outerAtoms[0].getSymbol();
//        	}
    		for (int i = 0; i < numOuter; i++) {
    			formula += outerAtoms[i].getSymbol();
			}
    	}
    	
    	return formula;
    }
}
