package command;

import controller.FileManager;
import model.EditorEngine;

import javax.swing.*;

/**
 * COMMAND TASARIM DESENİ — Kaydet Komutu
 * Mevcut belgeyi daha önce belirlenen dosya yoluna kaydeder.
 * Dosya yolu henüz belirlenmemişse {@link SaveAsCommand}'a yönlendirir.
 * Kaydetme işlemi geri alınamaz.
 */
public class SaveCommand implements Command {

    private final EditorEngine engine; // Uygulamanın merkezi veri modeli
    private final FileManager fileManager; // Dosya yazma işlemini yöneten denetleyici
    private final JTextArea textArea; // İçeriği okunacak metin alanı
    private final JFrame frame; // Başlığı güncellenecek ana pencere

    /**
     * SaveCommand oluşturur.
     *
     * @param engine      uygulama veri modeli
     * @param fileManager dosya işlemleri denetleyicisi
     * @param textArea    içeriği okunacak metin alanı
     * @param frame       ana pencere
     */
    public SaveCommand(EditorEngine engine, FileManager fileManager, JTextArea textArea, JFrame frame) {
        this.engine = engine;
        this.fileManager = fileManager;
        this.textArea = textArea;
        this.frame = frame;
    }

    /**
     * Metin alanındaki içeriği mevcut dosyaya kaydeder.
     * Dosya yolu belirlenmemişse {@link SaveAsCommand} devreye girer.
     * Hata durumunda kullanıcıya mesaj gösterir.
     */
    @Override
    public void execute() {
        engine.setCurrentText(textArea.getText());
        if (engine.getCurrentFile() == null) {
            // Dosya yolu yok — Farklı Kaydet'e yönlendir
            new SaveAsCommand(engine, fileManager, textArea, frame).execute();
            return;
        }
        try {
            fileManager.saveFile();
            frame.setTitle("SimpleEditor - " + engine.getCurrentFile().getName());
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(frame, "Kaydetme hatası: " + e.getMessage());
        }
    }

    /**
     * Kaydetme işlemi geri alınamaz.
     */
    @Override
    public void undo() {
        // Kaydetme işlemi geri alınamaz
    }
}
