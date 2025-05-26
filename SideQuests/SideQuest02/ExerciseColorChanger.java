import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.util.ArrayList;
import java.util.List;

/**
 * Beispielanwendung mit einem Fenster und vier farbigen Tasten
 * Beim Klicken auf eine Taste wird der Hintergrund des Fensters auf die entsprechende Farbe geändert
 */
public class ExerciseColorChanger extends JFrame {
    


    public ExerciseColorChanger() {
        super("Farbwechsler");
     
        // Instanz von JPanel erstellen
        
        panel.setLayout(new GridLayout(3,2));

        // ladeTasten(); 

        getContentPane().add(panel);
        setVisible(true); 
    }

    private void ladeTasten() {
        // ArrayList von JButton erstelle

        // Erstelle die Tasten für die ArrayList
        JButton roteTaste = new JButton("Rot");
    
        // Füge die Tasten zur Liste hinzu
    

        // Füge alle Tasten dem Panel hinzu
        for (JButton taste : tasten) {
            panel.add(taste);
        }


        roteTaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
       
            }
        });

        blaueTaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        grueneTaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        schwarzeTaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ExerciseColorChanger();
            }
        });
    }
}