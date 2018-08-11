package br.com.hoyler.apps.imprimeponto;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.jvfx.viewer.JasperViewerFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
import br.com.hoyler.apps.database.sqlite.Pessoas;
import br.com.hoyler.apps.database.sqlite.PessoasFabricaDAO;
import br.com.hoyler.apps.imagens.ImagensGetSet;
import br.com.hoyler.apps.tools.CriarJanelaSalvarPDF;
import br.com.hoyler.apps.tools.CriarMensagens;
import br.com.hoyler.apps.tools.HyperlinkCreate;

public class CadastroPessoaController {

	String NOVO = ("Novo");
	String SALVAR = ("Salvar");
	String CANCELAR = ("Cancelar");
	String ALTERAR = ("Alterar");
	String DELETAR = ("Deletar");
	String ATUALIZAR = ("Atualizar");
	String CONFIRMAR = ("Confirmar");

	String CadastroAntigo = "";

	ImagensGetSet imagensGetSet = new ImagensGetSet();
	CadastroPessoa cadastroPessoa = new CadastroPessoa();
	PessoasFabricaDAO pessoasFabricaDAO = new PessoasFabricaDAO();
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
	private Label LabelCTPSID;

	@FXML
	private TextField TextFieldCTPSID;

	@FXML
	private Label LabelAdmissaoID;

	@FXML
	private DatePicker DatePickerAdmissaoID;

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
	private TableView<Pessoas> TableViewColaboradorID;

	@FXML
	private TableColumn<Pessoas, Integer> TableColumnCodigoID;

	@FXML
	private TableColumn<Pessoas, String> TableColumnNomeID;

	@FXML
	private TableColumn<Pessoas, String> TableColumnCTPSID;

	@FXML
	private TableColumn<Pessoas, String> TableColumnAdmissaoID;

	@FXML
	private TableColumn<Pessoas, String> TableColumnFuncaoID;

	@FXML
	private TableColumn<Pessoas, String> TableColumnEmpresaID;

	@FXML
	private ComboBox<String> ComboBoxEmpresaID;

	@FXML
	private ComboBox<String> ComboBoxFuncaoID;

	@FXML
	private Label LabelFuncaoID;

	@FXML
	private Label LabelEmpresaID;

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

		String CODIGO = TextFieldCodigoID.getText().toString();

		if (BOTAO.equals(ALTERAR)) {
			if (CODIGO.length() >= 1) {

				CadastroAntigo = (TextFieldNomeID.getText());
				ButtonAlterarID.setText(ATUALIZAR);
				ButtonDeletarID.setText(CANCELAR);

				cadastroPessoa.setFocusTextField(TextFieldNomeID);
				cadastroPessoa.setOpacityTextFieldEscrita(TextFieldNomeID);

				cadastroPessoa.setOpacityTextFieldEscrita(TextFieldCTPSID);

				ButtonNovoID.setDisable(true);
				ButtonListarID.setDisable(true);
				TextFieldListarID.setDisable(true);
				TableViewColaboradorID.setDisable(true);
			}
		}

		if (BOTAO.equals(ATUALIZAR)) {

			String NOME = TextFieldNomeID.getText().toString();
			String CTPS = TextFieldCTPSID.getText().toString();
			String ADMISSAO = new CadastroPessoa().getDatePickerValue(DatePickerAdmissaoID);
			String FUNCAO = new CadastroPessoa().getComboBoxValue(ComboBoxFuncaoID);
			String EMPRESA = new CadastroPessoa().getComboBoxValue(ComboBoxEmpresaID);

			if (CODIGO.length() >= 1 & NOME.length() >= 1 & CTPS.length() >= 1 & ADMISSAO.length() >= 1
					& FUNCAO.length() >= 1 & EMPRESA.length() >= 1) {

				String msg = ("Deseja Realmente Atualizar?");
				Boolean resportaPergunta = new CriarMensagens().CriarMsgAlertaPerguntasSIMouNAO(msg);

				if (resportaPergunta) {

					boolean retornoBool = false;

					ButtonAlterarID.setText(ALTERAR);
					ButtonDeletarID.setText(DELETAR);

					TextFieldCodigoID.setText("");
					TextFieldNomeID.setText("");
					TextFieldCTPSID.setText("");

					DatePickerAdmissaoID.setValue(null);

					TextFieldListarID.setText("");

					ButtonNovoID.setDisable(false);
					ButtonListarID.setDisable(false);
					TextFieldListarID.setDisable(false);
					TableViewColaboradorID.setDisable(false);
					new CadastroPessoa().setDatePickerPromptText(DatePickerAdmissaoID, "");
					new CadastroPessoa().setComboBoxItensFabricaLimpar(ComboBoxFuncaoID, "");
					new CadastroPessoa().setComboBoxItensEmpresaLimpar(ComboBoxEmpresaID, "");

					new CadastroPessoa().setOpacityTextFieldLeitura(TextFieldNomeID);
					new CadastroPessoa().setOpacityTextFieldLeitura(TextFieldCTPSID);

					new CadastroEmpresa().setFocusTextField(TextFieldListarID);

					retornoBool = new PessoasFabricaDAO().UpdateEmpresasDados(CadastroAntigo, NOME, CTPS, ADMISSAO,
							FUNCAO, EMPRESA);

					new CadastroPessoa().TextFieldListarID_setOnKeyReleased(TextFieldListarID, TableViewColaboradorID);

					CadastroAntigo = "";

					if (retornoBool == false) {
						new CriarMensagens().MensagemErro("Erro ao Atualizar", "Não Foi Possivel Atualizar");
					} else {
						new CriarMensagens().MensagemInformacoes("Pessoa Atualizada com SUCESSO",
								NOME + "\n" + CTPS + "\n" + ADMISSAO + "\n" + FUNCAO + "\n" + EMPRESA);

					}

					new CadastroEmpresa().setFocusTextField(TextFieldListarID);

				}

			} else {
				new CriarMensagens().MensagemAlerta("Favor", "Checar os dados da Pessoa");
			}
		}

		if (BOTAO.equals(CONFIRMAR)) {

			String NOME = TextFieldNomeID.getText().toString();
			String CTPS = TextFieldCTPSID.getText().toString();
			String ADMISSAO = new CadastroPessoa().getDatePickerValue(DatePickerAdmissaoID);
			String FUNCAO = new CadastroPessoa().getComboBoxValue(ComboBoxFuncaoID);
			String EMPRESA = new CadastroPessoa().getComboBoxValue(ComboBoxEmpresaID);

			if (CODIGO.length() >= 1) {

				String msg = ("Deseja Realmente Deletar?");
				Boolean resportaPergunta = new CriarMensagens().CriarMsgAlertaPerguntasSIMouNAO(msg);

				if (resportaPergunta) {

					boolean retornoBool = false;

					int CODIGO_ID = Integer.parseInt(CODIGO);

					TextFieldListarID.setText("");

					retornoBool = new PessoasFabricaDAO().DeletarCodigosTabelaFuncoes(CODIGO_ID);

					new CadastroPessoa().TextFieldListarID_setOnKeyReleased(TextFieldListarID, TableViewColaboradorID);

					ButtonAlterarID.setText(ALTERAR);
					ButtonDeletarID.setText(DELETAR);

					TextFieldCodigoID.setText("");
					TextFieldNomeID.setText("");
					TextFieldCTPSID.setText("");

					DatePickerAdmissaoID.setValue(null);

					ButtonAlterarID.setText(ALTERAR);
					ButtonDeletarID.setText(DELETAR);

					ButtonNovoID.setDisable(false);
					ButtonListarID.setDisable(false);
					TextFieldListarID.setDisable(false);
					TableViewColaboradorID.setDisable(false);

					new CadastroPessoa().setDatePickerPromptText(DatePickerAdmissaoID, "");
					new CadastroPessoa().setComboBoxItensFabricaLimpar(ComboBoxFuncaoID, "");
					new CadastroPessoa().setComboBoxItensEmpresaLimpar(ComboBoxEmpresaID, "");

					new CadastroPessoa().setOpacityTextFieldLeitura(TextFieldNomeID);
					new CadastroPessoa().setOpacityTextFieldLeitura(TextFieldCTPSID);

					new CadastroEmpresa().setFocusTextField(TextFieldListarID);

					if (retornoBool == false) {
						new CriarMensagens().MensagemErro("Erro ao Deletar", "Não Foi Possivel Deletar");
					} else {
						new CriarMensagens().MensagemInformacoes("Pessoa deletada com SUCESSO",
								NOME + "\n" + CTPS + "\n" + ADMISSAO + "\n" + FUNCAO + "\n" + EMPRESA);

					}

					new CadastroFuncao().setFocusTextField(TextFieldListarID);
				}
			}

		}

		if (BOTAO.equals(SALVAR)) {

			String NOME = TextFieldNomeID.getText().toString();
			String CTPS = TextFieldCTPSID.getText().toString();
			String ADMISSAO = new CadastroPessoa().getDatePickerValue(DatePickerAdmissaoID);
			String FUNCAO = new CadastroPessoa().getComboBoxValue(ComboBoxFuncaoID);
			String EMPRESA = new CadastroPessoa().getComboBoxValue(ComboBoxEmpresaID);

			if (NOME.length() >= 1 & CTPS.length() >= 1 & ADMISSAO.length() >= 1 & FUNCAO.length() >= 1
					& EMPRESA.length() >= 1) {

				boolean retornoBool = true;

				ButtonAlterarID.setText(ALTERAR);
				ButtonDeletarID.setText(DELETAR);

				retornoBool = new PessoasFabricaDAO().SalvarPessoasDados(NOME, CTPS, ADMISSAO, FUNCAO, EMPRESA);

				TextFieldCodigoID.setText("");
				TextFieldNomeID.setText("");
				TextFieldCTPSID.setText("");

				DatePickerAdmissaoID.setValue(null);

				ButtonAlterarID.setText(ALTERAR);
				ButtonDeletarID.setText(DELETAR);

				ButtonNovoID.setDisable(false);
				ButtonListarID.setDisable(false);
				TextFieldListarID.setDisable(false);
				TableViewColaboradorID.setDisable(false);

				new CadastroPessoa().setDatePickerPromptText(DatePickerAdmissaoID, "");
				new CadastroPessoa().setComboBoxItensFabricaLimpar(ComboBoxFuncaoID, "");
				new CadastroPessoa().setComboBoxItensEmpresaLimpar(ComboBoxEmpresaID, "");

				new CadastroPessoa().setOpacityTextFieldLeitura(TextFieldNomeID);
				new CadastroPessoa().setOpacityTextFieldLeitura(TextFieldCTPSID);

				new CadastroEmpresa().setFocusTextField(TextFieldListarID);

				new CadastroFuncao().setFocusTextField(TextFieldListarID);

				if (retornoBool == false) {
					new CriarMensagens().MensagemErro("Erro ao Salvar", "Não Foi Possivel Salvar");
				} else {
					new CriarMensagens().MensagemInformacoes("Pessoa Salvo com SUCESSO",
							NOME + "\n" + CTPS + "\n" + ADMISSAO + "\n" + FUNCAO + "\n" + EMPRESA);

				}

			} else {

				new CriarMensagens().MensagemAlerta("Favor", "Checar dados da PESSOA");
				new CadastroFuncao().setFocusTextField(TextFieldNomeID);
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

				cadastroPessoa.setFocusTextField(TextFieldNomeID);

				// cadastroPessoa.setOpacityTextFieldEscrita(TextFieldNomeID);

				// cadastroPessoa.setOpacityTextFieldLeitura(TextFieldListarID);

				// TextFieldNomeID.setEditable(false);

				ButtonNovoID.setDisable(true);
				ButtonListarID.setDisable(true);
				TextFieldListarID.setDisable(true);
				TableViewColaboradorID.setDisable(true);

			}

		}

		if (BOTAO.equals(CANCELAR)) {

			TextFieldCodigoID.setText("");
			TextFieldNomeID.setText("");
			TextFieldCTPSID.setText("");

			DatePickerAdmissaoID.setValue(null);

			TextFieldListarID.setText("");

			ButtonAlterarID.setText(ALTERAR);
			ButtonDeletarID.setText(DELETAR);

			ButtonNovoID.setDisable(false);
			ButtonListarID.setDisable(false);
			TextFieldListarID.setDisable(false);
			TableViewColaboradorID.setDisable(false);

			cadastroPessoa.setDatePickerPromptText(DatePickerAdmissaoID, "");
			cadastroPessoa.setComboBoxItensFabricaLimpar(ComboBoxFuncaoID, "");
			cadastroPessoa.setComboBoxItensEmpresaLimpar(ComboBoxEmpresaID, "");

			cadastroPessoa.setOpacityTextFieldLeitura(TextFieldNomeID);
			cadastroPessoa.setOpacityTextFieldLeitura(TextFieldCTPSID);

			cadastroPessoa.setFocusTextField(TextFieldListarID);

		}
	}

	@FXML
	void ButtonListarID_OnAction(ActionEvent event) {
		cadastroPessoa.TextFieldListarID_setOnKeyReleased(TextFieldListarID, TableViewColaboradorID);
	}

	@FXML
	void ButtonNovoID_OnAction(ActionEvent event) {
		// System.out.println(new
		// CadastroPessoa().getDatePickerValue(DatePickerAdmissaoID));

		String BOTAO = (ButtonNovoID.getText());

		if (BOTAO.equals(NOVO)) {

			TextFieldCodigoID.setText("AUTOMATICO");

			TextFieldNomeID.setText("");
			TextFieldCTPSID.setText("");

			DatePickerAdmissaoID.setValue(null);

			TextFieldListarID.setText("");

			ButtonAlterarID.setText(SALVAR);
			ButtonDeletarID.setText(CANCELAR);
			ButtonNovoID.setDisable(true);
			ButtonListarID.setDisable(true);
			TextFieldListarID.setDisable(true);
			TableViewColaboradorID.setDisable(true);

			cadastroPessoa.setOpacityTextFieldEscrita(TextFieldNomeID);
			cadastroPessoa.setOpacityTextFieldEscrita(TextFieldCTPSID);

			cadastroPessoa.setDatePickerPromptText(DatePickerAdmissaoID, "");
			cadastroPessoa.setComboBoxItensFabricaLimpar(ComboBoxFuncaoID, "");
			cadastroPessoa.setComboBoxItensEmpresaLimpar(ComboBoxEmpresaID, "");

			cadastroPessoa.setFocusTextField(TextFieldNomeID);
		}

	}

	@FXML
	void ButtonPDFID_OnAction(ActionEvent event) {

		String NOME = TextFieldNomeID.getText().toString();

		if (NOME.isEmpty()) {

			file = new CriarJanelaSalvarPDF().CriarDiretorioParaSalvarPDF("RelatórioPessoas");

			if (file != null) {
				ProgressBarID.setProgress(1);
				ProgressBarID.setVisible(true);
				jrResultSetDataSource = new JRResultSetDataSource(pessoasFabricaDAO.ListarPessoas(null));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimePontoMeses());
					ProgressBarID.setProgress(25);
					
					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
					ProgressBarID.setProgress(75);
					
					JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());
					ProgressBarID.setProgress(85);
					
					Runtime.getRuntime().exec("cmd.exe /c start " + file.getAbsoluteFile().getParent());
					ProgressBarID.setProgress(99);
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
				jrResultSetDataSource = new JRResultSetDataSource(pessoasFabricaDAO.ListarPessoas(NOME));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimePontoMeses());
					ProgressBarID.setProgress(25);
					
					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
					ProgressBarID.setProgress(75);
					
					JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());
					ProgressBarID.setProgress(85);
					
					Runtime.getRuntime().exec("cmd.exe /c start " + file.getAbsoluteFile().getParent());
					ProgressBarID.setProgress(99);

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
				jrResultSetDataSource = new JRResultSetDataSource(pessoasFabricaDAO.ListarPessoas(null));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimePontoMeses());
					ProgressBarID.setProgress(25);
					
					
					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
					ProgressBarID.setProgress(50);
					
					jasperPrint.setOrientation(OrientationEnum.PORTRAIT);
					ProgressBarID.setProgress(85);
					try {

						JasperPrintManager.printReport(jasperPrint, false);
						ProgressBarID.setProgress(50);

					} catch (Exception e) {

						System.out.println(
								"public class CadastroPessoaController ButtonImprimirID_OnAction Impressao [ERRO] 4\n\n"
										+ e.getMessage() + "\n\n");

					}

				} catch (JRException e) {

					System.out.println(
							"public class CadastroPessoaController ButtonImprimirID_OnAction Impressao [ERRO] 3\n\n"
									+ e.getMessage() + "\n\n");
				}
				ProgressBarID.setVisible(false);
			} else {
				ProgressBarID.setProgress(1);
				ProgressBarID.setVisible(true);
				jrResultSetDataSource = new JRResultSetDataSource(pessoasFabricaDAO.ListarPessoas(NOME));

				try {

					jasperReport = (JasperReport) JRLoader
							.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimePontoMeses());
					ProgressBarID.setProgress(25);
					
					jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);
					ProgressBarID.setProgress(50);
					
					jasperPrint.setOrientation(OrientationEnum.PORTRAIT);
					ProgressBarID.setProgress(85);
					
					try {

						JasperPrintManager.printReport(jasperPrint, false);
						ProgressBarID.setProgress(99);
						
					} catch (Exception e) {

						System.out.println(
								"public class CadastroPessoaController ButtonImprimirID_OnAction Impressao [ERRO] 2\n\n"
										+ e.getMessage() + "\n\n");

					}

				} catch (JRException e) {

					System.out.println(
							"public class CadastroPessoaController ButtonImprimirID_OnAction Impressao [ERRO] 1\n\n"
									+ e.getMessage() + "\n\n");
				}
		
			}
		}
		ProgressBarID.setVisible(false);
	}

	@FXML
	void ButtonVisualizarID_OnAction(ActionEvent event) {

		String NOME = TextFieldNomeID.getText().toString();

		long tempoInicio = 0L;

		if (NOME.isEmpty()) {

			try {
				tempoInicio = System.currentTimeMillis();

				jrResultSetDataSource = new JRResultSetDataSource(pessoasFabricaDAO.ListarPessoas(null));

				jasperReport = (JasperReport) JRLoader.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimePontoMeses());

				jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);

			 
				 new JasperViewerFX((Stage) vBox.getScene().getWindow()).viewReport("Relatorios Imprime Pessoa", jasperPrint);
				 
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

				jrResultSetDataSource = new JRResultSetDataSource(pessoasFabricaDAO.ListarPessoas(NOME));

				jasperReport = (JasperReport) JRLoader
						.loadObject(new br.com.hoyler.apps.reports.Relatorios().getRelatorioImprimePontoMeses());

				jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrResultSetDataSource);

				 
				 new JasperViewerFX((Stage) vBox.getScene().getWindow()).viewReport("Relatorios Imprime Pessoa", jasperPrint);
				 
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
		cadastroPessoa.SelecionarLinhaTableView(TextFieldCodigoID, TextFieldNomeID, TextFieldCTPSID,
				DatePickerAdmissaoID, ComboBoxFuncaoID, ComboBoxEmpresaID, TableViewColaboradorID);
	}

	@FXML
	void TextFieldListarID_OnKeyRelesed(KeyEvent event) {

		cadastroPessoa.TextFieldListarID_setOnKeyReleased(TextFieldListarID, TableViewColaboradorID);

	}

	@FXML
	void initialize() {
		assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ImageViewLogo != null : "fx:id=\"ImageViewLogo\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert HyperLinkHOYLERTecnologiaID != null : "fx:id=\"HyperLinkHOYLERTecnologiaID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert LabelCodigoID != null : "fx:id=\"LabelCodigoID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TextFieldCodigoID != null : "fx:id=\"TextFieldCodigoID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert LabelNomeID != null : "fx:id=\"LabelNomeID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TextFieldNomeID != null : "fx:id=\"TextFieldNomeID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert LabelCTPSID != null : "fx:id=\"LabelCTPSID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TextFieldCTPSID != null : "fx:id=\"TextFieldCTPSID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert LabelAdmissaoID != null : "fx:id=\"LabelAdmissaoID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert DatePickerAdmissaoID != null : "fx:id=\"DatePickerAdmissaoID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert LabelFuncaoID != null : "fx:id=\"LabelFuncaoID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ComboBoxFuncaoID != null : "fx:id=\"ComboBoxFuncaoID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert LabelEmpresaID != null : "fx:id=\"LabelEmpresaID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ComboBoxEmpresaID != null : "fx:id=\"ComboBoxEmpresaID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ButtonNovoID != null : "fx:id=\"ButtonNovoID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ButtonAlterarID != null : "fx:id=\"ButtonAlterarID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ButtonDeletarID != null : "fx:id=\"ButtonDeletarID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ButtonListarID != null : "fx:id=\"ButtonListarID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TextFieldListarID != null : "fx:id=\"TextFieldListarID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TableViewColaboradorID != null : "fx:id=\"TableViewColaboradorID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TableColumnCodigoID != null : "fx:id=\"TableColumnCodigoID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TableColumnNomeID != null : "fx:id=\"TableColumnNomeID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TableColumnCTPSID != null : "fx:id=\"TableColumnCTPSID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TableColumnAdmissaoID != null : "fx:id=\"TableColumnAdmissaoID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TableColumnFuncaoID != null : "fx:id=\"TableColumnFuncaoID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert TableColumnEmpresaID != null : "fx:id=\"TableColumnEmpresaID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ButtonPDFID != null : "fx:id=\"ButtonPDFID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ButtonImprimirID != null : "fx:id=\"ButtonImprimirID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ButtonVisualizarID != null : "fx:id=\"ButtonVisualizarID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ButtonSairID != null : "fx:id=\"ButtonSairID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";
		assert ProgressBarID != null : "fx:id=\"ProgressBarID\" was not injected: check your FXML file 'CadastroPessoa.fxml'.";

		
		imagensGetSet.setImageImageView(ImageViewLogo);
		imagensGetSet.setImageButtons(ButtonPDFID);
		imagensGetSet.setImageButtons(ButtonImprimirID);
		imagensGetSet.setImageButtons(ButtonVisualizarID);
		imagensGetSet.setImageButtons(ButtonSairID);

		TableColumnCodigoID.setCellValueFactory(new PropertyValueFactory<Pessoas, Integer>("CODIGO"));
		TableColumnNomeID.setCellValueFactory(new PropertyValueFactory<Pessoas, String>("NOME"));
		TableColumnCTPSID.setCellValueFactory(new PropertyValueFactory<Pessoas, String>("CTPS"));
		TableColumnAdmissaoID.setCellValueFactory(new PropertyValueFactory<Pessoas, String>("ADMISSAO"));
		TableColumnFuncaoID.setCellValueFactory(new PropertyValueFactory<Pessoas, String>("FUNCAO"));
		TableColumnEmpresaID.setCellValueFactory(new PropertyValueFactory<Pessoas, String>("EMPRESA"));

		cadastroPessoa.setOpacityTextFieldLeitura(TextFieldCodigoID);
		cadastroPessoa.setOpacityTextFieldLeitura(TextFieldNomeID);
		cadastroPessoa.setOpacityTextFieldLeitura(TextFieldCTPSID);

		cadastroPessoa.setComboBoxItensFabrica(ComboBoxFuncaoID, "");
		cadastroPessoa.setComboBoxItensEmpresa(ComboBoxEmpresaID, "");

		cadastroPessoa.setDatePickerLeitura(DatePickerAdmissaoID);

	}
}
