package GUIComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Device.Device;
import MVC.BlueServerController;

public class InteractPanel extends JPanel{

	private static final long serialVersionUID = 3864614959262295459L;
	
	private BlueServerController mController;
	private Device mDevice;
	
	public InteractPanel(BlueServerController controller, Device d){
		mController = controller;
		mDevice = d;
		buildPanel();
		
	}
	
	private void buildPanel(){
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mController.disconnect(mDevice);
			}
		});
		
		this.add(closeButton);
	}

}
