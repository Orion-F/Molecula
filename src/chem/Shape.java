package chem;

public enum Shape {
    
//     The bonds, lonePairs, and hydridization variables could have been derived
//     from the AXE notation, but instead the values have been placed manually
//     for simplicity purposes.
//     Atoms and diatomics are included here for simplicity
    
    A(0, 0, "Atom", "N/A", "N/A"),
    AX(1, 0, "Diatomic", "N/A", "N/A"),
    AX2(2, 0, "Linear", "sp", "180"),
    AX3(3, 0, "Trigonal Planar", "sp2", "120"),
    AX2E1(2, 1, "Bent", "sp2", "<120"),
    AX4(4, 0, "Tetrahedral", "sp3", "109.5"),
    AX3E1(3, 1, "Trigonal Pyramidal", "sp3", "90 to <109.5"),
    AX2E2(2, 2, "Bent", "sp3", "90 to <109.5"),
    AX5(5, 0, "Trigonal Bipyramidal", "sp3d", "90, 120, 180"),
    AX4E1(4, 1, "See-saw", "sp3d", "90, <120, 180"),
    AX3E2(3, 2, "T-shaped", "sp3d", "90, 180"),
    AX2E3(2, 3, "Linear", "sp3d", "180"),
    AX6(6, 0, "Octahedral", "sp3d2", "90, 180"),
    AX5E1(5, 1, "Square Pyramidal", "sp3d2", "90, 180"),
    AX4E2(4, 2, "Square Planar", "sp3d2", "90, 180");
    
    private int bonds;
    private int lonePairs;
    private String geometry;
    private String hybridization;
    private String bondAngles;
    
    private Shape(int bonds, int lonePairs, String geometry, String hybridization, String bondAngles) {
        this.bonds = bonds;
        this.lonePairs = lonePairs;
        this.geometry = geometry;
        this.hybridization = hybridization;
        this.bondAngles = bondAngles;
    }
    
    public int getBonds() {
        return bonds;
    }

    public int getLonePairs() {
        return lonePairs;
    }

    public String getGeometry() {
        return geometry;
    }

    public String getHybridization() {
        return hybridization;
    }

    public String getBondAngles() {
        return bondAngles;
    }
    
    public static Shape getShape(int bonds, int lonePairs) {
        Shape[] shapes = Shape.values();
        for (int i = 0; i < shapes.length; i++) {
            if (shapes[i].getBonds() == bonds && shapes[i].getLonePairs() == lonePairs) {
                return shapes[i];
            }
        }
        return null;
    }
}
