package SubMenuOption;

import Menu.Ajustes;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.*;

public class ControlSettings extends JFrame implements ActionListener {
    
    private JButton guardarBtn, regresarBtn, defaultBtn;
    private JTextField controlArriba, controlAbajo, controlIzquierda, controlDerecha, controlReiniciar;
    private JLabel labelArriba, labelAbajo, labelIzquierda, labelDerecha, labelReiniciar, labelTitulo;
    private String usuarioActual;
    private JLabel fondo;
    private ResourceBundle messages;
    private Map<JTextField, Integer> keyCodeMap = new HashMap<>();
    
    public ControlSettings(String usuario) {
        this.usuarioActual = usuario;
        
        this.messages = LanguageManager.getMessages();
        
        initComponents();
        loadUserControls(); 
        updateControlLabels(); 
    }
    
    public ControlSettings(String usuario, Locale locale) {
        this.usuarioActual = usuario;
        this.messages = ResourceBundle.getBundle("messages", locale);
        initComponents();
        loadUserControls();
        updateControlLabels();
    }
    
    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(messages.getString("title.controls"));
        this.setLayout(null);
        this.setSize(800, 450);
        this.setLocationRelativeTo(null);
        
        ImageIcon imagenFondo = new ImageIcon("src/imagescan/fondogui.png");
        fondo = new JLabel(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 450);
        
        labelArriba = new JLabel(messages.getString("controls.moveUp"));
        labelArriba.setForeground(Color.white);
        labelArriba.setFont(new Font("Arial", Font.PLAIN, 14));
        labelArriba.setBounds(250, 120, 100, 30);
        this.add(labelArriba);
        
        controlArriba = new JTextField("W");
        controlArriba.setBounds(350, 120, 100, 30);
        setupKeyField(controlArriba, KeyEvent.VK_W);
        this.add(controlArriba);
       
        labelAbajo = new JLabel(messages.getString("controls.moveDown"));
        labelAbajo.setForeground(Color.white);
        labelAbajo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelAbajo.setBounds(250, 160, 100, 30);
        this.add(labelAbajo);
        
        controlAbajo = new JTextField("S");
        controlAbajo.setBounds(350, 160, 100, 30);
        setupKeyField(controlAbajo, KeyEvent.VK_S);
        this.add(controlAbajo);
        
        labelIzquierda = new JLabel(messages.getString("controls.moveLeft"));
        labelIzquierda.setForeground(Color.white);
        labelIzquierda.setFont(new Font("Arial", Font.PLAIN, 14));
        labelIzquierda.setBounds(250, 200, 100, 30);
        this.add(labelIzquierda);
        
        controlIzquierda = new JTextField("A");
        controlIzquierda.setBounds(350, 200, 100, 30);
        setupKeyField(controlIzquierda, KeyEvent.VK_A);
        this.add(controlIzquierda);
        
        labelDerecha = new JLabel(messages.getString("controls.moveRight"));
        labelDerecha.setForeground(Color.white);
        labelDerecha.setFont(new Font("Arial", Font.PLAIN, 14));
        labelDerecha.setBounds(250, 240, 100, 30);
        this.add(labelDerecha);
        
        controlDerecha = new JTextField("D");
        controlDerecha.setBounds(350, 240, 100, 30);
        setupKeyField(controlDerecha, KeyEvent.VK_D);
        this.add(controlDerecha);
        
        labelReiniciar = new JLabel(messages.getString("controls.reset"));
        labelReiniciar.setForeground(Color.white);
        labelReiniciar.setFont(new Font("Arial", Font.PLAIN, 14));
        labelReiniciar.setBounds(250, 280, 100, 30);
        this.add(labelReiniciar);
        
        controlReiniciar = new JTextField("R");
        controlReiniciar.setBounds(350, 280, 100, 30);
        setupKeyField(controlReiniciar, KeyEvent.VK_R);
        this.add(controlReiniciar);
        
        guardarBtn = new JButton(messages.getString("button.save"));
        guardarBtn.setBounds(200, 340, 130, 30);
        guardarBtn.addActionListener(this);
        this.add(guardarBtn);
        
        defaultBtn = new JButton(messages.getString("button.default") != null ? messages.getString("button.default") : "Default Controls");
        defaultBtn.setBounds(340, 340, 130, 30);
        defaultBtn.addActionListener(this);
        this.add(defaultBtn);
        
        regresarBtn = new JButton(messages.getString("button.back"));
        regresarBtn.setBounds(480, 340, 130, 30);
        regresarBtn.addActionListener(this);
        this.add(regresarBtn);
        
        this.add(fondo);
    }
    
    private void updateControlLabels() {
        labelArriba.setText(messages.getString("controls.moveUp") + ": " + controlArriba.getText());
        labelAbajo.setText(messages.getString("controls.moveDown") + ": " + controlAbajo.getText());
        labelIzquierda.setText(messages.getString("controls.moveLeft") + ": " + controlIzquierda.getText());
        labelDerecha.setText(messages.getString("controls.moveRight") + ": " + controlDerecha.getText());
        labelReiniciar.setText(messages.getString("controls.reset") + ": " + controlReiniciar.getText());
    }
    
    private void setupKeyField(JTextField field, int defaultKeyCode) {
        limitToSingleChar(field);
        keyCodeMap.put(field, defaultKeyCode);
        
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                field.setText(KeyEvent.getKeyText(keyCode));
                keyCodeMap.put(field, keyCode);
                updateControlLabels();
                e.consume();
            }
        });
        field.setEditable(false);
    }
    
    private void limitToSingleChar(JTextField textField) {
        AbstractDocument document = (AbstractDocument) textField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String resultText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                
                if (resultText.length() <= 1) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
            
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String resultText = currentText.substring(0, offset) + text + currentText.substring(offset);
                
                if (resultText.length() <= 1) {
                    super.insertString(fb, offset, text, attr);
                }
            }
        });
    }
        private void loadUserControls() {
        try {
            char[] controls = Juego.ControlManager.loadControls(usuarioActual);
            
            setControlFromChar(controlArriba, controls[0], KeyEvent.VK_W);
            setControlFromChar(controlAbajo, controls[1], KeyEvent.VK_S);
            setControlFromChar(controlIzquierda, controls[2], KeyEvent.VK_A);
            setControlFromChar(controlDerecha, controls[3], KeyEvent.VK_D);
            setControlFromChar(controlReiniciar, controls[4], KeyEvent.VK_R);
        } catch (Exception e) {
            e.printStackTrace();
            resetToDefaultControls();
        }
    }
    
    private void resetToDefaultControls() {
        controlArriba.setText(KeyEvent.getKeyText(KeyEvent.VK_W));
        keyCodeMap.put(controlArriba, KeyEvent.VK_W);
        
        controlAbajo.setText(KeyEvent.getKeyText(KeyEvent.VK_S));
        keyCodeMap.put(controlAbajo, KeyEvent.VK_S);
        
        controlIzquierda.setText(KeyEvent.getKeyText(KeyEvent.VK_A));
        keyCodeMap.put(controlIzquierda, KeyEvent.VK_A);
        
        controlDerecha.setText(KeyEvent.getKeyText(KeyEvent.VK_D));
        keyCodeMap.put(controlDerecha, KeyEvent.VK_D);
        
        controlReiniciar.setText(KeyEvent.getKeyText(KeyEvent.VK_R));
        keyCodeMap.put(controlReiniciar, KeyEvent.VK_R);
        
        updateControlLabels();
    }
    
    private void setControlFromChar(JTextField field, char control, int defaultKeyCode) {
        int keyCode;
        
        switch (control) {
            case '^': keyCode = KeyEvent.VK_UP; break;
            case 'v': keyCode = KeyEvent.VK_DOWN; break;
            case '<': keyCode = KeyEvent.VK_LEFT; break;
            case '>': keyCode = KeyEvent.VK_RIGHT; break;
            default: keyCode = (int) Character.toUpperCase(control); break;
        }
        
        field.setText(KeyEvent.getKeyText(keyCode));
        keyCodeMap.put(field, keyCode);
    }
    
    private boolean saveUserControls() {
        try {
            int upKeyCode = keyCodeMap.get(controlArriba);
            int downKeyCode = keyCodeMap.get(controlAbajo);
            int leftKeyCode = keyCodeMap.get(controlIzquierda);
            int rightKeyCode = keyCodeMap.get(controlDerecha);
            int resetKeyCode = keyCodeMap.get(controlReiniciar);
            
            if (hasDuplicateKeys(upKeyCode, downKeyCode, leftKeyCode, rightKeyCode, resetKeyCode)) {
                JOptionPane.showMessageDialog(
                    this,
                    messages.getString("dialog.duplicateKeys") != null ? 
                        messages.getString("dialog.duplicateKeys") : 
                        "Duplicate keys are not allowed. Please use different keys for each control.",
                    messages.getString("dialog.error") != null ? 
                        messages.getString("dialog.error") : 
                        "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
            
            char upKey = keyCodeToChar(upKeyCode);
            char downKey = keyCodeToChar(downKeyCode);
            char leftKey = keyCodeToChar(leftKeyCode);
            char rightKey = keyCodeToChar(rightKeyCode);
            char resetKey = keyCodeToChar(resetKeyCode);
            
            Juego.ControlManager.saveControls(usuarioActual, upKey, downKey, leftKey, rightKey, resetKey);
            
            Juego.ControlSettings.UP_KEY = upKey;
            Juego.ControlSettings.DOWN_KEY = downKey;
            Juego.ControlSettings.LEFT_KEY = leftKey;
            Juego.ControlSettings.RIGHT_KEY = rightKey;
            Juego.ControlSettings.RESET_KEY = resetKey;
            
            updateControlLabels();
            
            JOptionPane.showMessageDialog(
                this,
                messages.getString("dialog.controlsSaved"),
                messages.getString("dialog.success"),
                JOptionPane.INFORMATION_MESSAGE
            );
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error saving controls: " + e.getMessage(),
                messages.getString("dialog.error"),
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }
    
    private boolean hasDuplicateKeys(int... keyCodes) {
        Set<Integer> uniqueKeys = new HashSet<>();
        for (int keyCode : keyCodes) {
            if (!uniqueKeys.add(keyCode)) {
                return true; 
            }
        }
        return false; 
    }
    
    private char keyCodeToChar(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP: return '^';
            case KeyEvent.VK_DOWN: return 'v';
            case KeyEvent.VK_LEFT: return '<';
            case KeyEvent.VK_RIGHT: return '>';
            default: return (char) keyCode;
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guardarBtn) {
            if (saveUserControls()) {
                Ajustes ajustes = new Ajustes(usuarioActual);
                ajustes.setVisible(true);
                this.setVisible(false);
                this.dispose();
            }
        } else if (e.getSource() == defaultBtn) {
            resetToDefaultControls();
            JOptionPane.showMessageDialog(
                this,
                messages.getString("dialog.defaultControls") != null ? 
                    messages.getString("dialog.defaultControls") : 
                    "Controls have been reset to default (WASD + R). Click Save to apply.",
                messages.getString("dialog.info") != null ? 
                    messages.getString("dialog.info") : 
                    "Information",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else if (e.getSource() == regresarBtn) {
            Ajustes ajustes = new Ajustes(usuarioActual);
            ajustes.setVisible(true);
            this.setVisible(false);
            this.dispose();
        }
    }
}