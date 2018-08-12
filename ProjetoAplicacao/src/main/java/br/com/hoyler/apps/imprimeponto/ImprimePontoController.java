package br.com.hoyler.apps.imprimeponto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import br.com.hoyler.apps.imagens.ImagensGetSet;
import br.com.hoyler.apps.tools.CriarMensagens;
import br.com.hoyler.apps.tools.JanelasCSSEnum;
import br.com.hoyler.apps.tools.JanelasFXMLEnum;


public class ImprimePontoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox vBox;

    @FXML
    private MenuItem menuItemEmpresaID;

    @FXML
    private MenuItem menuItemFuncaoID;

    @FXML
    private MenuItem menuItemPessoaID;

    @FXML
    private MenuItem menuItemSairID;

    @FXML
    private MenuItem menuItemSuporteID;

    @FXML
    private ImageView ImageViewBackgroundID;

    @FXML
    private ImageView ImageViewPessoaID;

    @FXML
    private ImageView ImageViewFuncaoID;

    @FXML
    private ImageView ImageViewEmpresaID;

	@FXML
	void ImageViewEmpresaID_OnMousePressed(MouseEvent event) {
		ImageViewEmpresaID.setFitHeight(100);
		ImageViewEmpresaID.setFitWidth(100);
	}

	@FXML
	void ImageViewEmpresaID_OnMouseReleased(MouseEvent event) {
		ImageViewEmpresaID.setFitHeight(130);
		ImageViewEmpresaID.setFitWidth(130);
		menuItemEmpresaID_OnAction(null);
	}

	@FXML
	void ImageViewFuncaoID_OnMousePressed(MouseEvent event) {
		ImageViewFuncaoID.setFitHeight(100);
		ImageViewFuncaoID.setFitWidth(100);
	}

	@FXML
	void ImageViewFuncaoID_OnMouseReleased(MouseEvent event) {
		ImageViewFuncaoID.setFitHeight(130);
		ImageViewFuncaoID.setFitWidth(130);
		menuItemFuncaoID_OnAction(null);
	}

	@FXML
	void ImageViewPessoaID_OnMousePressed(MouseEvent event) {
		ImageViewPessoaID.setFitHeight(100);
		ImageViewPessoaID.setFitWidth(100);
	}

	@FXML
	void ImageViewPessoaID_OnMouseReleased(MouseEvent event) {
		ImageViewPessoaID.setFitHeight(130);
		ImageViewPessoaID.setFitWidth(130);
		menuItemPessoaID_OnAction(null);
	}
	
	@FXML
	void menuItemEmpresaID_OnAction(ActionEvent event) {
		new CadastroMetodoMain().AbrirJanela(JanelasFXMLEnum.CADASTRO_EMPRESA, JanelasCSSEnum.CSS_DEFAULT, new Stage());
	}

	@FXML
	void menuItemFuncaoID_OnAction(ActionEvent event) {
		new CadastroMetodoMain().AbrirJanela(JanelasFXMLEnum.CADASTRO_FUNCAO, JanelasCSSEnum.CSS_DEFAULT, new Stage());
	}

	@FXML
	void menuItemPessoaID_OnAction(ActionEvent event) {
		new CadastroMetodoMain().AbrirJanela(JanelasFXMLEnum.CADASTRO_PESSOA, JanelasCSSEnum.CSS_DEFAULT, new Stage());
	}

	@FXML
	void menuItemSairID_OnAction(ActionEvent event) {
		new CadastroMetodoMain().FecharJanela(vBox);
	}

    @FXML
    void menuItemSuporteID_OnAction(ActionEvent event) {
    	new CriarMensagens().MensagemAlerta("Dados para Suporte - GRATUITO", "Edley Hoyler\nTelefone: +55 65 9 9943-4063\nE-mail: edley@hoyler.com.br");
    }

    @FXML
    void initialize() {
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'ImprimePonto.fxml'.";
        assert menuItemEmpresaID != null : "fx:id=\"menuItemEmpresaID\" was not injected: check your FXML file 'ImprimePonto.fxml'.";
        assert menuItemFuncaoID != null : "fx:id=\"menuItemFuncaoID\" was not injected: check your FXML file 'ImprimePonto.fxml'.";
        assert menuItemPessoaID != null : "fx:id=\"menuItemPessoaID\" was not injected: check your FXML file 'ImprimePonto.fxml'.";
        assert menuItemSairID != null : "fx:id=\"menuItemSairID\" was not injected: check your FXML file 'ImprimePonto.fxml'.";
        assert menuItemSuporteID != null : "fx:id=\"menuItemSuporteID\" was not injected: check your FXML file 'ImprimePonto.fxml'.";
        assert ImageViewBackgroundID != null : "fx:id=\"ImageViewBackgroundID\" was not injected: check your FXML file 'ImprimePonto.fxml'.";
        assert ImageViewPessoaID != null : "fx:id=\"ImageViewPessoaID\" was not injected: check your FXML file 'ImprimePonto.fxml'.";
        assert ImageViewFuncaoID != null : "fx:id=\"ImageViewFuncaoID\" was not injected: check your FXML file 'ImprimePonto.fxml'.";
        assert ImageViewEmpresaID != null : "fx:id=\"ImageViewEmpresaID\" was not injected: check your FXML file 'ImprimePonto.fxml'.";
		this.setImagens();
	}

	private void setImagens() {
		
		ImagensGetSet imagensGetSet = new ImagensGetSet();
		imagensGetSet.setImageImageView(ImageViewBackgroundID);
		imagensGetSet.setImageImageView(ImageViewPessoaID);
		imagensGetSet.setImageImageView(ImageViewFuncaoID);
		imagensGetSet.setImageImageView(ImageViewEmpresaID);

	}
}
