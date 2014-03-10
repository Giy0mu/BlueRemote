package MVC;

import Device.Device;

public class BlueServerController {
	private BlueServerView mView;
	private BlueServerModel mModel;
	
	public BlueServerController(BlueServerModel model){
		mModel = model;
		mView = new BlueServerView(this);
		
		mModel.addListener(mView);
	}
	
	public void displayViews(){
		mView.display();
	}

	public void registerNewAuthorizedDevice(String deviceName) {
		mModel.registerNewAuthorizedDevice(deviceName);
	}
	
	public void disconnect(Device d){
		mModel.disconnect(d);
	}
}
