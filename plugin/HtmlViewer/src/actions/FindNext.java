package plugin.HtmlViewer.src.actions;

import  plugin.HtmlViewer.src.HtmlViewer;

import java.awt.event.ActionEvent;

public class FindNext extends Find
{
	public FindNext() {
		super("infoviewer.findnext");
	}
	public void actionPerformed(ActionEvent e)
	{
		HtmlViewer v = getViewer(e);
        
	}

}
