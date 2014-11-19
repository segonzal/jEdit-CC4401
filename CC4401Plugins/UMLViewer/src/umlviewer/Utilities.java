package umlviewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public static String parseFiles(String[] directory, List<String> files) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String separator = "[\\s+\\{+\\}+;+]";
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		
		for(String file : files){
			try {
				// Apertura del fichero y creacion de BufferedReader para poder
				// hacer una lectura comoda (disponer del metodo readLine()).
				archivo = new File (directory[0].concat("\\").concat(file));
				fr = new FileReader (archivo);
				br = new BufferedReader(fr);

				// Lectura del fichero a string (candidato a pasa a metodo extra)
				String line;
				List<String> tokens= new ArrayList<String>();
				while((line=br.readLine())!=null){
					if(line.contains("//"))
						continue;
					for(String token : line.split(separator)){
						tokens.add(token);
					}
				}
				
				data.add(parseClass(tokens));

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
		}
		return data.toString();
		
	}
	
	private static Map<String,String> parseClass(List<String> tokens){
		Map<String,String> data = new HashMap<String,String>();
		boolean visib_b=false,class_b=false,extends_b=false,implements_b=false;
		int method_c=0;
		
		for(String token : tokens){
			if(token.trim().equals("private")||token.trim().equals("public")||token.trim().equals("protected")){
				visib_b=true;
				continue;
			}
			if(token.trim().equals("class")){
				class_b=true;
				continue;
			}
			if(token.trim().equals("extends")){
				extends_b=true;
				continue;
			}
			if(token.trim().equals("implements")){
				implements_b=true;
				continue;
			}
			//aqui para introducir nuevas condiciones de si vio algo
			if(class_b){
				data.put("Class",token.trim());
				class_b = false;
				continue;
			}
			if(extends_b){
				data.put("Extends", token.trim());
				extends_b=false;
				continue;
			}
			if(implements_b){
				data.put("Implements", token.trim());
				implements_b=false;
				continue;
			}
			if(visib_b && token.trim().contains("(") && token.trim().contains(")") ){
				data.put("Method"+method_c, token.trim());
				visib_b = false;
				method_c++;
				continue;
			}
			//aqui para agregar nuevos datos
		}
		return data;
	}

}
