package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindowClass {
// this class is fore keeping JFrame game window
// extends JFrame first option 
	private JFrame jframe; // secound option and this is global variable
// constructor must have the same name as class
	public GameWindowClass(GamePanelClass gamePanelClass) { // this inside paramater GamePanelClass is needed because to assemble the frame with panel // the original Object
		
		jframe = new JFrame(); // JFrame object
		
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // this contains integer values 
		jframe.add(gamePanelClass);
		jframe.setLocationRelativeTo(null); // positioning to center
		jframe.setResizable(false);
		jframe.pack();
		jframe.setVisible(true); // setVisiable should be at the button
		
		jframe.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
		
			
			}
			
			
		});
	}
		
}
