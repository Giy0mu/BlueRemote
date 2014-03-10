package GUIComponent;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AlertPanel  extends JPanelAnimated{
	
	
	public AlertPanel(){
		super();
		this.add(new JButton("2"));
	}
	
	public void update(){
		clearScreen();
		Graphics2D g = (Graphics2D) this.getGraphics();
		if(g != null){
			g.drawString("!", getWidth()/2, getHeight()/2);
		}
	}
	
	private void clearScreen(){
		Graphics2D g = (Graphics2D) this.getGraphics();
		if(g != null){
			g.clearRect(0, 0, getWidth(), getHeight());
		}
	}

}
