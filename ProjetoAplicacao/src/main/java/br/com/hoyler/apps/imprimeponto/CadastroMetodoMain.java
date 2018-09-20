package br.com.hoyler.apps.imprimeponto;

import java.io.IOException;

import br.com.hoyler.apps.imagens.ImagensGetSet;
import br.com.hoyler.apps.tools.JanelasCSSEnum;
import br.com.hoyler.apps.tools.JanelasFXMLEnum;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastroMetodoMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			new CadastroMetodoMain().AbrirJanela(JanelasFXMLEnum.IMPRIME_PONTO, JanelasCSSEnum.CSS_DEFAULT,
					primaryStage);

		} catch (Exception e) {
			System.out
					.println(String.format("public class CadastroMetodoMain start ERRO Exception: %s", e.getMessage()));
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

		} catch (NoSuchFieldException nfe) {
			System.out.println(String.format(
					"public class CadastroMetodoMain AbrirJanela ERRO NoSuchFieldException: %s", nfe.getMessage()));
		} catch (SecurityException sex) {
			System.out.println(String.format("public class CadastroMetodoMain AbrirJanela ERRO SecurityException: %s",
					sex.getMessage()));
		} catch (IOException ioe) {
			System.out.println(String.format("public class CadastroMetodoMain AbrirJanela ERRO IOException: %s",
					ioe.getMessage()));
		}
	}
}
