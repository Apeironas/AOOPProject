package model;

import java.io.File;

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

    // Konfigürasyon Ayarları
    private int fontSize;
    private String theme; // Örn: "Light", "Dark"
    private String fontFamily;

    private EditorEngine() {
        this.currentText = "";
        this.currentFile = null;
        this.isModified = false;

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
    }

    public File getCurrentFile() { return currentFile; }
    public void setCurrentFile(File file) { this.currentFile = file; }

    public boolean isModified() { return isModified; }
    public void setModified(boolean modified) { isModified = modified; }

    // --- Konfigürasyon Getter/Setter (Tema ve Font Yönetimi) ---
    public int getFontSize() { return fontSize; }
    public void setFontSize(int fontSize) { this.fontSize = fontSize; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String getFontFamily() { return fontFamily; }
    public void setFontFamily(String fontFamily) { this.fontFamily = fontFamily; }
}