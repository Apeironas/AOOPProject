package controller;

import java.io.File;
import java.io.IOException;

/**
 * STRATEGY TASARIM DESENİ — Strateji Arayüzü
 * Dosya okuma ve yazma işlemlerinin nasıl yapılacağını tanımlayan arayüzdür.
 * Her somut strateji sınıfı farklı bir dosya formatını destekler.
 * Bu yapı sayesinde FileManager sınıfı, kullanılan dosya formatından
 * bağımsız çalışır. Yeni bir format eklemek için yalnızca bu arayüzü uygulayan
 * yeni bir sınıf yazmak yeterlidir.
 * Mevcut stratejiler:
 * PlainTextStrategy — düz metin (.txt)
 */
public interface FileStrategy {

    /**
     * Belirtilen içeriği dosyaya yazar.
     * file yazılacak hedef dosya
     * content kaydedilecek metin içeriği
     * IOException yazma işlemi başarısız olursa fırlatılır
     */
    void save(File file, String content) throws IOException;

    /**
     * Belirtilen dosyayı okur ve içeriğini String olarak döndürür.
     * file okunacak kaynak dosya
     * return dosyanın metin içeriği
     * IOException okuma işlemi başarısız olursa fırlatılır
     */
    String load(File file) throws IOException;
}
