package command;

import javax.swing.*;

public class CutCommand implements Command {

    private final JTextArea textArea;

    public CutCommand(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void execute() {
        textArea.cut();
    }

    @Override
    public void undo() {
        textArea.paste();
    }
}
