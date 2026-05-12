package controller;

import java.io.File;
import java.io.IOException;

/**
 * STRATEGY TASARIM DESENİ — Strateji Arayüzü
 *
 * <p>Dosya okuma ve yazma işlemlerinin nasıl yapılacağını tanımlayan arayüzdür.
 * Her somut strateji sınıfı farklı bir dosya formatını destekler.</p>
 *
 * <p>Bu yapı sayesinde {@link FileManager} sınıfı, kullanılan dosya formatından
 * bağımsız çalışır. Yeni bir format eklemek için yalnızca bu arayüzü uygulayan
 * yeni bir sınıf yazmak yeterlidir.</p>
 *
 * <p>Mevcut somut stratejiler:
 * <ul>
 *   <li>{@link PlainTextStrategy} — düz metin (.txt)</li>
 * </ul>
 * </p>
 *
 * @author 1. Üye
 */
public interface FileStrategy {

    /**
     * Belirtilen içeriği dosyaya yazar.
     *
     * @param file    yazılacak hedef dosya
     * @param content kaydedilecek metin içeriği
     * @throws IOException yazma işlemi başarısız olursa fırlatılır
     */
    void save(File file, String content) throws IOException;

    /**
     * Belirtilen dosyayı okur ve içeriğini String olarak döndürür.
     *
     * @param file okunacak kaynak dosya
     * @return dosyanın metin içeriği
     * @throws IOException okuma işlemi başarısız olursa fırlatılır
     */
    String load(File file) throws IOException;
}
