package command;

/**
 * COMMAND TASARIM DESENİ — Komut Arayüzü
 *
 * <p>
 * Uygulamadaki tüm işlemlerin (aç, kaydet, kes, kopyala vb.) uygulaması
 * gereken temel arayüzdür. Her somut komut sınıfı bu arayüzü uygular.
 * </p>
 *
 * <p>
 * Bu yapı sayesinde {@link view.EditorUI} hangi komutu çalıştırdığını
 * bilmek zorunda kalmaz; sadece {@code execute()} çağırır.
 * </p>
 *
 * 
 */
public interface Command {

    /**
     * Komutu çalıştırır.
     */
    void execute();

    /**
     * Komutun etkisini geri alır.
     * Geri alınamaz komutlar (kopyala, kaydet vb.) bu metodu boş bırakır.
     */
    void undo();
}
