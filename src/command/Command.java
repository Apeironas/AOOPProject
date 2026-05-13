package command;

/**
 * COMMAND TASARIM DESENİ — Komut Arayüzü
 * Uygulamadaki tüm işlemlerin (aç, kaydet, kes, kopyala vb.) uygulaması
 * gereken temel arayüzdür. Her somut komut sınıfı bu arayüzü uygular.
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
