import javafx.application.Application;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.LinkedList;



public class MainScreen extends Application {

        private static Stage mainStage;
        private static Scene mainScene;

        //the main screen will keep track of the database to keep things simple
        public static LinkedList<Declaration> database;
        
        public static void main(String[] args){
                database = new LinkedList<Declaration>();
                launch(args);
        }

        //Used in order to get back to this main menu screen
        public static Stage getStage(){
                return mainStage;
        }

        //Same as above
        public static Scene getScene(){
                return mainScene;
        }

        @Override
        public void start(Stage primaryStage) {
                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(15);
                grid.setHgap(15);

                //Setting up the main menu screen
                primaryStage.setTitle("Main Screen");
                Label topLabel = new Label();
                topLabel.setText("Main Screen");
                topLabel.setFont(new Font("Montserrat", 32));
                GridPane.setConstraints(topLabel, 0, 0);
                grid.getChildren().add(topLabel);

                //DE BUTTON
                Button DE = new Button("Data Entry");
                GridPane.setConstraints(DE, 0, 2);
                DE.setMaxWidth(150);
                grid.getChildren().add(DE);
                DE.setOnAction(e -> primaryStage.setScene(DE_screen.getScene()));

                //REVIEW BUTTON
                Button REV = new Button("Review");
                GridPane.setConstraints(REV, 0, 3);
                REV.setMaxWidth(150);
                grid.getChildren().add(REV);
                //change this to go to your screen
                REV.setOnAction(e -> primaryStage.setScene(Review_Screen.getScene()));
                //REV.setOnAction(x -> System.out.println("SIZEEE: " + database.size()));

                //APPROVER BUTTON
                Button APPROVE = new Button("Approver");
                GridPane.setConstraints(APPROVE, 0, 4);
                APPROVE.setMaxWidth(150);
                grid.getChildren().add(APPROVE);
                //same as above review button
                APPROVE.setOnAction(e -> primaryStage.setScene(Approval.getScene()));
                
                grid.setAlignment(Pos.CENTER);
                BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
                Background background = new Background(backgroundFill);
                grid.setBackground(background);
                Scene ourScene = new Scene(grid, 800, 600);
                mainStage = primaryStage;
                mainScene = ourScene;

                //set our screen to the DE scene
                primaryStage.setScene(ourScene);
                primaryStage.show();

        }   

}