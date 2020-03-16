package br.com.hoyler.apps.database.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FuncoesFabricaDAO {

	Database database = new Database();

	public int getCodigoViaNome(String nome) {

		return (getCodigoViaNomeFuncoes(nome));
	}

	public boolean deletarCodigos(int codigo) {

		return (database.DeletarCodigos(database.TABELA_FUNCOES, database.CAMPO_CODIGO, codigo));
	}

	public boolean deletarNome(String nome) {

		return (database.DeletarNomes(database.TABELA_EMPRESAS, database.CAMPO_CODIGO, nome));
	}

	public boolean updateNome(String valorNomeAntigo, Funcoes funcoes) {

		return (updateNomeFuncoes(valorNomeAntigo, funcoes));
	}

	public boolean salvarDados(Funcoes funcoes) {

		return (salvarDadosFuncoes(funcoes));
	}

	public ResultSet listarDados_ResultSet(String nome) {

		return (listarDadosFuncoes(nome));
	}

	public List<Funcoes> listarDados_ListFuncoes(String nome) {

		return (listarDadosFuncoes_(nome));
	}

	public ObservableList<Funcoes> listarDadosOL(String nome) {

		return (listarDadosOLFuncoes(nome));
	}

	private int getCodigoViaNomeFuncoes(String nome) {

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

	private boolean updateNomeFuncoes(String valorNomeAntigo, Funcoes funcoes) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String codigoSQL = ("UPDATE [Funcoes] SET [NOME] = (?) WHERE [NOME] = (?);");
		try {
			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(codigoSQL));

			database.getPreparedStatement().setString(1, funcoes.getNome());
			database.getPreparedStatement().setString(2, valorNomeAntigo);

			LinhasAfetadas = database.getPreparedStatement().executeUpdate();

			System.out.println(String.format(
					"public class FuncoesFabricaDAO UpdateFuncoesDados TABELA: [Funcoes] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - SALVAR: [%s] - [OK]",
					valorNomeAntigo, funcoes.getNome(), LinhasAfetadas));

			retornoBool = true;

		} catch (SQLException ex) {

			System.out.println(String.format(
					"public class FuncoesFabricaDAO UpdateFuncoesDados TABELA: [Funcoes] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CNPJ: [%s] - ENDERECO: [%s] SALVAR: [%s] - [TRY ERRO]\n%s",
					valorNomeAntigo, funcoes.getNome(), LinhasAfetadas, ex.getMessage()));
		}

		return retornoBool;
	}

	private boolean salvarDadosFuncoes(Funcoes funcoes) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String codigoSQL = ("INSERT INTO [Funcoes] (NOME) VALUES (?);");

		try {

			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(codigoSQL));

			database.getPreparedStatement().setString(1, funcoes.getNome());

			LinhasAfetadas = database.getPreparedStatement().executeUpdate();

			System.out.println(
					String.format("public class FuncoesFabricaDAO TABELA: [Funcoes] - NOME: [%s] - SALVAR: [%s] [OK]",
							funcoes.getNome(), LinhasAfetadas));

			retornoBool = true;

		} catch (SQLException ex) {

			System.out.println(String.format(
					"public class FuncoesFabricaDAO TABELA: [Funcoes] - NOME: [%s] - SALVAR: [%s] [TRY ERRO]\n%s",
					funcoes.getNome(), LinhasAfetadas, ex.getMessage()));

		}

		return retornoBool;
	}

	private ResultSet listarDadosFuncoes(String nome) {

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

	private List<Funcoes> listarDadosFuncoes_(String nome) {

		String querrySQL = "";
		querrySQL += ("SELECT ");
		querrySQL += ("[F].[CODIGO], ");
		querrySQL += ("[F].[NOME] ");
		querrySQL += ("FROM [Funcoes] [F] ");

		

		List<Funcoes> list = new ArrayList<>();
		


		if (nome == null || nome.isEmpty()) {
			ResultSet resultSet = database.ExecutarSQL(querrySQL);
			try {
				while (resultSet.next()) {
					Funcoes funcoes = new Funcoes();
					funcoes.setCodigo(resultSet.getInt(1));
					funcoes.setNome(resultSet.getString(2));
					
					list.add(funcoes);
					System.out.println(resultSet.getInt(1) + " - " + resultSet.getString(2));
				}
			} catch (SQLException e) {
				System.out.println("Erro xp1");
				e.printStackTrace();
			}
			
			return list;
		} else {
			
	 
			querrySQL += ("WHERE [F].[NOME] = ('" + nome + "');");
			ResultSet resultSet = database.ExecutarSQL(querrySQL);
			try {
				while (resultSet.next()) {
					Funcoes funcoes = new Funcoes();
					funcoes.setCodigo(resultSet.getInt(1));
					funcoes.setNome(resultSet.getString(2));
					
					list.add(funcoes);
					System.out.println(resultSet.getInt(1) + " - " + resultSet.getString(2));
				}
			} catch (SQLException e) {
				System.out.println("Erro xp2");
				e.printStackTrace();
			}
		
			return list;
		/*
			if (nome.isEmpty()) {

				return database.ExecutarSQL(querrySQL);

			} else {

				querrySQL += ("WHERE [F].[NOME] = ('" + nome + "');");

				return database.ExecutarSQL(querrySQL);
			}
			
			*/
		}
	}

	private ObservableList<Funcoes> listarDadosOLFuncoes(String nome) {

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
					funcoes.setCodigo(CODIGO);
					funcoes.setNome(NOME);

					retornoObservableList.add(funcoes);
				}

				System.out.println(String.format(
						"public class FuncoesFabricaDAO ListarNomes TABELA: [Funcoes] - NOME: [%s] - TOTAL: [%s]", nome,
						Integer.toString(LinhasAfetadas)));

			} else {

				Funcoes funcoes = new Funcoes();
				funcoes.setCodigo(0);
				funcoes.setNome("...: SEM DADOS :...");

				retornoObservableList.add(funcoes);

				System.out.println(String.format(
						"public class FuncoesFabricaDAO ListarNomes TABELA: [Funcoes] - NOME: [%s] - TOTAL: [%s]", nome,
						Integer.toString(LinhasAfetadas)));

			}

		} catch (SQLException e) {

			Funcoes funcoes = new Funcoes();
			funcoes.setCodigo(0);
			funcoes.setNome("...: SEM DADOS :...");

			retornoObservableList.add(funcoes);

			System.out.println(String.format(
					"public class FuncoesFabricaDAO ListarNomes TABELA: [Funcoes] - NOME: [%s] - [SQLException TRY ERRO]: [%s]",
					nome, Integer.toString(LinhasAfetadas)));

		}

		return (retornoObservableList);

	}

}
