import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;

public class Review_Screen extends Application {
    private static Scene REV;
    private static Declaration curr;
    private Declaration curr2;
    private static Integer taskID;
    private Integer taskID2;

    public static Scene getScene(){
        /* MAIN VBOX CONTAINING ALL LAYOUTS */
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        /* CREATE A TITLE LABEL */
        Label titleLabel = new Label("Review");
        titleLabel.setFont(Font.font("Open Sans", 20));
        VBox titleBox = new VBox();
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(titleLabel);
        root.getChildren().add(titleBox);

        /* CREATE GRID HOLDING ALL FIELDS */
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(10);

        /* CREATE LABELS */
        Label nameLabel = new Label("Name");
        nameLabel.setFont(Font.font("Open Sans", 14));
        Label dateLabel = new Label("Date");
        dateLabel.setFont(Font.font("Open Sans", 14));
        Label emailLabel = new Label("Email");
        emailLabel.setFont(Font.font("Open Sans", 14));
        Label durationLabel = new Label("Support Duration");
        durationLabel.setFont(Font.font("Open Sans", 14));
        Label appNumLabel = new Label("Applicant Number");
        appNumLabel.setFont(Font.font("Open Sans", 14));
        Label immigrantLabel = new Label("Immigrant Name");
        immigrantLabel.setFont(Font.font("Open Sans", 14));
        Label alienNumLabel = new Label("Alien Number");
        alienNumLabel.setFont(Font.font("Open Sans", 14));

        /* CREATE ASSOCIATED TEXT FIELDS */
        TextField nameField = new TextField();
        nameField.setFont(Font.font("Open Sans", 13));
        TextField dateField = new TextField();
        dateField.setFont(Font.font("Open Sans", 13));
        TextField emailField = new TextField();
        emailField.setFont(Font.font("Open Sans", 13));
        TextField durationField = new TextField();
        durationField.setFont(Font.font("Open Sans", 13));
        TextField appNumField = new TextField();
        appNumField.setFont(Font.font("Open Sans", 13));
        TextField immigrantField = new TextField();
        immigrantField.setFont(Font.font("Open Sans", 13));
        TextField alienNumField = new TextField();
        alienNumField.setFont(Font.font("Open Sans", 13));

        /* SET ALIGNMENT OF FIELDS */
        nameField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        dateField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        emailField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        durationField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        appNumField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        immigrantField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        alienNumField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);

        /* ADD EVERYTHING TO GRIDPLANE */
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(dateLabel, 0, 1);
        grid.add(dateField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(durationLabel, 0, 3);
        grid.add(durationField, 1, 3);
        grid.add(appNumLabel, 0, 4);
        grid.add(appNumField, 1, 4);
        grid.add(immigrantLabel, 0, 5);
        grid.add(immigrantField, 1, 5);
        grid.add(alienNumLabel, 0, 6);
        grid.add(alienNumField, 1, 6);

        /* ADD GRID UNDERNEATH THE TITLE */
        root.getChildren().add(grid);

        /* HOME BUTTON */
        Button home = new Button("Home");
        grid.add(home, 1, 8);

        /* SUBMIT BUTTON */
        Button submit = new Button("Submit");
        grid.add(submit, 0, 7);

        /* GET NEXT BUTTON */
        Button getNext = new Button("Get Next");
        grid.add(getNext, 0, 8);

        /* RESULT LABEL */
        Label resultLabel = new Label();
        grid.add(resultLabel, 1, 7);

        /* HOME BUTTON ACTIONS */
        home.setOnAction(e -> {
            MainScreen.getStage().setScene(MainScreen.getScene());
        });

        /* SUBMIT BUTTON ACTIONS */
        submit.setOnAction(e -> {

            TextField[] textFields = {nameField, dateField, emailField, durationField, appNumField, immigrantField, alienNumField};

            /* CHECK IF THERES NO CURRENT DECLARATION */
            if (curr == null){
                resultLabel.setText("Nothing to submit");
                return;
            }

            /* CHECK FOR EMPTY FIELDS */
            for (int i = 0; i < textFields.length; i++){
                if (textFields[i].getText().isEmpty()){
                    resultLabel.setText("Field missing info");
                    return;
                }
            }

            /* SAVE CHANGES TO CURRENT DECLARATION */
            curr.name = nameField.getText();
            curr.date = dateField.getText();
            curr.email = emailField.getText();
            curr.durationOfSupport = Integer.valueOf(durationField.getText());
            curr.applicantNumber = Integer.valueOf(appNumField.getText());
            curr.immigrantName = immigrantField.getText();
            curr.alienNumber = Integer.valueOf(alienNumField.getText());

            /* UPDATE WORKFLOW */
            WorkflowTable.removeTask(taskID);
            WorkflowTable.addTask(taskID, WorkflowTable.Step.APPROVAL);

            /* SET TEXT FIELDS TO EMPTY */
            for (TextField field : textFields){
                field.setText("");
            }

            /* SUBMISSION MESSAGE */
            resultLabel.setText("Declaration " + taskID + " submitted");

        });

        /* GET NEXT BUTTON ACTIONS */
        getNext.setOnAction(e -> {
            /* RESET MESSAGE */
            resultLabel.setText("");
            
            /* GET NEXT DEC ID + CHECK IF NULL */
            taskID = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);
            if (taskID == null){
                resultLabel.setText("No available declarations");
                return;
            }

            /* FIND NEXT DECLARATION */
            Boolean found = false;
            for (Declaration dec : MainScreen.database){
                if (dec.declarationID == taskID){
                    curr = dec;
                    found = true;
                }
            }
            if (found.equals(false)){
                resultLabel.setText("Database issue");
                return;
            }

            /* POPULATE FIELDS WITH DECLARATION INFO */
            nameField.setText(curr.name);
            dateField.setText(curr.date);
            emailField.setText(curr.email);
            durationField.setText(String.valueOf(curr.durationOfSupport));
            appNumField.setText(String.valueOf(curr.applicantNumber));
            immigrantField.setText(curr.immigrantName);
            alienNumField.setText(String.valueOf(curr.alienNumber));
            
        });

        /* SET BACKGROUND */
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        root.setBackground(background);

        REV = new Scene(root, 800, 600);
        return REV;
    }
    
    @Override
    public void start(Stage primaryStage){
        /* SET WINDOW TITLE */
        primaryStage.setTitle("Review Screen");

        /* MAIN VBOX CONTAINING ALL LAYOUTS */
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        /* CREATE A TITLE LABEL */
        Label titleLabel = new Label("Review");
        titleLabel.setFont(Font.font("Open Sans", 20));
        VBox titleBox = new VBox();
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(titleLabel);
        root.getChildren().add(titleBox);

        /* CREATE GRID HOLDING ALL FIELDS */
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(10);

        /* CREATE LABELS */
        Label nameLabel = new Label("Name");
        nameLabel.setFont(Font.font("Open Sans", 14));
        Label dateLabel = new Label("Date");
        dateLabel.setFont(Font.font("Open Sans", 14));
        Label emailLabel = new Label("Email");
        emailLabel.setFont(Font.font("Open Sans", 14));
        Label durationLabel = new Label("Support Duration");
        durationLabel.setFont(Font.font("Open Sans", 14));
        Label appNumLabel = new Label("Applicant Number");
        appNumLabel.setFont(Font.font("Open Sans", 14));
        Label immigrantLabel = new Label("Immigrant Name");
        immigrantLabel.setFont(Font.font("Open Sans", 14));
        Label alienNumLabel = new Label("Alien Number");
        alienNumLabel.setFont(Font.font("Open Sans", 14));

        /* CREATE ASSOCIATED TEXT FIELDS */
        TextField nameField = new TextField();
        nameField.setFont(Font.font("Open Sans", 13));
        TextField dateField = new TextField();
        dateField.setFont(Font.font("Open Sans", 13));
        TextField emailField = new TextField();
        emailField.setFont(Font.font("Open Sans", 13));
        TextField durationField = new TextField();
        durationField.setFont(Font.font("Open Sans", 13));
        TextField appNumField = new TextField();
        appNumField.setFont(Font.font("Open Sans", 13));
        TextField immigrantField = new TextField();
        immigrantField.setFont(Font.font("Open Sans", 13));
        TextField alienNumField = new TextField();
        alienNumField.setFont(Font.font("Open Sans", 13));

        /* SET ALIGNMENT OF FIELDS */
        nameField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        dateField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        emailField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        durationField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        appNumField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        immigrantField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        alienNumField.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);

        /* ADD EVERYTHING TO GRIDPLANE */
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(dateLabel, 0, 1);
        grid.add(dateField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(durationLabel, 0, 3);
        grid.add(durationField, 1, 3);
        grid.add(appNumLabel, 0, 4);
        grid.add(appNumField, 1, 4);
        grid.add(immigrantLabel, 0, 5);
        grid.add(immigrantField, 1, 5);
        grid.add(alienNumLabel, 0, 6);
        grid.add(alienNumField, 1, 6);

        /* ADD GRID UNDERNEATH THE TITLE */
        root.getChildren().add(grid);

        /* SUBMIT BUTTON */
        Button submit = new Button("Submit");
        grid.add(submit, 0, 7);

        /* GET NEXT BUTTON */
        Button getNext = new Button("Get Next");
        grid.add(getNext, 0, 8);

        /* RESULT LABEL */
        Label resultLabel = new Label();
        grid.add(resultLabel, 1, 7);

        /* SUBMIT BUTTON ACTIONS */
        submit.setOnAction(e -> {

            TextField[] textFields = {nameField, dateField, emailField, durationField, appNumField, immigrantField, alienNumField};

            /* CHECK IF THERES NO CURRENT DECLARATION */
            if (curr2 == null){
                resultLabel.setText("Nothing to submit");
                return;
            }

            /* CHECK FOR EMPTY FIELDS */
            for (int i = 0; i < textFields.length; i++){
                if (textFields[i].getText().isEmpty()){
                    resultLabel.setText("Field missing info");
                    return;
                }
            }

            /* SAVE CHANGES TO CURRENT DECLARATION */
            curr2.name = nameField.getText();
            curr2.date = dateField.getText();
            curr2.email = emailField.getText();
            curr2.durationOfSupport = Integer.valueOf(durationField.getText());
            curr2.applicantNumber = Integer.valueOf(appNumField.getText());
            curr2.immigrantName = immigrantField.getText();
            curr2.alienNumber = Integer.valueOf(alienNumField.getText());

            /* UPDATE WORKFLOW */
            WorkflowTable.removeTask(taskID2);
            WorkflowTable.addTask(taskID2, WorkflowTable.Step.APPROVAL);

            /* SET TEXT FIELDS TO EMPTY */
            for (TextField field : textFields){
                field.setText("");
            }

        });

        /* GET NEXT BUTTON ACTIONS */
        getNext.setOnAction(e -> {
            /* GET NEXT DEC ID + CHECK IF NULL */
            taskID2 = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);
            if (taskID2 == null){
                resultLabel.setText("No available declarations");
                return;
            }

            /* FIND NEXT DECLARATION */
            /* 
            Boolean found = false;
            for (Declaration dec : MainScreen.database){
                if (dec.declarationID == taskID2){
                    curr2 = dec;
                    found = true;
                }
            }
            */
            curr2 = Declaration.getFromDB(taskID2);
            if (curr2 == null){
                resultLabel.setText("Database issue");
                return;
            }

            /* POPULATE FIELDS WITH DECLARATION INFO */
            nameField.setText(curr2.name);
            dateField.setText(curr2.date);
            emailField.setText(curr2.email);
            durationField.setText(String.valueOf(curr2.durationOfSupport));
            appNumField.setText(String.valueOf(curr2.applicantNumber));
            immigrantField.setText(curr2.immigrantName);
            alienNumField.setText(String.valueOf(curr2.alienNumber));
            
        });

        /* SET BACKGROUND */
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        root.setBackground(background);


        /* CREATE SCRENE */
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);

        /* SHOW WINDOW */
        primaryStage.show();

    }
    public static void main( String[] args )
    {
        launch(args);
    }
}

