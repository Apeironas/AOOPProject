package command;

import model.EditorEngine;
import model.TextDocument;

import javax.swing.*;

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
        TextDocument doc = new TextDocument(text);

        // Iterator kullanarak satır satır gezinip ilk eşleşmeyi bul
        int offset = 0;
        int foundIndex = -1;
        for (String line : doc.lines()) {
            int i = line.indexOf(keyword);
            if (i >= 0) {
                foundIndex = offset + i;
                break;
            }
            // satır sonu için: metindeki gerçek newline uzunluğunu kaba şekilde 1 kabul ediyoruz;
            // JVM'de JTextArea '\n' ile çalıştığı için bu yeterli.
            offset += line.length() + 1;
        }

        if (foundIndex >= 0) {
            textArea.setCaretPosition(foundIndex);
            textArea.select(foundIndex, foundIndex + keyword.length());
        } else {
            JOptionPane.showMessageDialog(null, "\"" + keyword + "\" bulunamadı.");
        }
    }

    @Override
    public void undo() {
        // Arama işlemi geri alınamaz
    }
}
