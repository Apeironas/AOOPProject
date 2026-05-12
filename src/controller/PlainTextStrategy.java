package controller;

import java.io.*;
import java.nio.file.Files;

/**
 * STRATEGY TASARIM DESENİ — Düz Metin Stratejisi
 *
 * <p>{@link FileStrategy} arayüzünün düz metin (.txt) formatı için
 * somut gerçekleştirimidir. Dosyaları UTF-8 kodlamasıyla okur ve yazar.</p>
 *
 * <p>Farklı bir format (RTF, şifreli, XML vb.) desteklenmek istenirse
 * bu sınıfa dokunmadan yeni bir {@link FileStrategy} gerçekleştirimi yazılır.</p>
 *
 * @author 1. Üye
 */
public class PlainTextStrategy implements FileStrategy {

    /**
     * Verilen içeriği düz metin formatında belirtilen dosyaya yazar.
     * {@link BufferedWriter} kullanılarak performanslı yazma sağlanır.
     *
     * @param file    yazılacak hedef dosya
     * @param content kaydedilecek metin içeriği
     * @throws IOException yazma işlemi başarısız olursa fırlatılır
     */
    @Override
    public void save(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }

    /**
     * Belirtilen düz metin dosyasını okur ve içeriğini String olarak döndürür.
     * {@code java.nio.file.Files} kullanılarak tüm içerik tek seferde okunur.
     *
     * @param file okunacak kaynak dosya
     * @return dosyanın metin içeriği
     * @throws IOException okuma işlemi başarısız olursa fırlatılır
     */
    @Override
    public String load(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }
}
