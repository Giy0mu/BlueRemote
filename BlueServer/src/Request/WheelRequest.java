package Request;

import java.awt.AWTException;
import java.awt.Robot;

public class WheelRequest extends AbstractRequest {
	private Robot mRobot;
	
	public WheelRequest(){
		super();
		mID = 0x04;
		try {
			mRobot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	byte[] exec(byte[] param){
		int amt = param[2]<<24 | param[3]<<16 | param[4] <<8 | param[5];
		mRobot.mouseWheel(amt);
		return answer;
	}
}
