package command;

import javax.swing.*;

/**
 * COMMAND TASARIM DESENİ — Kes Komutu
 * Seçili metni panoya taşır (kes). {@link JTextArea}'nın yerleşik
 * {@code cut()} metodu kullanılır.
 * {@code undo()} çağrıldığında panodaki metin geri yapıştırılır.
 */
public class CutCommand implements Command {

    private final JTextArea textArea; // Kesme işleminin yapılacağı metin alanı

    /**
     * CutCommand oluşturur.
     *
     * @param textArea kesme işleminin yapılacağı metin alanı
     */
    public CutCommand(JTextArea textArea) {
        this.textArea = textArea;
    }

    /**
     * Seçili metni keser ve panoya taşır.
     */
    @Override
    public void execute() {
        textArea.cut();
    }

    /**
     * Kesilen metni geri yapıştırır.
     */
    @Override
    public void undo() {
        textArea.paste();
    }
}
