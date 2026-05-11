package command;

import model.EditorEngine;

import javax.swing.*;

public class ExitCommand implements Command {

    private final EditorEngine engine;
    private final JFrame frame;

    public ExitCommand(EditorEngine engine, JFrame frame) {
        this.engine = engine;
        this.frame = frame;
    }

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

    @Override
    public void undo() {
        // Çıkış işlemi geri alınamaz
    }
}
