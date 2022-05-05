import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.control.TextArea;

public class Menuset {
    MenuBar menubar = new MenuBar();
    // File
    public Menu FileMenu = new Menu("File");
    public MenuItem File_NewButton = new MenuItem("New");
    public MenuItem File_SaveButton = new MenuItem("Save");
    public MenuItem File_SaveAsButton = new MenuItem("Save as");
    public MenuItem File_OpenButton = new MenuItem("Open");
    public MenuItem File_ExitButton = new MenuItem("Exit");
    public Menu EditMenu = new Menu("Edit");
    private MenuItem Edit_UndoButton = new MenuItem("Undo");
    private MenuItem Edit_RedoButton = new MenuItem("Redo");
    private MenuItem Edit_CopyButton = new MenuItem("Copy");
    private MenuItem Edit_PasteButton = new MenuItem("Paste");
    private MenuItem Edit_DeleteButton = new MenuItem("Delete");
    private MenuItem Edit_CutButton = new MenuItem("Cut");

    Menu Developers = new Menu("Developers");
    MenuItem Dev_Miss = new MenuItem("Areeya Suwannathot 64011028");
    MenuItem Dev_Pear = new MenuItem("Passamon Sukmaksin 64011199");
    MenuItem Dev_Nep = new MenuItem("Supitcha Pulsiri 64011308");

    public void setFileMenu() {
        FileMenu.getItems().addAll(File_NewButton, File_SaveButton, File_SaveAsButton, File_OpenButton,
                File_ExitButton);
    }

    public void setActionEditMenu(TextArea textArea) {
        Edit_UndoButton.setOnAction(e -> {
            textArea.undo();
        });
        Edit_CutButton.setOnAction(e -> {
            textArea.cut();
        });
        Edit_CopyButton.setOnAction(e -> {
            textArea.copy();
        });
        Edit_PasteButton.setOnAction(e -> {
            textArea.paste();
        });
        Edit_RedoButton.setOnAction(e -> {
            textArea.redo();
        });
        Edit_DeleteButton.setOnAction(e -> {
            textArea.clear();
        });
        EditMenu.getItems().addAll(Edit_UndoButton, Edit_CutButton, Edit_RedoButton, Edit_CopyButton, Edit_PasteButton,
                Edit_DeleteButton);
    }

    public void setDeveloper() {
        Developers.getItems().addAll(Dev_Miss, Dev_Pear, Dev_Nep);
    }

    public void setMenuBar() {
        menubar.getMenus().addAll(FileMenu, EditMenu, Developers);
    }

}
