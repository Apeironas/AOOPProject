package controller;

import model.EditorEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/**
 * STRATEGY TASARIM DESENİ — Bağlam (Context) Sınıfı
 *
 * <p>Dosya açma ve kaydetme işlemlerini yöneten denetleyicidir.
 * Hangi formatta okuma/yazma yapılacağını {@link FileStrategy} arayüzü
 * üzerinden belirler; bu sayede yeni dosya formatları eklemek için
 * bu sınıfa dokunmak gerekmez.</p>
 *
 * <p>MVC mimarisinde "Controller" katmanını temsil eder.</p>
 *
 * <p>Kullanım örneği:
 * <pre>
 *   FileManager fm = new FileManager();
 *   fm.openFile(new File("belge.txt"));
 *   fm.saveFile();
 * </pre>
 * </p>
 *
 * @author 1. Üye
 */
public class FileManager {

    /** Uygulamanın merkezi veri modeli (Singleton). */
    private final EditorEngine engine = EditorEngine.getInstance();

    /** Dosya okuma/yazma stratejisi. Varsayılan: düz metin. */
    private FileStrategy strategy;

    /**
     * FileManager oluşturur. Varsayılan strateji {@link PlainTextStrategy}'dir.
     */
    public FileManager() {
        this.strategy = new PlainTextStrategy();
    }

    /**
     * Dosya işleme stratejisini değiştirir.
     * Farklı format desteği (RTF, şifreli vb.) eklemek için kullanılır.
     *
     * @param strategy uygulanacak yeni strateji
     */
    public void setStrategy(FileStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Belirtilen dosyayı okur ve içeriğini {@link EditorEngine}'e aktarır.
     *
     * @param file okunacak dosya
     * @throws IOException dosya okunamazsa fırlatılır
     */
    public void openFile(File file) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()));
        engine.setCurrentText(content);
        engine.setCurrentFile(file);
        engine.setModified(false);
    }

    /**
     * Mevcut belgeyi daha önce belirlenen dosya yoluna kaydeder.
     * Dosya yolu belirlenmemişse {@link FileNotFoundException} fırlatır.
     *
     * @throws IOException dosyaya yazılamazsa fırlatılır
     */
    public void saveFile() throws IOException {
        if (engine.getCurrentFile() != null) {
            saveToFile(engine.getCurrentFile());
        } else {
            throw new FileNotFoundException("Dosya yolu belirtilmemiş!");
        }
    }

    /**
     * Mevcut belgeyi belirtilen dosyaya kaydeder ve
     * {@link EditorEngine}'deki dosya yolunu günceller.
     *
     * @param file kaydedilecek hedef dosya
     * @throws IOException dosyaya yazılamazsa fırlatılır
     */
    public void saveToFile(File file) throws IOException {
        strategy.save(file, engine.getCurrentText());
        engine.setCurrentFile(file);
        engine.setModified(false);
    }
}
