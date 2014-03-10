package GUIComponent;

import java.awt.CardLayout;
import java.awt.Checkbox;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionPanel extends JPanel{
	private final String CONNECTED = "Connected";
	private final String DISCONNECTED = "Disconnected";
	private JPanel mConnectedPanel;
	private JPanel mDisconnectedPanel;
	private JPanel mStatePanel;
	private Checkbox mStartLaunchCheckBox;
	private CardLayout cl;
	
	public OptionPanel(){
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		mDisconnectedPanel = new JPanel(/*new BoxLayout(this, BoxLayout.X_AXIS)*/);
		mDisconnectedPanel.add(new JLabel(new ImageIcon("data/img/icon_disconnected.png")));
		mDisconnectedPanel.add(new JLabel(DISCONNECTED));
		mConnectedPanel = new JPanel();
		mConnectedPanel.add(new JLabel(new ImageIcon("data/img/icon_connected.png")));
		mConnectedPanel.add(new JLabel(CONNECTED));
		mStatePanel = new JPanel(new CardLayout());
		mStatePanel.add(mDisconnectedPanel, DISCONNECTED);
		mStatePanel.add(mConnectedPanel, CONNECTED);
		//mStartLaunchCheckBox = new Checkbox("Launch at startup");
		
		add(mStatePanel);
		//add(mStartLaunchCheckBox);
		
		cl = (CardLayout)(mStatePanel.getLayout());
		
		
	}
	
	public void connect(){
		cl.show(mStatePanel, CONNECTED);
	}
	
	public void disconnect(){
		cl.show(mStatePanel, DISCONNECTED);
	}

}
