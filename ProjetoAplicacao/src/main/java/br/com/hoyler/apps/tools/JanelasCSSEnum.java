package br.com.hoyler.apps.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public enum JanelasCSSEnum {

	@IDescricaoEnum(descricao = ("CSS DEFAULT"))
	CSS_DEFAULT("application.css");

	private String VALOR;

	JanelasCSSEnum(String valor) {
		this.VALOR = valor;
	}

	public String getVALOR() {
		return VALOR;
	}

	public static JanelasCSSEnum getEnumPorValores(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (JanelasCSSEnum v : values())
			if (value.equalsIgnoreCase(v.getVALOR()))
				return v;
		throw new IllegalArgumentException();
	}

	public static String getIDescricaoEnum(String fielName) throws NoSuchFieldException, SecurityException {

		Field field = JanelasCSSEnum.class.getField(fielName);

		Annotation annotation = field.getAnnotation(IDescricaoEnum.class);

		if (annotation instanceof IDescricaoEnum) {

			IDescricaoEnum iDescricaoENUM = (IDescricaoEnum) annotation;

			System.out.println("Descrição CSS: " + iDescricaoENUM.descricao());

			return iDescricaoENUM.descricao();
		}

		return "";

	}
}
