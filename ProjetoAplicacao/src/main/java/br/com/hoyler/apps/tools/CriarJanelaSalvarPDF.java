package br.com.hoyler.apps.tools;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CriarJanelaSalvarPDF {

	public File CriarArquivoParaSalvarPDF(String nome) {

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Arquivo (*.pdf)", "*.pdf");
		
		fileChooser.getExtensionFilters().add(extFilter);
		
		fileChooser.setTitle("Salvar PDF");
		
		fileChooser.setInitialFileName(nome);
		
		// Stage stage = (Stage) vBox.getScene().getWindow();
		
		// Show save file dialog
		File file = fileChooser.showSaveDialog(new Stage());

		if (file != null)
		{
			return (file);
		}
		else
		{
			System.out.println(String.format( "public class CriarJanelaSalvarPDF CriarArquivoParaSalvarPDF : [%s]", "null"));		
		}
		
		
		return file;
	}

	public File CriarDiretorioParaSalvarPDF(String nome)
	{
		File returnFile = null;
		
		String diretorioPDF = DiretorioParaSalvarPDF();
	
		if (diretorioPDF != null)
		{
		 
			returnFile = new File(String.format("%s\\%s.pdf", diretorioPDF, nome));
			
		}
		else
		{
			System.out.println(String.format( "public class CriarJanelaSalvarPDF CriarDiretorioParaSalvarPDF : [%s]", diretorioPDF));		
		}
		
		
		
		return (returnFile);
	}
	
	private String DiretorioParaSalvarPDF() {

		String returnString = new SelecionarDiretorio().Abrir("Diretorio para Salvar PDF(s)");
		
		System.out.println(String.format( "public class CriarJanelaSalvarPDF DiretorioParaSalvarPDF : [%s]", returnString));
		
		return (returnString);

	}

}
