import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Einfache GUI-Anwendung, um Name und Alter einzulesen und in einer Datei zu speichern
 */
public class SolutionMainWindow extends JFrame {

    private JTextField nameField;  
    private JTextField ageField;   
    private JButton saveButton;    
    private final String DATA_FILE = "userdata.txt"; 

    public SolutionMainWindow() {
        // Fenster-Eigenschaften setzen
        super("GUI Window"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null); // Fenster zentriert auf dem Bildschirm


        JPanel panel = new JPanel(new GridLayout(2, 2));
        
        // Name-Label und Textfeld hinzufügen
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        // Alter-Label und Textfeld hinzufügen
        panel.add(new JLabel("Alter:"));
        ageField = new JTextField();
        panel.add(ageField);

        // wichtig
        // Panel zum Fenster hinzufügen
        add(panel, BorderLayout.CENTER);

        // Speichern-Button erstellen und hinzufügen
        saveButton = new JButton("Speichern");
        add(saveButton, BorderLayout.SOUTH);

        // ActionListener für den Button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveDataToFile(); // Methode zum Speichern aufrufen
            }
        });

        // Beim Start prüfen, ob die Datei existiert und Daten laden zurück
        // loadDataFromFile();

        setVisible(true);
    }

    /**
     * Liest die gespeicherten Benutzerdaten aus der Datei (falls vorhanden) 
     * und setzt die Werte in die Textfelder
     */
    private void loadDataFromFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String name = reader.readLine();
                String age = reader.readLine(); 
                if (name != null) nameField.setText(name);
                if (age != null) ageField.setText(age);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Fehler beim Laden der Datei.", 
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Speichert die eingegebenen Benutzerdaten in die Datei
     */
    private void saveDataToFile() {
        String name = nameField.getText();
        String age = ageField.getText();

        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            writer.println(name);
            writer.println(age);
            JOptionPane.showMessageDialog(this, "Daten erfolgreich gespeichert.");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler beim Speichern der Datei.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Die GUI muss im Event-Dispatch-Thread erstellt werden wegen der Thread-Sicherheit

        // WENN NICHT:
        //  - Wenn du die GUI im Hauptthread erstellst und später z.B. ein Button-Klick einen neuen Thread startet, könnten beide Threads gleichzeitig auf die GUI zugreifen. Das führt zu schwer auffindbaren Fehlern
        //  - Manche GUI-Operationen funktionieren dann einfach nicht richtig oder das Fenster reagiert nicht mehr

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SolutionMainWindow();
            }
        });
    }
}