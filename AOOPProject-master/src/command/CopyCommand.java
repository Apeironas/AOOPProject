package command;

import javax.swing.*;

public class CopyCommand implements Command {

    private final JTextArea textArea;

    public CopyCommand(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void execute() {
        textArea.copy();
    }

    @Override
    public void undo() {
        // Kopyalama işlemi geri alınamaz
    }
}
