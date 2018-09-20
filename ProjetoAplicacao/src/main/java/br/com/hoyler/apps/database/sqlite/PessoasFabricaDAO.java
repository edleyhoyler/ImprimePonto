package br.com.hoyler.apps.database.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.hoyler.apps.tools.DiaSemana;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PessoasFabricaDAO {

	Database database = new Database();

	public boolean deletarCodigo(int codigo) {

		return (database.DeletarCodigos(database.TABELA_PESSOAS, database.CAMPO_CODIGO, codigo));
	}

	public boolean deletarNome(String nome) {

		return (database.DeletarNomes(database.TABELA_PESSOAS, database.CAMPO_CODIGO, nome));
	}

	public boolean updateNome(String valorNomeAntigo, Pessoas pessoas) {

		return (updateNomePessoas(valorNomeAntigo, pessoas));
	}

	public boolean salvarDados(Pessoas pessoas) {

		return (salvarDadosPessoas(pessoas));
	}

	public ObservableList<String> ListarFuncoesNomes(String consulta) {

		ObservableList<String> data = ListarNomes(consulta);

		return data;

	}

	public ObservableList<String> ListarEmpresasNomes(String consulta) {

		ObservableList<String> data = ListarEmpresas(consulta);

		return data;

	}

	public ResultSet ListarPessoas(String nome) {

		return (ListarPessoasDados(nome));
	}

	public ObservableList<Pessoas> ListarPessoasNomes(String consulta) {

		ObservableList<Pessoas> retornoObservableList = ListarPessoa(consulta);

		return (retornoObservableList);
	}

	private boolean updateNomePessoas(String valorNomeAntigo, Pessoas pessoas) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String nome = pessoas.getNome();
		String ctps = pessoas.getCtps();
		String admissao = pessoas.getAdmissao();
		String funcao = pessoas.getFuncao();
		String empresa = pessoas.getEmpresa();

		String CODIGO_SQL = ("UPDATE [Pessoas] SET [NOME] = (?), [CTPS] = (?), [ADMISSAO] = (?), [FUNCAO_CODIGO] = (?), [EMPRESA_CODIGO] = (?) WHERE [NOME] = (?);");

		int funcaoFabricaDAO = new FuncoesFabricaDAO().getCodigoViaNome(funcao);

		int empresasFabricaDAO = new EmpresasFabricaDAO().getCodigoViaNome(empresa);

		try {

			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(CODIGO_SQL));

			database.getPreparedStatement().setString(1, nome);
			database.getPreparedStatement().setString(2, ctps);
			database.getPreparedStatement().setString(3, admissao);
			database.getPreparedStatement().setInt(4, funcaoFabricaDAO);
			database.getPreparedStatement().setInt(5, empresasFabricaDAO);
			database.getPreparedStatement().setString(6, valorNomeAntigo);

			LinhasAfetadas = database.getPreparedStatement().executeUpdate();

			retornoBool = true;

			System.out.println(String.format(
					"public class PessoasFabricaDAO UpdatePessoaDados TABELA: [Pessoas] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CTPS: [%s] - ADMISSAO: [%s] - FUNCAO_CODIGO: [%s] - EMPRESA_CODIGO: [%s] UPDATE: [%s] - [OK]",
					valorNomeAntigo, nome, ctps, admissao, Integer.toString(funcaoFabricaDAO),
					Integer.toString(empresasFabricaDAO), LinhasAfetadas));

		} catch (SQLException e) {

			System.out.println(String.format(
					"public class PessoasFabricaDAO UpdatePessoaDados TABELA: [Pessoas] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CTPS: [%s] - ADMISSAO: [%s] - FUNCAO_CODIGO: [%s] - EMPRESA_CODIGO: [%s] UPDATE: [%s] - [TRY ERRO] %s",
					valorNomeAntigo, nome, ctps, admissao, Integer.toString(funcaoFabricaDAO),
					Integer.toString(empresasFabricaDAO), LinhasAfetadas, e.getMessage()));
		}

		return retornoBool;
	}

	private Boolean salvarDadosPessoas(Pessoas pessoas) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String nome = pessoas.getNome();
		String ctps = pessoas.getCtps();
		String admissao = pessoas.getAdmissao();
		String funcao = pessoas.getFuncao();
		String empresa = pessoas.getEmpresa();

		String CODIGO_SQL = ("INSERT INTO [Pessoas](NOME, CTPS, ADMISSAO, FUNCAO_CODIGO, EMPRESA_CODIGO) VALUES (?, ?, ?, ?, ?);");

		int funcaoFabricaDAO = new FuncoesFabricaDAO().getCodigoViaNome(funcao);

		int empresasFabricaDAO = new EmpresasFabricaDAO().getCodigoViaNome(empresa);

		try {

			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(CODIGO_SQL));

			database.getPreparedStatement().setString(1, nome);
			database.getPreparedStatement().setString(2, ctps);
			database.getPreparedStatement().setString(3, admissao);
			database.getPreparedStatement().setInt(4, funcaoFabricaDAO);
			database.getPreparedStatement().setInt(5, empresasFabricaDAO);

			LinhasAfetadas = database.getPreparedStatement().executeUpdate();

			retornoBool = true;

			System.out.println(String.format(
					"public class PessoasFabricaDAO SalvarPessoasDados TABELA: [Pessoas] - CAMPO: [NOME] [%s] - CTPS: [%s] - ADMISSAO: [%s] - FUNCAO: [%s] - EMPRESA: [%s] INSERT: [%s] - [OK]",
					nome, ctps, admissao, funcao, empresa, LinhasAfetadas));

		} catch (SQLException ex) {

			System.out.println(String.format(
					"public class PessoasFabricaDAO SalvarPessoasDados TABELA: [Pessoas] - CAMPO: [NOME] [%s] - CTPS: [%s] - ADMISSAO: [%s] - FUNCAO: [%s] - EMPRESA: [%s] INSERT: [%s] - [TRY ERRO]\n%s",
					nome, ctps, admissao, funcao, empresa, LinhasAfetadas, ex.getMessage()));
		}

		return retornoBool;
	}

	private ObservableList<Pessoas> ListarPessoa(String consulta) {

		ObservableList<Pessoas> retornoObservableList = FXCollections.observableArrayList();

		int LinhasAfetadas = -9999;

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
			}

			if (LinhasAfetadas >= 1) {

				database.setPreparedStatement(database.getConnection().prepareStatement(Listar));

				database.getPreparedStatement().setString(1, ('%' + consulta + '%'));

				resultSet = database.getPreparedStatement().executeQuery();

				while (resultSet.next()) {

					Pessoas pessoas = new Pessoas();
					pessoas.setCodigo(resultSet.getInt(database.CAMPO_CODIGO));
					pessoas.setNome(resultSet.getString(database.CAMPO_NOME));
					pessoas.setCtps(resultSet.getString(database.CAMPO_CTPS));
					pessoas.setAdmissao(resultSet.getString(database.CAMPO_ADMISSAO));
					pessoas.setFuncao(resultSet.getString(database.CAMPO_FUNCAO_CODIGO));
					pessoas.setEmpresa(resultSet.getString(database.CAMPO_EMPRESA_CODIGO));

					retornoObservableList.add(pessoas);
				}

				System.out.println(String.format(
						"public class PessoasFabricaDAO  ListarPessoasNomes TABELA: [Pessoas] - NOME: [%s] - TOTAL: [%s]",
						consulta, Integer.toString(LinhasAfetadas)));

			} else {

				Pessoas pessoas = new Pessoas();
				pessoas.setCodigo(0);
				pessoas.setNome("...: SEM DADOS :...");
				pessoas.setCtps("...: SEM DADOS :...");
				pessoas.setAdmissao("...: SEM DADOS :...");
				pessoas.setFuncao("...: SEM DADOS :...");
				pessoas.setEmpresa("...: SEM DADOS :...");

				retornoObservableList.add(pessoas);
				System.out.println(String.format(
						"public class PessoasFabricaDAO  ListarPessoasNomes TABELA: [Pessoas] - NOME: [%s] - TOTAL: [%s]",
						consulta, Integer.toString(LinhasAfetadas)));

			}

		} catch (SQLException e) {

			System.out.println(String.format(
					"public class PessoasFabricaDAO  ListarPessoasNomes TABELA: [Pessoas] - NOME: [%s] - [TRY ERRO]: [%s]",
					consulta, Integer.toString(LinhasAfetadas)));

		}
		return (retornoObservableList);
	}

	private ObservableList<String> ListarNomes(String consulta) {

		ObservableList<String> data = FXCollections.observableArrayList();

		int LinhasAfetadas = -9999;

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
						"public class PessoasFabricaDAO ListarFuncoes TABELA: [Pessoas] - NOME: [%s] - TOTAL: [%s]",
						consulta, Integer.toString(LinhasAfetadas)));

			} else {

				data.add("...: SEM DADOS :...");
				System.out.println(String.format(
						"public class PessoasFabricaDAO ListarFuncoes TABELA: [Pessoas] - NOME: [%s] - TOTAL: [%s]",
						consulta, Integer.toString(LinhasAfetadas)));
			}

		} catch (SQLException e) {

			System.out.println(String.format(
					"public class PessoasFabricaDAO ListarFuncoes TABELA: [Pessoas] - NOME: [%s] - [TRY ERRO]: [%s]",
					consulta, Integer.toString(LinhasAfetadas)));

			// e.printStackTrace();
		}

		return data;

	}

	private ObservableList<String> ListarEmpresas(String consulta) {

		ObservableList<String> retornoObservableList = FXCollections.observableArrayList();

		int LinhasAfetadas = -9999;

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

					retornoObservableList.add(resultSet.getString(database.CAMPO_NOME));
				}

				System.out.println(String.format(
						"public class PessoasFabricaDAO ListarFuncoes TABELA: [Funcoes] - NOME: [%s] - TOTAL: [%s]",
						consulta, Integer.toString(LinhasAfetadas)));

			} else {

				retornoObservableList.add("...: SEM DADOS :...");
				System.out.println(String.format(
						"public class PessoasFabricaDAO ListarFuncoes TABELA: [Funcoes] - NOME: [%s] - TOTAL: [%s]",
						consulta, Integer.toString(LinhasAfetadas)));
			}

		} catch (SQLException e) {

			System.out.println(String.format(
					"public class PessoasFabricaDAO ListarFuncoes TABELA: [Funcoes] - NOME: [%s] - [TRY ERRO]: [%s]",
					consulta, Integer.toString(LinhasAfetadas)));

		}

		return (retornoObservableList);

	}

	private ResultSet ListarPessoasDados(String nome) {

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
			if (i < totalDiasMES) {
				querrySQL += ", ";
			}
		}

		querrySQL += ("FROM [Pessoas] [P] ");
		querrySQL += ("INNER JOIN [Funcoes] [F] ON ");
		querrySQL += ("[P].[FUNCAO_CODIGO] = [F].[CODIGO] ");
		querrySQL += ("INNER JOIN  [Empresas] [E] ON ");
		querrySQL += ("[P].[EMPRESA_CODIGO] = [E].[CODIGO] ");
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
