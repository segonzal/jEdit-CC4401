package htmlviewer.actions;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.accessibility.AccessibleHyperlink;
import javax.accessibility.AccessibleHypertext;
import javax.accessibility.AccessibleText;
import javax.swing.JEditorPane;


public class follow_link extends HtmlViewerAction {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2574264546733855775L;
	private Point clickPoint = null;
    
    public follow_link() {
        super("htmlviewer.follow_link");
    }
    
    public void actionPerformed(ActionEvent evt) {
        if (clickPoint == null) return;
        JEditorPane pane = getViewer(evt).getViewer();
        AccessibleText txt = pane.getAccessibleContext().getAccessibleText();
        if (txt != null && txt instanceof AccessibleHypertext) {
            AccessibleHypertext hyp = (AccessibleHypertext) txt;
            int charIndex = hyp.getIndexAtPoint(clickPoint);
            int linkIndex = hyp.getLinkIndex(charIndex);
            if (linkIndex >= 0) {
                AccessibleHyperlink lnk = hyp.getLink(linkIndex);
                lnk.doAccessibleAction(0);
            }
        }
    }
    
    public void setClickPoint(Point p) {
        clickPoint = p;
    }
}

