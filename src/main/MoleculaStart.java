package main;

import gui.MoleculaGUI;
import javafx.application.Application;
import javafx.stage.Stage;
import run.VersionReader;
import chem.AtomData;

public class MoleculaStart extends Application {

    public static void main(String[] args) {
        AtomData.initData();
        VersionReader.useDefault(MoleculaStart.class);
        launch(args);
    }
    
    @Override
    public void start(Stage arg0) throws Exception {
        new MoleculaGUI();
    }
}
