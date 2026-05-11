package command;

import controller.FileManager;
import model.EditorEngine;

import javax.swing.*;
import java.io.File;

public class OpenCommand implements Command {

    private final EditorEngine engine;
    private final FileManager fileManager;
    private final JTextArea textArea;
    private final JFrame frame;

    public OpenCommand(EditorEngine engine, FileManager fileManager, JTextArea textArea, JFrame frame) {
        this.engine = engine;
        this.fileManager = fileManager;
        this.textArea = textArea;
        this.frame = frame;
    }

    @Override
    public void execute() {
        if (engine.isModified()) {
            int choice = JOptionPane.showConfirmDialog(
                frame,
                "Kaydedilmemiş değişiklikler var. Devam etmek istiyor musunuz?",
                "Dosya Aç",
                JOptionPane.YES_NO_OPTION
            );
            if (choice != JOptionPane.YES_OPTION) return;
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

    @Override
    public void undo() {
        // Dosya açma işlemi geri alınamaz
    }
}
