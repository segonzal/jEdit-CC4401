package plugin.HtmlViewer.src.actions;

import plugin.HtmlViewer.src.HtmlViewer;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.View;


public class edit_url extends HtmlViewerAction
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4493276148898998310L;


	public edit_url()
    {
        super("infoviewer.edit_url");
    }


    public void actionPerformed(ActionEvent evt)
    {
        View view = jEdit.getFirstView();
        HtmlViewer infoviewer = getViewer(evt);
        String url = infoviewer.getCurrentURL().getURL();
        Frame frame = getFrame(evt);

        if (frame != null && frame instanceof View)
            view = (View)frame;

        if (url == null)
        {
            GUIUtilities.error(null, "infoviewer.error.nourl", null);
            return;
        }

        // cut off anchor:
        int anchorPos = url.indexOf('#');
        if (anchorPos >= 0)
            url = url.substring(0, anchorPos);

        // open url:
        view.toFront();
        jEdit.openFile(view, url);
    }
}

