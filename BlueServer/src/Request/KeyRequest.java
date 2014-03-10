package Request;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.nio.ByteBuffer;
import java.util.Arrays;


public class KeyRequest extends AbstractRequest{
	private Robot mRobot;
	
	public KeyRequest(){
		super();
		mID = 0x07;
		try {
			mRobot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	@Override
	byte[] exec(byte[] param) {
		ByteBuffer b = ByteBuffer.wrap(Arrays.copyOfRange(param, 2, 6));
		int keyCode = b.getInt();
		try{
			if(specialKey(keyCode)){
				if(keyCode == KeyEvent.VK_CAPS_LOCK){
					Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, !Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK));
				}
			}
			else{
				if(param[6] == 0){
					
					mRobot.keyPress(keyCode);
				}
				else{
					mRobot.keyRelease(keyCode);
				}
			}
		}
		catch(IllegalArgumentException e){
			System.out.println("Wrong key code: "+String.valueOf(keyCode));
			//TODO set answer
		}
		System.out.println("Key Received: "+String.valueOf(keyCode)+" , "+String.valueOf(param[6]));
		return answer;
	}
	
	public boolean specialKey(int keycode){
		switch(keycode){
		case 20:
			return true;
		default:
			return false;
		}
	}
	

}
