package plugin.InfoViewer.infoviewer.actions;

import java.awt.event.ActionEvent;


public class bookmarks_add extends InfoViewerAction {
    

	private static final long serialVersionUID = -2516940595041688219L;

	public bookmarks_add() {
        super("infoviewer.bookmarks_add");
    }
    
    public void actionPerformed(ActionEvent evt) {
        getViewer(evt).addToBookmarks();
    }
}

