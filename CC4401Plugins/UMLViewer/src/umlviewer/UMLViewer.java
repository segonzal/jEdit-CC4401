package umlviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
		this.textArea.setText("");
		//abrir archivos
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		for(String file : files){
			try {
				// Apertura del fichero y creacion de BufferedReader para poder
				// hacer una lectura comoda (disponer del metodo readLine()).
				archivo = new File (directory[0].concat("\\").concat(file));
				fr = new FileReader (archivo);
				br = new BufferedReader(fr);
				this.textArea.append(file.concat(":\n"));
				// Lectura del fichero
				String linea;
				while((linea=br.readLine())!=null)
					this.textArea.append(linea.concat("\n"));
			}
			catch(Exception e){
				e.printStackTrace();
			}finally{
		        // En el finally cerramos el fichero, para asegurarnos
		        // que se cierra tanto si todo va bien como si salta 
		        // una excepcion.
				try{                    
					if( null != fr ){   
						fr.close();     
					}                  
				}catch (Exception e2){ 
					e2.printStackTrace();
				}
			}
			this.textArea.append("----------------------------------------\n");
		}
		
		return;
	}
}