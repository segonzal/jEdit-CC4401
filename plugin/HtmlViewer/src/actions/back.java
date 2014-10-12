package plugin.HtmlViewer.src.actions;

import java.awt.event.ActionEvent;


public class back extends HtmlViewerAction {

	public back() {
        super("infoviewer.back");
    }
    
    public void actionPerformed(ActionEvent evt) {
        getViewer(evt).back();
    }
}

