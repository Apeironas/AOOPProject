package command;

import javax.swing.*;

/**
 * COMMAND TASARIM DESENİ — Kes Komutu
 *
 * <p>Seçili metni panoya taşır (kes). {@link JTextArea}'nın yerleşik
 * {@code cut()} metodu kullanılır.</p>
 *
 * <p>{@code undo()} çağrıldığında panodaki metin geri yapıştırılır.</p>
 *
 * @author 2. Üye
 */
public class CutCommand implements Command {

    /** Kesme işleminin yapılacağı metin alanı. */
    private final JTextArea textArea;

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
