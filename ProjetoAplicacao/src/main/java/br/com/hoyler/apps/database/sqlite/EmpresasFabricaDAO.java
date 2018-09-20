package br.com.hoyler.apps.database.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmpresasFabricaDAO {

	Database database = new Database();

	public int getCodigoViaNome(String nome) {

		return (getCodigoViaNomeEmpresa(nome));
	}

	public boolean deletarCodigos(int codigo) {

		return (database.DeletarCodigos(database.TABELA_EMPRESAS, database.CAMPO_CODIGO, codigo));
	}

	public boolean deletarNome(String nome) {

		return (database.DeletarNomes(database.TABELA_EMPRESAS, database.CAMPO_CODIGO, nome));
	}

	public boolean updateNome(String valorNomeAntigo, Empresas empresas) {

		return (updateNomeEmpresas(valorNomeAntigo, empresas));
	}

	public boolean salvarDados(Empresas empresas) {

		return salvarDadosEmpresa(empresas);
	}
	
	public ResultSet listarDados(String nome) {

		return (listarDadosEmpresa(nome));
	}
	
	public ObservableList<Empresas> listarDadosOL(String nome) {

		return (listarDadosOLEmpresas(nome));
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
					empresas.setCodigo(resultSet.getInt(database.CAMPO_CODIGO));
					empresas.setNome(resultSet.getString(database.CAMPO_NOME));
					empresas.setCnpj(resultSet.getString(database.CAMPO_CNPJ));
					empresas.setEndereco(database.CAMPO_ENDERECO);

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

	public ResultSet listarDadosEmpresa(String nome) {

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
	
	private int getCodigoViaNomeEmpresa(String nome) {

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
	
	private boolean salvarDadosEmpresa(Empresas empresas) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String NOME = empresas.getNome();
		String CNPJ = empresas.getCnpj();
		String ENDERECO = empresas.getEndereco();

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

	private boolean updateNomeEmpresas(String valorNomeAntigo, Empresas empresas) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		String codigoSQL = ("UPDATE [Empresas] SET [NOME] = (?), [CNPJ] = (?), [ENDERECO] = (?) WHERE [NOME] = (?);");
		try {
			database.CriarConexaoDB();

			database.setPreparedStatement(database.getConnection().prepareStatement(codigoSQL));

			database.getPreparedStatement().setString(1, empresas.getNome());
			database.getPreparedStatement().setString(2, empresas.getCnpj());
			database.getPreparedStatement().setString(3, empresas.getEndereco());
			database.getPreparedStatement().setString(4, valorNomeAntigo);

			LinhasAfetadas = database.getPreparedStatement().executeUpdate();

			System.out.println(String.format(
					"public class EmpresasFabricaDAO UpdateEmpresas TABELA: [Empresas] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CNPJ: [%s] - ENDERECO: [%s] SALVAR: [%s] - [OK]",
					valorNomeAntigo, empresas.getNome(), empresas.getCnpj(), empresas.getEndereco(), LinhasAfetadas));

			retornoBool = true;

		} catch (SQLException ex) {

			System.out.println(String.format(
					"public class EmpresasFabricaDAO UpdateEmpresas TABELA: [Empresas] - CAMPO: [NOME] - VALOR ANTIGO: [%s] - VALOR NOVO: [%s] - CNPJ: [%s] - ENDERECO: [%s] SALVAR: [%s] - [TRU ERRO]\n%s",
					valorNomeAntigo, empresas.getNome(), empresas.getCnpj(), empresas.getEndereco(), LinhasAfetadas,
					ex.getMessage()));
		}

		return retornoBool;
	}

	private ObservableList<Empresas> listarDadosOLEmpresas(String nome) {

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

					Empresas empresas = new Empresas();
					empresas.setCodigo(resultSet.getInt(database.CAMPO_CODIGO));
					empresas.setNome(resultSet.getString(database.CAMPO_NOME));
					empresas.setCnpj(resultSet.getString(database.CAMPO_CNPJ));
					empresas.setEndereco(resultSet.getString(database.CAMPO_ENDERECO));

					retornoObservableList.add(empresas);
				}

				System.out.println(String.format(
						"public class EmpresasFabricaDAO ListarNomes TABELA: [Empresas] - NOME: [%s] - TOTAL: [%s]",
						nome, Integer.toString(LinhasAfetadas)));

			} else {

				Empresas empresas = new Empresas();
				empresas.setCodigo(0);
				empresas.setNome("...: SEM DADOS :...");
				empresas.setCnpj("...: SEM DADOS :...");
				empresas.setEndereco("...: SEM DADOS :...");

				retornoObservableList.add(empresas);
				System.out.println(String.format(
						"public class EmpresasFabricaDAO ListarNomes TABELA: [Empresas] - NOME: [%s] - TOTAL: [%s]",
						nome, Integer.toString(LinhasAfetadas)));

			}

		} catch (SQLException e) {

			System.out.println(String.format(
					"public class EmpresasFabricaDAO ListarNomes TABELA: [Empresas] - NOME: [%s] - [TRY ERRO]: [%s]",
					nome, Integer.toString(LinhasAfetadas)));

		}

		return (retornoObservableList);

	}
}
