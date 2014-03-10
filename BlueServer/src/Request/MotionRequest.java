package Request;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;


public class MotionRequest extends AbstractRequest {
	private Robot mRobot;
	
	public MotionRequest(){
		super();
		mID = 0x06;
		try {
			mRobot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	byte[] exec(byte[] param) {
		Point curMouseLocation = MouseInfo.getPointerInfo().getLocation();
		int dx = param[2]<<24 | param[3]<<16 | param[4] <<8 | param[5];
		int dy = param[6]<<24 | param[7]<<16 | param[8] <<8 | param[9];
		mRobot.mouseMove(curMouseLocation.x - dx, curMouseLocation.y - dy);
		return answer;
	}

}
