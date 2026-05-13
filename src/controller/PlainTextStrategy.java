package controller;

import java.io.*;
import java.nio.file.Files;

/**
 * STRATEGY TASARIM DESENİ — Düz Metin Stratejisi
 *
 * FileStrategy arayüzünün düz metin (.txt) formatı için
 * somut gerçekleştirimidir. Dosyaları UTF-8 kodlamasıyla okur ve yazar.
 *
 * Farklı bir format (RTF, şifreli, XML vb.) desteklenmek istenirse
 * bu sınıfa dokunmadan yeni bir FileStrategy gerçekleştirimi yazılır.
 */
public class PlainTextStrategy implements FileStrategy {

    /**
     * Verilen içeriği düz metin formatında belirtilen dosyaya yazar.
     * BufferedWriter kullanılarak performanslı yazma sağlanır.
     *
     * file yazılacak hedef dosya
     * content kaydedilecek metin içeriği
     * IOException yazma işlemi başarısız olursa fırlatılır
     */
    @Override
    public void save(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }

    /**
     * Belirtilen düz metin dosyasını okur ve içeriğini String olarak döndürür.
     * java.nio.file.Files} kullanılarak tüm içerik tek seferde okunur.
     *
     * file okunacak kaynak dosya
     * return dosyanın metin içeriği
     * IOException okuma işlemi başarısız olursa fırlatılır
     */
    @Override
    public String load(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }
}
