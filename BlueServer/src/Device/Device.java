package Device;

public class Device {
	public static final int DISCONNECTED = 0;
	public static final int CONNECTED = 1;
	public static final int REQUEST = 2;
	
	public static final int PHONE = 0;
	public static final int TABLET = 1;
	
	private int mConnectionState;
	private int mDeviceType;
	private String mDeviceName;
	
	public Device(String deviceString){
		setDeviceName(deviceString.substring(0, deviceString.length()));
//		setDeviceType(Integer.parseInt(deviceString.substring(deviceString.length()-1))); //TODO ???
		setConnectionState(DISCONNECTED);
	}
	public Device(String deviceName, int deviceType, int connectionState){
		setDeviceName(deviceName);
		setDeviceType(deviceType);
		setConnectionState(connectionState);
	}

	public int getConnectionState() {
		return mConnectionState;
	}

	public void setConnectionState(int mConnectionState) {
		this.mConnectionState = mConnectionState;
	}

	public int getDeviceType() {
		return mDeviceType;
	}

	public void setDeviceType(int mDeviceType) {
		this.mDeviceType = mDeviceType;
	}

	public String getDeviceName() {
		return mDeviceName;
	}

	public void setDeviceName(String mDeviceName) {
		this.mDeviceName = mDeviceName;
	}
	
	@Override
	public String toString(){
		return mDeviceName;
	}
	
}
