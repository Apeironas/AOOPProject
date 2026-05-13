import view.EditorUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            EditorUI ui = new EditorUI();
            ui.setVisible(true);
        });
    }
}
