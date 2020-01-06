package br.com.hoyler.apps.imprimeponto;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.jvfx.viewer.JasperViewerFX;

import br.com.hoyler.apps.database.sqlite.Funcoes;
import br.com.hoyler.apps.database.sqlite.FuncoesFabricaDAO;
import br.com.hoyler.apps.imagens.ImagensGetSet;
import br.com.hoyler.apps.tools.CriarJanelaSalvarPDF;
import br.com.hoyler.apps.tools.CriarMensagens;
import br.com.hoyler.apps.tools.HyperlinkCreate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.util.JRLoader;

public class CadastroFuncaoController {

	String NOVO = ("Novo");
	String SALVAR = ("Salvar");
	String CANCELAR = ("Cancelar");
	String ALTERAR = ("Alterar");
	String DELETAR = ("Deletar");
	String ATUALIZAR = ("Atualizar");
	String CONFIRMAR = ("Confirmar");

	String CadastroAntigo = "";
	Funcoes funcoes = new Funcoes();
	ImagensGetSet imagensGetSet = new ImagensGetSet();
	CadastroFuncao cadastroFuncao = new CadastroFuncao();
	FuncoesFabricaDAO funcoesFabricaDAO = new FuncoesFabricaDAO();
	JRResultSetDataSource jrResultSetDataSource;
	JasperReport jasperReport;
	JasperPrint jasperPrint;
	File file;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private VBox vBox;

	@FXML
	private ImageView ImageViewLogo;

	@FXML
	private Hyperlink HyperLinkHOYLERTecnologiaID;

	@FXML
	private Label LabelCodigoID;

	@FXML
	private TextField TextFieldCodigoID;

	@FXML
	private Label LabelNomeID;

	@FXML
	private TextField TextFieldNomeID;

	@FXML
	private Button ButtonNovoID;

	@FXML
	private Button ButtonAlterarID;

	@FXML
	private Button ButtonDeletarID;

	@FXML
	private Button ButtonListarID;

	@FXML
	private TextField TextFieldListarID;

	@FXML
	private TableView<Funcoes> TableViewColaboradorID;

	@FXML
	private TableColumn<Funcoes, Integer> TableColumnCodigoID;

	@FXML
	private TableColumn<Funcoes, String> TableColumnFuncaoID;

	@FXML
	private Button ButtonPDFID;

	@FXML
	private Button ButtonImprimirID;

	@FXML
	private Button ButtonVisualizarID;

	@FXML
	private Button ButtonSairID;

	@FXML
	private ProgressBar ProgressBarID;

	@FXML
	void ButtonAlterarID_OnAction(ActionEvent event) {

		String BOTAO = (ButtonAlterarID.getText());

		if (BOTAO.equals(ALTERAR)) {
			if (TextFieldCodigoID.getText().length() >= 1) {

				CadastroAntigo = (TextFieldNomeID.getText());
				ButtonAlterarID.setText(ATUALIZAR);
				ButtonDeletarID.setText(CANCELAR);

				cadastroFuncao.setFocusTextField(TextFieldNomeID);
				cadastroFuncao.setOpacityTextFieldEscrita(TextFieldNomeID);

				ButtonNovoID.setDisable(true);
				ButtonListarID.setDisable(true);
				TextFieldListarID.setDisable(true);
				TableViewColaboradorID.setDisable(true);
			}

		}

		if (BOTAO.equals(ATUALIZAR)) {

			if (TextFieldNomeID.getText().length() >= 1) {

				String msg = ("Deseja Realmente Atualizar?");
				Boolean resportaPergunta = new CriarMensagens().CriarMsgAlertaPerguntasSIMouNAO(msg);

				if (resportaPergunta) {

					funcoes.setNome(TextFieldNomeID.getText().toString());

					funcoesFabricaDAO.updateNome(CadastroAntigo, funcoes);

					cadastroFuncao.Listar(TextFieldListarID, TableViewColaboradorID);

					ButtonAlterarID.setText(ALTERAR);
					ButtonDeletarID.setText(DELETAR);

					TextFieldCodigoID.setText("");
					TextFieldNomeID.setText("");
					TextFieldListarID.setText("");

					cadastroFuncao.setOpacityTextFieldEscrita(TextFieldListarID);
					cadastroFuncao.setOpacityTextFieldLeitura(TextFieldCodigoID);
					cadastroFuncao.setOpacityTextFieldLeitura(TextFieldNomeID);

					ButtonNovoID.setDisable(false);
					ButtonListarID.setDisable(false);
					TextFieldListarID.setDisable(false);
					TableViewColaboradorID.setDisable(false);

					CadastroAntigo = "";

					new CriarMensagens().MensagemInformacoes("Atualizado com SUCESSO", funcoes.getNome());

					cadastroFuncao.setFocusTextField(TextFieldListarID);

				}

			} else {
				new CriarMensagens().MensagemAlerta("Favor", "Checar os dados da FUNÇÃO");
			}
		}

		if (BOTAO.equals(CONFIRMAR)) {

			if (TextFieldCodigoID.getText().length() >= 1) {

				String msg = ("Deseja Realmente Deletar?");
				Boolean resportaPergunta = new CriarMensagens().CriarMsgAlertaPerguntasSIMouNAO(msg);

				if (resportaPergunta) {
					String NOME = TextFieldNomeID.getText().toString();
					int CODIGO = Integer.parseInt(TextFieldCodigoID.getText().toString());
					boolean retornoBool = false;
					retornoBool = new FuncoesFabricaDAO().deletarCodigos(CODIGO);

					cadastroFuncao.Listar(TextFieldListarID, TableViewColaboradorID);

					ButtonAlterarID.setText(ALTERAR);
					ButtonDeletarID.setText(DELETAR);

					TextFieldCodigoID.setText("");
					TextFieldNomeID.setText("");
					TextFieldListarID.setText("");

					cadastroFuncao.setOpacityTextFieldEscrita(TextFieldListarID);
					cadastroFuncao.setOpacityTextFieldLeitura(TextFieldCodigoID);
					cadastroFuncao.setOpacityTextFieldLeitura(TextFieldNomeID);

					ButtonNovoID.setDisable(false);
					ButtonListarID.setDisable(false);
					TextFieldListarID.setDisable(false);
					TableViewColaboradorID.setDisable(false);

					if (retornoBool == false) {
						new CriarMensagens().MensagemErro("Erro ao Deletar", "Não Foi Possivel Deletar");
					} else {
						new CriarMensagens().MensagemInformacoes("Função deletada com SUCESSO", NOME);

					}

					cadastroFuncao.setFocusTextField(TextFieldListarID);
				}
			}

		}

		if (BOTAO.equals(SALVAR)) {

			if (TextFieldNomeID.getText().length() >= 1) {

				funcoes.setNome(TextFieldNomeID.getText().toString());

				new FuncoesFabricaDAO().salvarDados(funcoes);

				cadastroFuncao.Listar(TextFieldListarID, TableViewColaboradorID);

				ButtonAlterarID.setText(ALTERAR);
				ButtonDeletarID.setText(DELETAR);

				TextFieldCodigoID.setText("");
				TextFieldNomeID.setText("");
				TextFieldListarID.setText("");

				cadastroFuncao.setOpacityTextFieldEscrita(TextFieldListarID);
				cadastroFuncao.setOpacityTextFieldLeitura(TextFieldCodigoID);
				cadastroFuncao.setOpacityTextFieldLeitura(TextFieldNomeID);

				ButtonNovoID.setDisable(false);
				ButtonListarID.setDisable(false);
				TextFieldListarID.setDisable(false);
				TableViewColaboradorID.setDisable(false);
				new CriarMensagens().MensagemInformacoes("Salvo com SUCESSO", funcoes.getNome());
				cadastroFuncao.setFocusTextField(TextFieldListarID);
			} else {

				new CriarMensagens().MensagemAlerta("Favor", "Checar nome da Função");
				cadastroFuncao.setFocusTextField(TextFieldNomeID);
			}

		}

	}

	@FXML
	void ButtonDeletar_OnAction(ActionEvent event) {

		String BOTAO = (ButtonDeletarID.getText());

		if (BOTAO.equals(DELETAR)) {

			if (TextFieldNomeID.getText().length() >= 1) {

				ButtonAlterarID.setText(CONFIRMAR);
				ButtonDeletarID.setText(CANCELAR);

				cadastroFuncao.setFocusTextField(TextFieldNomeID);

				// new CadastroFuncao().setOpacityTextFieldEscrita(TextFieldNomeID);

				// new CadastroFuncao().setOpacityTextFieldLeitura(TextFieldListarID);

				// TextFieldNomeID.setEditable(false);

				ButtonNovoID.setDisable(true);
				ButtonListarID.setDisable(true);
				TextFieldListarID.setDisable(true);
				TableViewColaboradorID.setDisable(true);

			}

		}

		if (BOTAO.equals(CANCELAR)) {

			ButtonAlterarID.setText(ALTERAR);
			ButtonDeletarID.setText(DELETAR);

			TextFieldCodigoID.setText("");
			TextFieldNomeID.setText("");
			TextFieldListarID.setText("");

			cadastroFuncao.setOpacityTextFieldEscrita(TextFieldListarID);

			cadastroFuncao.setOpacityTextFieldLeitura(TextFieldCodigoID);
			cadastroFuncao.setOpacityTextFieldLeitura(TextFieldNomeID);

			ButtonNovoID.setDisable(false);
			ButtonListarID.setDisable(false);
			TextFieldListarID.setDisable(false);
			TableViewColaboradorID.setDisable(false);

			cadastroFuncao.setFocusTextField(TextFieldListarID);

		}
	}

	@FXML
	void ButtonImprimirID_OnAction(ActionEvent event) {

		final String msg = ("Deseja Realmente Imprimir?");

		final Boolean resportaPergunta = new CriarMensagens().CriarMsgAlertaPerguntasSIMouNAO(msg);

		if (resportaPergunta) {

			String NOME = TextFieldNomeID.getText().toString();

			if (NOME.isEmpty()) {
				ProgressBarID.setProgress(1);
				ProgressBarID.setVisible(true);
				jrResultSetDataSource = new JRResultSetDataSource(funcoesFabricaDAO.listarDados_ResultSet(null));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeFuncoes());
					ProgressBarID.setProgress(25);

					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
					ProgressBarID.setProgress(75);

					jasperPrint.setOrientation(OrientationEnum.PORTRAIT);
					ProgressBarID.setProgress(85);

					try {

						JasperPrintManager.printReport(jasperPrint, false);
						ProgressBarID.setProgress(100);
					} catch (Exception e) {

						System.out.println(
								"public class CadastroFuncaoController ButtonImprimirID_OnAction Impressao [ERRO] 4\n\n"
										+ e.getMessage() + "\n\n");

					}

				} catch (JRException e) {

					System.out.println(
							"public class CadastroFuncaoController ButtonImprimirID_OnAction Impressao [ERRO] 3\n\n"
									+ e.getMessage() + "\n\n");
				}
				ProgressBarID.setVisible(false);

			} else {
				ProgressBarID.setProgress(1);
				ProgressBarID.setVisible(true);
				jrResultSetDataSource = new JRResultSetDataSource(funcoesFabricaDAO.listarDados_ResultSet(NOME));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeFuncoes());
					ProgressBarID.setProgress(25);

					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
					ProgressBarID.setProgress(75);

					jasperPrint.setOrientation(OrientationEnum.PORTRAIT);
					ProgressBarID.setProgress(85);
					try {

						JasperPrintManager.printReport(jasperPrint, false);
						ProgressBarID.setProgress(100);
					} catch (Exception e) {

						System.out.println(
								"public class CadastroFuncaoController ButtonImprimirID_OnAction Impressao [ERRO] 2\n\n"
										+ e.getMessage() + "\n\n");

					}

				} catch (JRException e) {

					System.out.println(
							"public class CadastroFuncaoController ButtonImprimirID_OnAction Impressao [ERRO] 1\n\n"
									+ e.getMessage() + "\n\n");
				}
				ProgressBarID.setVisible(false);
			}
		}
	}

	@FXML
	void ButtonListar_OnAction(ActionEvent event) {

		new CadastroFuncao().Listar(TextFieldListarID, TableViewColaboradorID);
	}

	@FXML
	void ButtonNovoID_OnAction(ActionEvent event) {

		String BOTAO = (ButtonNovoID.getText());

		if (BOTAO.equals(NOVO)) {

			TextFieldCodigoID.setText("AUTOMATICO");
			TextFieldNomeID.setText("");
			TextFieldListarID.setText("");

			cadastroFuncao.setFocusTextField(TextFieldNomeID);
			cadastroFuncao.setOpacityTextFieldEscrita(TextFieldNomeID);
			cadastroFuncao.setOpacityTextFieldLeitura(TextFieldListarID);

			ButtonAlterarID.setText(SALVAR);
			ButtonDeletarID.setText(CANCELAR);

			ButtonNovoID.setDisable(true);
			ButtonListarID.setDisable(true);
			TextFieldListarID.setDisable(true);
			TableViewColaboradorID.setDisable(true);
		}
	}

	@FXML
	void ButtonPDFID_OnAction(ActionEvent event) {

		String NOME = TextFieldNomeID.getText().toString();

		if (NOME.isEmpty()) {

			file = new CriarJanelaSalvarPDF().CriarDiretorioParaSalvarPDF("RelatórioFunções");

			if (file != null) {
				ProgressBarID.setProgress(1);
				ProgressBarID.setVisible(true);
				jrResultSetDataSource = new JRResultSetDataSource(funcoesFabricaDAO.listarDados_ResultSet(null));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeFuncoes());
					ProgressBarID.setProgress(25);

					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
					ProgressBarID.setProgress(75);

					JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());
					ProgressBarID.setProgress(99);

					Runtime.getRuntime().exec("cmd.exe /c start " + file.getAbsoluteFile().getParent());
					ProgressBarID.setProgress(100);
				} catch (JRException e) {

					System.out.println(
							"public class CadastroEmpresaController ButtonPDFID_OnAction Impressao [ERRO] 1\n\n"
									+ e.getMessage() + "\n\n");

				} catch (IOException e) {

					System.out.println(
							"public class CadastroEmpresaController ButtonPDFID_OnAction Impressao [ERRO] 2\n\n"
									+ e.getMessage() + "\n\n");

				}
				ProgressBarID.setVisible(false);
			}
		} else {

			file = new CriarJanelaSalvarPDF().CriarArquivoParaSalvarPDF(NOME);

			if (file != null) {

				ProgressBarID.setProgress(1);
				ProgressBarID.setVisible(true);
				jrResultSetDataSource = new JRResultSetDataSource(funcoesFabricaDAO.listarDados_ResultSet(NOME));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeFuncoes());
					ProgressBarID.setProgress(25);

					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
					ProgressBarID.setProgress(75);

					JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());
					ProgressBarID.setProgress(99);

					Runtime.getRuntime().exec("cmd.exe /c start " + file.getAbsoluteFile().getParent());
					ProgressBarID.setProgress(100);

				} catch (JRException e) {

					System.out
							.println("public class CadastroFuncaoController ButtonPDFID_OnAction Impressao [ERRO] 1\n\n"
									+ e.getMessage() + "\n\n");

				} catch (IOException e) {

					System.out
							.println("public class CadastroFuncaoController ButtonPDFID_OnAction Impressao [ERRO] 2\n\n"
									+ e.getMessage() + "\n\n");

				}

				ProgressBarID.setVisible(false);
			}
		}
	}

	@FXML
	void ButtonSairID_OnAction(ActionEvent event) {

		new CadastroMetodoMain().FecharJanela(vBox);

	}

	@FXML
	void ButtonVisualizarID_OnAction(ActionEvent event) {

		String NOME = TextFieldNomeID.getText().toString();

		long tempoInicio = 0L;

		if (NOME.isEmpty()) {

			try {
				tempoInicio = System.currentTimeMillis();

				jrResultSetDataSource = new JRResultSetDataSource(funcoesFabricaDAO.listarDados_ResultSet(null));

				ProgressBarID.setProgress(25);
				jasperReport = (JasperReport) JRLoader
						.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeFuncoes());

				jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
				ProgressBarID.setProgress(75);

				new JasperViewerFX((Stage) vBox.getScene().getWindow()).viewReport("Relatorios Imprime Funções",
						jasperPrint);

				ProgressBarID.setProgress(100);
				ProgressBarID.setVisible(false);

				System.out.println("Tempo Total Relatorio  (NOME null): "
						+ TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis() - tempoInicio)));

			} catch (JRException e) {

				System.out.println(
						"public class CadastroFuncaoController ButtonVisualizarID_OnAction Impressao [ERRO] 2\n\n"
								+ e.getMessage() + "\n\n");
			}

		} else {

			try {

				tempoInicio = System.currentTimeMillis();

				ProgressBarID.setProgress(1);
				ProgressBarID.setVisible(true);

				jrResultSetDataSource = new JRResultSetDataSource(funcoesFabricaDAO.listarDados_ResultSet(NOME));
				ProgressBarID.setProgress(25);

				jasperReport = (JasperReport) JRLoader
						.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeFuncoes());
				ProgressBarID.setProgress(50);
				
				jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
				ProgressBarID.setProgress(75);

				new JasperViewerFX((Stage) vBox.getScene().getWindow()).viewReport("Relatorios Imprime Funções",
						jasperPrint);

				ProgressBarID.setProgress(100);
				ProgressBarID.setVisible(false);

				System.out.println("Tempo Total Relatorio: "
						+ TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis() - tempoInicio)));

			} catch (JRException e) {

				System.out.println(
						"public class CadastroFuncaoController ButtonVisualizarID_OnAction Impressao [ERRO] 1\n\n"
								+ e.getMessage() + "\n\n");

			}
		}

	}

	@FXML
	void HyperLinkHOYLERTecnologiaID_OnMouseClicked(MouseEvent event) {

		new HyperlinkCreate().openLinkeHOYLER(HyperLinkHOYLERTecnologiaID);

	}

	@FXML
	void initialize() {
		assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert ImageViewLogo != null : "fx:id=\"ImageViewLogo\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert HyperLinkHOYLERTecnologiaID != null : "fx:id=\"HyperLinkHOYLERTecnologiaID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert LabelCodigoID != null : "fx:id=\"LabelCodigoID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert TextFieldCodigoID != null : "fx:id=\"TextFieldCodigoID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert LabelNomeID != null : "fx:id=\"LabelNomeID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert TextFieldNomeID != null : "fx:id=\"TextFieldNomeID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert ButtonNovoID != null : "fx:id=\"ButtonNovoID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert ButtonAlterarID != null : "fx:id=\"ButtonAlterarID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert ButtonDeletarID != null : "fx:id=\"ButtonDeletarID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert ButtonListarID != null : "fx:id=\"ButtonListarID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert TextFieldListarID != null : "fx:id=\"TextFieldListarID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert TableViewColaboradorID != null : "fx:id=\"TableViewColaboradorID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert TableColumnCodigoID != null : "fx:id=\"TableColumnCodigoID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert TableColumnFuncaoID != null : "fx:id=\"TableColumnFuncaoID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert ButtonPDFID != null : "fx:id=\"ButtonPDFID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert ButtonImprimirID != null : "fx:id=\"ButtonImprimirID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert ButtonVisualizarID != null : "fx:id=\"ButtonVisualizarID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert ButtonSairID != null : "fx:id=\"ButtonSairID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";
		assert ProgressBarID != null : "fx:id=\"ProgressBarID\" was not injected: check your FXML file 'CadastroFuncao.fxml'.";

		imagensGetSet.setImageImageView(ImageViewLogo);
		imagensGetSet.setImageButtons(ButtonPDFID);
		imagensGetSet.setImageButtons(ButtonImprimirID);
		imagensGetSet.setImageButtons(ButtonVisualizarID);
		imagensGetSet.setImageButtons(ButtonSairID);

		Field[] fields = Funcoes.class.getDeclaredFields();

		TableColumnCodigoID.setCellValueFactory(new PropertyValueFactory<Funcoes, Integer>(fields[0].getName()));
		TableColumnFuncaoID.setCellValueFactory(new PropertyValueFactory<Funcoes, String>(fields[1].getName()));

		cadastroFuncao.TextFieldListarID_setOnKeyReleased(TextFieldListarID, TableViewColaboradorID);

		cadastroFuncao.TableViewColaboradorID_setOnMousePressed(TextFieldCodigoID, TextFieldNomeID,
				TableViewColaboradorID);

		cadastroFuncao.setOpacityTextFieldLeitura(TextFieldCodigoID);
		cadastroFuncao.setOpacityTextFieldLeitura(TextFieldNomeID);

	}
}
