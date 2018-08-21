package br.com.hoyler.apps.database.sqlite;

import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmpresasFabricaDAO {

	Database database = new Database();

	public Boolean DeletarCodigosTabelaEmpresas(int codigo) {

		Boolean retornoBool = false;

		retornoBool = database.DeletarCodigos(database.TABELA_EMPRESAS, database.CAMPO_CODIGO, codigo);

		return retornoBool;
	}

	public Boolean DeletarNomeTabelaEmpresas(String nome) {

		Boolean retornoBool = false;

		retornoBool = database.DeletarNomes(database.TABELA_EMPRESAS, database.CAMPO_CODIGO, nome);

		return retornoBool;
	}

	public Boolean UpdateEmpresasDados(String valorNomeAntigo, String valorNomeNovo, String valorCNPJNovo,
			String valorEnderecoNovo) {

		Boolean retornoBool = false;

		Empresas empresas = new Empresas();
		empresas.setNOME(valorNomeNovo);
		empresas.setCPNJ(valorCNPJNovo);
		empresas.setENDERECO(valorEnderecoNovo);

		retornoBool = UpdateEmpresasDados(empresas, valorNomeAntigo);

		return retornoBool;
	}

	private Boolean UpdateEmpresasDados(Empresas empresas, String valorNomeAntigo) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String NOME = empresas.getNOME();
		String CNPJ = empresas.getCPNJ();
		String ENDERECO = empresas.getENDERECO();

		String codigoSQL = ("UPDATE [Empresas] SET [NOME] = (?), [CNPJ] = (?), [ENDERECO] = (?) WHERE [NOME] = (?);");
		try {
			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(codigoSQL));

			database.getPreparedStatement().setString(1, NOME);
			database.getPreparedStatement().setString(2, CNPJ);
			database.getPreparedStatement().setString(3, ENDERECO);
			database.getPreparedStatement().setString(4, valorNomeAntigo);

			LinhasAfetadas = database.getPreparedStatement().executeUpdate();

			System.out.println(String.format(
					"public class EmpresasFabricaDAO UpdateEmpresas TABELA: [Empresas] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CNPJ: [%s] - ENDERECO: [%s] SALVAR: [%s] - [OK]",
					valorNomeAntigo, NOME, CNPJ, ENDERECO, LinhasAfetadas));

			retornoBool = true;

		} catch (SQLException ex) {

			System.out.println(String.format(
					"public class EmpresasFabricaDAO UpdateEmpresas TABELA: [Empresas] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CNPJ: [%s] - ENDERECO: [%s] SALVAR: [%s] - [TRU ERRO]\n%s",
					valorNomeAntigo, NOME, CNPJ, ENDERECO, LinhasAfetadas, ex.getMessage()));
		}

		return retornoBool;
	}

	public ObservableList<Empresas> ListarEmpresasNomes(String nome) {

		ObservableList<Empresas> retornoObservableList = ListarNomes(nome);

		return (retornoObservableList);
	}

	private ObservableList<Empresas> ListarNomes(String nome) {

		ObservableList<Empresas> retornoObservableList = FXCollections.observableArrayList();

		int LinhasAfetadas = -9999;

		String Listar = ("SELECT * FROM [Empresas] WHERE [NOME] like ?;");
		String Contar = ("SELECT Count(*) AS [COUNT] FROM [Empresas] WHERE [NOME] like ?;");
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
					String CNPJ = resultSet.getString(database.CAMPO_CNPJ);
					String ENDERECO = resultSet.getString(database.CAMPO_ENDERECO);

					Empresas empresas = new Empresas();
					empresas.setCODIGO(CODIGO);
					empresas.setNOME(NOME);
					empresas.setCPNJ(CNPJ);
					empresas.setENDERECO(ENDERECO);

					retornoObservableList.add(empresas);
				}

				System.out.println(String.format(
						"public class EmpresasFabricaDAO ListarNomes TABELA: [Empresas] - NOME: [%s] - TOTAL: [%s]",
						nome, Integer.toString(LinhasAfetadas)));

			} else {

				Empresas empresas = new Empresas();
				empresas.setCODIGO(0);
				empresas.setNOME("...: SEM DADOS :...");
				empresas.setCPNJ("...: SEM DADOS :...");
				empresas.setENDERECO("...: SEM DADOS :...");

				retornoObservableList.add(empresas);
				System.out.println(String.format(
						"public class EmpresasFabricaDAO ListarNomes TABELA: [Empresas] - NOME: [%s] - TOTAL: [%s]",
						nome, Integer.toString(LinhasAfetadas)));

			}

		} catch (SQLException e) {

			System.out.println(String.format(
					"public class EmpresasFabricaDAO ListarNomes TABELA: [Empresas] - NOME: [%s] - [TRY ERRO]: [%s]",nome, Integer.toString(LinhasAfetadas)));

		}

		return (retornoObservableList);

	}

	public Boolean SalvarEmpresasDados(String valorNomeNovo, String valorCNPJNovo, String valorEnderecoNovo) {

		Boolean retornoBool = false;

		Empresas empresas = new Empresas();
		empresas.setNOME(valorNomeNovo);
		empresas.setCPNJ(valorCNPJNovo);
		empresas.setENDERECO(valorEnderecoNovo);

		retornoBool = SalvarEmpresasDados(empresas);

		return retornoBool;
	}

	private Boolean SalvarEmpresasDados(Empresas empresas) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String NOME = empresas.getNOME();
		String CNPJ = empresas.getCPNJ();
		String ENDERECO = empresas.getENDERECO();

		String codigoSQL = ("INSERT INTO [Empresas] (NOME, CNPJ, ENDERECO) VALUES (?, ?, ?);");

		try {

			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(codigoSQL));

			database.getPreparedStatement().setString(1, NOME);
			database.getPreparedStatement().setString(2, CNPJ);
			database.getPreparedStatement().setString(3, ENDERECO);

			LinhasAfetadas = database.getPreparedStatement().executeUpdate();

			System.out.println(String.format(
					"public class EmpresasFabricaDAO SalvarEmpresasDados TABELA: [Empresas] - NOME: [%s] - CNPJ: [%s] ENDERECO: [%s] SALVAR: [%s] [OK]",
					NOME, CNPJ, ENDERECO, LinhasAfetadas));

			retornoBool = true;

		} catch (SQLException ex) {

			System.out.println(String.format(
					"public class EmpresasFabricaDAO SalvarEmpresasDados TABELA: [Empresas] - NOME: [%s] - CNPJ: [%s] ENDERECO: [%s] SALVAR: [%s] [TRY ERRO]\n%s",
					NOME, CNPJ, ENDERECO, LinhasAfetadas, ex.getMessage()));

		}

		return retornoBool;
	}

	public int getCodigoPeloNome(String nome) {

		int RetornoInt = -9999;

		int CONTADOR_BUSCA = -9999;

		String NOME = nome;

		String CONTADOR_SQL = ("SELECT  Count(*) AS [COUNT] FROM [Empresas] WHERE [NOME] = (?);");

		String CODIGO_SQL = ("SELECT [F].[CODIGO] FROM [Empresas] [F] WHERE [NOME] = (?);");

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

					RetornoInt = resultSet.getInt(database.CAMPO_CODIGO);

				}

				System.out.println(String.format(
						"public class EmpresasFabricaDAO getCodigoPeloNome TABELA: [Empresas] - NOME: [%s] CODIGO: [%s] [OK] CONTADOR_BUSCA: [%s]",
						NOME, RetornoInt, CONTADOR_BUSCA));

			} else {

				System.out.println(String.format(
						"public class EmpresasFabricaDAO getCodigoPeloNome TABELA: [Empresas] - NOME: [%s] CODIGO: [%s] [ELSE OK] CONTADOR_BUSCA: [%s]",
						NOME, RetornoInt, CONTADOR_BUSCA));

			}

		} catch (SQLException e) {

			System.out.println(String.format(
					"public class FuncoesFabricaDAO getCodigoPeloNome TABELA: [Empresas] - NOME: [%s] CODIGO: [%s] [TRY ERRO] [%s]",
					NOME, RetornoInt, CONTADOR_BUSCA));

		}

		return RetornoInt;
	}

	public List<Empresas> ListarEmpresas() {
		int CONTADOR_BUSCA = -9999;

		List<Empresas> ListaEmpresas = null;

		String CONTADOR_SQL = ("SELECT  Count(*) AS [COUNT] FROM [Empresas]");

		String CODIGO_SQL = ("SELECT [E].[CODIGO], [E].[NOME],  [E].[CNPJ], [E].[ENDERECO] FROM  [Empresas]");

		try {

			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(CONTADOR_SQL));

			ResultSet resultSet = database.getPreparedStatement().executeQuery();

			while (resultSet.next()) {

				CONTADOR_BUSCA = Integer.parseInt(resultSet.getString("COUNT"));

			}
			if (CONTADOR_BUSCA >= 1) {

				ListaEmpresas = new ArrayList<>();

				database.setPreparedStatement(database.getConnection().prepareStatement(CODIGO_SQL));

				resultSet = database.getPreparedStatement().executeQuery();

				while (resultSet.next()) {

					Empresas empresas = new Empresas();
					empresas.setCODIGO(resultSet.getInt(database.CAMPO_CODIGO));
					empresas.setNOME(resultSet.getString(database.CAMPO_NOME));
					empresas.setCPNJ(resultSet.getString(database.CAMPO_CNPJ));
					empresas.setENDERECO(database.CAMPO_ENDERECO);

					ListaEmpresas.add(empresas);
				}
			} else {
				System.out.println(
						"public class FuncoesFabricaDAO List<Empresas> ListarEmpresas() TABELA: [Empresas] COUNT: [0]");
			}

		} catch (SQLException e) {
			System.out.println("public class FuncoesFabricaDAO List<Empresas> ListarEmpresas() TABELA: [Empresas]");
		}

		return (ListaEmpresas);
	}

	public ResultSet ListarEmpresas(String nome) {

		String querrySQL = "";
		querrySQL += ("SELECT ");
		querrySQL += ("[E].[CODIGO], ");
		querrySQL += ("[E].[NOME], ");
		querrySQL += ("[E].[CNPJ], ");
		querrySQL += ("[E].[ENDERECO] ");
		querrySQL += ("FROM [Empresas] [E] ");

		if (nome == null) {

			return database.ExecutarSQL(querrySQL);
		} else {
			if (nome.isEmpty()) {

				return database.ExecutarSQL(querrySQL);

			} else {

				querrySQL += ("WHERE [E].[NOME] = ('" + nome + "');");

				return database.ExecutarSQL(querrySQL);
			}
		}
	}
}
