package br.com.hoyler.apps.tools;

import java.io.File;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SelecionarDiretorio {

	
	public String Abrir(String setTitle) {

		DirectoryChooser directoryChooser = new DirectoryChooser();

		directoryChooser.setTitle(setTitle);
	
		File selectedDirectory = directoryChooser.showDialog(new Stage());

		String returnString;

		if (selectedDirectory == null) {

			returnString = null;

		} else {

			returnString = (selectedDirectory.getAbsolutePath());

		}

		return (returnString);

	}
	
}
