package CC4401Plugins.UMLViewer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.View;

import org.gjt.sp.jedit.gui.DockableWindowManager;


public class UMLViewer extends JPanel{
// private members
private View view;
private boolean floating;
public static final String title = "UMLViewer";
public static final String label = "UMLViewer";

private UMLViewerImgArea imgArea;

	public UMLViewer(View view, String position){
		
		super(new BorderLayout());
		
		this.view=view;
		this.floating = position.equals(DockableWindowManager.FLOATING);
		
		if(floating){
			this.setPreferredSize(new Dimension(500,250));
		}
		
		imgArea = new UMLViewerImgArea();
		JScrollPane pane = new JScrollPane(imgArea);
		add(BorderLayout.CENTER, pane);
	}
	
	public void generateUML(){
		return;
	}
}