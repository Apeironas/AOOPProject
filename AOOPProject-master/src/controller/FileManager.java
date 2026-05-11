package controller;
import model.EditorEngine;
import java.io.*;
import java.nio.file.Files;

public class FileManager {
    private final EditorEngine engine = EditorEngine.getInstance();
    private FileStrategy strategy;

    public FileManager(){
        this.strategy = new PlainTextStrategy();
    }

    public void setStrategy(FileStrategy strategy){
        this.strategy = strategy;
    }
    //IOException : Input/Output exception
    //Dosyayı okur ve EditorEngine' e aktarır
    public void openFile(File file) throws IOException{
        String content = new String(Files.readAllBytes(file.toPath()));
        engine.setCurrentText(content);
        engine.setCurrentFile(file);
        engine.clearHistory();
        engine.setModified(false);
    }
    //Mevcut dosyayı günceller ve kullanıcı ilk defa kaydediyorsa Farklı kaydet kısmına yönlendirir
    public void saveFile() throws IOException{
        if (engine.getCurrentFile()!= null){
            saveToFile(engine.getCurrentFile());
        } else {
            throw new FileNotFoundException("Dosya yolu belirtilmemiş!");
        }
    }

    public void saveToFile(File file) throws IOException{
        strategy.save(file, engine.getCurrentText());
        engine.setCurrentFile(file);
        engine.setModified(false);

    }
}
