import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import java.io.*;
import java.util.Optional;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;

public class App extends Application {

    // Window
    BorderPane BPane = new BorderPane();
    Scene scene = new Scene(BPane, 600, 400);
    static TextArea textArea = new TextArea();
    FileChooser fileChooser = new FileChooser();
    ButtonType buttonSave = new ButtonType("Save", ButtonData.YES);
    ButtonType buttonDontSave = new ButtonType("Don't save", ButtonData.NO);
    ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    TextFile textFile = new TextFile(null, "Untitled");
    AlertSet alertSet = new AlertSet();
    Menuset menuset = new Menuset();

    public boolean isEdited() {
        return !textFile.getText().equals(textArea.getText());
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("spiral-note-pad.png")));

        // MenuBar
        setMenuBar(primaryStage);

        FileChooser.ExtensionFilter TextFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        FileChooser.ExtensionFilter AllFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
        fileChooser.getExtensionFilters().addAll(TextFilter, AllFilter);

        // TextArea
        textArea.setStyle("-fx-border-color: transparent; -fx-border-radius: 2; -fx-border-insets: 6, 6, 6, 6; "
                + "-fx-border-style: solid inside, solid outside;");
        BPane.setCenter(textArea);

        // sence
        primaryStage.setTitle("Text Editor - " + textFile.getName());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showNewSaveDialog(Stage stage) {
        alertSet.SaveAlertShow(textFile.getName(), buttonSave, buttonDontSave, buttonCancel);
        Optional<ButtonType> result = alertSet.saveAlert.showAndWait();
        if (result.isPresent() && result.get() == buttonSave) {
            try {
                Save(stage);
                if (textFile.getFile() != null) {
                    New(stage);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (result.isPresent() && result.get() == buttonDontSave) {
            alertSet.saveAlert.close();
            New(stage);
        } else if (result.isPresent() && result.get() == buttonCancel) {
            alertSet.saveAlert.close();
        }
    }

    public void showExitSaveDialog(Stage stage) {
        alertSet.SaveAlertShow(textFile.getName(), buttonSave, buttonDontSave, buttonCancel);
        Optional<ButtonType> result = alertSet.saveAlert.showAndWait();
        if (result.isPresent() && result.get() == buttonSave) {
            try {
                System.out.println(textFile.getName());
                Save(stage);
                stage.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (result.isPresent() && result.get() == buttonDontSave) {
            stage.close();
        } else if (result.isPresent() && result.get() == buttonCancel) {
            alertSet.saveAlert.close();
        }
    }

    public void showOpenSaveDialog(Stage stage) throws FileNotFoundException {
        alertSet.SaveAlertShow(textFile.getName(), buttonSave, buttonDontSave, buttonCancel);
        Optional<ButtonType> result = alertSet.saveAlert.showAndWait();
        if (result.isPresent() && result.get() == buttonSave) {
            try {
                Save(stage);
                Open(stage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (result.isPresent() && result.get() == buttonDontSave) {
            alertSet.saveAlert.close();
            Open(stage);
        } else if (result.isPresent() && result.get() == buttonCancel) {
            alertSet.saveAlert.close();
        }
    }

    public void showReadOnlyDialog(Stage stage) {
        alertSet.ReadonlyAlertShow(buttonSave, buttonCancel);
        Optional<ButtonType> result = alertSet.readAlert.showAndWait();

        if (result.isPresent() && result.get() == buttonSave) {
            try {
                SaveAs(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setMenuBar(Stage stage) throws IOException {
        BPane.setTop(menuset.menubar);

        // setAction
        menuset.File_NewButton.setOnAction(e -> {
            if (textFile.getFile() == null && !textArea.getText().isEmpty()) {
                showNewSaveDialog(stage);
            } else if (textFile.getFile() != null && isEdited()) {
                showNewSaveDialog(stage);
            } else {
                New(stage);
            }
        });
        menuset.File_ExitButton.setOnAction(e -> {
            if (!textArea.getText().isEmpty() || (textFile.getFile() != null && isEdited())) {
                showExitSaveDialog(stage);
            } else
                stage.close();
        });
        menuset.File_OpenButton.setOnAction(e -> {
            try {
                if (textFile.getFile() != null && isEdited()) {
                    showOpenSaveDialog(stage);
                }

                else if (textFile.getFile() == null && !textArea.getText().isEmpty()) {
                    showOpenSaveDialog(stage);
                } else {
                    Open(stage);
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        menuset.File_SaveButton.setOnAction(e -> {
            try {
                Save(stage);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

        });
        menuset.File_SaveAsButton.setOnAction(e -> {
            try {
                SaveAs(stage);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        menuset.setActionEditMenu(textArea);
        menuset.setDeveloper();
        menuset.setFileMenu();
        menuset.setMenuBar();

    }

    public void New(Stage stage) {
        textArea.setText("");
        textFile.setFile(null);
        textFile.setName("Untitled");
        stage.setTitle("Text Editor - " + textFile.getName());
    }

    public void Save(Stage stage) throws FileNotFoundException {
        if (textFile.getFile() != null) {
            if (!textFile.getFile().canWrite()) {
                showReadOnlyDialog(stage);
            } else {
                java.io.PrintWriter output = new java.io.PrintWriter(textFile.getFile());
                output.write(textArea.getText());
                output.close();
            }
        } else {
            SaveAs(stage);
        }
    }

    public void SaveAs(Stage stage) throws FileNotFoundException {
        fileChooser.setTitle("Save as");
        File savedFile = fileChooser.showSaveDialog(stage);
        textFile.setFile(savedFile);
        if (textFile.getFile() != null) {
            java.io.PrintWriter output = new java.io.PrintWriter(textFile.getFile());
            output.write(textArea.getText());
            output.close();
            stage.setTitle("Text Editor - " + textFile.getFile().getName());
        }
    }

    public void Open(Stage stage) throws FileNotFoundException {
        fileChooser.setTitle("Open");
        File openedFile = fileChooser.showOpenDialog(stage);
        if (openedFile == null) {
            return;
        }
        textFile.setFile(openedFile);
        if (!textFile.isTextFile()) {
            alertSet.TextfileAlertShow();
        } else {
            textArea.setText(textFile.getText());
            stage.setTitle("Text Editor - " + textFile.getName());
        }
    }
}