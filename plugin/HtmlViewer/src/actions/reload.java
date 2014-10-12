package plugin.HtmlViewer.src.actions;

import java.awt.event.ActionEvent;


public class reload extends HtmlViewerAction {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1068023551547862646L;

	public reload() {
        super("infoviewer.reload");
    }
    
    public void actionPerformed(ActionEvent evt) {
        getViewer(evt).reload();
    }
}

