package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * SINGLETON + OBSERVER TASARIM DESENİ — Merkezi Veri Modeli
 *
 * <p>Uygulamanın tek ve merkezi veri deposudur. Metin içeriği,
 * dosya durumu ve kullanıcı tercihlerini (font, tema) yönetir.</p>
 *
 * <p>Singleton Pattern: tüm sınıflar {@link #getInstance()} ile
 * aynı nesneye erişir.</p>
 *
 * <p>Observer Pattern: durum değişikliklerinde kayıtlı
 * {@link EditorObserver}'lar otomatik bilgilendirilir.</p>
 *
 * @author 1. Üye (Singleton), 4. Üye (Observer + UndoHistory entegrasyonu)
 */
public class EditorEngine {

    private static EditorEngine instance;

    // Metin ve Dosya Durumu
    private String currentText;
    private File currentFile;
    private boolean isModified;

    // Memento — Undo/Redo geçmişi
    private final UndoHistory undoHistory;
    private final UndoHistory redoHistory;

    // Observer listesi
    private final List<EditorObserver> observers;

    // Konfigürasyon Ayarları
    private int fontSize;
    private String theme;
    private String fontFamily;

    private EditorEngine() {
        this.currentText  = "";
        this.currentFile  = null;
        this.isModified   = false;
        this.undoHistory  = new UndoHistory(200);
        this.redoHistory  = new UndoHistory(200);
        this.observers    = new ArrayList<>();
        this.fontSize     = 12;
        this.theme        = "Light";
        this.fontFamily   = "Monospaced";
    }

    /**
     * Thread-safe Singleton erişimi.
     *
     * @return tek EditorEngine örneği
     */
    public static synchronized EditorEngine getInstance() {
        if (instance == null) instance = new EditorEngine();
        return instance;
    }

    // --- Metin ve Dosya Getter/Setter ---

    /** @return güncel metin içeriği */
    public String getCurrentText() { return currentText; }

    /**
     * Metin içeriğini günceller ve Observer'ları bilgilendirir.
     * @param text yeni metin
     */
    public void setCurrentText(String text) {
        this.currentText = text;
        this.isModified  = true;
        notifyObservers();
    }

    /** @return açık dosya; henüz kaydedilmemişse {@code null} */
    public File getCurrentFile() { return currentFile; }

    public void setCurrentFile(File file) {
        this.currentFile = file;
        notifyObservers();
    }

    /** @return kaydedilmemiş değişiklik varsa {@code true} */
    public boolean isModified() { return isModified; }

    public void setModified(boolean modified) {
        this.isModified = modified;
        notifyObservers();
    }

    // --- Memento (Undo/Redo) API ---

    /**
     * Değişiklik öncesi metin durumunu undo yığınına kaydeder.
     * Redo yığını temizlenir (yeni değişiklik redo geçmişini geçersiz kılar).
     *
     * @param textBeforeChange değişiklik öncesi metin
     */
    public void recordSnapshot(String textBeforeChange) {
        undoHistory.push(textBeforeChange);
        redoHistory.clear();
    }

    /** @return geri alınabilecek kayıt varsa {@code true} */
    public boolean canUndo() { return undoHistory.canUndo(); }

    /** @return yinelenebilecek kayıt varsa {@code true} */
    public boolean canRedo() { return redoHistory.canUndo(); }

    /**
     * Bir önceki duruma döner (Undo).
     * Mevcut metin redo yığınına kaydedilir.
     *
     * @param currentTextFallback geri alınacak kayıt yoksa döndürülecek metin
     * @return geri alınan metin
     */
    public String undoText(String currentTextFallback) {
        String previous = undoHistory.pop();
        if (previous == null) return currentTextFallback;
        redoHistory.push(this.currentText);
        this.currentText = previous;
        this.isModified  = true;
        notifyObservers();
        return previous;
    }

    /**
     * Geri alınan durumu yeniden uygular (Redo).
     * Mevcut metin undo yığınına kaydedilir.
     *
     * @param currentTextFallback yinelenecek kayıt yoksa döndürülecek metin
     * @return yinelenen metin
     */
    public String redoText(String currentTextFallback) {
        String next = redoHistory.pop();
        if (next == null) return currentTextFallback;
        undoHistory.push(this.currentText);
        this.currentText = next;
        this.isModified  = true;
        notifyObservers();
        return next;
    }

    /** Undo ve Redo geçmişini temizler. */
    public void clearHistory() {
        undoHistory.clear();
        redoHistory.clear();
    }

    // --- Observer API ---

    /**
     * Observer kaydeder.
     * @param observer eklenecek gözlemci
     */
    public void addObserver(EditorObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    /** @param observer kaldırılacak gözlemci */
    public void removeObserver(EditorObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (EditorObserver o : new ArrayList<>(observers)) {
            try { o.onEngineStateChanged(this); } catch (Exception ignored) {}
        }
    }

    // --- Konfigürasyon Getter/Setter ---

    public int getFontSize()    { return fontSize; }
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        notifyObservers();
    }

    public String getTheme()    { return theme; }
    public void setTheme(String theme) {
        this.theme = theme;
        notifyObservers();
    }

    public String getFontFamily() { return fontFamily; }
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        notifyObservers();
    }
}
