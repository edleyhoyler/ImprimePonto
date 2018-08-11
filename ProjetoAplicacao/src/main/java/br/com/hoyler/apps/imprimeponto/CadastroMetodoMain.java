package br.com.hoyler.apps.imprimeponto;

import java.io.IOException;

import br.com.hoyler.apps.imagens.ImagensGetSet;
import br.com.hoyler.apps.tools.JanelasCSSEnum;
import br.com.hoyler.apps.tools.JanelasFXMLEnum;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

public class CadastroMetodoMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			/*
			 * VBox root =
			 * (VBox)FXMLLoader.load(getClass().getResource("ImprimeFolha.fxml")); Scene
			 * scene = new Scene(root,400,400);
			 * scene.getStylesheets().add(getClass().getResource("application.css").
			 * toExternalForm()); primaryStage.setScene(scene); primaryStage.show();
			 */
			 
			
			new CadastroMetodoMain().AbrirJanela(JanelasFXMLEnum.IMPRIME_PONTO, JanelasCSSEnum.CSS_DEFAULT, primaryStage);
			
			
			//new CadastroMetodoMain().AbrirJanela(JanelasFXMLEnum.CADASTRO_PESSOA, JanelasCSSEnum.CSS_DEFAULT);
			//new CadastroMetodoMain().AbrirJanela(JanelasFXMLEnum.CADASTRO_EMPRESA, JanelasCSSEnum.CSS_DEFAULT);
			// new CadastroMetodoMain().AbrirJanela(JanelasFXMLEnum.CADASTRO_FUNCAO, JanelasCSSEnum.CSS_DEFAULT);
			
			//new CadastroMetodoMain().AbrirJanela(JanelasFXMLEnum.RELATORIO_EMPRESA, JanelasCSSEnum.CSS_DEFAULT);
			/*
			 * 
			 * //final JanelasFXMLEnum ARQUIVO_FXML = JanelasFXMLEnum.CADASTRO_EMPRESA;
			 * 
			 * final JanelasFXMLEnum ARQUIVO_FXML = JanelasFXMLEnum.CADASTRO_PESSOA;
			 * 
			 * //final JanelasFXMLEnum ARQUIVO_FXML = JanelasFXMLEnum.CADASTRO_PESSOA;
			 * 
			 * final JanelasCSSEnum ARQUIVO_CSS = JanelasCSSEnum.CSS_DEFAULT;
			 * 
			 * final String JANELA = ARQUIVO_FXML.getVALOR();
			 * 
			 * final String JANELA_TITULO =
			 * JanelasFXMLEnum.getIDescricaoEnum(ARQUIVO_FXML.name());
			 * 
			 * final String APLICATION_CSS = ARQUIVO_CSS.getVALOR();
			 * 
			 * final Parent root = FXMLLoader.load(getClass().getResource(JANELA));
			 * 
			 * primaryStage.setTitle(JANELA_TITULO);
			 * 
			 * primaryStage.setMaximized(true);
			 * 
			 * final Scene scene = new Scene(root, ((VBox) root).getPrefWidth(), ((VBox)
			 * root).getPrefHeight());
			 * 
			 * scene.getStylesheets().add(getClass().getResource(APLICATION_CSS).
			 * toExternalForm());
			 * 
			 * primaryStage.setScene(scene);
			 * 
			 * primaryStage.show();
			 * 
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void FecharJanela(VBox vBox) {

		Stage stage = (Stage) vBox.getScene().getWindow();
		stage.close();

	}
	
	public void AbrirJanela(JanelasFXMLEnum janelasFXMLEnum, JanelasCSSEnum janelasCSSEnum, Stage primaryStage) {

		final JanelasFXMLEnum ARQUIVO_FXML = janelasFXMLEnum;

		final JanelasCSSEnum ARQUIVO_CSS = janelasCSSEnum;

		final String JANELA = ARQUIVO_FXML.getVALOR();

		final String JANELA_TITULO;
		try {
			JANELA_TITULO = JanelasFXMLEnum.getIDescricaoEnum(ARQUIVO_FXML.name());

			final String APLICATION_CSS = ARQUIVO_CSS.getVALOR();

			//final Stage primaryStage = new Stage();

			final Parent root = FXMLLoader.load(getClass().getResource(JANELA));

			primaryStage.setTitle(JANELA_TITULO);

			new ImagensGetSet().setIconLogoPNG(primaryStage);

			final Scene scene = new Scene(root, ((VBox) root).getPrefWidth(), ((VBox) root).getPrefHeight());

			scene.getStylesheets().add(getClass().getResource(APLICATION_CSS).toExternalForm());

			if (janelasFXMLEnum == JanelasFXMLEnum.IMPRIME_PONTO) {

				primaryStage.setResizable(false);
			}

			primaryStage.setScene(scene);

			primaryStage.show();
			
			
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
