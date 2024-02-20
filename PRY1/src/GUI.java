import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JTextField textField;
    private JButton saveButton;
    private String message;

    public GUI() {
        setTitle("Message GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());

        // Create the text field
        textField = new JTextField(20);
        add(textField);

        // Create the save button
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message = textField.getText();
                JOptionPane.showMessageDialog(null, "Message saved: " + message);
            }
        });
        add(saveButton);

 
        setVisible(true);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }
}
