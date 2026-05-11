package controller;
import java.io.File;
import java.io.IOException;

    //Strategy patterni - Interface
    //Dosya işlemlerinin nasıl yapılacağını seçmemizi sağlar
    //Sadece düz metin tanımlı bu projede
    // Farklı dosya formatları eklenmek istendiğinde FileManager değiştirilmeden yeni bir strategy class ı oluşturulabilri
public interface FileStrategy {
    //Belirtilen dosyayı okur ve içeriğini String olarak döndürür
    void save(File file, String content) throws IOException;
    String load(File file) throws IOException;
}
