package br.com.hoyler.apps.database.sqlite;

import java.sql.ResultSet;
import java.util.Calendar;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import br.com.hoyler.apps.tools.CheckFile;
import br.com.hoyler.apps.tools.DiaSemana;

public class PessoasFabricaDAO {

	Database database = new Database();

	public boolean DeletarCodigosTabelaFuncoes(int codigo) {

		Boolean retornoBool = false;

		retornoBool = database.DeletarCodigos(database.TABELA_PESSOAS, database.CAMPO_CODIGO, codigo);

		return retornoBool;
	}

	public boolean DeletarNomeTabelaFuncoes(String nome) {

		Boolean retornoBool = false;

		retornoBool = database.DeletarNomes(database.TABELA_PESSOAS, database.CAMPO_CODIGO, nome);

		return retornoBool;
	}

	public Boolean UpdateEmpresasDados(String valorNomeAntigo, String valorNomeNovo, String valorCTPSNovo,
			String valorAdmissaoNovo, String valorFuncaoNovo, String valorAEmpresaNovo) {

		Boolean retornoBool = false;

		Pessoas pessoas = new Pessoas();
		pessoas.setNOME(valorNomeNovo);
		pessoas.setCTPS(valorCTPSNovo);
		pessoas.setADMISSAO(valorAdmissaoNovo);
		pessoas.setFUNCAO(valorFuncaoNovo);
		pessoas.setEMPRESA(valorAEmpresaNovo);

		retornoBool = UpdateEmpresasDados(pessoas, valorNomeAntigo);

		return retornoBool;
	}

	public Boolean SalvarPessoasDados(String valorNomeNovo, String valorCTPSNovo, String valorAdmissaoNovo,
			String valorFuncaoNovo, String valorAEmpresaNovo) {

		Boolean retornoBool = false;

		Pessoas pessoas = new Pessoas();
		pessoas.setNOME(valorNomeNovo);
		pessoas.setCTPS(valorCTPSNovo);
		pessoas.setADMISSAO(valorAdmissaoNovo);
		pessoas.setFUNCAO(valorFuncaoNovo);
		pessoas.setEMPRESA(valorAEmpresaNovo);

		retornoBool = SalvarPessoasDados(pessoas);

		return retornoBool;
	}

	private Boolean SalvarPessoasDados(Pessoas pessoas) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		Boolean BANCO_EXISTE = (new CheckFile().FileExists(database.PATCH_FILE));

		String NOME = pessoas.getNOME();
		String CTPS = pessoas.getCTPS();
		String ADMISSAO = pessoas.getADMISSAO();
		String FUNCAO = pessoas.getFUNCAO();
		String EMPRESA = pessoas.getEMPRESA();

		if (BANCO_EXISTE) {

			String CODIGO_SQL = ("INSERT INTO [Pessoas](NOME, CTPS, ADMISSAO, FUNCAO_CODIGO, EMPRESA_CODIGO) VALUES (?, ?, ?, ?, ?);");

			int FUNCAO_CODIGO = new FuncoesFabricaDAO().getCodigoPeloNome(FUNCAO);

			int EMPRESA_CODIGO = new EmpresasFabricaDAO().getCodigoPeloNome(EMPRESA);

			try {

				database.CriarConexaoDB();

				database.setPreparedStatement(database.getConnection().prepareStatement(CODIGO_SQL));

				database.getPreparedStatement().setString(1, NOME);
				database.getPreparedStatement().setString(2, CTPS);
				database.getPreparedStatement().setString(3, ADMISSAO);
				database.getPreparedStatement().setInt(4, FUNCAO_CODIGO);
				database.getPreparedStatement().setInt(5, EMPRESA_CODIGO);

				LinhasAfetadas = database.getPreparedStatement().executeUpdate();

				retornoBool = true;

				System.out.println(String.format(
						"public class PessoasFabricaDAO SalvarPessoasDados TABELA: [Pessoas] - CAMPO: [NOME] [%s] - CTPS: [%s] - ADMISSAO: [%s] - FUNCAO: [%s] - EMPRESA: [%s] INSERT: [%s] - [OK]",
						NOME, CTPS, ADMISSAO, FUNCAO, EMPRESA, LinhasAfetadas));

			} catch (SQLException ex) {

				System.out.println(String.format(
						"public class PessoasFabricaDAO SalvarPessoasDados TABELA: [Pessoas] - CAMPO: [NOME] [%s] - CTPS: [%s] - ADMISSAO: [%s] - FUNCAO: [%s] - EMPRESA: [%s] INSERT: [%s] - [TRY ERRO]\n%s",
						NOME, CTPS, ADMISSAO, FUNCAO, EMPRESA, LinhasAfetadas, ex.getMessage()));
			}

		} else {

			System.out.println(String.format(
					"public class PessoasFabricaDAO SalvarPessoasDados TABELA: [Pessoas] - CAMPO: [NOME] [%s] - CTPS: [%s] - ADMISSAO: [%s] - FUNCAO: [%s] - EMPRESA: [%s] INSERT: [%s] - [ELSE ERRO]",
					NOME, CTPS, ADMISSAO, FUNCAO, EMPRESA, LinhasAfetadas));

		}
		return retornoBool;
	}

	private Boolean UpdateEmpresasDados(Pessoas pessoas, String valorNomeAntigo) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String NOME = pessoas.getNOME();
		String CTPS = pessoas.getCTPS();
		String ADMISSAO = pessoas.getADMISSAO();
		String FUNCAO = pessoas.getFUNCAO();
		String EMPRESA = pessoas.getEMPRESA();

		Boolean BANCO_EXISTE = (new CheckFile().FileExists(database.PATCH_FILE));

		if (BANCO_EXISTE) {

			String CODIGO_SQL = ("UPDATE [Pessoas] SET [NOME] = (?), [CTPS] = (?), [ADMISSAO] = (?), [FUNCAO_CODIGO] = (?), [EMPRESA_CODIGO] = (?) WHERE [NOME] = (?);");

			int FUNCAO_CODIGO = new FuncoesFabricaDAO().getCodigoPeloNome(FUNCAO);

			int EMPRESA_CODIGO = new EmpresasFabricaDAO().getCodigoPeloNome(EMPRESA);

			try {

				database.CriarConexaoDB();

				database.setPreparedStatement(database.getConnection().prepareStatement(CODIGO_SQL));

				database.getPreparedStatement().setString(1, NOME);
				database.getPreparedStatement().setString(2, CTPS);
				database.getPreparedStatement().setString(3, ADMISSAO);
				database.getPreparedStatement().setInt(4, FUNCAO_CODIGO);
				database.getPreparedStatement().setInt(5, EMPRESA_CODIGO);
				database.getPreparedStatement().setString(6, valorNomeAntigo);

				LinhasAfetadas = database.getPreparedStatement().executeUpdate();

				retornoBool = true;

				System.out.println(String.format(
						"public class PessoasFabricaDAO UpdatePessoaDados TABELA: [Pessoas] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CTPS: [%s] - ADMISSAO: [%s] - FUNCAO_CODIGO: [%s] - EMPRESA_CODIGO: [%s] UPDATE: [%s] - [OK]",
						valorNomeAntigo, NOME, CTPS, ADMISSAO, Integer.toString(FUNCAO_CODIGO),
						Integer.toString(EMPRESA_CODIGO), LinhasAfetadas));

			} catch (SQLException e) {

				System.out.println(String.format(
						"public class PessoasFabricaDAO UpdatePessoaDados TABELA: [Pessoas] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CTPS: [%s] - ADMISSAO: [%s] - FUNCAO_CODIGO: [%s] - EMPRESA_CODIGO: [%s] UPDATE: [%s] - [TRY ERRO] %s",
						valorNomeAntigo, NOME, CTPS, ADMISSAO, Integer.toString(FUNCAO_CODIGO),
						Integer.toString(EMPRESA_CODIGO), LinhasAfetadas, e.getMessage()));
			}

		} else {

			System.out.println(String.format(
					"public class PessoasFabricaDAO UpdatePessoaDados TABELA: [Pessoas] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CTPS: [%s] - ADMISSAO: [%s] - FUNCAO: [%s] - EMPRESA: [%s] UPDATE: [%s] - [ELSE ERRO]",
					valorNomeAntigo, NOME, CTPS, ADMISSAO, FUNCAO, EMPRESA, LinhasAfetadas));

		}

		return retornoBool;
	}

	public ObservableList<Pessoas> ListarPessoasNomes(String consulta) {

		ObservableList<Pessoas> data = ListarPessoa(consulta);

		return data;
	}

	private ObservableList<Pessoas> ListarPessoa(String consulta) {

		Boolean BANCO_EXISTE = (new CheckFile().FileExists(database.PATCH_FILE));

		ObservableList<Pessoas> data = FXCollections.observableArrayList();

		int LinhasAfetadas = -9999;

		if (BANCO_EXISTE) {

			String Listar = ("SELECT " + "[P].[CODIGO], " + "[P].[NOME], " + "[P].[CTPS], " + "[P].[ADMISSAO], "
					+ "[F].[NOME] [FUNCAO_CODIGO], " + "[E].[NOME] [EMPRESA_CODIGO] " + "FROM [Pessoas] [P] "
					+ "INNER JOIN    [Funcoes] [F] ON " + "[P].[FUNCAO_CODIGO] = [F].[CODIGO] "
					+ "INNER JOIN  [Empresas] [E] ON " + "[P].[EMPRESA_CODIGO] = [E].[CODIGO] "
					+ "WHERE [P].[NOME] LIKE ?;");

			String Contar = ("SELECT " + "Count(*) COUNT " + "FROM [Pessoas] [P] " + "INNER JOIN    [Funcoes] [F] ON "
					+ "[P].[FUNCAO_CODIGO] = [F].[CODIGO] " + "INNER JOIN  [Empresas] [E] ON "
					+ "[P].[EMPRESA_CODIGO] = [E].[CODIGO] " + "WHERE [P].[NOME] LIKE ?;");

			try {

				database.CriarConexaoDB();

				database.setPreparedStatement(database.getConnection().prepareStatement(Contar));

				database.getPreparedStatement().setString(1, ('%' + consulta + '%'));

				ResultSet resultSet = database.getPreparedStatement().executeQuery();

				while (resultSet.next()) {
					LinhasAfetadas = Integer.parseInt(resultSet.getString("COUNT"));
					// System.out.println(Integer.parseInt(resultSet.getString("COUNT")));
				}

				if (LinhasAfetadas >= 1) {

					database.setPreparedStatement(database.getConnection().prepareStatement(Listar));

					database.getPreparedStatement().setString(1, ('%' + consulta + '%'));

					resultSet = database.getPreparedStatement().executeQuery();

					while (resultSet.next()) {

						Integer CODIGO = resultSet.getInt(database.CAMPO_CODIGO);
						String NOME = resultSet.getString(database.CAMPO_NOME);
						String CTPS = resultSet.getString(database.CAMPO_CTPS);
						String ADMISSAO = resultSet.getString(database.CAMPO_ADMISSAO);
						String FUNCAO_CODIGO = resultSet.getString(database.CAMPO_FUNCAO_CODIGO);
						String EMPRESA_CODIGO = resultSet.getString(database.CAMPO_EMPRESA_CODIGO);

						Pessoas pessoas = new Pessoas();
						pessoas.setCODIGO(CODIGO);
						pessoas.setNOME(NOME);
						pessoas.setCTPS(CTPS);
						pessoas.setADMISSAO(ADMISSAO);
						pessoas.setFUNCAO(FUNCAO_CODIGO);
						pessoas.setEMPRESA(EMPRESA_CODIGO);

						data.add(pessoas);
					}

					System.out.println(String.format(
							"public class PessoasFabricaDAO  ListarPessoasNomes TABELA: [Pessoas] - NOME: [%s] - BANCO_EXISTE: [%s] - TOTAL: [%s]",
							consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));

				} else {

					Pessoas pessoas = new Pessoas();
					pessoas.setCODIGO(0);
					pessoas.setNOME("...: SEM DADOS :...");
					pessoas.setCTPS("...: SEM DADOS :...");
					pessoas.setADMISSAO("...: SEM DADOS :...");
					pessoas.setFUNCAO("...: SEM DADOS :...");
					pessoas.setEMPRESA("...: SEM DADOS :...");

					data.add(pessoas);
					System.out.println(String.format(
							"public class PessoasFabricaDAO  ListarPessoasNomes TABELA: [Pessoas] - NOME: [%s] - BANCO_EXISTE: [%s] - TOTAL: [%s]",
							consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));

				}

			} catch (SQLException e) {

				System.out.println(String.format(
						"public class PessoasFabricaDAO  ListarPessoasNomes TABELA: [Pessoas] - NOME: [%s] - BANCO_EXISTE: [%s] - [TRY ERRO]: [%s]",
						consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));

			}

		} else {

			System.out.println(String.format(
					"public class PessoasFabricaDAO  ListarPessoasNomes TABELA: [Pessoas] - NOME: [%s] BANCO_EXISTE: [%s] [ELSE ERRO]: [%s]",
					consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));

		}
		return data;
	}

	public ObservableList<String> ListarFuncoesNomes(String consulta) {

		ObservableList<String> data = ListarNomes(consulta);

		return data;

	}

	private ObservableList<String> ListarNomes(String consulta) {

		Boolean BANCO_EXISTE = (new CheckFile().FileExists(database.PATCH_FILE));

		ObservableList<String> data = FXCollections.observableArrayList();

		int LinhasAfetadas = -9999;
		if (BANCO_EXISTE) {

			String Listar = ("SELECT [F].[NOME] FROM [FUNCOES] AS [F] WHERE [NOME] LIKE ?;");
			String Contar = ("SELECT COUNT(*) AS [COUNT] FROM [FUNCOES] AS [F] WHERE [NOME] LIKE ?;");

			try {

				database.CriarConexaoDB();

				database.setPreparedStatement(database.getConnection().prepareStatement(Contar));

				database.getPreparedStatement().setString(1, ('%' + consulta + '%'));

				ResultSet resultSet = database.getPreparedStatement().executeQuery();

				while (resultSet.next()) {

					LinhasAfetadas = Integer.parseInt(resultSet.getString("COUNT"));

				}

				if (LinhasAfetadas >= 1) {

					database.setPreparedStatement(database.getConnection().prepareStatement(Listar));

					database.getPreparedStatement().setString(1, ('%' + consulta + '%'));

					resultSet = database.getPreparedStatement().executeQuery();

					while (resultSet.next()) {

						data.add(resultSet.getString(database.CAMPO_NOME));
					}

					System.out.println(String.format(
							"public class PessoasFabricaDAO ListarFuncoes TABELA: [Pessoas] - NOME: [%s] - BANCO_EXISTE: [%s] - TOTAL: [%s]",
							consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));

				} else {

					data.add("...: SEM DADOS :...");
					System.out.println(String.format(
							"public class PessoasFabricaDAO ListarFuncoes TABELA: [Pessoas] - NOME: [%s] - BANCO_EXISTE: [%s] - TOTAL: [%s]",
							consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));
				}

			} catch (SQLException e) {

				System.out.println(String.format(
						"public class PessoasFabricaDAO ListarFuncoes TABELA: [Pessoas] - NOME: [%s] - BANCO_EXISTE: [%s] - [TRY ERRO]: [%s]",
						consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));

				// e.printStackTrace();
			}

		} else {

			System.out.println(String.format(
					"public class PessoasFabricaDAO ListarFuncoes TABELA: [Pessoas] - NOME: [%s] BANCO_EXISTE: [%s] [ELSE ERRO]: [%s]",
					consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));
		}

		return data;

	}

	public ObservableList<String> ListarEmpresasNomes(String consulta) {

		ObservableList<String> data = ListarEmpresas(consulta);

		return data;

	}

	private ObservableList<String> ListarEmpresas(String consulta) {

		Boolean BANCO_EXISTE = (new CheckFile().FileExists(database.PATCH_FILE));

		ObservableList<String> data = FXCollections.observableArrayList();

		int LinhasAfetadas = -9999;
		if (BANCO_EXISTE) {

			String Listar = ("SELECT [F].[NOME] FROM [EMPRESAS] AS [F] WHERE [NOME] LIKE ?;");
			String Contar = ("SELECT COUNT(*) AS [COUNT] FROM [EMPRESAS] AS [F] WHERE [NOME] LIKE ?;");

			try {

				database.CriarConexaoDB();

				database.setPreparedStatement(database.getConnection().prepareStatement(Contar));

				database.getPreparedStatement().setString(1, ('%' + consulta + '%'));

				ResultSet resultSet = database.getPreparedStatement().executeQuery();

				while (resultSet.next()) {

					LinhasAfetadas = Integer.parseInt(resultSet.getString("COUNT"));

				}

				if (LinhasAfetadas >= 1) {

					database.setPreparedStatement(database.getConnection().prepareStatement(Listar));

					database.getPreparedStatement().setString(1, ('%' + consulta + '%'));

					resultSet = database.getPreparedStatement().executeQuery();

					while (resultSet.next()) {

						data.add(resultSet.getString(database.CAMPO_NOME));
					}

					System.out.println(String.format(
							"public class PessoasFabricaDAO ListarFuncoes TABELA: [Funcoes] - NOME: [%s] - BANCO_EXISTE: [%s] - TOTAL: [%s]",
							consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));

				} else {

					data.add("...: SEM DADOS :...");
					System.out.println(String.format(
							"public class PessoasFabricaDAO ListarFuncoes TABELA: [Funcoes] - NOME: [%s] - BANCO_EXISTE: [%s] - TOTAL: [%s]",
							consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));
				}

			} catch (SQLException e) {

				System.out.println(String.format(
						"public class PessoasFabricaDAO ListarFuncoes TABELA: [Funcoes] - NOME: [%s] - BANCO_EXISTE: [%s] - [TRY ERRO]: [%s]",
						consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));

				// e.printStackTrace();
			}

		} else {

			System.out.println(String.format(
					"public class PessoasFabricaDAO ListarFuncoes TABELA: [Funcoes] - NOME: [%s] BANCO_EXISTE: [%s] [ELSE ERRO]: [%s]",
					consulta, BANCO_EXISTE.toString(), Integer.toString(LinhasAfetadas)));
		}

		return data;

	}

	public ResultSet ListarPessoas(String nome) {

		String querrySQL = "";
		querrySQL += ("SELECT [P].[CODIGO], ");
		querrySQL += ("[P].[NOME] [PESSOA], ");
		querrySQL += ("[P].[CTPS], ");
		querrySQL += ("[P].[ADMISSAO], ");
		querrySQL += ("[F].[NOME] [FUNCAO], ");
		querrySQL += ("[E].[NOME] [EMPRESA], ");
		querrySQL += ("[E].[CNPJ], ");
		querrySQL += ("[E].[ENDERECO], ");
		
		int totalDiasMES = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= totalDiasMES; i++) {
			String dia = Integer.toString(i);
			String Semana = DiaSemana.ChecarDiaSemana(dia);
			querrySQL += String.format("(select ' %s %s') AS DIA_%s ", dia, Semana, dia);
			if (i < totalDiasMES)
			{
				querrySQL += ", ";
			}
		}
		
		querrySQL += ("FROM [Pessoas] [P] ");
		querrySQL += ("INNER JOIN [Funcoes] [F] ON ");
		querrySQL += ("[P].[FUNCAO_CODIGO] = [F].[CODIGO] ");
		querrySQL += ("INNER JOIN  [Empresas] [E] ON ");
		querrySQL += ("[P].[EMPRESA_CODIGO] = [E].[CODIGO] ");
		System.out.println(DiaSemana.ChecarDiaSemana("222"));
		if (nome == null) {

			return database.ExecutarSQL(querrySQL);
		} else {
			if (nome.isEmpty()) {

				return database.ExecutarSQL(querrySQL);

			} else {

				querrySQL += ("WHERE [P].[NOME] = ('" + nome + "');");

				return database.ExecutarSQL(querrySQL);
			}
		}
	}
}
