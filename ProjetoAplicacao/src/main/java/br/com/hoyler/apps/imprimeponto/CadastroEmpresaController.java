package br.com.hoyler.apps.imprimeponto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import br.com.hoyler.apps.database.sqlite.Empresas;
import br.com.hoyler.apps.database.sqlite.EmpresasFabricaDAO;
import br.com.hoyler.apps.imagens.ImagensGetSet;
import br.com.hoyler.apps.tools.CriarJanelaSalvarPDF;
import br.com.hoyler.apps.tools.CriarMensagens;
import br.com.hoyler.apps.tools.HyperlinkCreate;
import org.jvfx.viewer.JasperViewerFX;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.util.JRLoader;

public class CadastroEmpresaController {

	String NOVO = ("Novo");
	String SALVAR = ("Salvar");
	String CANCELAR = ("Cancelar");
	String ALTERAR = ("Alterar");
	String DELETAR = ("Deletar");
	String ATUALIZAR = ("Atualizar");
	String CONFIRMAR = ("Confirmar");

	String CadastroAntigo = "";

	ImagensGetSet imagensGetSet = new ImagensGetSet();
	CadastroEmpresa cadastroEmpresa = new CadastroEmpresa();
	EmpresasFabricaDAO empresasFabricaDAO = new EmpresasFabricaDAO();
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
	private AnchorPane AnchorPanePrincipalID;

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
	private Label LabelCNPJID;

	@FXML
	private TextField TextFieldCNPJID;

	@FXML
	private Label LabelEnderecoID;

	@FXML
	private TextField TextFieldEnderecoID;

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
	private TableView<Empresas> TableViewColaboradorID;

	@FXML
	private TableColumn<Empresas, Integer> TableColumnCodigoID;

	@FXML
	private TableColumn<Empresas, String> TableColumnNomeID;

	@FXML
	private TableColumn<Empresas, String> TableColumnCNPJID;

	@FXML
	private TableColumn<Empresas, String> TableColumnEnderecoID;

	@FXML
	private Button ButtonPDFID;

	@FXML
	private Button ButtonImprimirID;

	@FXML
	private Button ButtonVisualizarID;

	@FXML
	private Button ButtonSairID;

	@FXML
	void ButtonAlterarID_OnAction(ActionEvent event) {

		String BOTAO = (ButtonAlterarID.getText());

		if (BOTAO.equals(ALTERAR)) {
			if (TextFieldCodigoID.getText().length() >= 1) {

				CadastroAntigo = (TextFieldNomeID.getText());
				ButtonAlterarID.setText(ATUALIZAR);
				ButtonDeletarID.setText(CANCELAR);

				cadastroEmpresa.setFocusTextField(TextFieldNomeID);
				cadastroEmpresa.setOpacityTextFieldEscrita(TextFieldNomeID);
				cadastroEmpresa.setOpacityTextFieldEscrita(TextFieldCNPJID);
				cadastroEmpresa.setOpacityTextFieldEscrita(TextFieldEnderecoID);

				ButtonNovoID.setDisable(true);
				ButtonListarID.setDisable(true);
				TextFieldListarID.setDisable(true);
				TableViewColaboradorID.setDisable(true);
			}

		}

		if (BOTAO.equals(ATUALIZAR)) {

			if (TextFieldNomeID.getText().length() >= 1 & TextFieldCNPJID.getText().length() >= 1
					& TextFieldEnderecoID.getText().length() >= 1) {

				String msg = ("Deseja Realmente Atualizar?");
				Boolean resportaPergunta = new CriarMensagens().CriarMsgAlertaPerguntasSIMouNAO(msg);

				if (resportaPergunta) {
					boolean retornoBool = false;
					String NOME = TextFieldNomeID.getText().toString();
					String CNPJ = TextFieldCNPJID.getText().toString();
					String ENDERECO = TextFieldEnderecoID.getText().toString();

					retornoBool = empresasFabricaDAO.UpdateEmpresasDados(CadastroAntigo, NOME, CNPJ, ENDERECO);

					cadastroEmpresa.TextFieldListarID_setOnKeyReleased(TextFieldListarID, TableViewColaboradorID);

					ButtonAlterarID.setText(ALTERAR);
					ButtonDeletarID.setText(DELETAR);

					TextFieldCodigoID.setText("");
					TextFieldNomeID.setText("");
					TextFieldCNPJID.setText("");
					TextFieldEnderecoID.setText("");
					TextFieldListarID.setText("");

					cadastroEmpresa.setOpacityTextFieldEscrita(TextFieldListarID);

					cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldCodigoID);
					cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldNomeID);
					cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldCNPJID);
					cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldEnderecoID);

					ButtonNovoID.setDisable(false);
					ButtonListarID.setDisable(false);
					TextFieldListarID.setDisable(false);
					TableViewColaboradorID.setDisable(false);

					CadastroAntigo = "";

					if (retornoBool == false) {
						new CriarMensagens().MensagemErro("Erro ao Deletar", "Não Foi Possivel Deletar");
					} else {
						new CriarMensagens().MensagemInformacoes("Empresa Atualizado com SUCESSO",
								NOME + "\n" + CNPJ + "\n" + ENDERECO);
					}

					cadastroEmpresa.setFocusTextField(TextFieldListarID);
				}

			} else {
				new CriarMensagens().MensagemAlerta("Favor", "Checar os dados da Empresa");
			}
		}

		if (BOTAO.equals(CONFIRMAR)) {

			if (TextFieldCodigoID.getText().length() >= 1) {

				String msg = ("Deseja Realmente Deletar?");

				Boolean resportaPergunta = new CriarMensagens().CriarMsgAlertaPerguntasSIMouNAO(msg);

				if (resportaPergunta) {

					boolean retornoBool = false;

					String NOME = TextFieldNomeID.getText().toString();
					String CNPJ = TextFieldCNPJID.getText().toString();
					String ENDERECO = TextFieldEnderecoID.getText().toString();

					int CODIGO = Integer.parseInt(TextFieldCodigoID.getText().toString());

					retornoBool = empresasFabricaDAO.DeletarCodigosTabelaEmpresas(CODIGO);

					cadastroEmpresa.TextFieldListarID_setOnKeyReleased(TextFieldListarID, TableViewColaboradorID);

					ButtonAlterarID.setText(ALTERAR);
					ButtonDeletarID.setText(DELETAR);

					TextFieldCodigoID.setText("");
					TextFieldNomeID.setText("");
					TextFieldNomeID.setText("");
					TextFieldCNPJID.setText("");
					TextFieldEnderecoID.setText("");

					cadastroEmpresa.setOpacityTextFieldEscrita(TextFieldListarID);
					cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldCodigoID);
					cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldNomeID);
					cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldCNPJID);
					cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldEnderecoID);

					ButtonNovoID.setDisable(false);
					ButtonListarID.setDisable(false);
					TextFieldListarID.setDisable(false);
					TableViewColaboradorID.setDisable(false);

					if (retornoBool == false) {
						new CriarMensagens().MensagemErro("Erro ao Deletar", "Não Foi Possivel Deletar");
					} else {
						new CriarMensagens().MensagemInformacoes("Empresa deletada com SUCESSO",
								NOME + "\n" + CNPJ + "\n" + ENDERECO);
					}

					new CadastroEmpresa().setFocusTextField(TextFieldListarID);
				}
			}
		}

		if (BOTAO.equals(SALVAR)) {

			if (TextFieldNomeID.getText().length() >= 1 & TextFieldCNPJID.getText().length() >= 1
					& TextFieldEnderecoID.getText().length() >= 1) {

				boolean retornoBool = false;

				String NOME = TextFieldNomeID.getText().toString();
				String CNPJ = TextFieldCNPJID.getText().toString();
				String ENDERECO = TextFieldEnderecoID.getText().toString();

				retornoBool = empresasFabricaDAO.SalvarEmpresasDados(NOME, CNPJ, ENDERECO);

				cadastroEmpresa.TextFieldListarID_setOnKeyReleased(TextFieldListarID, TableViewColaboradorID);

				ButtonAlterarID.setText(ALTERAR);
				ButtonDeletarID.setText(DELETAR);

				TextFieldCodigoID.setText("");
				TextFieldNomeID.setText("");
				TextFieldNomeID.setText("");
				TextFieldCNPJID.setText("");
				TextFieldEnderecoID.setText("");

				cadastroEmpresa.setOpacityTextFieldEscrita(TextFieldListarID);
				cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldCodigoID);
				cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldNomeID);
				cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldCNPJID);
				cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldEnderecoID);

				ButtonNovoID.setDisable(false);
				ButtonListarID.setDisable(false);
				TextFieldListarID.setDisable(false);
				TableViewColaboradorID.setDisable(false);

				if (retornoBool == false) {

					new CriarMensagens().MensagemErro("Erro ao Salvar", "Não Foi Possivel Salvar");
				} else {

					new CriarMensagens().MensagemInformacoes("Empresa Salvo com SUCESSO",
							NOME + "\n" + CNPJ + "\n" + ENDERECO);

				}

				new CadastroEmpresa().setFocusTextField(TextFieldListarID);
			} else {

				new CriarMensagens().MensagemAlerta("Favor", "Checar os dados da Empresa");
				cadastroEmpresa.setFocusTextField(TextFieldNomeID);
			}

		}

	}

	@FXML
	void ButtonDeletarID_OnAction(ActionEvent event) {

		String BOTAO = (ButtonDeletarID.getText());

		if (BOTAO.equals(DELETAR)) {

			if (TextFieldNomeID.getText().length() >= 1) {

				ButtonAlterarID.setText(CONFIRMAR);
				ButtonDeletarID.setText(CANCELAR);

				cadastroEmpresa.setFocusTextField(TextFieldNomeID);

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
			TextFieldCNPJID.setText("");
			TextFieldEnderecoID.setText("");
			TextFieldListarID.setText("");

			cadastroEmpresa.setOpacityTextFieldEscrita(TextFieldListarID);

			cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldCodigoID);
			cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldNomeID);
			cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldCNPJID);
			cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldEnderecoID);

			ButtonNovoID.setDisable(false);
			ButtonListarID.setDisable(false);
			TextFieldListarID.setDisable(false);
			TableViewColaboradorID.setDisable(false);

			cadastroEmpresa.setFocusTextField(TextFieldListarID);

		}
	}

	@FXML
	void ButtonListarID_OnAction(ActionEvent event) {

		cadastroEmpresa.TextFieldListarID_setOnKeyReleased(TextFieldListarID, TableViewColaboradorID);

	}

	@FXML
	void ButtonNovoID_OnAction(ActionEvent event) {

		String BOTAO = (ButtonNovoID.getText());

		if (BOTAO.equals(NOVO)) {

			TextFieldCodigoID.setText("AUTOMATICO");

			TextFieldNomeID.setText("");
			TextFieldCNPJID.setText("");
			TextFieldEnderecoID.setText("");

			TextFieldListarID.setText("");

			cadastroEmpresa.setOpacityTextFieldEscrita(TextFieldNomeID);
			cadastroEmpresa.setOpacityTextFieldEscrita(TextFieldCNPJID);
			cadastroEmpresa.setOpacityTextFieldEscrita(TextFieldEnderecoID);

			cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldListarID);

			cadastroEmpresa.setFocusTextField(TextFieldNomeID);

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

			file = new CriarJanelaSalvarPDF().CriarDiretorioParaSalvarPDF("RelatórioEmpresa");

			if (file != null) {

				jrResultSetDataSource = new JRResultSetDataSource(empresasFabricaDAO.ListarEmpresas(null));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeEmpresas());
					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);

					JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());

					Runtime.getRuntime().exec("cmd.exe /c start " + file.getAbsoluteFile().getParent());

				} catch (JRException e) {

					System.out.println(
							"public class CadastroEmpresaController ButtonPDFID_OnAction Impressao [ERRO] 1\n\n"
									+ e.getMessage() + "\n\n");

				} catch (IOException e) {

					System.out.println(
							"public class CadastroEmpresaController ButtonPDFID_OnAction Impressao [ERRO] 2\n\n"
									+ e.getMessage() + "\n\n");

				}
			}
		} else {

			file = new CriarJanelaSalvarPDF().CriarArquivoParaSalvarPDF(NOME);

			if (file != null) {

				jrResultSetDataSource = new JRResultSetDataSource(empresasFabricaDAO.ListarEmpresas(NOME));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeEmpresas());
					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);

					JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());

					Runtime.getRuntime().exec("cmd.exe /c start " + file.getAbsoluteFile().getParent());

				} catch (JRException e) {

					System.out.println(
							"public class CadastroEmpresaController ButtonPDFID_OnAction Impressao [ERRO] 1\n\n"
									+ e.getMessage() + "\n\n");

				} catch (IOException e) {

					System.out.println(
							"public class CadastroEmpresaController ButtonPDFID_OnAction Impressao [ERRO] 2\n\n"
									+ e.getMessage() + "\n\n");

				}
			}
		}
	}

	@FXML
	void ButtonImprimirID_OnAction(ActionEvent event) {

		final String msg = ("Deseja Realmente Imprimir?");

		final Boolean resportaPergunta = new CriarMensagens().CriarMsgAlertaPerguntasSIMouNAO(msg);

		if (resportaPergunta) {

			String NOME = TextFieldNomeID.getText().toString();

			if (NOME.isEmpty()) {

				jrResultSetDataSource = new JRResultSetDataSource(empresasFabricaDAO.ListarEmpresas(null));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeEmpresas());
					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
					jasperPrint.setOrientation(OrientationEnum.PORTRAIT);

					try {

						JasperPrintManager.printReport(jasperPrint, false);

					} catch (Exception e) {

						System.out.println(
								"public class CadastroEmpresaController ButtonImprimirID_OnAction Impressao [ERRO] 4\n\n"
										+ e.getMessage() + "\n\n");

					}

				} catch (JRException e) {

					System.out.println(
							"public class CadastroEmpresaController ButtonImprimirID_OnAction Impressao [ERRO] 3\n\n"
									+ e.getMessage() + "\n\n");
				}

			} else {

				jrResultSetDataSource = new JRResultSetDataSource(empresasFabricaDAO.ListarEmpresas(NOME));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeEmpresas());
					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
					jasperPrint.setOrientation(OrientationEnum.PORTRAIT);

					try {

						JasperPrintManager.printReport(jasperPrint, false);

					} catch (Exception e) {

						System.out.println(
								"public class CadastroEmpresaController ButtonImprimirID_OnAction Impressao [ERRO] 2\n\n"
										+ e.getMessage() + "\n\n");

					}

				} catch (JRException e) {

					System.out.println(
							"public class CadastroEmpresaController ButtonImprimirID_OnAction Impressao [ERRO] 1\n\n"
									+ e.getMessage() + "\n\n");
				}
			}
		}
	}

	@FXML
	void ButtonVisualizarID_OnAction(ActionEvent event) {

		String NOME = TextFieldNomeID.getText().toString();

		long tempoInicio = 0L;

		if (NOME.isEmpty()) {

			try {
				tempoInicio = System.currentTimeMillis();

				jrResultSetDataSource = new JRResultSetDataSource(empresasFabricaDAO.ListarEmpresas(null));

				jasperReport = (JasperReport) JRLoader
						.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeEmpresas());

				jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);

				new JasperViewerFX((Stage) vBox.getScene().getWindow()).viewReport("Relatorios Imprime Empresas",
						jasperPrint);

				System.out.println("Tempo Total Relatorio  (NOME null): "
						+ TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis() - tempoInicio)));

			} catch (JRException e) {

				System.out.println(
						"public class CadastroEmpresaController ButtonVisualizarID_OnAction Impressao [ERRO] 2\n\n"
								+ e.getMessage() + "\n\n");
			}

		} else {

			try {

				tempoInicio = System.currentTimeMillis();

				jrResultSetDataSource = new JRResultSetDataSource(empresasFabricaDAO.ListarEmpresas(NOME));

				jasperReport = (JasperReport) JRLoader
						.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimeEmpresas());

				jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);

				new JasperViewerFX((Stage) vBox.getScene().getWindow()).viewReport("Relatorios Imprime Empresas",
						jasperPrint);

				System.out.println("Tempo Total Relatorio: "
						+ TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis() - tempoInicio)));

			} catch (JRException e) {

				System.out.println(
						"public class CadastroEmpresaController ButtonVisualizarID_OnAction Impressao [ERRO] 1\n\n"
								+ e.getMessage() + "\n\n");

			}
		}

	}

	@FXML
	void ButtonSairID_OnAction(ActionEvent event) {

		new CadastroMetodoMain().FecharJanela(vBox);

	}

	@FXML
	void HyperLinkHOYLERTecnologiaID_OnMouseClicked(MouseEvent event) {

		new HyperlinkCreate().openLinkeHOYLER(HyperLinkHOYLERTecnologiaID);

	}

	@FXML
	void TableViewColaboradorID_OnMousePressed(MouseEvent event) {

		cadastroEmpresa.SelecionarLinhaTableView(TextFieldCodigoID, TextFieldNomeID, TextFieldCNPJID,
				TextFieldEnderecoID, TableViewColaboradorID);

	}

	@FXML
	void TextFieldListarID_OnKeyRelesed(KeyEvent event) {

		cadastroEmpresa.TextFieldListarID_setOnKeyReleased(TextFieldListarID, TableViewColaboradorID);

	}

	@FXML
	void initialize() {
		assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert AnchorPanePrincipalID != null : "fx:id=\"AnchorPanePrincipalID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert ImageViewLogo != null : "fx:id=\"ImageViewLogo\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert HyperLinkHOYLERTecnologiaID != null : "fx:id=\"HyperLinkHOYLERTecnologiaID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert LabelCodigoID != null : "fx:id=\"LabelCodigoID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert TextFieldCodigoID != null : "fx:id=\"TextFieldCodigoID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert LabelNomeID != null : "fx:id=\"LabelNomeID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert TextFieldNomeID != null : "fx:id=\"TextFieldNomeID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert LabelCNPJID != null : "fx:id=\"LabelCNPJID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert TextFieldCNPJID != null : "fx:id=\"TextFieldCNPJID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert LabelEnderecoID != null : "fx:id=\"LabelEnderecoID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert TextFieldEnderecoID != null : "fx:id=\"TextFieldEnderecoID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert ButtonNovoID != null : "fx:id=\"ButtonNovoID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert ButtonAlterarID != null : "fx:id=\"ButtonAlterarID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert ButtonDeletarID != null : "fx:id=\"ButtonDeletarID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert ButtonListarID != null : "fx:id=\"ButtonListarID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert TextFieldListarID != null : "fx:id=\"TextFieldListarID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert TableViewColaboradorID != null : "fx:id=\"TableViewColaboradorID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert TableColumnCodigoID != null : "fx:id=\"TableColumnCodigoID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert TableColumnNomeID != null : "fx:id=\"TableColumnNomeID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert TableColumnCNPJID != null : "fx:id=\"TableColumnCNPJID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert TableColumnEnderecoID != null : "fx:id=\"TableColumnEnderecoID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert ButtonPDFID != null : "fx:id=\"ButtonPDFID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert ButtonImprimirID != null : "fx:id=\"ButtonImprimirID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert ButtonVisualizarID != null : "fx:id=\"ButtonVisualizarID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";
		assert ButtonSairID != null : "fx:id=\"ButtonSairID\" was not injected: check your FXML file 'CadastroEmpresa.fxml'.";

		imagensGetSet.setImageImageView(ImageViewLogo);
		imagensGetSet.setImageButtons(ButtonPDFID);
		imagensGetSet.setImageButtons(ButtonImprimirID);
		imagensGetSet.setImageButtons(ButtonVisualizarID);
		imagensGetSet.setImageButtons(ButtonSairID);

		TableColumnCodigoID.setCellValueFactory(new PropertyValueFactory<Empresas, Integer>("CODIGO"));
		TableColumnNomeID.setCellValueFactory(new PropertyValueFactory<Empresas, String>("NOME"));
		TableColumnCNPJID.setCellValueFactory(new PropertyValueFactory<Empresas, String>("CPNJ"));
		TableColumnEnderecoID.setCellValueFactory(new PropertyValueFactory<Empresas, String>("ENDERECO"));

		cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldCodigoID);
		cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldNomeID);
		cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldCNPJID);
		cadastroEmpresa.setOpacityTextFieldLeitura(TextFieldEnderecoID);

	}
}
