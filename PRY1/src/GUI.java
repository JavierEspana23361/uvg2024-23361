import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JTextField entradaTextField;
    private JTextArea salidaTextArea;
    private LispInterpreter interpreter;

    public GUI() {
        super("Intérprete de Lisp");

        interpreter = new LispInterpreter();

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        entradaTextField = new JTextField();
        entradaTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comando = entradaTextField.getText();
                ejecutarComando(comando);
            }
        });
        panel.add(entradaTextField, BorderLayout.SOUTH);

        salidaTextArea = new JTextArea();
        salidaTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(salidaTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        setVisible(true);

        entradaTextField.requestFocusInWindow();
    }

    private void ejecutarComando(String comando) {
        salidaTextArea.setText("");

        ArrayList<String> tokens = interpreter.tokenize(comando);

        try {
            Object resultado = interpreter.eval(tokens);
            salidaTextArea.append("Salida: " + resultado.toString() + "\n");
        } catch (Exception e) {
            salidaTextArea.append("Error: " + e.getMessage() + "\n");
        }

        entradaTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
