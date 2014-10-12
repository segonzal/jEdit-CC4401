
package plugin.HtmlViewer.src.actions;

import java.awt.event.ActionEvent;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.GUIUtilities;
import plugin.HtmlViewer.src.HtmlViewer;


public class home extends HtmlViewerAction {
    
	private static final long serialVersionUID = 2183528446575469446L;

	public home() {
        super("infoviewer.home");
    }
    
    public void actionPerformed(ActionEvent evt) {
        String home = jEdit.getProperty("infoviewer.homepage");
        HtmlViewer viewer = getViewer(evt);
        if (home == null) {
            GUIUtilities.error(viewer, "infoviewer.error.nohome", null);
        } else {
            viewer.gotoURL(home, true, 0);
        }
    }
}

