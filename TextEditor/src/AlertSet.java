import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertSet {
    public Alert readAlert = new Alert(Alert.AlertType.WARNING);
    public Alert textFileAlert = new Alert(Alert.AlertType.ERROR);
    public Alert saveAlert = new Alert(AlertType.NONE);

    public void ReadonlyAlertShow(ButtonType Save, ButtonType Cancel) {
        readAlert.setTitle("Read-only");
        readAlert.setHeaderText("File opened in Read-only mode.");
        readAlert.setContentText("You must use Save as to save changes.");
        readAlert.getButtonTypes().setAll(Save, Cancel);
    }

    public void SaveAlertShow(String FileName, ButtonType Save, ButtonType DontSave, ButtonType Cancel) {
        saveAlert.setTitle("Text Editor");
        saveAlert.setHeaderText("Do you want to save changes to " + FileName + " ?");
        saveAlert.setContentText("Choose your option.");
        saveAlert.getButtonTypes().setAll(Save, DontSave, Cancel);
    }

    public void TextfileAlertShow() {
        Alert textFileAlert = new Alert(Alert.AlertType.ERROR);
        textFileAlert.setTitle("Text Editor");
        textFileAlert.setHeaderText("This is not a text file.");
        textFileAlert.setContentText("Text Editor can not open non text file.");
        textFileAlert.show();

    }
}
