package br.com.hoyler.apps.database.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import org.sqlite.SQLiteConfig;
import java.sql.PreparedStatement;
import br.com.hoyler.apps.tools.CheckFile;

public class Database {

	public Database() {
		
	}

	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private final String BANCO_DE_DADOS_ARQUIVO = ("ImprimePonto.s3db");
	private final String BANCO_DE_DADOS_CAMINHO = ("ImprimePonto/Database/");

	public final String TABELA_FUNCOES = ("Funcoes");
	public final String TABELA_EMPRESAS = ("Empresas");
	public final String TABELA_PESSOAS = ("Pessoas");
	public final String CAMPO_CODIGO = ("CODIGO");
	public final String CAMPO_NOME = ("NOME");
	public final String CAMPO_CNPJ = ("CNPJ");
	public final String CAMPO_CTPS = ("CTPS");
	public final String CAMPO_ENDERECO = ("ENDERECO");
	public final String CAMPO_ADMISSAO = ("ADMISSAO");
	public final String CAMPO_FUNCAO_CODIGO = ("FUNCAO_CODIGO");
	public final String CAMPO_EMPRESA_CODIGO = ("EMPRESA_CODIGO");

	public final String getBANCO_DE_DADOS_ARQUIVO() {
		return BANCO_DE_DADOS_ARQUIVO;
	}

	public final String getBANCO_DE_DADOS_CAMINHO() {
		return BANCO_DE_DADOS_CAMINHO;
	}

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

		Boolean ARQUIVO_EXISTE = (new CheckFile().FileExists(BANCO_DE_DADOS_CAMINHO + BANCO_DE_DADOS_ARQUIVO));

		if (ARQUIVO_EXISTE) {

			String DatabaseURL = String.format("%s%s%s", "jdbc:sqlite:", BANCO_DE_DADOS_CAMINHO,
					BANCO_DE_DADOS_ARQUIVO);

			String DRIVER = "org.sqlite.JDBC";

			try {

				Class.forName(DRIVER);

				SQLiteConfig sqLiteConfig = new SQLiteConfig();
				sqLiteConfig.setBusyTimeout("15000");
				sqLiteConfig.enforceForeignKeys(true);

				connection = DriverManager.getConnection(DatabaseURL, sqLiteConfig.toProperties());

			} catch (ClassNotFoundException e) {

				System.out.println(String.format(
						"public class br.com.hoyler.apps.database.sqlite CriarConexaoDB DRIVER: [%s] [ClassNotFoundException TRY ERRO]",
						DRIVER));

			} catch (SQLException e) {

				System.out.println(String.format(
						"public class br.com.hoyler.apps.database.sqlite CriarConexaoDB DB_URL: [%s] [SQLException TRY ERRO]",
						DatabaseURL));
			}

		} else {
			System.out.println(String.format(
					"public class br.com.hoyler.apps.database.sqlite CriarConexaoDB BANCO_EXISTE: [%s] [ELSE ERRO]",
					ARQUIVO_EXISTE.toString()));

		}

	}

	public void ConexaoDBClose() throws SQLException {

		if (connection.isClosed()) {

			connection = null;
			preparedStatement = null;
			System.out.println("ConexaoDBClose SET NULL [OK]\n");
		} else {
			connection.close();
			preparedStatement.close();
			ConexaoDBClose();
			System.out.println("ConexaoDBClose IS CLOSE [OK]\n");
		}
	}

	public Boolean DeletarCodigos(String tablela, String campo, int codigo) {

		Boolean returnBool = false;

		int LinhasAfetadas = -9999;

		String DeletarFuncoesCodigoSQL = String.format("DELETE FROM [%s] WHERE %s = (?);", tablela, campo);
		try {

			this.CriarConexaoDB();

			preparedStatement = connection.prepareStatement(DeletarFuncoesCodigoSQL);
			preparedStatement.setInt(1, codigo);

			LinhasAfetadas = preparedStatement.executeUpdate();

			System.out.println(String.format(
					"public class br.com.hoyler.apps.database.sqlite DeletarCodigos TABELA: [%s] - CAMPO: [%s] - VALOR: [%s] DELETADO: [%s] [OK]",
					tablela, campo, Integer.toString(codigo), LinhasAfetadas));

			returnBool = true;

		} catch (SQLException ex) {

			System.out.println(String.format(
					"public class br.com.hoyler.apps.database.sqlite DeletarCodigos TABELA: [%s] - CAMPO: [%s] - VALOR: [%s] DELETADO: [%s] [SQLException TRY ERRO]\n%s",
					tablela, campo, Integer.toString(codigo), LinhasAfetadas, ex.getMessage()));
		}
		return returnBool;
	}

	public Boolean DeletarNomes(String tablela, String campo, String nome) {

		Boolean returnBool = false;

		int LinhasAfetadas = -9999;

		String DeletarFuncoesCodigoSQL = String.format("DELETE FROM [%s] WHERE %s = (?);", tablela, campo);
		try {

			this.CriarConexaoDB();

			preparedStatement = connection.prepareStatement(DeletarFuncoesCodigoSQL);
			preparedStatement.setString(1, nome);

			LinhasAfetadas = preparedStatement.executeUpdate();

			System.out.println(String.format(
					"public class br.com.hoyler.apps.database.sqlite DeletarNomes TABELA: [%s] - CAMPO: [%s] - VALOR: [%s] DELETADO: [%s] [OK]",
					tablela, campo, nome, LinhasAfetadas));

			returnBool = true;

		} catch (SQLException ex) {
			System.out.println(String.format(
					"public class br.com.hoyler.apps.database.sqlite DeletarNomes TABELA: [%s] - CAMPO: [%s] - VALOR: [%s] [SQLException TRY ERRO]\n%s",
					tablela, campo, nome));
		}

		return returnBool;
	}

	public ResultSet ExecutarSQL(String scriptSQL) {

		ResultSet returnResultSet = null;

		this.CriarConexaoDB();

		try {

			preparedStatement = connection.prepareStatement(scriptSQL);

			returnResultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {

			System.out.println(String.format(
					"public class br.com.hoyler.apps.database.sqlite ExecutarSQL SQL: [%s] [SQLException TRY ERRO]", scriptSQL));
		}

		return (returnResultSet);
	}
}
