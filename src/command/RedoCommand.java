package command;

import model.EditorEngine;

import javax.swing.*;
import java.awt.*;

/**
 * COMMAND + MEMENTO TASARIM DESENİ — Yinele Komutu
 * Ctrl+Y ile tetiklenir. {@link EditorEngine#redoText} aracılığıyla
 * geri alınan durumu yeniden uygular.
 */
public class RedoCommand implements Command {

    private final EditorEngine engine; // Uygulamanın merkezi veri modeli
    private final JTextArea textArea; // İçeriğin güncelleneceği metin alanı
    private final JFrame frame; // Pencere başlığını güncellenecek ana pencere

    public RedoCommand(EditorEngine engine, JTextArea textArea, JFrame frame) {
        this.engine = engine;
        this.textArea = textArea;
        this.frame = frame;
    }

    /**
     * Yineleme işlemini uygular.
     * Yinelenecek kayıt yoksa sesli uyarı verir.
     */
    @Override
    public void execute() {
        if (!engine.canRedo()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        String next = engine.redoText(textArea.getText());
        textArea.setText(next);
        textArea.setCaretPosition(next.length());
    }

    /**
     * Geri al komutunu geri alınamaz.
     */
    @Override
    public void undo() {
        // Geri al komutunu geri alınamaz.
    }
}
