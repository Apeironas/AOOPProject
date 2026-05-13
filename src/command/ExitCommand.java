package command;

import model.EditorEngine;

import javax.swing.*;

/**
 * COMMAND TASARIM DESENİ — Çıkış Komutu
 *
 * <p>Uygulamayı kapatır. Kaydedilmemiş değişiklik varsa
 * kullanıcıya onay sorar; kullanıcı vazgeçerse çıkış iptal edilir.</p>
 *
 * <p>Çıkış işlemi geri alınamaz; {@code undo()} boş bırakılmıştır.</p>
 *
 * @author 2. Üye
 */
public class ExitCommand implements Command {

    /** Kaydedilmemiş değişiklik kontrolü için veri modeli. */
    private final EditorEngine engine;

    /** Onay diyaloğunun gösterileceği ana pencere. */
    private final JFrame frame;

    /**
     * ExitCommand oluşturur.
     *
     * @param engine uygulama veri modeli
     * @param frame  ana pencere
     */
    public ExitCommand(EditorEngine engine, JFrame frame) {
        this.engine = engine;
        this.frame  = frame;
    }

    /**
     * Kaydedilmemiş değişiklik varsa kullanıcıya sorar,
     * onay alınırsa uygulamayı sonlandırır.
     */
    @Override
    public void execute() {
        if (engine.isModified()) {
            int choice = JOptionPane.showConfirmDialog(
                frame,
                "Kaydedilmemiş değişiklikler var. Çıkmak istiyor musunuz?",
                "Çıkış",
                JOptionPane.YES_NO_OPTION
            );
            if (choice != JOptionPane.YES_OPTION) return;
        }
        System.exit(0);
    }

    /**
     * Çıkış işlemi geri alınamaz.
     */
    @Override
    public void undo() {
        // Çıkış işlemi geri alınamaz
    }
}
