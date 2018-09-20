package br.com.hoyler.apps.reports;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import br.com.hoyler.apps.tools.CheckFile;

public class Relatorios {

	private final String relatorioCaminho           = ("ImprimePonto/Relatorio/");
	private final String relatorioImprimeFuncoes    = ("ImprimeFuncoes.jasper");
	private final String relatorioImprimeEmpresas   = ("ImprimeEmpresas.jasper");
	private final String relatorioImprimePontoMes28 = ("ImprimePontoMes28.jasper");
	private final String relatorioImprimePontoMes29 = ("ImprimePontoMes29.jasper");
	private final String relatorioImprimePontoMes30 = ("ImprimePontoMes30.jasper");
	private final String relatorioImprimePontoMes31 = ("ImprimePontoMes31.jasper");

	private String getRelatorioString(String relatorio) {

		final String returnString = String.format("%s%s", relatorioCaminho, relatorio);

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

			System.out.println(String.format("public class Relatorios getRelatorioFile [%s] [%s] %s\n",
					file.getAbsolutePath(), ioe.getMessage()));
		}

		return (null);
	}

	private String getImprimePontoMeses() {

		int totalDiasMES = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

		String nomeArquivoRelatorio = null;

		switch (totalDiasMES) {
		case (28): {

			nomeArquivoRelatorio = relatorioImprimePontoMes28;

			break;
		}
		case (29): {

			nomeArquivoRelatorio = relatorioImprimePontoMes29;

			break;
		}
		case (30): {

			nomeArquivoRelatorio = relatorioImprimePontoMes30;

			break;
		}

		default: {

			nomeArquivoRelatorio = relatorioImprimePontoMes31;

			break;
		}
		}

		return (getRelatorioString(nomeArquivoRelatorio));
	}

	private String getImprimeEmpresas() {

		return (getRelatorioString(relatorioImprimeEmpresas));
	}

	private String getImprimeFuncoes() {

		return (getRelatorioString(relatorioImprimeFuncoes));
	}

	public File getRelatorioImprimePontoMeses() {

		return (getRelatorioFile(getImprimePontoMeses()));
	}

	public File getRelatorioImprimeEmpresas() {

		return (getRelatorioFile(getImprimeEmpresas()));
	}

	public File getRelatorioImprimeFuncoes() {

		return (getRelatorioFile(getImprimeFuncoes()));
	}
}