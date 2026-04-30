package command;

import model.EditorEngine;

import javax.swing.*;

// 3. üyenin arama metodu hazır olduğunda bu sınıf güncellenerek ona delege edilecek
public class FindCommand implements Command {

    private final EditorEngine engine;
    private final JTextArea textArea;

    public FindCommand(EditorEngine engine, JTextArea textArea) {
        this.engine = engine;
        this.textArea = textArea;
    }

    @Override
    public void execute() {
        String keyword = JOptionPane.showInputDialog(null, "Aranacak kelimeyi girin:");
        if (keyword == null || keyword.isEmpty()) return;

        String text = textArea.getText();
        int index = text.indexOf(keyword);
        if (index >= 0) {
            textArea.setCaretPosition(index);
            textArea.select(index, index + keyword.length());
        } else {
            JOptionPane.showMessageDialog(null, "\"" + keyword + "\" bulunamadı.");
        }
    }

    @Override
    public void undo() {
        // Arama işlemi geri alınamaz
    }
}
