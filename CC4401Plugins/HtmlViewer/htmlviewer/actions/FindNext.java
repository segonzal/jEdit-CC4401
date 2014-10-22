package htmlviewer.actions;

import htmlviewer.HtmlViewer;

import java.awt.event.ActionEvent;

public class FindNext extends Find
{
	public FindNext() {
		super("htmlviewer.findnext");
	}
	public void actionPerformed(ActionEvent e)
	{
        HtmlViewer v = getViewer(e);
        
	}

}
