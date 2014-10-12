package plugin.InfoViewer.infoviewer.actions;

import java.awt.event.ActionEvent;


public class copy extends InfoViewerAction {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 229428765692334869L;

	public copy() {
        super("infoviewer.copy");
    }
    
    public void actionPerformed(ActionEvent evt) {
        getViewer(evt).getViewer().copy();
    }
}

