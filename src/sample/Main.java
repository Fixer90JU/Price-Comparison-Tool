package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.shape.Shape;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Price Comparator");
        primaryStage.setScene(new Scene(root, 650, 825));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
