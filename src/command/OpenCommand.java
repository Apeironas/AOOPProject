package command;

import controller.FileManager;
import model.EditorEngine;

import javax.swing.*;
import java.io.File;

/**
 * COMMAND TASARIM DESENİ — Dosya Aç Komutu
 * Dosya seçim diyaloğu açarak kullanıcının seçtiği dosyayı okur
 * ve içeriğini metin alanına yükler. Kaydedilmemiş değişiklik varsa
 * önce kullanıcıya onay sorar.
 * Dosya açma işlemi geri alınamaz.
 */
public class OpenCommand implements Command {

    private final EditorEngine engine; // Uygulamanın merkezi veri modeli
    private final FileManager fileManager; // Dosya okuma işlemini yöneten denetleyici
    private final JTextArea textArea; // İçeriğin yükleneceği metin alanı
    private final JFrame frame; // Başlığı güncellenecek ana pencere

    /**
     * OpenCommand oluşturur.
     *
     * @param engine      uygulama veri modeli
     * @param fileManager dosya işlemleri denetleyicisi
     * @param textArea    içeriğin yükleneceği metin alanı
     * @param frame       ana pencere
     */
    public OpenCommand(EditorEngine engine, FileManager fileManager, JTextArea textArea, JFrame frame) {
        this.engine = engine;
        this.fileManager = fileManager;
        this.textArea = textArea;
        this.frame = frame;
    }

    /**
     * Dosya seçim diyaloğunu açar; seçilen dosyayı okuyarak
     * metin alanına yükler ve pencere başlığını günceller.
     * Hata durumunda kullanıcıya mesaj gösterir.
     */
    @Override
    public void execute() {
        if (engine.isModified()) {
            int choice = JOptionPane.showConfirmDialog(
                    frame,
                    "Kaydedilmemiş değişiklikler var. Devam etmek istiyor musunuz?",
                    "Dosya Aç",
                    JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION)
                return;
        }

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                fileManager.openFile(file);
                textArea.setText(engine.getCurrentText());
                frame.setTitle("SimpleEditor - " + file.getName());
            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(frame, "Dosya açılamadı: " + e.getMessage());
            }
        }
    }

    /**
     * Dosya açma işlemi geri alınamaz.
     */
    @Override
    public void undo() {
        // Dosya açma işlemi geri alınamaz
    }
}
