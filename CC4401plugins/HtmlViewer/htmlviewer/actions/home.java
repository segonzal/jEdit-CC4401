package htmlviewer.actions;

import java.awt.event.ActionEvent;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.GUIUtilities;
import htmlviewer.HtmlViewer;


public class home extends HtmlViewerAction {
    
	private static final long serialVersionUID = 2183528446575469446L;

	public home() {
        super("htmlviewer.home");
    }
    
    public void actionPerformed(ActionEvent evt) {
        String home = jEdit.getProperty("htmlviewer.homepage");
        HtmlViewer viewer = getViewer(evt);
        if (home == null) {
            GUIUtilities.error(viewer, "htmlviewer.error.nohome", null);
        } else {
            viewer.gotoURL(home, true, 0);
        }
    }
}

