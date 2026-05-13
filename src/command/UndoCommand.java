package command;

import model.EditorEngine;

import javax.swing.*;
import java.awt.*;

/**
 * COMMAND + MEMENTO TASARIM DESENİ — Geri Al Komutu
 * Ctrl+Z ile tetiklenir. {@link EditorEngine#undoText} aracılığıyla
 * bir önceki metin durumuna döner.
 */
public class UndoCommand implements Command {

    private final EditorEngine engine;
    private final JTextArea textArea;
    private final JFrame frame;

    public UndoCommand(EditorEngine engine, JTextArea textArea, JFrame frame) {
        this.engine = engine;
        this.textArea = textArea;
        this.frame = frame;
    }

    /**
     * Geri alma işlemini uygular.
     * Geri alınacak kayıt yoksa sesli uyarı verir.
     */
    @Override
    public void execute() {
        if (!engine.canUndo()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        String previous = engine.undoText(textArea.getText());
        textArea.setText(previous);
        textArea.setCaretPosition(previous.length());
    }

    /**
     * Geri alma işlemi geri alınamaz.
     */
    @Override
    public void undo() {
        // Geri alma işlemi geri alınamaz.
    }
}
