package htmlviewer.actions;

import java.awt.event.ActionEvent;


public class forward extends HtmlViewerAction {
    
	private static final long serialVersionUID = 5148347240386631300L;

	public forward() {
        super("htmlviewer.forward");
    }
    
    public void actionPerformed(ActionEvent evt) {
        getViewer(evt).forward();
    }
}

