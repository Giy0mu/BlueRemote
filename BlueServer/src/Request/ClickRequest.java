package Request;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;


public class ClickRequest extends AbstractRequest {
	private Robot mRobot;
	
	public ClickRequest(){
		super();
		mID = 0x03;
		try {
			mRobot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	@Override
	byte[] exec(byte[] param) {
		int button = 0;
		switch(param[3]){
		case 0:
			button = InputEvent.BUTTON3_MASK;
			break;
		case 1:
			button = InputEvent.BUTTON1_MASK;
			break;
		case 2:
			button = InputEvent.BUTTON2_MASK;
			break;
		}
		if(param[2] == 0)
			mRobot.mousePress(button);
		else if(param[2] == 1)
			mRobot.mouseRelease(button);
		else if(param[2] == 2){
			mRobot.mousePress(button);
			mRobot.mouseRelease(button);
		}
		return answer;
	}


}
