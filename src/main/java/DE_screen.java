import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class DE_screen extends Application {

    private static Scene DE;
    public static void main(String[] args) {
        launch(args);
    }

    //Used by the MainScreen class to be able to access and show this class' screen
    public static Scene getScene(){
        //create the grid to hold all the elements
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(15);
        grid.setHgap(15);

        //Title label
        
        Label topLabel = new Label();
        topLabel.setText("Data Entry");
        topLabel.setFont(new Font("Montserrat", 24));
        GridPane.setConstraints(topLabel, 0, 0);
        grid.getChildren().add(topLabel);

        //NAME FIELD
        TextField name = new TextField();
        name.setPromptText("Enter your name. (First Last)");
        name.setPrefColumnCount(10);
        name.getText();
        GridPane.setConstraints(name, 0, 1);
        grid.getChildren().add(name);

        //DATE FIELD
        TextField date = new TextField();
        date.setPromptText("Enter the date. (MM/DD/YYYY)");
        GridPane.setConstraints(date, 0, 2);
        grid.getChildren().add(date);

        //EMAIL FIELD
        TextField email = new TextField();
        email.setPrefColumnCount(15);
        email.setPromptText("Enter your email.");
        GridPane.setConstraints(email, 0, 3);
        grid.getChildren().add(email);

        //DURATION FIELD
        TextField duration = new TextField();
        duration.setPrefColumnCount(15);
        duration.setPromptText("Enter the duration of your support. (Months)");
        GridPane.setConstraints(duration, 0, 4);
        grid.getChildren().add(duration);
        grid.getColumnConstraints().add(new ColumnConstraints(350));

        //APPLICANT NUMBER FIELD
        TextField appnum = new TextField();
        appnum.setPrefColumnCount(15);
        appnum.setPromptText("Enter your applicant number. (12 Digits)");
        GridPane.setConstraints(appnum, 0, 5);
        grid.getChildren().add(appnum);

        //IMMIGRANT NAME NUMBER FIELD
        TextField iname = new TextField();
        iname.setPrefColumnCount(15);
        iname.setPromptText("Enter immigrant name. (First Last)");
        GridPane.setConstraints(iname, 0, 6);
        grid.getChildren().add(iname);

        //ALIEN NUMBER NUMBER FIELD
        TextField anum = new TextField();
        anum.setPrefColumnCount(15);
        anum.setPromptText("Enter alien number. (8 digits)");
        GridPane.setConstraints(anum, 0, 7);
        grid.getChildren().add(anum);

        //SUBMIT BUTTON
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 0, 8);
        submit.setMaxWidth(100);
        grid.getChildren().add(submit);

        //HOME BUTTON
        Button home = new Button("Home");
        GridPane.setConstraints(home, 1, 8);
        home.setMaxWidth(100);
        grid.getChildren().add(home);

        //ERROR LABEL
        Label errorLabel = new Label();
        GridPane.setConstraints(errorLabel, 0, 9);
        GridPane.setColumnSpan(errorLabel, 2);
        grid.getChildren().add(errorLabel);
        errorLabel.setFont(new Font("Montserrat", 14));

        home.setOnAction(e -> //Get back to main menu - get its stage and set its stage
        MainScreen.getStage().setScene(MainScreen.getScene()));

        //Handle the click for the submit button
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                public void handle(ActionEvent e) {
                    errorLabel.setTextFill(Color.color(1, 0, 0));
                    if (!(name.getText() != null && !name.getText().isEmpty())) {
                        errorLabel.setText("You are missing the name. Make sure you have formatted it correctly.");
                        return;
                    } 
                    if (!(date.getText() != null && !date.getText().isEmpty())) {
                        errorLabel.setText("You are missing the date. Make sure you have formatted it correctly.");
                        return;
                    }
                    if (!(email.getText() != null && !email.getText().isEmpty())) {
                        errorLabel.setText("You are missing the email. Make sure you have formatted it correctly.");
                        return;
                    } 
                    if (!(duration.getText() != null && !duration.getText().isEmpty())) {
                        errorLabel.setText("You are missing the duration. Make sure you have formatted it correctly.");
                        return;
                    }
                    if (!(appnum.getText() != null && !appnum.getText().isEmpty())) {
                        errorLabel.setText("You are missing the Applicant Number. Make sure you have formatted it correctly.");
                        return;
                    }
                    if (!(iname.getText() != null && !iname.getText().isEmpty())) {
                        errorLabel.setText("You are missing the immigrant name. Make sure you have formatted it correctly.");
                        return;
                    }  
                    if (!(anum.getText() != null && !anum.getText().isEmpty())) {
                        errorLabel.setText("You are missing the alien number. Make sure you have formatted it correctly.");
                        return;
                    }
                    //all fields are present and valid!
                    //call submit to create and save the Declaration and send it to the next step in the workflow
                    boolean bad = false;
                    Declaration d = null;
                    try {
                        d = Declaration.create(date.getText(), name.getText(), email.getText(), Integer.parseInt(duration.getText()), Integer.parseInt(appnum.getText()), iname.getText(), Integer.parseInt(anum.getText()), false, 0);
                    } catch (Exception x){
                        errorLabel.setText("WARNING: Check formatting for all fields. Make sure number fields only include numerical digits.");
                        bad = true;
                    }
                    if(!bad){
                        //add it to the workflow and database
                        MainScreen.database.add(d);
                        WorkflowTable.addTask(d.declarationID,WorkflowTable.Step.REVIEW);
                        //System.out.println("WF Size: "+WorkflowTable.WF.size()+ "ID: "+d.declarationID);

                        errorLabel.setTextFill(Color.color(0, 0, 0));
                        errorLabel.setText("Your Declaration has been submitted. Have a great day!"); 
                        name.setText("");   
                        date.setText("");
                        email.setText(""); 
                        duration.setText("");  
                        appnum.setText("");  
                        iname.setText("");  
                        anum.setText("");                         
                    }
        }});
            
        //do nice formatting stuff
        grid.setAlignment(Pos.CENTER);

        Platform.runLater(() -> grid.requestFocus());

        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        grid.setBackground(background);
        DE = new Scene(grid, 800, 600);
        return DE;
    }

    /////////////////////////////////////////////////////

    //NOT REALLY USED ANYMORE - this would only be used if you were directly calling the DE screen
    //but usually you will call the main screen which will use the above block of code if it wants
    //to access this screen.
    @Override
    public void start(Stage primaryStage) {
        //create the grid to hold all the elements
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(15);
        grid.setHgap(15);

        //Title label
        primaryStage.setTitle("Data Entry");
        Label topLabel = new Label();
        topLabel.setText("Data Entry");
        topLabel.setFont(new Font("Montserrat", 24));
        GridPane.setConstraints(topLabel, 0, 0);
        grid.getChildren().add(topLabel);

        //NAME FIELD
        TextField name = new TextField();
        name.setPromptText("Enter your name. (First Last)");
        name.setPrefColumnCount(10);
        name.getText();
        GridPane.setConstraints(name, 0, 1);
        grid.getChildren().add(name);

        //DATE FIELD
        TextField date = new TextField();
        date.setPromptText("Enter the date. (MM/DD/YYYY)");
        GridPane.setConstraints(date, 0, 2);
        grid.getChildren().add(date);

        //EMAIL FIELD
        TextField email = new TextField();
        email.setPrefColumnCount(15);
        email.setPromptText("Enter your email.");
        GridPane.setConstraints(email, 0, 3);
        grid.getChildren().add(email);

        //DURATION FIELD
        TextField duration = new TextField();
        duration.setPrefColumnCount(15);
        duration.setPromptText("Enter the duration of your support. (Months)");
        GridPane.setConstraints(duration, 0, 4);
        grid.getChildren().add(duration);
        grid.getColumnConstraints().add(new ColumnConstraints(350));

        //APPLICANT NUMBER FIELD
        TextField appnum = new TextField();
        appnum.setPrefColumnCount(15);
        appnum.setPromptText("Enter your applicant number. (12 Digits)");
        GridPane.setConstraints(appnum, 0, 5);
        grid.getChildren().add(appnum);

        //IMMIGRANT NAME NUMBER FIELD
        TextField iname = new TextField();
        iname.setPrefColumnCount(15);
        iname.setPromptText("Enter immigrant name. (First Last)");
        GridPane.setConstraints(iname, 0, 6);
        grid.getChildren().add(iname);

        //ALIEN NUMBER NUMBER FIELD
        TextField anum = new TextField();
        anum.setPrefColumnCount(15);
        anum.setPromptText("Enter alien number. (8 digits)");
        GridPane.setConstraints(anum, 0, 7);
        grid.getChildren().add(anum);

        //SUBMIT BUTTON
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 0, 8);
        submit.setMaxWidth(100);
        grid.getChildren().add(submit);

        //ERROR LABEL
        Label errorLabel = new Label();
        GridPane.setConstraints(errorLabel, 0, 9);
        GridPane.setColumnSpan(errorLabel, 2);
        grid.getChildren().add(errorLabel);
        errorLabel.setFont(new Font("Montserrat", 14));

        //Handle the click for the submit button
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                public void handle(ActionEvent e) {
                    errorLabel.setTextFill(Color.color(1, 0, 0));
                    if (!(name.getText() != null && !name.getText().isEmpty())) {
                        errorLabel.setText("You are missing the name. Make sure you have formatted it correctly.");
                        return;
                    } 
                    if (!(date.getText() != null && !date.getText().isEmpty())) {
                        errorLabel.setText("You are missing the date. Make sure you have formatted it correctly.");
                        return;
                    }
                    if (!(email.getText() != null && !email.getText().isEmpty())) {
                        errorLabel.setText("You are missing the email. Make sure you have formatted it correctly.");
                        return;
                    } 
                    if (!(duration.getText() != null && !duration.getText().isEmpty())) {
                        errorLabel.setText("You are missing the duration. Make sure you have formatted it correctly.");
                        return;
                    }
                    if (!(appnum.getText() != null && !appnum.getText().isEmpty())) {
                        errorLabel.setText("You are missing the Applicant Number. Make sure you have formatted it correctly.");
                        return;
                    }
                    if (!(iname.getText() != null && !iname.getText().isEmpty())) {
                        errorLabel.setText("You are missing the immigrant name. Make sure you have formatted it correctly.");
                        return;
                    }  
                    if (!(anum.getText() != null && !anum.getText().isEmpty())) {
                        errorLabel.setText("You are missing the alien number. Make sure you have formatted it correctly.");
                        return;
                    }
                    //all fields are present and valid!
                    //call submit to create and save the Declaration and send it to the next step in the workflow
                    boolean bad = false;
                    try {
                        Declaration d = new Declaration(date.getText(), name.getText(), email.getText(), Integer.parseInt(duration.getText()), Integer.parseInt(appnum.getText()), iname.getText(), Integer.parseInt(anum.getText()), false, 0);
                    } catch (Exception x){
                        errorLabel.setText("WARNING: Check formatting for all fields. Make sure number fields only include numerical digits.");
                        bad = true;
                    }
                    if(!bad){
                        errorLabel.setTextFill(Color.color(0, 0, 0));
                        errorLabel.setText("Your Declaration has been submitted. Have a great day!");                        
                    }
        }});
            
        //do nice formatting stuff
        grid.setAlignment(Pos.CENTER);

        Platform.runLater(() -> grid.requestFocus());

        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        grid.setBackground(background);
        DE = new Scene(grid, 800, 600);
        primaryStage.setScene(DE);
        primaryStage.show();
    }
}

