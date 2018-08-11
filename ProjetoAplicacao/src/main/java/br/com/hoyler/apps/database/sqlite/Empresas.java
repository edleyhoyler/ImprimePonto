package br.com.hoyler.apps.database.sqlite;

public class Empresas {

	private int CODIGO;
	private String NOME;
	private String CPNJ;
	private String ENDERECO;
	
	public int getCODIGO() {
		return CODIGO;
	}
	public void setCODIGO(int cODIGO) {
		CODIGO = cODIGO;
	}
	public String getNOME() {
		return NOME;
	}
	public void setNOME(String nOME) {
		NOME = nOME;
	}
	public String getCPNJ() {
		return CPNJ;
	}
	public void setCPNJ(String cPNJ) {
		CPNJ = cPNJ;
	}
	public String getENDERECO() {
		return ENDERECO;
	}
	public void setENDERECO(String eNDERECO) {
		ENDERECO = eNDERECO;
	}

}
