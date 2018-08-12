package br.com.hoyler.apps.tools;

import java.io.File;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

public class CriarJanelaSalvarPDF {

	public File CriarArquivoParaSalvarPDF(String nome) {

		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Arquivo (*.pdf)", "*.pdf");
		
		fileChooser.getExtensionFilters().add(extFilter);
		
		fileChooser.setTitle("Salvar PDF --> " + nome);
		
		fileChooser.setInitialFileName(nome);

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

		String returnString = SelecionarDiretorio.AbrirDialogoParaSelecionarDiretorio("Diretorio para Salvar PDF(s)");
		
		System.out.println(String.format( "public class CriarJanelaSalvarPDF DiretorioParaSalvarPDF : [%s]", returnString));
		
		return (returnString);

	}

}
