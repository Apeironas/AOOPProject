package command;

import model.EditorEngine;

import javax.swing.*;

public class NewCommand implements Command {

    private final EditorEngine engine;
    private final JTextArea textArea;
    private final JFrame frame;

    public NewCommand(EditorEngine engine, JTextArea textArea, JFrame frame) {
        this.engine = engine;
        this.textArea = textArea;
        this.frame = frame;
    }

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

    @Override
    public void undo() {
        // Yeni dosya işlemi geri alınamaz
    }
}
