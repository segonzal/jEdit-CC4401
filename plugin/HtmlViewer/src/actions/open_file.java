package plugin.InfoViewer.infoviewer.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.io.FileVFS;
import org.gjt.sp.jedit.io.VFSManager;


public class open_file extends InfoViewerAction
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8244129956268231628L;


	public open_file()
    {
        super("infoviewer.open_file");
    }


    public void actionPerformed(ActionEvent evt)
    {
        if (lastfile == null)
        {
            lastfile = jEdit.getProperty("infoviewer.lastfile");
            if (lastfile == null)
                lastfile = jEdit.getJEditHome();
        }

        Frame frame = getFrame(evt);
        View view = jEdit.getFirstView();
        if(frame != null && frame instanceof View)
            view = (View) frame;

        String files[] = GUIUtilities.showVFSFileDialog(
            view, lastfile, JFileChooser.OPEN_DIALOG, false);

        if (files == null || files.length == 0)
            return; // no file chosen

        if (VFSManager.getVFSForPath(files[0]) instanceof FileVFS)
            lastfile = "file:" + files[0];
        else
            lastfile = files[0];

        jEdit.setProperty("infoviewer.lastfile", lastfile);
        getViewer(evt).gotoURL(lastfile, true, -1);
    }


    private static String lastfile = null;

}

