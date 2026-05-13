package command;

import model.EditorEngine;

import javax.swing.*;

/**
 * COMMAND TASARIM DESENİ — Bul Komutu
 *
 * <p>Metin alanı içinde anahtar kelime araması yapar.
 * Kullanıcıdan aranacak kelimeyi alır, büyük/küçük harf duyarsız biçimde
 * arar ve bulduğu ilk eşleşmeyi seçili hale getirir.</p>
 *
 * <p>Arama işlemi geri alınamaz; {@code undo()} boş bırakılmıştır.</p>
 *
 * @author 3. Üye
 */
public class FindCommand implements Command {

    /** Uygulamanın merkezi veri modeli. */
    private final EditorEngine engine;

    /** Aramanın yapılacağı metin alanı. */
    private final JTextArea textArea;

    /** Bir önceki aramanın bittiği konum; sonraki eşleşmeye geçmek için kullanılır. */
    private int lastFoundIndex = -1;

    /** En son aranan anahtar kelime. */
    private String lastKeyword = "";

    /**
     * FindCommand oluşturur.
     *
     * @param engine   uygulama veri modeli
     * @param textArea aramanın yapılacağı metin alanı
     */
    public FindCommand(EditorEngine engine, JTextArea textArea) {
        this.engine = engine;
        this.textArea = textArea;
    }

    /**
     * Kullanıcıdan anahtar kelime alır ve metin içinde arar.
     * Aynı kelime tekrar aranırsa sonraki eşleşmeye geçer.
     * Büyük/küçük harf duyarsız arama yapılır.
     */
    @Override
    public void execute() {
        String keyword = JOptionPane.showInputDialog(null, "Aranacak kelimeyi girin:");
        if (keyword == null || keyword.isEmpty()) return;

        String text = textArea.getText();
        String textLower    = text.toLowerCase();
        String keywordLower = keyword.toLowerCase();

        // Farklı bir kelime arandıysa baştan başla
        if (!keyword.equalsIgnoreCase(lastKeyword)) {
            lastFoundIndex = -1;
            lastKeyword    = keyword;
        }

        // Bir sonraki eşleşmeyi bul
        int startFrom = lastFoundIndex + 1;
        int index = textLower.indexOf(keywordLower, startFrom);

        // Sona gelindiyse başa dön
        if (index == -1 && startFrom > 0) {
            index = textLower.indexOf(keywordLower, 0);
        }

        if (index >= 0) {
            textArea.setCaretPosition(index);
            textArea.select(index, index + keyword.length());
            lastFoundIndex = index;
        } else {
            JOptionPane.showMessageDialog(null,
                "\"" + keyword + "\" bulunamadı.",
                "Bul",
                JOptionPane.INFORMATION_MESSAGE);
            lastFoundIndex = -1;
        }
    }

    /**
     * Arama işlemi geri alınamaz.
     */
    @Override
    public void undo() {
        // Arama işlemi geri alınamaz
    }
}
