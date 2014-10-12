package plugin.HtmlViewer.src.actions;

import plugin.HtmlViewer.src.HtmlViewer;

import java.awt.event.ActionEvent;

import org.gjt.sp.jedit.EditAction;
import org.gjt.sp.jedit.jEdit;

public class ToggleSidebar extends HtmlViewerAction
{
	public static final String name ="infoviewer.toggle_sidebar"; 

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
		return "InfoViewer.getViewer(view).toggleSideBar();";
	}
*/

	public void actionPerformed(ActionEvent e)
	{
		getViewer(e).toggleSideBar();
	}

}
