package command;

import model.EditorEngine;

import javax.swing.*;

/**
 * COMMAND TASARIM DESENİ — Yeni Dosya Komutu
 *
 * <p>Editörü sıfırlayarak yeni boş bir belge açar.
 * Kaydedilmemiş değişiklik varsa kullanıcıya onay sorar.</p>
 *
 * <p>Yeni dosya açma işlemi geri alınamaz; {@code undo()} boş bırakılmıştır.</p>
 *
 * @author 1. Üye
 */
public class NewCommand implements Command {

    /** Uygulamanın merkezi veri modeli. */
    private final EditorEngine engine;

    /** Temizlenecek metin alanı. */
    private final JTextArea textArea;

    /** Başlığı güncellenecek ana pencere. */
    private final JFrame frame;

    /**
     * NewCommand oluşturur.
     *
     * @param engine   uygulama veri modeli
     * @param textArea temizlenecek metin alanı
     * @param frame    ana pencere
     */
    public NewCommand(EditorEngine engine, JTextArea textArea, JFrame frame) {
        this.engine   = engine;
        this.textArea = textArea;
        this.frame    = frame;
    }

    /**
     * Kaydedilmemiş değişiklik varsa kullanıcıya sorar,
     * onay alınırsa editörü sıfırlar ve başlığı günceller.
     */
    @Override
    public void execute() {
        if (engine.isModified()) {
            int choice = JOptionPane.showConfirmDialog(
                frame,
                "Kaydedilmemiş değişiklikler var. Devam etmek istiyor musunuz?",
                "Yeni Dosya",
                JOptionPane.YES_NO_OPTION
            );
            if (choice != JOptionPane.YES_OPTION) return;
        }
        engine.setCurrentText("");
        engine.setCurrentFile(null);
        engine.setModified(false);
        textArea.setText("");
        frame.setTitle("SimpleEditor - Yeni Dosya");
    }

    /**
     * Yeni dosya açma işlemi geri alınamaz.
     */
    @Override
    public void undo() {
        // Yeni dosya işlemi geri alınamaz
    }
}
