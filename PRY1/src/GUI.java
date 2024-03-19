import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JTextField entradaTextField;
    private JTextArea historialTextArea;
    private LispInterpreter interpreter;

    public GUI() {
        super("Intérprete de Lisp");
        interpreter = new LispInterpreter();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        int margenWidth = 250;
        JPanel margenPanel = new JPanel(new BorderLayout());
        margenPanel.setPreferredSize(new Dimension(margenWidth, 600));
        margenPanel.setBackground(new Color(52, 73, 94));

        // Título elegante
        JLabel titleLabel = new JLabel("Lisp Interpreter", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        margenPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());

        entradaTextField = new JTextField();
        entradaTextField.addActionListener(e -> ejecutarComando(entradaTextField.getText()));
        historialTextArea = new JTextArea();
        historialTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(historialTextArea);
        panel.add(entradaTextField, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panel, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS)); 
        JLabel imageLabel = new JLabel();
        try {
            Image image = ImageIO.read(new File("resources/Logo.jpg"));
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(margenWidth, -1, Image.SCALE_DEFAULT));
            imageLabel.setIcon(imageIcon);
            imagePanel.add(imageLabel);
            margenPanel.add(imagePanel, BorderLayout.NORTH);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        panelPrincipal.add(margenPanel, BorderLayout.WEST);
        add(panelPrincipal);

        setSize(800, 600);
        setMinimumSize(new Dimension(800 + margenWidth, 600));
        setVisible(true);

        entradaTextField.requestFocusInWindow();
    }

    private void ejecutarComando(String comando) {
        ArrayList<String> tokens = interpreter.tokenize(comando);
        actualizarHistorial("Entrada: " + comando);
        try {
            Object resultado = interpreter.eval(tokens);
            actualizarHistorial("Salida: " + resultado);
        } catch (Exception e) {
            actualizarHistorial("Error: " + e.getMessage());
        }
        entradaTextField.setText("");
    }

    private void actualizarHistorial(String texto) {
        historialTextArea.append(texto + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
