package htmlviewer.actions;

import htmlviewer.HtmlViewer;

import java.awt.event.ActionEvent;

import org.gjt.sp.jedit.EditAction;
import org.gjt.sp.jedit.jEdit;

/**
 * An action to toggle whether we see the sidebar or not.
 * 
 * TODO: Make the menu item that appears a checkboxMenuItem
 * 
 * @author ezust
 * 
 */
public class ToggleSidebar extends HtmlViewerAction
{
	public static final String name ="htmlviewer.toggle_sidebar"; 

	public boolean isToggle() {
		return true;
	}
	
	public ToggleSidebar()
	{
		super(name);
	}

/*	public String getCode()
	{
		// TODO Auto-generated method stub
		return "HtmlViewer.getViewer(view).toggleSideBar();";
	}
*/

	public void actionPerformed(ActionEvent e)
	{
		getViewer(e).toggleSideBar();
	}

}
