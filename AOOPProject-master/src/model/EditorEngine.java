package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * SINGLETON TASARIM DESENİ
 * Uygulamanın merkezi veri deposudur. Metin içeriği, dosya durumu ve
 * kullanıcı tercihlerini (font, tema) yönetir.
 */
public class EditorEngine {
    private static EditorEngine instance;

    // Metin ve Dosya Durumu
    private String currentText;
    private File currentFile;
    private boolean isModified;

    // Memento (Undo geçmişi)
    private final UndoHistory undoHistory;

    // Observer
    private final List<EditorObserver> observers;

    // Konfigürasyon Ayarları
    private int fontSize;
    private String theme; // Örn: "Light", "Dark"
    private String fontFamily;

    private EditorEngine() {
        this.currentText = "";
        this.currentFile = null;
        this.isModified = false;
        this.undoHistory = new UndoHistory(200);
        this.observers = new ArrayList<>();

        // Varsayılan Ayarlar
        this.fontSize = 12;
        this.theme = "Light";
        this.fontFamily = "Monospaced";
    }

    public static synchronized EditorEngine getInstance() {
        if (instance == null) {
            instance = new EditorEngine();
        }
        return instance;
    }

    // --- Metin ve Dosya Getter/Setter ---
    public String getCurrentText() { return currentText; }
    public void setCurrentText(String text) {
        this.currentText = text;
        this.isModified = true;
        notifyObservers();
    }

    public File getCurrentFile() { return currentFile; }
    public void setCurrentFile(File file) {
        this.currentFile = file;
        notifyObservers();
    }

    public boolean isModified() { return isModified; }
    public void setModified(boolean modified) {
        isModified = modified;
        notifyObservers();
    }

    // --- Memento (Undo) API ---
    public void recordSnapshot(String textBeforeChange) {
        undoHistory.push(textBeforeChange);
    }

    public boolean canUndo() {
        return undoHistory.canUndo();
    }

    /**
     * Son kaydedilmiş anlık görüntüye geri döner.
     * Not: Bu metot {@link #setCurrentText(String)} çağırmaz; aksi halde tekrar snapshot alınabilir.
     */
    public String undoText(String currentTextFallback) {
        String previous = undoHistory.pop();
        if (previous == null) return currentTextFallback;
        this.currentText = previous;
        this.isModified = true;
        notifyObservers();
        return previous;
    }

    public void clearHistory() {
        undoHistory.clear();
    }

    // --- Observer API ---
    public void addObserver(EditorObserver observer) {
        if (observer == null) return;
        if (!observers.contains(observer)) observers.add(observer);
    }

    public void removeObserver(EditorObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (EditorObserver o : new ArrayList<>(observers)) {
            try {
                o.onEngineStateChanged(this);
            } catch (Exception ignored) {}
        }
    }

    // --- Konfigürasyon Getter/Setter (Tema ve Font Yönetimi) ---
    public int getFontSize() { return fontSize; }
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        notifyObservers();
    }

    public String getTheme() { return theme; }
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