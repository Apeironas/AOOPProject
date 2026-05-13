package controller;

import model.EditorEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/*
STRATEGY TASARIM DESENİ — Bağlam (Context) Sınıfı
Dosya açma ve kaydetme işlemlerini yöneten denetleyicidir.
Hangi formatta okuma/yazma yapılacağını FileStrategy arayüzü
üzerinden belirler; bu sayede yeni dosya formatları eklemek için
bu sınıfa dokunmak gerekmez.

Model-View-Controller mimarisinde "Controller" katmanını temsil eder.
*/

public class FileManager {

    // Uygulamanın merkezi veri modeli (Singleton).
    private final EditorEngine engine = EditorEngine.getInstance();

    // Dosya okuma/yazma stratejisi.
    // Varsayılan: düz metin. (PlainText)
    private FileStrategy strategy;

    /**
     * FileManager oluşturur. Varsayılan strateji PlainTextStrategy'dir.
     */
    public FileManager() {
        this.strategy = new PlainTextStrategy();
    }

    /**
     * Dosya işleme stratejisini değiştirir.
     * Farklı format desteği (RTF, şifreli vb.) eklemek için kullanılır.
     */
    public void setStrategy(FileStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Belirtilen dosyayı okur ve içeriğiniEditorEngine}'e aktarır.
     * file okunacak dosya
     * IOException dosya okunamazsa fırlatılır
     */
    public void openFile(File file) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()));
        engine.setCurrentText(content);
        engine.setCurrentFile(file);
        engine.setModified(false);
    }

    /**
     * Mevcut belgeyi daha önce belirlenen dosya yoluna kaydeder..
     * Dosya yolu belirlenmemişse FileNotFoundException fırlatır.
     * IOException dosyaya yazılamazsa fırlatılır
     */
    public void saveFile() throws IOException {
        if (engine.getCurrentFile() != null) {
            saveToFile(engine.getCurrentFile());
        } else {
            throw new FileNotFoundException("Dosya yolu belirtilmemiş!");
        }
    }

    /**
     * Mevcut  belgeyi belirtilen dosyaya kaydeder ve
     * EditorEngine'deki dosya yolunu günceller.
     * file kaydedilecek hedef dosya
     * IOException dosyaya yazılamazsa fırlatılır
     */
    public void saveToFile(File file) throws IOException {
        strategy.save(file, engine.getCurrentText());
        engine.setCurrentFile(file);
        engine.setModified(false);
    }
}
