import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.util.ArrayList;
import java.util.List;

/**
 * Beispielanwendung mit einem Fenster und vier farbigen Tasten
 * Beim Klicken auf eine Taste wird der Hintergrund des Fensters auf die entsprechende Farbe geändert
 */
public class SolutionColorChanger extends JFrame {
    private JPanel panel; // Hauptpanel für die Anordnung der Tasten

    /**
     * Konstruktor: Fenster und Panel einrichten, Tasten laden.
     */
    public SolutionColorChanger() {
        super("Farbwechsler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(400, 400);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));
        ladeTasten(); // Tasten erstellen und dem Panel hinzufügen

        getContentPane().add(panel); // Panel dem Fenster hinzufügen
        setVisible(true); // Fenster sichtbar machen
    }

    /**
     * Methode zum Erstellen der vier Tasten, Speichern in einer Liste und Hinzufügen zum Fenster
     * Jede Taste bekommt einen ActionListener, der die Hintergrundfarbe ändert
     */
    private void ladeTasten() {
        List<JButton> tasten = new ArrayList<>(); // Liste für die Tasten

        // Erstelle die Tasten mit Beschriftung
        JButton roteTaste = new JButton("Rot");
        JButton blaueTaste = new JButton("Blau");
        JButton grueneTaste = new JButton("Gr\u00fcn");
        JButton schwarzeTaste = new JButton("Schwarz");

        // Füge die Tasten zur Liste hinzu
        tasten.add(roteTaste);
        tasten.add(blaueTaste);
        tasten.add(grueneTaste);
        tasten.add(schwarzeTaste);

        // Füge alle Tasten dem Panel hinzu
        for (JButton taste : tasten) {
            panel.add(taste);
        }

        // ActionListener für die rote Taste: Hintergrund auf Rot setzen
        roteTaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setBackground(Color.RED);
            }
        });

        // ActionListener für die blaue Taste: Hintergrund auf Blau setzen
        blaueTaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setBackground(Color.BLUE);
            }
        });

        // ActionListener für die grüne Taste: Hintergrund auf Grün setzen
        grueneTaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setBackground(Color.GREEN);
            }
        });

        // ActionListener für die schwarze Taste: Hintergrund auf Schwarz setzen
        schwarzeTaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setBackground(Color.BLACK);
            }
        });
    }

    /**
     * Hauptmethode: Startet die Anwendung im Event-Dispatch-Thread.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SolutionColorChanger();
            }
        });
    }
}
