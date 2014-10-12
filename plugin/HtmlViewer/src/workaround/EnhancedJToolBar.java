package plugin.InfoViewer.infoviewer.workaround;

import java.awt.Insets;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;


/**
 * this is a workaround class for <code>JToolBar</code>
 * for JDK versions prior to 1.3. The method <code>add(Action)</code>
 * doesn't set the right properties.
 */
public class EnhancedJToolBar extends JToolBar {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6938925964865407925L;


	public EnhancedJToolBar() {
        super();
        putClientProperty("JToolBar.isRollover", Boolean.TRUE);
    }


    public EnhancedJToolBar(int orientation) {
        super(orientation);
        putClientProperty("JToolBar.isRollover", Boolean.TRUE);
    }


    public JButton add(Action a) {
        JButton b = super.add(a);
        b.setText(null);
        b.setToolTipText(a.getValue(Action.SHORT_DESCRIPTION).toString());
        b.setMargin(new Insets(0,0,0,0));
        b.setRequestFocusEnabled(false);
        return b;
    }

}
