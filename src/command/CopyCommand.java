package command;

import javax.swing.*;

/**
 * COMMAND TASARIM DESENİ — Kopyala Komutu
 *
 * <p>Seçili metni panoya kopyalar. {@link JTextArea}'nın yerleşik
 * {@code copy()} metodu kullanılır.</p>
 *
 * <p>Kopyalama işlemi geri alınamaz; {@code undo()} boş bırakılmıştır.</p>
 *
 * @author 2. Üye
 */
public class CopyCommand implements Command {

    /** Kopyalama işleminin yapılacağı metin alanı. */
    private final JTextArea textArea;

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
