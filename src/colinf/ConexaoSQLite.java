package colinf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {

	public ConexaoSQLite() {

	}
	
	public java.sql.Connection getConexaoSQLite() {
		Connection conn = null;
        try {
            String url = "jdbc:sqlite:/colinf.sqlite3";
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return conn;
	}
	public boolean closeConexao() {
		try {
			getConexaoSQLite().close();
			return true;
		}
		catch (SQLException e) {
			return false;
		}
	}
}