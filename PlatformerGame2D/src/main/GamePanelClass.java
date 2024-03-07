package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import inputs.KeyboardInputsClass;
import inputs.MouseInputsClass;

// extending class is not the same as implementing the interface and only one class can be extended but interfaces can be extended more than one 
public class GamePanelClass extends JPanel{
// this class is responsible where drawing is happening
	private MouseInputsClass mouseInputsClass; //make code looking indistinguishable because addMouseListener needs only addMouseListener and the addMouseMotionListener need both 
// but my class MouseInputsClass implements them both so with private can managed better   
	private List<MyRect> rectangles = new ArrayList<>(); // Collection of MyRect
	
	private float xDelta = 100, yDelta = 100;
	private int frames = 0;
	private long lastCheck = 0;
	private float xDir = 2f, yDir = 2f;
	private Color color = new Color(150, 20, 90); 
	private Random random;
	
	public GamePanelClass() {
		random = new Random();
		addKeyListener(new KeyboardInputsClass(this)); // this live grants access to the methods of KeyListener
		
		mouseInputsClass = new MouseInputsClass(this);
		addMouseListener(mouseInputsClass);
		addMouseMotionListener(mouseInputsClass);
		// temp
		 for (int i = 0; i < 1145; i++) { // Example: Create 5 MyRect instances
	            rectangles.add(new MyRect(random.nextInt(400), random.nextInt(400)));
	        }
		
	}
	
	public void changeXDelta(int value) {
		this.xDelta += value;
		
	}
	public void changeYDelta(int value) {
		this.yDelta += value;
		
	}	
	
	public void setRectPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		updateRectangle();
		g.setColor(color);
		g.fillRect((int)xDelta, (int)yDelta, 200, 50);
		// temp
		 for (MyRect rect : rectangles) {
	            rect.draw(g); // This calls the draw method of each MyRect instance
	        }
	}

	private void updateRectangle() {
		xDelta += xDir;
		if(xDelta > 400 || xDelta < 0) {
			xDir *= -1;
			color = getRandomColor();
		}
		
		yDelta +=yDir;
		if(yDelta > 400 || yDelta < 0 ) {
			yDir *= -1; 
			color = getRandomColor();
		}
		// temp
		for (MyRect rect : rectangles) {
            rect.updateRect(); // This calls the update method of each MyRect instance
        }
	}

	private Color getRandomColor() {
		// TODO Auto-generated method stub
		int r = random.nextInt(255);
		int b = random.nextInt(255);
		int g = random.nextInt(255); 
		
		return new Color(r,b,g);
	}
	
	public class MyRect {
		int x, y, w, h;
		int xDir = 1, yDir = 1;
		Color color;

		public MyRect(int x, int y) {
			this.x = x;
			this.y = y;
			w = random.nextInt(50);
			h = w;
			color = newColor();
		}

		public void updateRect() {
			this.x += xDir;
			this.y += yDir;

			if ((x + w) > 400 || x < 0) {
				xDir *= -1;
				color = newColor();
			}
			if ((y + h) > 400 || y < 0) {
				yDir *= -1;
				color = newColor();
			}

		}

		private Color newColor() {
			return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		}

		public void draw(Graphics g) {
			g.setColor(color);
			g.fillRect(x, y, w, h);
		}

	}
	
}
