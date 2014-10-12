package plugin.InfoViewer.infoviewer.actions;

import plugin.InfoViewer.infoviewer.*;
import java.awt.event.*;


public class bookmarks_edit extends InfoViewerAction {


	private static final long serialVersionUID = 8814698507050714830L;

	public bookmarks_edit() {
        super("infoviewer.bookmarks_edit");
    }

    public void actionPerformed(ActionEvent evt) {
        new BookmarksDialog(getFrame(evt));
    }

}

