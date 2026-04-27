package model;
import java.io.File;
//Singleton pattern burada kullanılıyor

public class EditorEngine {
    private static EditorEngine instance;
    private String currentText;
    private File currentFile;
    private boolean isModified;

    //Constructor method
    private EditorEngine(){
        this.currentText = "";
        this.currentFile = null;
        this.isModified = false;
    }

    //get instance method : sınıfın tekil örneğine erişim
    public static synchronized EditorEngine getInstance(){
        if (instance == null){
            instance = new EditorEngine();
        }
        return instance;
    }
    //getter
    public String getCurrentText(){ return currentText; }
    //current text set edildiğinde isModified true oluyor
    public void setCurrentText(String text){
        this.currentText = text;
        this.isModified = true;
    }

    public File getCurrentFile(){ return currentFile; }
    public void setCurrentFile(File file){ this.currentFile = file; }
    public boolean isModified(){ return isModified; }
    public void setModified(boolean modified){ isModified = modified; }

}
