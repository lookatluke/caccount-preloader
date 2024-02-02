/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXPreloader.java to edit this template
 */
package caccount;

import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author trito
 */
public class Caccount_Preloader extends Preloader {

    Stage stage;
    boolean noLoadingProgress = true;

    private Scene createPreloaderScene() {
        Image image = new Image(Caccount_Preloader.class.getResourceAsStream("_preloader.png"));
        ImageView imgView = new ImageView(image);
        AnchorPane page = new AnchorPane();
        page.getChildren().add(imgView);
        page.setStyle("-fx-background-color: transparent;");
        return new Scene(page, 600, 400);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Scene scene = createPreloaderScene();
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(Caccount_Preloader.class.getResourceAsStream("_stageIcon.png")));
        stage.show();
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        //application loading progress is rescaled to be first 50%
        //Even if there is nothing to load 0% and 100% events can be
        // delivered
        if (pn.getProgress() != 1.0 || !noLoadingProgress) {
            if (pn.getProgress() > 0) {
                noLoadingProgress = false;
            }
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        /*
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
         */
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
        if (pn instanceof ProgressNotification) {
            //expect application to send us progress notifications 
            //with progress ranging from 0 to 1.0
            double v = ((ProgressNotification) pn).getProgress();
            if (!noLoadingProgress) {
                //if we were receiving loading progress notifications 
                //then progress is already at 50%. 
                //Rescale application progress to start from 50%               
                v = 0.5 + v / 2;
            }
        } else if (pn instanceof StateChangeNotification) {
            //hide after get any state update from application
            stage.hide();
        }
    }

}
