package br.com.hoyler.apps.imprimeponto;
 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.hoyler.apps.database.sqlite.Pessoas;
import br.com.hoyler.apps.database.sqlite.PessoasFabricaDAO;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CadastroPessoa {

	public void setFocusTextField(TextField textField) {
		textField.requestFocus();
	}

	public void setOpacityTextFieldEscrita(TextField textField) {
		//textField.setOpacity(1);
		textField.setEditable(true);
	}

	public void setOpacityTextFieldLeitura(TextField textField) {
		//textField.setOpacity(0.5);
		textField.setEditable(false);
	}

	public void setDatePickerLeitura(DatePicker datePicker) {

		datePicker.setEditable(false);
		
	}


	public void setDatePickerValue(DatePicker datePicker, String dateString) {

		try {
			String pattern = "dd/MM/yyyy";
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			
			LocalDate localDate = LocalDate.parse(dateString, formatter);

			//setDatePickerPromptText(datePicker, "");

			datePicker.setValue(localDate);
			
		} catch (Exception e) {
			
			datePicker.setValue(null);
			
			setDatePickerPromptText(datePicker, "ERRO DATA - " + dateString);
		//	datePicker.setPromptText("ERRO DATA - " + dateString);
			
			System.out.println(String.format("public class CadastroPessoa setDatePickerValue [ERRO TRY]"));
		}

	}

	public void setDatePickerPromptText(DatePicker datePicker, String promptText) {
		
		String PromptTextPadrao = ("Admissão da Pessoa");
		if (promptText.equals(""))
		{
			datePicker.setPromptText(PromptTextPadrao);
		}
		else
		{
			datePicker.setPromptText(promptText);
		}
		
	}
	
	public void  setComboBoxItensFabricaLimpar(ComboBox<String> comboBox, String promptText) {
		
		String PromptTextPadrao = ("Função da Pessoa");
		if (promptText.equals(""))
		{
			comboBox.setPromptText(PromptTextPadrao);
		}
		else
		{
			comboBox.setPromptText(promptText);
		}
		
		comboBox.setValue(null);
	}
	
	public void  setComboBoxItensEmpresaLimpar(ComboBox<String> comboBox, String promptText) {
		
		String PromptTextPadrao = ("Empresa da Pessoa");
		if (promptText.equals(""))
		{
			comboBox.setPromptText(PromptTextPadrao);
		}
		else
		{
			comboBox.setPromptText(promptText);
		}
		comboBox.setValue(null);
	}
	
	public String getDatePickerValue(DatePicker datePicker) {

		String pattern = "dd/MM/yyyy";

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		String returnString = "";

		try {

			returnString = (datePicker.getValue().format(dateFormatter).toString());

		} catch (Exception e) {
			returnString = "";
			System.out.println(String.format("public class CadastroPessoa getDatePickerValue [ERRO TRY]"));
		}

		return (returnString);

	}

	public String getComboBoxValue(ComboBox<String> comboBox) {
		
		String returnString = "";
			try {
				
				returnString = comboBox.getValue().toString();
			
				if (returnString == null){
					returnString = "";
				}
				
			} catch (Exception e) {
				returnString = "";
				System.out.println(String.format("public class CadastroPessoa getComboBoxValue [ERRO TRY]"));
			}
			return (returnString);
	}
	public void setComboBoxEscrita(ComboBox<String> comboBox) {
		comboBox.setEditable(true);
	}

	public void setComboBoxItensFabrica(ComboBox<String> comboBox, String consulta) {
		PessoasFabricaDAO pessoasFabricaDAO = new PessoasFabricaDAO();
		comboBox.setItems(pessoasFabricaDAO.ListarFuncoesNomes(consulta));
	}

	public void setComboBoxItensEmpresa(ComboBox<String> comboBox, String consulta) {
		comboBox.setItems(new PessoasFabricaDAO().ListarEmpresasNomes(consulta));
	}

	public void TextFieldListarID_setOnKeyReleased(TextField textField, TableView<Pessoas> tableView) {

		String Consulta = textField.getText().toString();

		tableView.setItems(new br.com.hoyler.apps.database.sqlite.PessoasFabricaDAO().ListarPessoasNomes(Consulta));
	}

	public void SelecionarLinhaTableView(TextField codigo, TextField nome, TextField ctps, DatePicker admissao, ComboBox<String> funcao, ComboBox<String> empresa, TableView<Pessoas> tableView) {

		Pessoas pessoas = tableView.getSelectionModel().getSelectedItem();

		if (pessoas == null) {
			
			System.out.println("DADOS NULL");
			
		} else {
			
			codigo.setText(Integer.toString(pessoas.getCodigo()));
			
			nome.setText(pessoas.getNome());
			
			ctps.setText(pessoas.getCtps());
			try {
				setDatePickerValue(admissao, pessoas.getAdmissao());
			} catch (Exception e) {
				System.out.println(String.format("public class CadastroPessoa SelecionarLinhaTableView ERRO: %s", e.getMessage()));
			}
		
			
			funcao.setValue(pessoas.getFuncao());
			
			empresa.setValue(pessoas.getEmpresa());

		}
	}
}
