package htmlviewer.actions;

import java.awt.event.ActionEvent;


public class back extends HtmlViewerAction {

	public back() {
        super("htmlviewer.back");
    }
    
    public void actionPerformed(ActionEvent evt) {
        getViewer(evt).back();
    }
}

