package Communication;
import Device.Device;


public interface ConnectionHandler {
	public int notifyConnectionRequest(Device d);
	public int notifyConnectionAccepted(Device d);
	public int notifyDisconnection(Device d);
	public int notifyConnectionRefused(Device d);
}
