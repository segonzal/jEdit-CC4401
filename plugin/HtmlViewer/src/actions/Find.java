package plugin.HtmlViewer.src.actions;

import plugin.HtmlViewer.src.HtmlViewer;

import java.awt.event.ActionEvent;

public class Find extends HtmlViewerAction
{
	public Find() {
		super("infoviewer.find");
	}
	protected Find(String name) {
		super(name);
	}
	public void actionPerformed(ActionEvent e)
	{
		HtmlViewer v = getViewer(e);
        
		getViewer(e).focusAddressBar();
	}

    protected static String lastString; 
}
