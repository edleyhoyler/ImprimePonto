package br.com.hoyler.apps.imprimeponto;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;


public class RelatorioPessoasPorEmpresaController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtonSairID;

    @FXML
    private BarChart<String, Integer> BarCharRelatorioID;

    @FXML
    private CategoryAxis CategoryAxisPessoaID;

    @FXML
    void ButtonSairID_OnAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert ButtonSairID != null : "fx:id=\"ButtonSairID\" was not injected: check your FXML file 'RelatorioPessoasPorEmpresa.fxml'.";
        assert BarCharRelatorioID != null : "fx:id=\"BarCharRelatorioID\" was not injected: check your FXML file 'RelatorioPessoasPorEmpresa.fxml'.";
        assert CategoryAxisPessoaID != null : "fx:id=\"CategoryAxisPessoaID\" was not injected: check your FXML file 'RelatorioPessoasPorEmpresa.fxml'.";
        GeradorGrafico();
    }
    
    
    private void GeradorGrafico() {
    	
    	XYChart.Data<String, Integer> empresaa = new XYChart.Data<String, Integer>("JNP", 10);
    	XYChart.Data<String, Integer> empresaB = new XYChart.Data<String, Integer>("AGRO", 20);
    	 
    	XYChart.Series<String, Integer> xyChart = new 	XYChart.Series<>();
    	xyChart.setName("Pessoas");
    	 
    	xyChart.getData().add(empresaa);
    	xyChart.getData().add(empresaB);
    	
    	BarCharRelatorioID.getData().add(xyChart);
    	
    }
}
