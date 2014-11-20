
package htmlviewer.actions;

import java.awt.event.ActionEvent;


public class select_all extends HtmlViewerAction {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7748072451126761133L;

	public select_all() {
        super("htmlviewer.select_all");
    }
    
    public void actionPerformed(ActionEvent evt) {
        getViewer(evt).getViewer().selectAll();
    }
}

