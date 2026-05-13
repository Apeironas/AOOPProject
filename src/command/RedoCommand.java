package command;

import model.EditorEngine;

import javax.swing.*;
import java.awt.*;

/**
 * COMMAND + MEMENTO TASARIM DESENİ — Yinele Komutu
 *
 * <p>Ctrl+Y ile tetiklenir. {@link EditorEngine#redoText} aracılığıyla
 * geri alınan durumu yeniden uygular.</p>
 *
 * @author 3. Üye
 */
public class RedoCommand implements Command {

    private final EditorEngine engine;
    private final JTextArea textArea;
    private final JFrame frame;

    public RedoCommand(EditorEngine engine, JTextArea textArea, JFrame frame) {
        this.engine   = engine;
        this.textArea = textArea;
        this.frame    = frame;
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

    @Override
    public void undo() {}
}
