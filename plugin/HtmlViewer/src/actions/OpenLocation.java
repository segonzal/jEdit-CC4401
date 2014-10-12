package plugin.HtmlViewer.src.actions;

import java.awt.event.ActionEvent;

public class OpenLocation extends HtmlViewerAction
{
	public OpenLocation() {
		super("infoviewer.openlocation");
	}
	public void actionPerformed(ActionEvent e)
	{
		getViewer(e).focusAddressBar();
	}

}
