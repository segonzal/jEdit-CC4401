package htmlviewer.actions;

import java.awt.event.ActionEvent;

public class OpenLocation extends HtmlViewerAction
{
	public OpenLocation() {
		super("htmlviewer.openlocation");
	}
	public void actionPerformed(ActionEvent e)
	{
		getViewer(e).focusAddressBar();
	}

}
