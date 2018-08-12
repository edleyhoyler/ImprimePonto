package br.com.hoyler.apps.tools;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface IDescricaoEnum {

	String descricao() default "";
}
