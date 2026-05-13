package command;

import javax.swing.*;

/**
 * COMMAND TASARIM DESENİ — Kopyala Komutu
 * Seçili metni panoya kopyalar. {@link JTextArea}'nın yerleşik
 * {@code copy()} metodu kullanılır.
 * Kopyalama işlemi geri alınamaz.
 */
public class CopyCommand implements Command {

    private final JTextArea textArea; // Kopyalama işleminin yapılacağı metin alanı

    /**
     * CopyCommand oluşturur.
     *
     * @param textArea kopyalamanın yapılacağı metin alanı
     */
    public CopyCommand(JTextArea textArea) {
        this.textArea = textArea;
    }

    /**
     * Seçili metni panoya kopyalar.
     */
    @Override
    public void execute() {
        textArea.copy();
    }

    /**
     * Kopyalama işlemi geri alınamaz.
     */
    @Override
    public void undo() {
        // Kopyalama işlemi geri alınamaz
    }
}
