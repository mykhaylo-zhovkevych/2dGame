import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class ExerciseMainWindow extends JFrame {


    private JTextField nameField;  
    private JTextField ageField;   
    private JButton saveButton;   
    private final String DATA_FILE = "userdata.txt"; 

    public ExerciseMainWindow() {
        
        // Fenstertitel
        super("GUI Window"); 
        

        // Erstelle eine Panel mit Instanz von GridLayout


        // Vergiss nicht, das es JLabel und JTextField gibt, die du brauchst, um die Eingabefelder zu erstellen
        // Die du übrigens später in der Panel hinzufügen kannst


        // Panel muss zum Fenster hinzufügen werden
        add(panel, BorderLayout.CENTER);

        // Erstelle eine variable für JButton und füge es zum Fenster hinzu



        // ActionListener für den Button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Die Methode zum Speichern der Daten aufrufen

            }
        });


        setVisible(true);
    }


    private void saveDataToFile() {
        String name = nameField.getText();
        String age = ageField.getText();

        // Nutze PrintWriter
        try () {
            
            JOptionPane.showMessageDialog(this, "Daten erfolgreich gespeichert.");
        } catch (IOException ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(this, "Fehler beim Speichern der Datei.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
      
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SolutionMainWindow();
            }
        });
    }
}