package htmlviewer.actions;

import java.awt.event.ActionEvent;


public class bookmarks_add extends HtmlViewerAction {
    

	private static final long serialVersionUID = -2516940595041688219L;

	public bookmarks_add() {
        super("htmlviewer.bookmarks_add");
    }
    
    public void actionPerformed(ActionEvent evt) {
        getViewer(evt).addToBookmarks();
    }
}

