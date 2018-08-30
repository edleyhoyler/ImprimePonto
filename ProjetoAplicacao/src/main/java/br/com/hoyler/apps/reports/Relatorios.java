package br.com.hoyler.apps.reports;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import br.com.hoyler.apps.tools.CheckFile;

public class Relatorios {

	private String getRelatorio(String relatorio) {

		final String CAMINHO_PADRAO = ("ImprimePonto/Relatorio/");

		final String returnString = String.format("%s%s", CAMINHO_PADRAO, relatorio);

		return (returnString);
	}

	private File getRelatorioFile(String relatorio) {

		File file = new File(relatorio);

		try {

			file.createNewFile();

			Boolean fileExist = new CheckFile().FileExists(relatorio);

			if (fileExist) {

				return (file);
			}

		} catch (IOException ioe) {
			
			System.out.println(String.format("public class Relatorios getRelatorioFile [%s] [%s] %s\n", file.getAbsolutePath(), ioe.getMessage()));
		}

		return (null);
	}

	private String getImprimePontoMeses() {

		int totalDiasMES = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

		String nomeArquivoRelatorio = null;

		switch (totalDiasMES) {
		case (28): {

			nomeArquivoRelatorio = ("ImprimePontoMes28.jasper");

			break;
		}
		case (29): {

			nomeArquivoRelatorio = ("ImprimePontoMes29.jasper");

			break;
		}
		case (30): {

			nomeArquivoRelatorio = ("ImprimePontoMes30.jasper");

			break;
		}

		default: {

			nomeArquivoRelatorio = ("ImprimePontoMes31.jasper");

			break;
		}
		}

		return (getRelatorio(nomeArquivoRelatorio));
	}

	public File getRelatorioImprimePontoMeses() {

		return (getRelatorioFile(getImprimePontoMeses()));
	}

	private String getImprimeEmpresas() {

		return (getRelatorio("ImprimeEmpresas.jasper"));
	}

	public File getRelatorioImprimeEmpresas() {

		return (getRelatorioFile(getImprimeEmpresas()));
	}

	private String getImprimeFuncoes() {

		return (getRelatorio("ImprimeFuncoes.jasper"));
	}

	public File getRelatorioImprimeFuncoes() {

		return (getRelatorioFile(getImprimeFuncoes()));
	}

}
