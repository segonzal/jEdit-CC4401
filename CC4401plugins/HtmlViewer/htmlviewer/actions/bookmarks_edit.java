package htmlviewer.actions;

import htmlviewer.*;
import java.awt.event.*;


public class bookmarks_edit extends HtmlViewerAction {


	private static final long serialVersionUID = 8814698507050714830L;

	public bookmarks_edit() {
        super("htmlviewer.bookmarks_edit");
    }

    public void actionPerformed(ActionEvent evt) {
        new BookmarksDialog(getFrame(evt));
    }

}

