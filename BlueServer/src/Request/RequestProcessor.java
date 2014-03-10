package Request;
import java.util.Hashtable;




public class RequestProcessor {
	static public final byte STX = 0x02;
	static public final byte ETX = 0x03;
	
	static private Hashtable<Byte, AbstractRequest> mRequestRegister;
	
	public RequestProcessor(){
		mRequestRegister = new Hashtable<Byte, AbstractRequest>();
		
		Byte testComm = 0x00;
		mRequestRegister.put(testComm, new TestCommRequest());
		
		Byte sleepRequest = 0x01;
		mRequestRegister.put(sleepRequest,  new SleepRequest());
		
		Byte volumeRequest = 0x02;
		mRequestRegister.put(volumeRequest, new VolumeRequest());
		
		Byte clickRequest = 0x03;
		mRequestRegister.put(clickRequest, new ClickRequest());
		
		Byte wheelRequest = 0x04;
		mRequestRegister.put(wheelRequest, new WheelRequest());
		
		Byte motionRequest = 0x06;
		mRequestRegister.put(motionRequest,  new MotionRequest());
		
		Byte keyRequest = 0x07;
		mRequestRegister.put(keyRequest,  new KeyRequest());
	}
	
	public byte[] process(byte[] buffer){
		byte[] answer = {0,0,0,0};
		
		if(buffer[0] != STX){
			System.out.println("Request received unreadable: Wrong STX");
			return answer;
		}
		
		AbstractRequest curRequest = null;
		curRequest = mRequestRegister.get(buffer[1]);
		if(curRequest != null){
			curRequest.exec(buffer);
		}
		else{
			System.out.println("Request received unreadable");
//			throw new NullPointerException("Request doesn't exists"); //TODO deal
			return answer;
		}
		return answer;
	}
}
