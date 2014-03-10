package Device;

import javax.swing.ImageIcon;


public class IconStateFactory {
	static public ImageIcon make(int id){
		switch(id){
		case Device.CONNECTED:
			return new ImageIcon("data/img/icon_connected.png", "device connected");
		case Device.DISCONNECTED:
			return new ImageIcon("data/img/icon_disconnected.png", "device disconnected");
		default:
			return null;
		}
	}
}
