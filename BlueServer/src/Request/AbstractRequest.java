package Request;

public abstract class AbstractRequest {
	public final byte STX = 0x02;
	public final byte ETX = 0x03;
	
	protected byte mID;
	protected byte[] answer;
	
	public AbstractRequest(){
		answer = new byte[4];
		answer[0] = STX;
		answer[1] = 0;
		answer[2] = 0;
		answer[3] = ETX;
	}
	abstract byte[] exec(byte[] param);
	public byte getID(){
		return mID;
	}
}
