package Device;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;




public class DeviceCellRenderer extends JPanel implements ListCellRenderer {

	private static final long serialVersionUID = 5226746834486784063L;
	
	private JLabel mDeviceLabel;
	private JLabel mConnectIcon;
	private Color mBackground;
	private Color mForeground;
	
	public DeviceCellRenderer() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		mDeviceLabel = new JLabel();
		mConnectIcon = new JLabel();
		add(mDeviceLabel);
		add(mConnectIcon);
    }
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		mDeviceLabel.setText(value.toString());
		Device entry = (Device) value;
        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) {
        	mBackground = Color.BLUE;
        	mForeground = Color.WHITE;
        // check if this cell is selected
        } else if (isSelected) {
        	mBackground = Color.RED;
        	mForeground = Color.WHITE;
        // unselected, and not the DnD drop location
        } else {
        	mBackground = Color.WHITE;
        	mForeground = Color.BLACK;
        };
        setBackground(Color.BLUE);
        setForeground(Color.GREEN);
        mDeviceLabel.setBackground(mBackground);
        mDeviceLabel.setForeground(mForeground);
        mDeviceLabel.setIcon(IconDeviceFactory.make(entry.getDeviceType()));
        mConnectIcon.setIcon(IconStateFactory.make(Device.DISCONNECTED));
		return this;
	}

}
