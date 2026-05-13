package command;

import controller.FileManager;
import model.EditorEngine;

import javax.swing.*;
import java.io.File;

/**
 * COMMAND TASARIM DESENİ — Farklı Kaydet Komutu
 *
 * <p>Kullanıcıdan yeni bir dosya yolu seçmesini ister ve
 * belgeyi seçilen konuma kaydeder. {@link SaveCommand}'dan farklı olarak
 * her seferinde dosya seçim diyaloğu açar.</p>
 *
 * <p>Farklı kaydetme işlemi geri alınamaz; {@code undo()} boş bırakılmıştır.</p>
 *
 * @author 1. Üye
 */
public class SaveAsCommand implements Command {

    /** Uygulamanın merkezi veri modeli. */
    private final EditorEngine engine;

    /** Dosya yazma işlemini yöneten denetleyici. */
    private final FileManager fileManager;

    /** İçeriği okunacak metin alanı. */
    private final JTextArea textArea;

    /** Başlığı güncellenecek ana pencere. */
    private final JFrame frame;

    /**
     * SaveAsCommand oluşturur.
     *
     * @param engine      uygulama veri modeli
     * @param fileManager dosya işlemleri denetleyicisi
     * @param textArea    içeriği okunacak metin alanı
     * @param frame       ana pencere
     */
    public SaveAsCommand(EditorEngine engine, FileManager fileManager, JTextArea textArea, JFrame frame) {
        this.engine      = engine;
        this.fileManager = fileManager;
        this.textArea    = textArea;
        this.frame       = frame;
    }

    /**
     * Dosya kaydetme diyaloğunu açar; kullanıcının seçtiği konuma
     * belgeyi kaydeder ve pencere başlığını günceller.
     * Hata durumunda kullanıcıya mesaj gösterir.
     */
    @Override
    public void execute() {
        engine.setCurrentText(textArea.getText());
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                fileManager.saveToFile(file);
                frame.setTitle("SimpleEditor - " + file.getName());
            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(frame, "Kaydetme hatası: " + e.getMessage());
            }
        }
    }

    /**
     * Farklı kaydetme işlemi geri alınamaz.
     */
    @Override
    public void undo() {
        // Farklı kaydet işlemi geri alınamaz
    }
}
