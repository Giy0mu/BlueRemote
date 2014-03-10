package Request;

import java.io.IOException;



public class SleepRequest extends AbstractRequest{
	public SleepRequest(){
		super();
		mID = 0x01;
	}
	
	@Override
	byte[] exec(byte[] param) {
		System.out.println("Sleep request");
		try {
			Runtime.getRuntime().exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}

}
