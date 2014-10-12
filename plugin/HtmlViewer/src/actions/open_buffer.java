package plugin.InfoViewer.infoviewer.actions;

import java.awt.event.ActionEvent;


public class open_buffer extends InfoViewerAction
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4070973801912095786L;


	public open_buffer()
    {
        super("infoviewer.open_buffer");
    }


    public void actionPerformed(ActionEvent evt)
    {
        getViewer(evt).gotoBufferURL();
    }

}

