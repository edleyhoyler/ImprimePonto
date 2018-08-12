package br.com.hoyler.apps.tools;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class CriarMensagens {

	public Boolean CriarMsgAlertaPerguntasSIMouNAO(String msg) {

		Boolean returnBoolean = false;

		ButtonType btnNAO = new ButtonType("NAO", ButtonData.OK_DONE);

		ButtonType btnSIM = new ButtonType("SIM", ButtonData.CANCEL_CLOSE);

		Alert alert = new Alert(AlertType.WARNING, msg, btnSIM, btnNAO);

		alert.setTitle("ALERTA");

		alert.setHeaderText("SUPER PERIGOSO");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.orElse(btnNAO) == btnSIM) {

			returnBoolean = true;
			System.out.println(btnSIM);

		} else {

			System.out.println(btnNAO);
		}
		return returnBoolean;
	}

	public void MensagemInformacoes(String cabecalho, String informacao) {

		MensagemInformacoes("", cabecalho, informacao);
	}

	private void MensagemInformacoes(String Titulo, String cabecalho, String informacao) {

		Alert alert = new Alert(AlertType.INFORMATION);

		if (Titulo.equals("")) {
			alert.setTitle("Informação");
		} else {
			alert.setTitle(Titulo);
		}

		if (cabecalho.equals("")) {
			alert.setHeaderText("Informação Importante");
		} else {
			alert.setHeaderText(cabecalho);
		}

		if (informacao.equals("")) {
			alert.setContentText("");
		} else {
			alert.setContentText(informacao);
		}
		alert.showAndWait();
	}

	public void MensagemAlerta(String cabecalho, String informacao) {

		MensagemAlerta("", cabecalho, informacao);
	}

	private void MensagemAlerta(String Titulo, String cabecalho, String informacao) {

		Alert alert = new Alert(AlertType.WARNING);

		if (Titulo.equals("")) {
			alert.setTitle("Alerta");
		} else {
			alert.setTitle(Titulo);
		}

		if (cabecalho.equals("")) {
			alert.setHeaderText("Alerta Importante");
		} else {
			alert.setHeaderText(cabecalho);
		}

		if (informacao.equals("")) {
			alert.setContentText("");
		} else {
			alert.setContentText(informacao);
		}
		alert.showAndWait();
	}

	public void MensagemErro(String cabecalho, String informacao) {
		MensagemErro("", cabecalho, informacao);
	}

	private void MensagemErro(String Titulo, String cabecalho, String informacao) {

		Alert alert = new Alert(AlertType.ERROR);

		if (Titulo.equals("")) {
			alert.setTitle("Erro");
		} else {
			alert.setTitle(Titulo);
		}

		if (cabecalho.equals("")) {
			alert.setHeaderText("Erro Grave");
		} else {
			alert.setHeaderText(cabecalho);
		}

		if (informacao.equals("")) {
			alert.setContentText("");
		} else {
			alert.setContentText(informacao);
		}
		alert.showAndWait();
	}
}
