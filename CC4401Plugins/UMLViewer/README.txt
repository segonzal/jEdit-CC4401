Archivo build.xml 'malo'.
Para crear el .jar se debe usar la opcion de exportar -> jar file de eclipse mientras se tiene seleccionado
el paquete CC4401Plugins.UMLViewer.
Para instalar el Plugin se debe copiar el .jar generado y pegarlo en la carpeta jars de jEdit.
***Tareas
-HECHO-Que jEdit detecte el plugin.
-HECHO-Que se desplieguen las opciones en el men�.
-HECHO-Cambiar los archivos a su propia carpeta src para mejorar el orden.
-HECHO-Hacer que la opci�n 'UMLViewer' cree una ventana de prueba.
-HECHO-Comprobar que se pueden agregar acciones y que se vea un efecto de prueba.
-RECHAZADO-Cambiar las opciones del menu Plugins de la Barra Superior al menu Plugins de la barra de File Browser.
	Al final se crea otra instancia de File Browser para seleccionar la carpeta.
-HECHO-Implementar clase que recorra el fichero actual de File Browser.
	-Hacer que el recorrido incluya subcarpetas.
	-S�lo entregar archivos con extensi�n .java
-HECHO-Implementar rutina de salida/t�rmino.
	-Se aprovecha la implementaci�n propia de las clases que extienden de EditPlugin.
-HECHO-Implementar Parser para obtener datos de las clases.
	-Parser b�sico que entrega clase, extiende a, implementa a y m�todos.
-HECHO-Implementar creador de dibujo.
	-Se utiliza PlantUML para dibujar los gr�ficos uml. Se proporciona el .jar de la librer�a de manera aparte.
	-PlantUML utiliza Graphviz, con lo cual se debe tener correctamente instalada una versi�n reciente.
