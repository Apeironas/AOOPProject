import view.EditorUI;
import javax.swing.SwingUtilities;

/**
 * SimpleEditor Uygulamasının Başlangıç Noktası
 *
 * <p>Uygulamayı başlatır ve ana pencereyi ({@link EditorUI}) oluşturur.
 * Swing bileşenlerinin güvenli şekilde oluşturulması için
 * {@link SwingUtilities#invokeLater} kullanılır; bu sayede
 * arayüz işlemleri Event Dispatch Thread (EDT) üzerinde çalışır.</p>
 *
 * @author 1. Üye
 */
public class Main {

    /**
     * Uygulamanın giriş noktası.
     *
     * @param args komut satırı argümanları (kullanılmıyor)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditorUI ui = new EditorUI();
            ui.setVisible(true);
        });
    }
}
