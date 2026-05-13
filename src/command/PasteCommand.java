package command;

import javax.swing.*;

/**
 * COMMAND TASARIM DESENİ — Yapıştır Komutu
 * Panodan metin yapıştırır. {@link JTextArea}'nın yerleşik
 * {@code paste()} metodu kullanılır.
 * Yapıştırma işleminin geri alınması {@link model.History} (Memento)
 * sistemi aracılığıyla {@link UndoCommand} tarafından yönetilir.
 */
public class PasteCommand implements Command {

    private final JTextArea textArea; // Yapıştırma işleminin yapılacağı metin alanı

    /**
     * PasteCommand oluşturur.
     *
     * @param textArea yapıştırmanın yapılacağı metin alanı
     */
    public PasteCommand(JTextArea textArea) {
        this.textArea = textArea;
    }

    /**
     * Panodan metin yapıştırır.
     */
    @Override
    public void execute() {
        textArea.paste();
    }

    /**
     * Yapıştırma işlemi Memento sistemi aracılığıyla geri alınır.
     * Doğrudan bu metot üzerinden geri alma yapılmaz.
     */
    @Override
    public void undo() {
        // Memento sistemiyle (UndoCommand) geri alınır
    }
}
