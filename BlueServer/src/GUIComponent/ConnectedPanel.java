package GUIComponent;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import Device.Device;
import Device.DeviceCellRenderer;
import MVC.BlueServerController;


public class ConnectedPanel extends JPanelAnimated{

	private static final long serialVersionUID = -817400026044302295L;

	private BlueServerController mController;
	
	private JPanel mDeviceListPanel;
	private ArrayList<InteractPanel> mInteractPanelList;
	private JPanel mInteractPanelCards;
	
	private Integer mInteractPanelIndex = 0;	
	public ConnectedPanel(BlueServerController controller){
		mController = controller;
		mInteractPanelList = new ArrayList<InteractPanel>();
		builPanel();
	}
	
	public void updateList(){
//		mUIDeviceList = new JList(mRegisteredDevices.toArray());
//		mUIDeviceList.setCellRenderer(new DeviceCellRenderer());
		//TODO
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * To call when a new connection is starting
	 */
	public void addDevice(Device d){
		InteractPanel newInteractPanel = new InteractPanel(mController, d);
		mInteractPanelCards.add(newInteractPanel, d.getDeviceName());
		mInteractPanelList.add(newInteractPanel);
		mInteractPanelIndex++;
	}
	
	private void builPanel(){
		mInteractPanelCards = new JPanel(new CardLayout());
		mDeviceListPanel = new JPanel();
		JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mDeviceListPanel, mInteractPanelCards);
		mainSplit.setOneTouchExpandable(true);
		this.setLayout(new BorderLayout());
		this.add(mainSplit, BorderLayout.CENTER);
	}
}
