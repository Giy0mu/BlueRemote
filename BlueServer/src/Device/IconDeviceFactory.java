package Device;

import javax.swing.ImageIcon;


public class IconDeviceFactory {
	static public ImageIcon make(int id){
		switch(id){
		case Device.PHONE:
			return new ImageIcon("data/img/icon_phone.png", "device is a phone");
		case Device.TABLET:
			return new ImageIcon("data/img/icon_tablet.png", "device is a tablet");
		default:
			return null;
		}
	}
}
