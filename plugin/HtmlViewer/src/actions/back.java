package plugin.InfoViewer.infoviewer.actions;

import java.awt.event.ActionEvent;


public class back extends InfoViewerAction {

	public back() {
        super("infoviewer.back");
    }
    
    public void actionPerformed(ActionEvent evt) {
        getViewer(evt).back();
    }
}

