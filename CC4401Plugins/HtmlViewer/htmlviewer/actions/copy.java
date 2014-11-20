package htmlviewer.actions;

import java.awt.event.ActionEvent;


public class copy extends HtmlViewerAction {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 229428765692334869L;

	public copy() {
        super("htmlviewer.copy");
    }
    
    public void actionPerformed(ActionEvent evt) {
        getViewer(evt).getViewer().copy();
    }
}

