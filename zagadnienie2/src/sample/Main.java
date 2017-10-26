package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Adaline");
        primaryStage.setScene(new Scene(root, 440, 275));
        primaryStage.show();

        // Give the controller access to the main app.
        /*Controller controller = loader.getController();
        controller.setMain(this);*/
    }
    public static void main(String[] args) {
        launch(args);
    }
}
