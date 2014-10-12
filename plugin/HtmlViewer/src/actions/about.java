package plugin.HtmlViewer.src.actions;

import java.awt.event.ActionEvent;

import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.jEdit;


public class about extends HtmlViewerAction {


	private static final long serialVersionUID = -3569448369419866147L;

	public about() {
        super("infoviewer.about");
    }

    public void actionPerformed(ActionEvent evt) {
        GUIUtilities.message(getViewer(evt), "infoviewer.aboutdialog",
            new String[] {
                jEdit.getProperty("plugin.infoviewer.InfoViewerPlugin.version") });
    }
}

