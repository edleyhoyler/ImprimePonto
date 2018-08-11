package br.com.hoyler.apps.imprimeponto;

import br.com.hoyler.apps.database.sqlite.Empresas;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CadastroEmpresa {

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

	public void TextFieldListarID_setOnKeyReleased(TextField textField, TableView<Empresas> tableView) {

		String Consulta = textField.getText().toString();

		tableView.setItems(new br.com.hoyler.apps.database.sqlite.EmpresasFabricaDAO().ListarEmpresasNomes(Consulta));

		// System.out.println("Key Pressed: " + ke.getCharacter());

	}
	
	public void SelecionarLinhaTableView(TextField codigo,TextField nome, TextField cnpj,TextField endereco,TableView<Empresas> tableView) {
		
		Empresas empresas = tableView.getSelectionModel().getSelectedItem();
		
		if (empresas == null)
		{
			System.out.println("DADOS NULL");
		}
		else
		{
			codigo.setText(Integer.toString(empresas.getCODIGO()));
			nome.setText(empresas.getNOME());
			cnpj.setText(empresas.getCPNJ());
			endereco.setText(empresas.getENDERECO());
		}
	}
	
 
 
	
	
	
	
}
