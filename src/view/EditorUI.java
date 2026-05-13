package view;

import command.*;
import controller.FileManager;
import model.EditorEngine;
import model.EditorObserver;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * OBSERVER TASARIM DESENİ — Ana Pencere (View)
 *
 * <p>
 * Java Swing ile oluşturulmuş ana penceredir. Menü, metin alanı
 * ve durum çubuğunu yönetir. {@link EditorObserver} uygulayarak
 * {@link EditorEngine}'deki değişiklikleri otomatik yansıtır.
 * </p>
 *
 * <p>
 * MVC mimarisinde "View" katmanını temsil eder.
 * </p>
 *
 * @author 2. Üye (GUI), 3. Üye (Memento entegrasyonu), 4. Üye (Observer)
 */
public class EditorUI extends JFrame {

    /** Satır numaralarını gösteren alan. */
    private JTextArea lineNumbers;

    /** Kullanıcının metin girdiği alan. */
    private final JTextArea textArea;

    /** Uygulamanın merkezi veri modeli (Singleton). */
    private final EditorEngine engine;

    /** Dosya okuma/yazma işlemlerini yöneten denetleyici. */
    private final FileManager fileManager;

    /** Satır, sütun ve değişiklik bilgisini gösteren durum çubuğu. */
    private final JLabel statusBar;

    /** Memento kaydı için bir önceki metin durumu. */
    private String lastTextSnapshot;

    /**
     * EditorUI penceresini oluşturur ve tüm bileşenleri başlatır.
     */
    public EditorUI() {
        this.engine = EditorEngine.getInstance();
        this.fileManager = new FileManager();
        this.textArea = new JTextArea();
        this.statusBar = new JLabel(" Satır: 1  Sütun: 1  |  Değiştirilmedi");
        this.lastTextSnapshot = "";

        initUI();
        initMenuBar();
        initDocumentListener();
        initCaretListener();
        initWindowCloseHandler();
        initObserver();
    }

    /**
     * Pencerenin genel düzenini ve temel bileşenlerini kurar.
     */
    private void initUI() {
        setTitle("SimpleEditor - Yeni Dosya");
        setSize(800, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // Satır numaraları
        lineNumbers = new JTextArea("1");
        lineNumbers.setBackground(new Color(240, 240, 240));
        lineNumbers.setForeground(new Color(120, 120, 120));
        lineNumbers.setEditable(false);
        lineNumbers.setFont(new Font("Monospaced", Font.PLAIN, 14));
        lineNumbers.setMargin(new Insets(0, 8, 0, 8));

        // Metin değişince satır numaralarını güncelle
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers(lineNumbers);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers(lineNumbers);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers(lineNumbers);
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setRowHeaderView(lineNumbers);
        add(scrollPane, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
    }

    /**
     * Menü çubuğunu oluşturur; tüm komutları kısayol tuşlarıyla bağlar.
     */
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // --- Dosya Menüsü ---
        JMenu fileMenu = new JMenu("Dosya");
        fileMenu.setMnemonic(KeyEvent.VK_D);

        addMenuItem(fileMenu, "Yeni", KeyEvent.VK_N, new NewCommand(engine, textArea, this));
        addMenuItem(fileMenu, "Aç...", KeyEvent.VK_O, new OpenCommand(engine, fileManager, textArea, this));
        addMenuItem(fileMenu, "Kaydet", KeyEvent.VK_S, new SaveCommand(engine, fileManager, textArea, this));
        addMenuItem(fileMenu, "Farklı Kaydet...", -1, new SaveAsCommand(engine, fileManager, textArea, this));
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Çıkış", KeyEvent.VK_Q, new ExitCommand(engine, this));

        // --- Düzenle Menüsü ---
        JMenu editMenu = new JMenu("Düzenle");
        editMenu.setMnemonic(KeyEvent.VK_U);

        addMenuItem(editMenu, "Geri Al", KeyEvent.VK_Z, new UndoCommand(engine, textArea, this));
        addMenuItem(editMenu, "Yinele", KeyEvent.VK_Y, new RedoCommand(engine, textArea, this));
        editMenu.addSeparator();
        addMenuItem(editMenu, "Kes", KeyEvent.VK_X, new CutCommand(textArea));
        addMenuItem(editMenu, "Kopyala", KeyEvent.VK_C, new CopyCommand(textArea));
        addMenuItem(editMenu, "Yapıştır", KeyEvent.VK_V, new PasteCommand(textArea));
        editMenu.addSeparator();
        addMenuItem(editMenu, "Bul...", KeyEvent.VK_F, new FindCommand(engine, textArea));

        // --- Görünüm Menüsü ---
        JMenu viewMenu = new JMenu("Görünüm");

        // Koyu Tema
        JMenuItem darkTheme = new JMenuItem("Koyu Tema");
        darkTheme.addActionListener(e -> applyTheme("Dark"));
        viewMenu.add(darkTheme);

        // Açık Tema
        JMenuItem lightTheme = new JMenuItem("Açık Tema");
        lightTheme.addActionListener(e -> applyTheme("Light"));
        viewMenu.add(lightTheme);

        viewMenu.addSeparator();

        // Font Büyüt
        JMenuItem fontUp = new JMenuItem("Yazıyı Büyüt");
        fontUp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        fontUp.addActionListener(e -> changeFontSize(2));
        viewMenu.add(fontUp);

        // Font Küçült
        JMenuItem fontDown = new JMenuItem("Yazıyı Küçült");
        fontDown.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        fontDown.addActionListener(e -> changeFontSize(-2));
        viewMenu.add(fontDown);

        menuBar.add(viewMenu);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Menüye öğe ekler ve tıklandığında komutun execute() metodunu çağırır.
     */
    private void addMenuItem(JMenu menu, String label, int keyCode, Command command) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(e -> command.execute());
        if (keyCode != -1) {
            item.setAccelerator(
                    KeyStroke.getKeyStroke(keyCode, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        }
        menu.add(item);
    }

    /**
     * Metin değişikliklerini dinler.
     * Memento kaydı kelime sınırlarında (boşluk, enter vb.) yapılır.
     */
    private void initDocumentListener() {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onTextChanged();
            }
        });

        // Memento kaydını kelime sınırlarında yap (her karakterde değil)
        textArea.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int k = e.getKeyCode();
                boolean isWordBoundary = k == KeyEvent.VK_SPACE ||
                        k == KeyEvent.VK_ENTER ||
                        k == KeyEvent.VK_BACK_SPACE ||
                        k == KeyEvent.VK_DELETE ||
                        k == KeyEvent.VK_PERIOD ||
                        k == KeyEvent.VK_COMMA ||
                        k == KeyEvent.VK_X ||
                        k == KeyEvent.VK_V;

                if (isWordBoundary) {
                    engine.recordSnapshot(lastTextSnapshot);
                    lastTextSnapshot = textArea.getText();
                }
            }
        });
    }

    /**
     * Metin değiştiğinde başlığı ve durum çubuğunu günceller.
     */
    private void onTextChanged() {
        engine.setCurrentText(textArea.getText());
        String fileName = engine.getCurrentFile() != null
                ? engine.getCurrentFile().getName()
                : "Yeni Dosya";
        setTitle("SimpleEditor - " + fileName + " *");
        updateStatusBar();
    }

    /**
     * İmleç konumu değiştikçe durum çubuğunu günceller.
     */
    private void initCaretListener() {
        textArea.addCaretListener(e -> updateStatusBar());
    }

    /**
     * Durum çubuğunu güncel satır, sütun ve değişiklik bilgisiyle günceller.
     */
    private void updateStatusBar() {
        try {
            int caretPos = textArea.getCaretPosition();
            int line = textArea.getLineOfOffset(caretPos) + 1;
            int col = caretPos - textArea.getLineStartOffset(line - 1) + 1;
            String mod = engine.isModified() ? "Değiştirildi" : "Değiştirilmedi";
            statusBar.setText(" Satır: " + line + "  Sütun: " + col + "  |  " + mod);
        } catch (Exception ignored) {
        }
    }

    /**
     * Satır numaralarını günceller.
     */
    private void updateLineNumbers(JTextArea lineNumbers) {
        int lines = textArea.getLineCount();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= lines; i++) {
            sb.append(i).append("\n");
        }
        lineNumbers.setText(sb.toString());
    }

    /**
     * Koyu veya açık temayı uygular.
     * 
     * @param theme "Dark" veya "Light"
     */
    private void applyTheme(String theme) {
        engine.setTheme(theme);
        if (theme.equals("Dark")) {
            textArea.setBackground(new Color(30, 30, 30));
            textArea.setForeground(new Color(212, 212, 212));
            textArea.setCaretColor(Color.WHITE);
            getContentPane().setBackground(new Color(30, 30, 30));
            statusBar.setBackground(new Color(50, 50, 50));
            statusBar.setForeground(new Color(212, 212, 212));
            statusBar.setOpaque(true);
            lineNumbers.setBackground(new Color(40, 40, 40));
            lineNumbers.setForeground(new Color(150, 150, 150));
        } else {
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
            textArea.setCaretColor(Color.BLACK);
            getContentPane().setBackground(Color.WHITE);
            statusBar.setBackground(null);
            statusBar.setForeground(Color.BLACK);
            statusBar.setOpaque(false);
            lineNumbers.setBackground(new Color(240, 240, 240));
            lineNumbers.setForeground(new Color(120, 120, 120));
        }
    }

    /**
     * Font boyutunu değiştirir.
     * 
     * @param delta artış veya azalış miktarı (punto)
     */
    private void changeFontSize(int delta) {
        int newSize = engine.getFontSize() + delta;
        if (newSize < 8 || newSize > 72)
            return;
        engine.setFontSize(newSize);
        textArea.setFont(new Font(engine.getFontFamily(), Font.PLAIN, newSize));
        lineNumbers.setFont(new Font("Monospaced", Font.PLAIN, newSize));
    }

    /**
     * Pencere kapatma butonuna basıldığında ExitCommand çalıştırır.
     */
    private void initWindowCloseHandler() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new ExitCommand(engine, EditorUI.this).execute();
            }
        });
    }

    /**
     * OBSERVER TASARIM DESENİ — EditorEngine'e gözlemci kaydeder.
     * Engine'deki her değişiklikte başlık ve durum çubuğu otomatik güncellenir.
     */
    private void initObserver() {
        engine.addObserver(new EditorObserver() {
            @Override
            public void onEngineStateChanged(EditorEngine eng) {
                String fileName = eng.getCurrentFile() != null
                        ? eng.getCurrentFile().getName()
                        : "Yeni Dosya";
                setTitle("SimpleEditor - " + fileName + (eng.isModified() ? " *" : ""));
                updateStatusBar();
                // Undo/Redo sonrası metni güncelle (sonsuz döngüyü önle)
                if (!textArea.getText().equals(eng.getCurrentText())) {
                    textArea.setText(eng.getCurrentText());
                    lastTextSnapshot = textArea.getText();
                }
            }
        });
    }

    /**
     * @return metin alanı bileşeni
     */
    public JTextArea getTextArea() {
        return textArea;
    }

    /**
     * @return durum çubuğu bileşeni
     */
    public JLabel getStatusBar() {
        return statusBar;
    }

    /**
     * Pencere başlığını ve durum çubuğunu günceller.
     * 
     * @param fileName gösterilecek dosya adı
     * @param modified değiştirilmişse true
     */
    public void updateTitle(String fileName, boolean modified) {
        setTitle("SimpleEditor - " + fileName + (modified ? " *" : ""));
        updateStatusBar();
    }
}
