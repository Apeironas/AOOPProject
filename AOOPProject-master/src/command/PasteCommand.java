package command;

import javax.swing.*;

public class PasteCommand implements Command {

    private final JTextArea textArea;

    public PasteCommand(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void execute() {
        textArea.paste();
    }

    @Override
    public void undo() {
        // 3. üyenin Memento sistemiyle koordineli olarak uygulanacak
    }
}
