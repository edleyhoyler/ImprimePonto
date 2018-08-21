package br.com.hoyler.apps.database.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FuncoesFabricaDAO {
	Database database = new Database();

	public Boolean DeletarCodigosTabelaFuncoes(int codigo) {

		Boolean retornoBool = false;

		retornoBool = database.DeletarCodigos(database.TABELA_FUNCOES, database.CAMPO_CODIGO, codigo);

		return retornoBool;
	}

	public Boolean DeletarNomeTabelaFuncoess(String nome) {

		Boolean retornoBool = false;

		retornoBool = database.DeletarNomes(database.TABELA_EMPRESAS, database.CAMPO_CODIGO, nome);

		return retornoBool;
	}

	public Boolean UpdateFuncoesDados(String valorNomeAntigo, String valorNomeNovo) {

		Boolean retornoBool = false;

		Funcoes funcoes = new Funcoes();
		funcoes.setNOME(valorNomeNovo);

		retornoBool = UpdateFuncoesDados(funcoes, valorNomeAntigo);

		return retornoBool;
	}

	private Boolean UpdateFuncoesDados(Funcoes funcoes, String valorNomeAntigo) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String NOME = funcoes.getNOME();

		String codigoSQL = ("UPDATE [Funcoes] SET [NOME] = (?) WHERE [NOME] = (?);");
		try {
			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(codigoSQL));

			database.getPreparedStatement().setString(1, NOME);
			database.getPreparedStatement().setString(2, valorNomeAntigo);

			LinhasAfetadas = database.getPreparedStatement().executeUpdate();

			System.out.println(String.format(
					"public class FuncoesFabricaDAO UpdateFuncoesDados TABELA: [Funcoes] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - SALVAR: [%s] - [OK]",
					valorNomeAntigo, NOME, LinhasAfetadas));

			retornoBool = true;

		} catch (SQLException ex) {

			System.out.println(String.format(
					"public class FuncoesFabricaDAO UpdateFuncoesDados TABELA: [Funcoes] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CNPJ: [%s] - ENDERECO: [%s] SALVAR: [%s] - [TRY ERRO]\n%s",
					valorNomeAntigo, NOME, LinhasAfetadas, ex.getMessage()));
		}

		return retornoBool;
	}

	public ObservableList<Funcoes> ListarFuncoesNomes(String nome) {

		ObservableList<Funcoes> retornoObservableList = ListarNomes(nome);

		return (retornoObservableList);
	}

	private ObservableList<Funcoes> ListarNomes(String nome) {

		ObservableList<Funcoes> retornoObservableList = FXCollections.observableArrayList();

		int LinhasAfetadas = -9999;

		String Listar = ("SELECT * FROM [Funcoes] WHERE [NOME] like ?;");
		String Contar = ("SELECT Count(*) AS [COUNT] FROM [Funcoes] WHERE [NOME] like ?;");

		try {

			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(Contar));

			database.getPreparedStatement().setString(1, ('%' + nome + '%'));

			ResultSet resultSet = database.getPreparedStatement().executeQuery();

			while (resultSet.next()) {
				LinhasAfetadas = Integer.parseInt(resultSet.getString("COUNT"));
			}

			if (LinhasAfetadas >= 1) {

				database.setPreparedStatement(database.getConnection().prepareStatement(Listar));

				database.getPreparedStatement().setString(1, ('%' + nome + '%'));

				resultSet = database.getPreparedStatement().executeQuery();

				while (resultSet.next()) {

					Integer CODIGO = resultSet.getInt(database.CAMPO_CODIGO);
					String NOME = resultSet.getString(database.CAMPO_NOME);

					Funcoes funcoes = new Funcoes();
					funcoes.setCODIGO(CODIGO);
					funcoes.setNOME(NOME);

					retornoObservableList.add(funcoes);
				}

				System.out.println(String.format(
						"public class FuncoesFabricaDAO ListarNomes TABELA: [Funcoes] - NOME: [%s] - TOTAL: [%s]", nome,
						Integer.toString(LinhasAfetadas)));

			} else {

				Funcoes funcoes = new Funcoes();
				funcoes.setCODIGO(0);
				funcoes.setNOME("...: SEM DADOS :...");

				retornoObservableList.add(funcoes);
				
				System.out.println(String.format(
						"public class FuncoesFabricaDAO ListarNomes TABELA: [Funcoes] - NOME: [%s] - TOTAL: [%s]", nome,
						Integer.toString(LinhasAfetadas)));

			}

		} catch (SQLException e) {
			
			Funcoes funcoes = new Funcoes();
			funcoes.setCODIGO(0);
			funcoes.setNOME("...: SEM DADOS :...");

			retornoObservableList.add(funcoes);
			
			System.out.println(String.format(
					"public class FuncoesFabricaDAO ListarNomes TABELA: [Funcoes] - NOME: [%s] - [SQLException TRY ERRO]: [%s]",
					nome, Integer.toString(LinhasAfetadas)));

		}

		return (retornoObservableList);

	}

	public Boolean SalvarFuncoesDados(String valorNomeNovo) {

		Boolean retornoBool = false;

		Funcoes funcoes = new Funcoes();
		funcoes.setNOME(valorNomeNovo);

		retornoBool = SalvarFuncoesDados(funcoes);

		return retornoBool;
	}

	private Boolean SalvarFuncoesDados(Funcoes funcoes) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String NOME = funcoes.getNOME();

		String codigoSQL = ("INSERT INTO [Funcoes] (NOME) VALUES (?);");

		try {

			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(codigoSQL));

			database.getPreparedStatement().setString(1, NOME);

			LinhasAfetadas = database.getPreparedStatement().executeUpdate();

			System.out.println(
					String.format("public class FuncoesFabricaDAO TABELA: [Funcoes] - NOME: [%s] - SALVAR: [%s] [OK]",
							NOME, LinhasAfetadas));

			retornoBool = true;

		} catch (SQLException ex) {

			System.out.println(String.format(
					"public class FuncoesFabricaDAO TABELA: [Funcoes] - NOME: [%s] - SALVAR: [%s] [TRY ERRO]\n%s", NOME,
					LinhasAfetadas, ex.getMessage()));

		}

		return retornoBool;
	}

	public int getCodigoPeloNome(String nome) {

		int CODIGO_RETORNO = -9999;

		int CONTADOR_BUSCA = -9999;

		String NOME = nome;

		String CONTADOR_SQL = ("SELECT  Count(*) AS [COUNT] FROM [Funcoes] WHERE [NOME] = (?);");

		String CODIGO_SQL = ("SELECT [F].[CODIGO] FROM [Funcoes] [F] WHERE [NOME] = (?);");

		try {

			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(CONTADOR_SQL));

			database.getPreparedStatement().setString(1, NOME);

			ResultSet resultSet = database.getPreparedStatement().executeQuery();

			while (resultSet.next()) {

				CONTADOR_BUSCA = Integer.parseInt(resultSet.getString("COUNT"));
			}

			if (CONTADOR_BUSCA >= 1) {

				database.setPreparedStatement(database.getConnection().prepareStatement(CODIGO_SQL));

				database.getPreparedStatement().setString(1, NOME);

				resultSet = database.getPreparedStatement().executeQuery();

				while (resultSet.next()) {

					CODIGO_RETORNO = resultSet.getInt(database.CAMPO_CODIGO);

				}

				System.out.println(String.format(
						"public class FuncoesFabricaDAO getCodigoPeloNome TABELA: [Funcoes] - NOME: [%s] CODIGO: [%s] [OK] - CONTADOR_BUSCA: [%s]",
						NOME, CODIGO_RETORNO, CONTADOR_BUSCA));

			} else {

				System.out.println(String.format(
						"public class FuncoesFabricaDAO getCodigoPeloNome TABELA: [Funcoes] - NOME: [%s] CODIGO: [%s] [ELSE OK] - CONTADOR_BUSCA: [%s]",
						NOME, CODIGO_RETORNO, CONTADOR_BUSCA));

			}

		} catch (SQLException e) {

			System.out.println(String.format(
					"public class FuncoesFabricaDAO getCodigoPeloNome TABELA: [Funcoes] - NOME: [%s] CODIGO: [%s] - [TRY ERRO] [%s]",
					NOME, CODIGO_RETORNO, CONTADOR_BUSCA));

		}

		return CODIGO_RETORNO;
	}

	public ResultSet ListarFuncoes(String nome) {

		String querrySQL = "";
		querrySQL += ("SELECT ");
		querrySQL += ("[F].[CODIGO], ");
		querrySQL += ("[F].[NOME] ");
		querrySQL += ("FROM [Funcoes] [F] ");

		if (nome == null) {

			return database.ExecutarSQL(querrySQL);
		} else {
			if (nome.isEmpty()) {

				return database.ExecutarSQL(querrySQL);

			} else {

				querrySQL += ("WHERE [F].[NOME] = ('" + nome + "');");

				return database.ExecutarSQL(querrySQL);
			}
		}
	}

}
