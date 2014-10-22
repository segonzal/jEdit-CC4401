package umlviewer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utilities {

	static public List<String> getFnD(File dir){
		List<String> list = new ArrayList<String>();
		String[] dirlist = dir.list();
		for(String file : dirlist){
			if(file.endsWith(".java")){//Modificar esto para aceptar carpetas y recorrerlas
				list.add(file);
			}
		}
		return list;
	}
}
