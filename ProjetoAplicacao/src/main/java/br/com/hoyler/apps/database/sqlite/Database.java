package br.com.hoyler.apps.database.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import org.sqlite.SQLiteConfig;
import java.sql.PreparedStatement;
import br.com.hoyler.apps.tools.CheckFile;
import br.com.hoyler.apps.tools.ProgramDirectoryUtilities;

public class Database {

	private Connection connection = null;
	private PreparedStatement preparedStatement = null;

	private final String BANCO_DE_DADOS = ("ImprimeFolha.s3db");

	public final String TABELA_FUNCOES = ("Funcoes");
	public final String TABELA_EMPRESAS = ("Empresas");
	public final String TABELA_PESSOAS = ("Pessoas");

	public final String CAMPO_CODIGO = "CODIGO";
	public final String CAMPO_NOME = "NOME";
	public final String CAMPO_CNPJ = "CNPJ";
	public final String CAMPO_CTPS = "CTPS";
	public final String CAMPO_ENDERECO = "ENDERECO";
	public final String CAMPO_ADMISSAO = "ADMISSAO";
	public final String CAMPO_FUNCAO_CODIGO = "FUNCAO_CODIGO";
	public final String CAMPO_EMPRESA_CODIGO = "EMPRESA_CODIGO";

	public final String PATCH_FILE = String.format("%s\\ImprimePonto\\Database\\%s",
			ProgramDirectoryUtilities.getProgramDirectory(), BANCO_DE_DADOS);

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	public void CriarConexaoDB() {

		Boolean BANCO_EXISTE = (new CheckFile().FileExists(PATCH_FILE));

		if (BANCO_EXISTE) {

			String DatabaseURL = String.format("%s%s", "jdbc:sqlite:", PATCH_FILE);

			String DB_URL = DatabaseURL;

			String DRIVER = "org.sqlite.JDBC";

			try {

				Class.forName(DRIVER);

				SQLiteConfig config = new SQLiteConfig();
				config.setBusyTimeout("15000");
				config.enforceForeignKeys(true);

				connection = DriverManager.getConnection(DB_URL, config.toProperties());

			} catch (ClassNotFoundException e) {

				System.out.println(
						String.format("public class br.com.hoyler.apps.database.sqlite CriarConexaoDB DRIVER: [%s] [CATCH ERRO]", DRIVER));

			} catch (SQLException e) {

				System.out.println(
						String.format("public class br.com.hoyler.apps.database.sqlite CriarConexaoDB DB_URL: [%s] [CATCH ERRO]", DB_URL));
			}

		} else

		{
			System.out
					.println(String.format("public class br.com.hoyler.apps.database.sqlite CriarConexaoDB BANCO_EXISTE: [%s] [ELSE ERRO]",
							BANCO_EXISTE.toString()));

		}

	}

	public void ConexaoDBClose() throws SQLException {

		if (connection.isClosed()) {

			connection = null;
			preparedStatement = null;
			System.out.println("ConexaoDBClose NULL [OK]\n");
		} else {
			connection.close();
			preparedStatement.close();
			ConexaoDBClose();
			System.out.println("ConexaoDBClose IS CLOSE [OK]\n");
		}
	}

	public Boolean DeletarCodigos(String tablela, String campo, int codigo) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		Boolean bancoExiste = (new CheckFile().FileExists(PATCH_FILE));

		if (bancoExiste) {
			String DeletarFuncoesCodigoSQL = String.format("DELETE FROM [%s] WHERE %s = (?);", tablela, campo);
			try {

				this.CriarConexaoDB();

				preparedStatement = connection.prepareStatement(DeletarFuncoesCodigoSQL);
				preparedStatement.setInt(1, codigo);

				LinhasAfetadas = preparedStatement.executeUpdate();

				System.out.println(String.format(
						"public class br.com.hoyler.apps.database.sqlite DeletarCodigos TABELA: [%s] - CAMPO: [%s] - VALOR: [%s] DELETADO: [%s] [OK]",
						tablela, campo, Integer.toString(codigo), LinhasAfetadas));

				retornoBool = true;

			} catch (SQLException ex) {

				System.out.println(String.format(
						"public class br.com.hoyler.apps.database.sqlite DeletarCodigos TABELA: [%s] - CAMPO: [%s] - VALOR: [%s] DELETADO: [%s] [TRY ERRO]\n%s",
						tablela, campo, Integer.toString(codigo), LinhasAfetadas, ex.getMessage()));
			}

		} else {

			System.out.println(String.format(
					"public class br.com.hoyler.apps.database.sqlite DeletarCodigos TABELA: [%s] - CAMPO: [%s] - VALOR: [%s] DELETADO: [%s] [ELSE ERRO]",
					tablela, campo, Integer.toString(codigo), LinhasAfetadas));
		}

		return retornoBool;
	}

	public Boolean DeletarNomes(String tablela, String campo, String nome) {

		Boolean retornoBool = false;

		int LinhasAfetadas = -9999;

		Boolean bancoExiste = (new CheckFile().FileExists(PATCH_FILE));

		if (bancoExiste) {

			String DeletarFuncoesCodigoSQL = String.format("DELETE FROM [%s] WHERE %s = (?);", tablela, campo);
			try {

				this.CriarConexaoDB();

				preparedStatement = connection.prepareStatement(DeletarFuncoesCodigoSQL);
				preparedStatement.setString(1, nome);

				LinhasAfetadas = preparedStatement.executeUpdate();

				System.out.println(String.format(
						"public class br.com.hoyler.apps.database.sqlite DeletarNomes TABELA: [%s] - CAMPO: [%s] - VALOR: [%s] DELETADO: [%s] [OK]",
						tablela, campo, nome, LinhasAfetadas));

				retornoBool = true;

			} catch (SQLException ex) {
				System.out.println(String.format(
						"public class br.com.hoyler.apps.database.sqlite DeletarNomes TABELA: [%s] - CAMPO: [%s] - VALOR: [%s] [TRY ERRO]\n%s",
						tablela, campo, nome));
			}

		} else {
			System.out.println(String.format(
					"public class br.com.hoyler.apps.database.sqlite DeletarNomes TABELA: [%s] - CAMPO: [%s] - VALOR: [%s] DELETADO: [%s] [ELSE ERRO]",
					tablela, campo, nome, LinhasAfetadas));
		}

		return retornoBool;
	}

	public ResultSet ExecutarSQL(String scriptSQL) {

		ResultSet returnResultSet = null;

		this.CriarConexaoDB();

		try {

			preparedStatement = connection.prepareStatement(scriptSQL);

			returnResultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
 
			System.out.println(String.format("public class br.com.hoyler.apps.database.sqlite ExecutarSQL SQL: [%s] [TRY ERRO]", scriptSQL));
		}

		return (returnResultSet);
	}
}
