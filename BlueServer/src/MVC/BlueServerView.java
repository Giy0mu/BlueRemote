package MVC;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import Communication.ConnectionHandler;
import Device.Device;


public class BlueServerView implements ConnectionHandler {
	private BlueServerController mController;
	
	//private JFrame mFrame;
	//private JPanel mCards;
//	private JPanel mListeningCard;
//	private JPanel mAlertCard;
	//private OptionPanel mOptionCard;
	//private Timer mTimer;
	
	private final String OPTION_LABEL = "Option";
//	private final String ALERT_LABEL = "Alert";
//	private final String LISTENING_LABEL = "Listening";
//	private final String CONNECTED_LABEL = "Connected";
	
	private final int MIN_FRAME_WIDTH = 300;
	private final int MIN_FRAME_HEIGHT = 75;
	
	
	private TrayIcon mTrayIcon;
	
	public BlueServerView(BlueServerController controller){
		mController = controller;
		initializeWindow();
		initializeSystemTray();
//		mTimer = new Timer(50,this);
//		mTimer.start();	
	}
	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		getCurrentCard().update();
//	}
//	
//	private JPanelAnimated getCurrentCard()	{
//		JPanelAnimated card = null;
//
//	    for (Component comp : mCards.getComponents() ) {
//	        if (comp.isVisible() == true) {
//	            card = (JPanelAnimated)comp;
//	        }
//	    }
//	    return card;
//	}
	
	private void initializeWindow(){
		//Main Frame
		/*mFrame = new JFrame("BlueLink");
		mFrame.setIconImage(createImage("data/img/icon_blue_link.png", "frame icon"));
		mFrame.setBounds(0, 0, MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT);
		mFrame.setMinimumSize(new Dimension(MIN_FRAME_WIDTH,MIN_FRAME_HEIGHT));
		mFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);*/
		//Layouts		
//		mListeningCard = new ListeningPanel();
//		mAlertCard = new AlertPanel();
		//mOptionCard = new OptionPanel();
//		mConnectedCard = new ConnectedPanel(mController);
		
		/*mCards = new JPanel(new CardLayout());
		mCards.add(mOptionCard, OPTION_LABEL);*/
//		mCards.add(mAlertCard, ALERT_LABEL);
//		mCards.add(mListeningCard, LISTENING_LABEL);
//		mCards.add(mConnectedCard, CONNECTED_LABEL);
		
		/*mFrame.add(mCards, BorderLayout.CENTER);
		
		CardLayout cl = (CardLayout)(mCards.getLayout());
	    cl.show(mCards, OPTION_LABEL);*/
		
	}
	
	private void initializeSystemTray(){
		if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
		
        mTrayIcon = new TrayIcon(createImage("data/img/tray_icon_blue_link.png", "tray icon"));
        mTrayIcon.setImageAutoSize(true);
        final SystemTray tray = SystemTray.getSystemTray();
       
        
        
        final PopupMenu popup = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tray.remove(mTrayIcon);
				System.exit(0);
			}
        	
        });
        popup.add(exitItem);
        mTrayIcon.setPopupMenu(popup);
        
        
        try {
            tray.add(mTrayIcon);
        } 
        catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
	}
	
    protected static Image createImage(String path, String description) {
            return (new ImageIcon(path, description)).getImage();
    }
	
	public void updateList(){
//		mUIDeviceList = new JList(mRegisteredDevices.toArray());
//		mUIDeviceList.setCellRenderer(new DeviceCellRenderer());
		//TODO
	}
	
	public void display(){
		//mFrame.setVisible(true);
	}
	
	private synchronized int updateConnectionState(Device d, int state){
		//CardLayout cl = (CardLayout)(mCards.getLayout());
		switch(state){
		case Device.REQUEST:
			mTrayIcon.displayMessage("Connection Request", "A device is trying to connect", TrayIcon.MessageType.INFO);
//		    cl.show(mCards, ALERT_LABEL); 
			//TODO display state connection
		    String message = "Do you accept connection from "+d.getDeviceName()+"?";
		    JCheckBox checkbox = new JCheckBox("Always authorize connection from this device");
		    Object[] params = {message, checkbox};
		    int ret = JOptionPane.showConfirmDialog(null,params);
		    boolean registerDevice = checkbox.isSelected();
		    if(registerDevice){
		    	mController.registerNewAuthorizedDevice(d.getDeviceName());
		    }
			return ret;
		case Device.CONNECTED:
			updateViewConnected(d);
			return 0;
		case Device.DISCONNECTED:
			updateViewDisconnected();
			return 0;
		default:
			return -1;
		}
	}
	
	private void updateViewConnected(Device d){
		mTrayIcon.displayMessage("Connection Accepted", "A device is connected", TrayIcon.MessageType.INFO);
		//mOptionCard.connect();
	}
	
	private void updateViewDisconnected(){
		mTrayIcon.displayMessage("Disconnection", "A device is disconnected", TrayIcon.MessageType.INFO);
		//mOptionCard.disconnect();
	}
	
	@Override
	public int notifyConnectionRequest(Device d) {
		return updateConnectionState(d, Device.REQUEST);
	}

	@Override
	public int notifyConnectionAccepted(Device d) {
		return updateConnectionState(d, Device.CONNECTED);
	}

	@Override
	public int notifyDisconnection(Device d) {
		return updateConnectionState(d, Device.DISCONNECTED);
	}

	@Override
	public int notifyConnectionRefused(Device d) {
		return 0;
	}
}
