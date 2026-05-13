import view.EditorUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/*SimpleEditor Uygulamasının Başlangıç Noktası
Uygulamayı başlatır ve ana pencereyi EditorUI oluşturur.
Swing bileşenlerinin güvenli şekilde oluşturulması için
SwingUtilities#invokeLater kullanılır; bu sayede
arayüz işlemleri Event Dispatch Thread (EDT) üzerinde çalışır.
*/
public class Main {

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
