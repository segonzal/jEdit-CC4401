
package htmlviewer.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import org.gjt.sp.jedit.View;


public class close extends HtmlViewerAction
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4424449640799402279L;


	public close()
    {
        super("htmlviewer.close");
    }


    public void actionPerformed(ActionEvent evt)
    {
        Frame frame = getFrame(evt);

        if (frame == null)
            return;

        if (frame instanceof View)
            ((View)frame).getDockableWindowManager().removeDockableWindow("htmlviewer");
        else
            frame.dispose();
    }
}

