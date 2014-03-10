package Request;


public class TestCommRequest extends AbstractRequest{

	public TestCommRequest(){
		super();
		mID = 0x00;
	}
	@Override
	byte[] exec(byte[] param) {
		// TODO not much
		return answer;
	}

}
