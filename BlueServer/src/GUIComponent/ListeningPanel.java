package GUIComponent;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

public class ListeningPanel extends JPanelAnimated{

	private static final long serialVersionUID = -7411678784642412553L;
	private int mHeadLinePosition;
	private boolean mOrientation;
	
	public ListeningPanel(){
		super();
		this.setBackground(Color.BLACK);
		mHeadLinePosition = 0;
		mOrientation = true;
		
	}
	
	public void update(){
		if(mOrientation)
			mHeadLinePosition+=10;
		else{
			mHeadLinePosition-=10;
		}
		if(mHeadLinePosition>getWidth() || mHeadLinePosition<0){
			mOrientation = !mOrientation;
		}
		Graphics2D g = (Graphics2D) this.getGraphics();
		if(g!=null){
			g.setPaint(Color.BLUE);
			g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
			
			g.setPaint(new GradientPaint(0,0,Color.BLUE, 0, 100,Color.RED));
			g.drawLine(mHeadLinePosition, getHeight()/2, mHeadLinePosition+50, getHeight()/2);
		}
	}

}
