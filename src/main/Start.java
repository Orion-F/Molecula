package main;

import gui.MoleculaGUI;
import javafx.application.Application;
import javafx.stage.Stage;
import chem.Atom;
import chem.AtomData;

public class Start extends Application {

    public static void main(String[] args) {
        AtomData.initData();
        launch(args);
    }
    
    @Override
    public void start(Stage arg0) throws Exception {
        new MoleculaGUI();
    }
}
