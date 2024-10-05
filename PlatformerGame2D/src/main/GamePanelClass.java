package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputsClass;
import inputs.MouseInputsClass;
import static utilts.ConstantsClass.PlyerConstants.*;
import static utilts.ConstantsClass.Directions.*;
import static main.GameClass.GAME_HEIGHT;
import static main.GameClass.GAME_WIDTH;

// extending class is not the same as implementing the interface and only one class can be extended but interfaces can be extended more than one 
	public class GamePanelClass extends JPanel{
// this class is responsible where drawing is happening
// make code looking indistinguishable because addMouseListener needs only addMouseListener and the addMouseMotionListener need both     
// but my class MouseInputsClass implements them both so with private can managed better   
	private MouseInputsClass mouseInputsClass; 
	private GameClass game;
		
	public GamePanelClass(GameClass game) {
		mouseInputsClass = new MouseInputsClass(this);
		this.game = game;
		addKeyListener(new KeyboardInputsClass(this)); // this live grants access to the methods of KeyListener
		setPanelSize();
		addMouseListener(mouseInputsClass);
		addMouseMotionListener(mouseInputsClass);
	}

	private void setPanelSize() {
		// TODO Auto-generated method stub
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		System.out.println("size: "+ GAME_WIDTH + ":" + GAME_HEIGHT);
	}
	

//	public void changeXDelta(int value) {
//		this.xDelta += value;
//// this is secure feature from preventing misconduct because of local variables or parameters with the same name as the instance variables xDelta and yDelt
//		
//	}
//	public void changeYDelta(int value) {
//		this.yDelta += value;
//		
//	} 
	
	
//    Increment the aniTick counter.
//    Once aniTick reaches aniSpeed, it resets aniTick to 0 and increments aniIndex.
//    If aniIndex reaches the length of the sprite array for the current action, it resets aniIndex to 0 to loop the animation.
	
//    this update the animation loop

	public void paintComponent(Graphics g) {
	super.paintComponent(g);
	
	game.render(g);
	
	
	}
	public GameClass getGameClass() {
		return game;
		
	}

}
