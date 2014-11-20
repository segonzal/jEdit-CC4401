package umlviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.IIOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.sourceforge.plantuml.SourceStringReader;

import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.browser.VFSBrowser;
import org.gjt.sp.jedit.gui.DockableWindowManager;

public class UMLViewer extends JPanel {

	private static final long serialVersionUID = 9058963628784818159L;
	private View view;
	private boolean floating;
	public static final String title = "UMLViewer";
	public static final String label = "UMLViewer";

	// private UMLViewerTextArea textArea;
	private UMLViewerGraphArea graphArea;

	public UMLViewer(View view, String position) {
		super(new BorderLayout());

		this.view = view;
		this.floating = position.equals(DockableWindowManager.FLOATING);

		if (floating)
			this.setPreferredSize(new Dimension(640, 480));

		// this.textArea = new UMLViewerTextArea();
		this.graphArea = new UMLViewerGraphArea();
		JScrollPane pane = new JScrollPane(this.graphArea);

		add(BorderLayout.CENTER, pane);
	}

	public void generateUML() {
		String[] directory = GUIUtilities.showVFSFileDialog(this.view, null,
				VFSBrowser.CHOOSE_DIRECTORY_DIALOG, false);
		List<String> files = Utilities.getFnD(new File(directory[0]));
		// this.textArea.setText("Abriendo...");

		String lines = Utilities.parseFiles(directory, files);
		
		try {
			File file = File.createTempFile("tempo", ".png");
			OutputStream png = new FileOutputStream(file);
			//String source = "@startuml\nBob -> Alice : hello\n@enduml\n";

			SourceStringReader reader = new SourceStringReader(lines);
			String desc = reader.generateImage(png);
			this.graphArea.setResource(file);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
		return;
	}
}