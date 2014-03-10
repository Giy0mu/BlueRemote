package MVC;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GraphicScreen {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480; //TODO
	private Graphics2D mBuffer;
	Color backgrounColor = Color.BLACK;
	Color fontColor = Color.BLUE;
	Font font = new Font(null, Font.PLAIN, 18);
	
	public GraphicScreen(BufferedImage img){
		mBuffer = (Graphics2D) img.getGraphics();
	}
	
	public void startAlert(){
		mBuffer.setColor(fontColor);
		mBuffer.setFont(font);
		mBuffer.drawString("!", 100, 100);
	}
	
	public void clearScreen(){
		mBuffer.clearRect(0, 0, WIDTH, HEIGHT);
	}
}
