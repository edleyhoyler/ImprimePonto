package br.com.hoyler.apps.imprimeponto;

import br.com.hoyler.apps.database.sqlite.Funcoes;
import br.com.hoyler.apps.database.sqlite.FuncoesFabricaDAO;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CadastroFuncao {

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

	public void Listar(TextField textField, TableView<Funcoes> tableView) {

		String Consulta = textField.getText().toString();
		
		tableView.setItems(new FuncoesFabricaDAO().ListarFuncoesNomes(Consulta));
		
		
		//new CadastroFuncao().TextFieldListarID_setOnKeyReleased(textField, tableView);
		
		
		
		//tableView.setItems(new br.com.hoyler.apps.database.sqlite.Database().ListarFuncoesNomes(Consulta));

	}

	public void SelecionarLinhaTableView(TextField codigo,TextField nome, TableView<Funcoes> tableView) {
		
		Funcoes funcoes = tableView.getSelectionModel().getSelectedItem();
		
		if (funcoes == null)
		{
			System.out.println("DADOS NULL");
		}
		else
		{
			codigo.setText(Integer.toString(funcoes.getCODIGO()));
			nome.setText(funcoes.getNOME());
		}
	}
	
	public void TableViewColaboradorID_setOnMousePressed(TextField codigo,TextField nome, TableView<Funcoes> tableView) {
		
		tableView.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				SelecionarLinhaTableView(codigo, nome, tableView);
				
			}
		});
	}
	
	public void TextFieldListarID_setOnKeyReleased(TextField textField, TableView<Funcoes> tableView) {
		
		textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {

				String Consulta = textField.getText().toString();

				tableView.setItems(new FuncoesFabricaDAO().ListarFuncoesNomes(Consulta));

				//System.out.println("Key Pressed: " + ke.getCharacter());
			}
		});
	}
	
	

}
