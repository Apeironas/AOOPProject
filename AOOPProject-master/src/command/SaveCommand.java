package command;

import controller.FileManager;
import model.EditorEngine;

import javax.swing.*;

public class SaveCommand implements Command {

    private final EditorEngine engine;
    private final FileManager fileManager;
    private final JTextArea textArea;
    private final JFrame frame;

    public SaveCommand(EditorEngine engine, FileManager fileManager, JTextArea textArea, JFrame frame) {
        this.engine = engine;
        this.fileManager = fileManager;
        this.textArea = textArea;
        this.frame = frame;
    }

    @Override
    public void execute() {
        engine.setCurrentText(textArea.getText());
        if (engine.getCurrentFile() == null) {
            // Dosya yolu yok — Farklı Kaydet'e yönlendir
            new SaveAsCommand(engine, fileManager, textArea, frame).execute();
            return;
        }
        try {
            fileManager.saveFile();
            frame.setTitle("SimpleEditor - " + engine.getCurrentFile().getName());
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(frame, "Kaydetme hatası: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        // Kaydetme işlemi geri alınamaz
    }
}
