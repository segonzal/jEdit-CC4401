package umlviewer;

import java.io.File;

public class Utilities {

	static public String[] getFnD(File dir){
		String[] list = dir.list();
		/*if (list != null){
			//Aqui deberia hacerse algo para soportar subdirectorios
			
		}*/
		return list;
	}
}
