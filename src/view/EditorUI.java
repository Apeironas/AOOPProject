package view;

import command.*;
import controller.FileManager;
import model.EditorEngine;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditorUI extends JFrame {

    private final JTextArea textArea;
    private final EditorEngine engine;
    private final FileManager fileManager;

    // 4. üyenin Observer'ı bu label'a bağlanacak
    private final JLabel statusBar;

    public EditorUI() {
        this.engine = EditorEngine.getInstance();
        this.fileManager = new FileManager();
        this.textArea = new JTextArea();
        this.statusBar = new JLabel(" Satır: 1  Sütun: 1  |  Değiştirilmedi");

        initUI();
        initMenuBar();
        initDocumentListener();
        initCaretListener();
        initWindowCloseHandler();
    }

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

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // --- Dosya Menüsü ---
        JMenu fileMenu = new JMenu("Dosya");
        fileMenu.setMnemonic(KeyEvent.VK_D);

        addMenuItem(fileMenu, "Yeni",             KeyEvent.VK_N, new NewCommand(engine, textArea, this));
        addMenuItem(fileMenu, "Aç...",            KeyEvent.VK_O, new OpenCommand(engine, fileManager, textArea, this));
        addMenuItem(fileMenu, "Kaydet",           KeyEvent.VK_S, new SaveCommand(engine, fileManager, textArea, this));
        addMenuItem(fileMenu, "Farklı Kaydet...", -1,            new SaveAsCommand(engine, fileManager, textArea, this));
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Çıkış",            KeyEvent.VK_Q, new ExitCommand(engine, this));

        // --- Düzenle Menüsü ---
        JMenu editMenu = new JMenu("Düzenle");
        editMenu.setMnemonic(KeyEvent.VK_U);

        addMenuItem(editMenu, "Geri Al", KeyEvent.VK_Z, new UndoCommand());
        editMenu.addSeparator();
        addMenuItem(editMenu, "Kes",      KeyEvent.VK_X, new CutCommand(textArea));
        addMenuItem(editMenu, "Kopyala",  KeyEvent.VK_C, new CopyCommand(textArea));
        addMenuItem(editMenu, "Yapıştır", KeyEvent.VK_V, new PasteCommand(textArea));
        editMenu.addSeparator();
        addMenuItem(editMenu, "Bul...",   KeyEvent.VK_F, new FindCommand(engine, textArea));

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);
    }

    private void addMenuItem(JMenu menu, String label, int keyCode, Command command) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(e -> command.execute());
        if (keyCode != -1) {
            item.setAccelerator(
                KeyStroke.getKeyStroke(keyCode, java.awt.event.InputEvent.CTRL_DOWN_MASK)
            );
        }
        menu.add(item);
    }

    private void initDocumentListener() {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { onTextChanged(); }
            @Override public void removeUpdate(DocumentEvent e)  { onTextChanged(); }
            @Override public void changedUpdate(DocumentEvent e) { onTextChanged(); }
        });
    }

    private void onTextChanged() {
        engine.setCurrentText(textArea.getText());
        String fileName = engine.getCurrentFile() != null
            ? engine.getCurrentFile().getName()
            : "Yeni Dosya";
        setTitle("SimpleEditor - " + fileName + " *");
        updateStatusBar();
    }

    // Satır/sütun bilgisini günceller — 4. üyenin Observer'ı da buraya eklenecek
    private void initCaretListener() {
        textArea.addCaretListener(e -> updateStatusBar());
    }

    private void updateStatusBar() {
        try {
            int caretPos = textArea.getCaretPosition();
            int line = textArea.getLineOfOffset(caretPos) + 1;
            int col  = caretPos - textArea.getLineStartOffset(line - 1) + 1;
            String mod = engine.isModified() ? "Değiştirildi" : "Değiştirilmedi";
            statusBar.setText(" Satır: " + line + "  Sütun: " + col + "  |  " + mod);
        } catch (Exception ignored) {}
    }

    private void initWindowCloseHandler() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new ExitCommand(engine, EditorUI.this).execute();
            }
        });
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JLabel getStatusBar() {
        return statusBar;
    }

    public void updateTitle(String fileName, boolean modified) {
        setTitle("SimpleEditor - " + fileName + (modified ? " *" : ""));
        updateStatusBar();
    }
}
