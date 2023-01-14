package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
 
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private static Stage stg;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
    	stg=primaryStage;
    	primaryStage.setResizable(false);
    	Parent root = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
    	primaryStage.setTitle("CheapSkate");
    	primaryStage.getIcons().add(new Image("C:/Users/Asus/Documents/JavaFX/symbol.jpg"));
    	primaryStage.setScene(new Scene(root,350,645));
    	primaryStage.show();
    }
    
    public void changeScene(String fxml) throws Exception{
    	Parent pane=FXMLLoader.load(getClass().getResource(fxml));
    	stg.getScene().setRoot(pane);
    }
}
