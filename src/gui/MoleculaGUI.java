package gui;

import chem.Atom;
import chem.Molecule;
import chem.Shape;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.VersionReader;

import java.util.ArrayList;
import java.util.Optional;

@SuppressWarnings("FieldCanBeLocal")
public class MoleculaGUI extends Stage {

    private final int WIDTH = 1000;
    private final int HEIGHT = 600;

    private final int DISPLAY_SUB_PANE_WIDTH = 700;
    private final int DISPLAY_SUB_PANE_HEIGHT = 700;
    private final int INFO_PANE_WIDTH = 300;

    private final double ZOOM_SPEED = 0.01;

    private Scene baseScene;
    private BorderPane basePane;
    private TabPane infoPane;
    private MenuBar menuBar;
    private Menu menuFile, menuMolecule;
    private MenuItem menuItemFormula, menuItemManual, menuItemChangeMolecule;
    private Group displayGroup, rootGroup;
    private SubScene subScene;
    private PerspectiveCamera camera;

    private double cameraZoom = -20;

    private ArrayList<Molecule> molecules;
    private int curMoleculeNum;

    private boolean moleculeDrawn;

    public MoleculaGUI() {
        this.setTitle("Molecula V" + VersionReader.get());
        this.setResizable(false);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);

        this.setOnCloseRequest(this::closeConfirmation);

        molecules = new ArrayList<>();

        basePane = new BorderPane();

        baseScene = new Scene(basePane);

        menuBar = new MenuBar();
        menuFile = new Menu("File");
        menuMolecule = new Menu("Molecule");
        menuItemFormula = new MenuItem("Add Molecule Using Formula");
        menuItemFormula.setOnAction(event -> addMoleculeByFormula());
        menuItemManual = new MenuItem("Add Molecule Manually");
        menuItemManual.setOnAction(event -> addMoleculeManually());
        menuItemChangeMolecule = new MenuItem("Change Molecule");
        menuItemChangeMolecule.setOnAction(event -> changeMolecule());
        menuMolecule.getItems().addAll(menuItemFormula, menuItemManual, menuItemChangeMolecule);

        menuBar.getMenus().addAll(menuFile, menuMolecule);

        Molecule molecule = new Molecule(new Atom("S"), new Atom[]{new Atom("F"),
                new Atom("F"), new Atom("F"), new Atom("F"),
                new Atom("F"), new Atom("F")}, 0);
        molecules.add(molecule);
        molecule = new Molecule(new Atom(5), new Atom[]{new Atom(9), new Atom(9),
                new Atom(9)}, 0);
        molecules.add(molecule);
        molecule = new Molecule(new Atom("O"), new Atom[]{new Atom("H"),
                new Atom("H")}, 1);
        molecules.add(molecule);
        molecule = new Molecule(new Atom("C"), new Atom[]{new Atom("O")}, 0);
        molecules.add(molecule);
        curMoleculeNum = 0;

        camera = new PerspectiveCamera(true);
        camera.setTranslateZ(cameraZoom);

        rootGroup = new Group();
//        rootGroup.getChildren().addAll(molecule.get3D());

        subScene = new SubScene(rootGroup, DISPLAY_SUB_PANE_WIDTH,
                DISPLAY_SUB_PANE_HEIGHT, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setOnScroll(event -> {
            cameraZoom += event.getDeltaY() * ZOOM_SPEED;
            camera.setTranslateZ(cameraZoom);
        });

        displayGroup = new Group(subScene);

        infoPane = new TabPane();
        infoPane.setPrefWidth(INFO_PANE_WIDTH);
        infoPane.setMinWidth(INFO_PANE_WIDTH);
        infoPane.setMinWidth(INFO_PANE_WIDTH);
        infoPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        basePane.setTop(menuBar);
//        basePane.setCenter(displayPane);
        basePane.setCenter(displayGroup);
        basePane.setRight(infoPane);

        this.setScene(baseScene);

        this.show();
    }

    private void addMoleculeByFormula() {
        String formula;

        TextInputDialog centralDialog = new TextInputDialog();
        centralDialog.setTitle("Add Molecule");
        centralDialog.setHeaderText("Molecular Formula");
        centralDialog.setContentText("Please enter the molecular formula of the molecule:");

        Optional<String> result = centralDialog.showAndWait();
        formula = result.orElse("");

        if (!formula.equals("")) {
            molecules.add(new Molecule(formula));
        } else {
            showInvalidMoleculeAlert();
        }
    }

    private void addMoleculeManually() {
//    	ArrayList<String> choices = new ArrayList<>();
//    	Shape[] shapes = Shape.values();
//    	for (int i = 0; i < shapes.length; i++) {
//			choices.add(shapes[i].name() + " " + shapes[i].getGeometry());
//		}
//    	ChoiceDialog<String> dialog = new ChoiceDialog<>(Shape.A.name(), choices);
//    	dialog.setTitle("Add Molecule");
//    	dialog.setHeaderText("Change Molecule");
//    	dialog.setContentText("Please select AXE/geometry for molecule:");
//
//    	Optional<String> result = dialog.showAndWait();
//    	if (result.isPresent()){
//    		if (moleculeDrawn) {
//    			rootGroup.getChildren().remove(molecules.get(curMoleculeNum));
//    		}
//    		rootGroup.getChildren().addAll(molecules.get(choices.indexOf(result.get())).get3D());
//     	}

        boolean valid = true;

        Atom central = null;
        Atom[] outers = null;
        int lonePairs = 0;

        TextInputDialog centralDialog = new TextInputDialog();
        centralDialog.setTitle("Add Molecule");
        centralDialog.setHeaderText("Central Atom");
        centralDialog.setContentText("Please enter the symbol or name of the central atom:");

        Optional<String> result1 = centralDialog.showAndWait();
        if (result1.isPresent()) {
            central = new Atom(result1.get());
        } else {
            valid = false;
        }

        TextInputDialog outerDialog = new TextInputDialog();
        outerDialog.setTitle("Add Molecule");
        outerDialog.setHeaderText("Outer Atoms");
        outerDialog.setContentText("Please enter the symbol or name of the outer atoms, separated by commas:");

        Optional<String> result2 = outerDialog.showAndWait();
        if (result2.isPresent()) {
            String[] outerStrings = result2.get().split(",");
            outers = new Atom[outerStrings.length];
            for (int i = 0; i < outerStrings.length; i++) {
                outers[i] = new Atom(outerStrings[i]);
            }
        } else {
            valid = false;
        }

        TextInputDialog pairsDialog = new TextInputDialog();
        pairsDialog.setTitle("Add Molecule");
        pairsDialog.setHeaderText("Lone Pairs");
        pairsDialog.setContentText("Please enter the number of lone pairs on the central atom:");

        Optional<String> result3 = pairsDialog.showAndWait();
        if (result3.isPresent()) {
            lonePairs = Integer.parseInt(result3.get());
        } else {
            valid = false;
        }

        if (valid) {
            molecules.add(new Molecule(central, outers, lonePairs));
        } else {
            showInvalidMoleculeAlert();
        }


    }

    private void changeMolecule() {
        if (molecules.size() == 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Molecules to Change To");
            alert.setContentText("Please add a molecule by using the Add Molecule menu");

            alert.showAndWait();
        } else {
            ArrayList<String> choices = new ArrayList<>();
            for (Molecule molecule : molecules) {
                choices.add(molecule.getFormula());
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle("Change Molecule");
            dialog.setHeaderText("Change Molecule");
            dialog.setContentText("Choose molecule to change to:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                if (moleculeDrawn) {
                    rootGroup.getChildren().clear();
                }
                curMoleculeNum = choices.indexOf(result.get());
                rootGroup.getChildren().addAll(molecules.get(curMoleculeNum).get3D());
                moleculeDrawn = true;
                refreshInfo();
            }
        }
    }

    private void refreshInfo() {
        infoPane.getTabs().clear();

        Molecule molecule = molecules.get(curMoleculeNum);
        Shape shape = molecule.getShape();
        Tab moleculeTab = new Tab();
        moleculeTab.setText("Molecule");
        VBox moleculeVBox = new VBox();
        moleculeVBox.setPadding(new Insets(10));
        moleculeVBox.setSpacing(8);
        moleculeVBox.getChildren().addAll(new Label("Formula: " + molecule.getFormula()),
                new Label("AXE Notation: " + shape.name()),
                new Label("Geometry: " + shape.getGeometry()),
                new Label("Hybridization: " + shape.getHybridization()),
                new Label("# Bonds: " + shape.getBonds()),
                new Label("# Lone Pairs: " + shape.getLonePairs()),
                new Label("Bond Angles: " + shape.getBondAngles())
        );

        moleculeTab.setContent(moleculeVBox);

        Atom cen = molecules.get(curMoleculeNum).getCentralAtom();
        Tab atomTab = new Tab();
        atomTab.setText(cen.getSymbol() + " (Central)");
        VBox cenBox = new VBox();
        cenBox.setPadding(new Insets(10));
        cenBox.setSpacing(8);
        cenBox.getChildren().addAll(new Label("Name: " + cen.getName()),
                new Label("Mass: " + cen.getMass()),
                new Label("Radius: " + cen.getRadius()),
                new Label("# Valence: " + cen.getValence())
        );
        atomTab.setContent(cenBox);

        Atom[] outers = molecules.get(curMoleculeNum).getOuterAtoms();
        Tab[] atomTabs = new Tab[outers.length];
        for (int i = 0; i < outers.length; i++) {
            Atom atom = outers[i];
            atomTabs[i] = new Tab();
            atomTabs[i].setText(atom.getSymbol());
            VBox atomBox = new VBox();
            atomBox.setPadding(new Insets(10));
            atomBox.setSpacing(8);
            atomBox.getChildren().addAll(new Label("Name: " + atom.getName()),
                    new Label("Mass: " + atom.getMass()),
                    new Label("Radius: " + atom.getRadius()),
                    new Label("# Valence: " + atom.getValence())
            );
            atomTabs[i].setContent(atomBox);
        }

        Tab[] tabs = new Tab[2 + outers.length];
        tabs[0] = moleculeTab;
        tabs[1] = atomTab;
        System.arraycopy(atomTabs, 0, tabs, 2, atomTabs.length);

        infoPane.getTabs().addAll(tabs);
    }

    private void closeConfirmation(WindowEvent event) {
        Alert closeAlert = new Alert(AlertType.CONFIRMATION);
        closeAlert.setTitle("Confirm Close");
        closeAlert.setHeaderText("Confirm Close");
        closeAlert.setContentText("Are you sure that you would like to close Molecula?");
        Optional<ButtonType> result = closeAlert.showAndWait();
        if (result.orElse(ButtonType.OK) == ButtonType.OK) {
            this.close();
        } else {
            event.consume();
        }
    }

    private void showInvalidMoleculeAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Molecule");
        alert.setContentText("The molecule entered is invalid");

        alert.showAndWait();
    }
}
