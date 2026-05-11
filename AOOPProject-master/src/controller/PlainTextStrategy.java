package controller;
import java.io.*;
import java.nio.file.Files;
    //Strategy patterni
    //Bu class FileStrategy interface ini düz metin için gerçekleştirir
public class PlainTextStrategy implements FileStrategy {
    @Override
    //Düz metin formatında dosyaya yazma işlemi
    public void save(File file, String content) throws IOException{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write(content);
        }
    }
    @Override
    //Düz metin formatındaki dosyayı okur
    //java.nio.file.Files kütüphanesi kullanılarak tüm içeriği byte dizisi olarak okur ve String'e dönüştürür
    public String load(File file) throws IOException{
        return new String(Files.readAllBytes(file.toPath()));
    }
}
