package umlviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.browser.VFSBrowser;
import org.gjt.sp.jedit.gui.DockableWindowManager;


public class UMLViewer extends JPanel{

	private static final long serialVersionUID = 9058963628784818159L;
	private View view;
	private boolean floating;
	public static final String title = "UMLViewer";
	public static final String label = "UMLViewer";
	
	private UMLViewerTextArea textArea;
	
	public UMLViewer(View view, String position){
		super(new BorderLayout());
		
		this.view = view;
		this.floating = position.equals(DockableWindowManager.FLOATING);
		
		if (floating)
			this.setPreferredSize(new Dimension(640, 480));
		
		this.textArea = new UMLViewerTextArea();
		JScrollPane pane = new JScrollPane(this.textArea);
		
		add(BorderLayout.CENTER,pane);
	}
	
	public void generateUML(){
		String[] directory = GUIUtilities.showVFSFileDialog(this.view, null, VFSBrowser.CHOOSE_DIRECTORY_DIALOG, false);
		List<String> files = Utilities.getFnD(new File(directory[0]));
		//this.textArea.setText("Abriendo...");
		
		String lines = Utilities.parseFiles(directory, files);
		//this.textArea.append("\nProcesando\n");
		//abrir archivos
		this.textArea.setText(lines);
		
		//this.textArea.append("\nListo\n");
		
		return;
	}
}