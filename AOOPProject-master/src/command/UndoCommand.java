package command;

import model.EditorEngine;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Toolkit;

public class UndoCommand implements Command {

    private final EditorEngine engine;
    private final JTextArea textArea;
    private final JFrame frame;

    public UndoCommand(EditorEngine engine, JTextArea textArea, JFrame frame) {
        this.engine = engine;
        this.textArea = textArea;
        this.frame = frame;
    }

    @Override
    public void execute() {
        if (!engine.canUndo()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        String previous = engine.undoText(textArea.getText());
        textArea.setText(previous);
    }

    @Override
    public void undo() {}
}
