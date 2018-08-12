package br.com.hoyler.apps.tools;

import java.lang.reflect.Field;
import java.lang.annotation.Annotation;

public enum JanelasFXMLEnum {

	@IDescricaoEnum(descricao = ("Cadastro de Empresa - Criado por Edy"))
	CADASTRO_EMPRESA("CadastroEmpresa.fxml"),

	@IDescricaoEnum(descricao = ("Cadastro de Função - Criado por Edy"))
	CADASTRO_FUNCAO("CadastroFuncao.fxml"),

	@IDescricaoEnum(descricao = ("Cadastro de Funcionario - Criado por Edy"))
	CADASTRO_PESSOA("CadastroPessoa.fxml"),

	@IDescricaoEnum(descricao = ("Imprime Ponto - Criado por Edy"))
	IMPRIME_PONTO("ImprimePonto.fxml"),
	
	
	@IDescricaoEnum(descricao = ("Imprime Ponto - Criado por Edy"))
	RELATORIO_EMPRESA("RelatorioPessoasPorEmpresa.fxml");

	private String VALOR;

	JanelasFXMLEnum(String valor) {
		this.VALOR = valor;
	}

	public String getVALOR() {
		return VALOR;
	}

	public static JanelasFXMLEnum getEnumPorValores(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (JanelasFXMLEnum v : values())
			if (value.equalsIgnoreCase(v.getVALOR()))
				return v;
		throw new IllegalArgumentException();
	}

	public static String getIDescricaoEnum(String fielName) throws NoSuchFieldException, SecurityException {

		Field field = JanelasFXMLEnum.class.getField(fielName);

		Annotation annotation = field.getAnnotation(IDescricaoEnum.class);

		if (annotation instanceof IDescricaoEnum) {

			IDescricaoEnum iDescricaoENUM = (IDescricaoEnum) annotation;

			System.out.println("Descricao Janela: " + iDescricaoENUM.descricao());

			return iDescricaoENUM.descricao();
		}

		return "";

	}
}
