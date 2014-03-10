package Request;

import java.io.File;


public class FileRequest extends AbstractRequest{
	private File mCurFile;
	
	public FileRequest(){
		super();
		mID = 0x04;
		mCurFile = new File("\\");
	}
	@Override
	byte[] exec(byte[] param) {
		int length;
		byte[] pathBuffer = new byte[10000];
		byte[] answerBuffer = new byte[10000];
		switch(param[2]){
		case 0x00:
			//cd command
			length = param[3];
			System.arraycopy(param, 4, pathBuffer, 0, length);
			
			mCurFile = new File(mCurFile, pathBuffer.toString());
			
			break;
		case 0x01:
			//ls command
			if(mCurFile.isDirectory()){
				File[] listFiles = mCurFile.listFiles();
				
				int pos = 0;
				for(int i=0; i<listFiles.length; i++){
					answerBuffer = listFiles[i].getName().getBytes();
					int curLength = answerBuffer.length;
					System.arraycopy(listFiles[i].getName().getBytes(), 0, answerBuffer, pos, curLength); //TODO add separator
					pos += curLength;
				}
			}
			break;
		}
		return answer;
	}
	
	
}
