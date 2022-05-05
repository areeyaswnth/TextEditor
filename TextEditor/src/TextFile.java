import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextFile {
    private String name;
    private File file;
    private String text;

    public TextFile(File file, String name) {
        this.file = file;
        this.name = name;
    }

    public String getText() {
        try {
            text = "";
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                text += input.nextLine() + "\n";
            }
            int i = text.lastIndexOf('\n');
            text = text.substring(0,i);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        if (file != null) {
            name = file.getName();
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isTextFile() {
        String extension = "";
        int i = file.getName().lastIndexOf('.');
        extension = file.getName().substring(i + 1);
        return i >= 0 && extension.equals("txt");
    }
}