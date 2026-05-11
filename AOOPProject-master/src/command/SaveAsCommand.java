package command;

import controller.FileManager;
import model.EditorEngine;

import javax.swing.*;
import java.io.File;

public class SaveAsCommand implements Command {

    private final EditorEngine engine;
    private final FileManager fileManager;
    private final JTextArea textArea;
    private final JFrame frame;

    public SaveAsCommand(EditorEngine engine, FileManager fileManager, JTextArea textArea, JFrame frame) {
        this.engine = engine;
        this.fileManager = fileManager;
        this.textArea = textArea;
        this.frame = frame;
    }

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

    @Override
    public void undo() {
        // Farklı kaydet işlemi geri alınamaz
    }
}
