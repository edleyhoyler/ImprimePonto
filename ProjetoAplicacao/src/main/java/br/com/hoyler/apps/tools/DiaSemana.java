package br.com.hoyler.apps.tools;

import java.util.Calendar;
import java.time.LocalDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DiaSemana {

	public static String ChecarDiaSemana(String dia) {

		Calendar data = Calendar.getInstance();
		
		SimpleDateFormat simpleFormat_DiaMesAno = new SimpleDateFormat("dd/MM/yyyy");

		String Mes = Integer.toString(LocalDate.now().getMonthValue());
		
		String Ano = Integer.toString(LocalDate.now().getYear());

		String DiaMesAno = (String.format("%s/%s/%s", dia, Mes, Ano));

		try {
			data.setTime(simpleFormat_DiaMesAno.parse(DiaMesAno));
		} catch (ParseException e) {
			System.out.println(String.format("public class DiaSemana ChecarDiaSemana ERRO ParseException DATA: %s", DiaMesAno));
		}

		String diaSemana = checaFDS(data);

		return (diaSemana);
	}

	public static String checaFDS(Calendar data) {
		
		String diaSemana = null;

		switch (data.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY): {
			diaSemana = ("DOM");
			break;
		}
		case (Calendar.MONDAY): {
			diaSemana = ("SEG");
			break;
		}
		case (Calendar.TUESDAY): {
			diaSemana = ("TER");
			break;
		}
		case (Calendar.WEDNESDAY): {
			diaSemana = ("QUA");
			break;
		}
		case (Calendar.THURSDAY): {
			diaSemana = ("QUI");
			break;
		}
		case (Calendar.FRIDAY): {
			diaSemana = ("SEX");
			break;
		}
		case (Calendar.SATURDAY): {
			diaSemana = ("SAB");
			break;
		}
		default:
			diaSemana = ("");
			break;
		}
		return diaSemana;
	}
}
