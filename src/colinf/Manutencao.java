package colinf;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import colinf.ConexaoSQLite;

public class Manutencao{

	PreparedStatement st;
	ConexaoSQLite conn;
	ResultSet rs;
	String sql;
	
	public Manutencao (String codigo, String descricao, String tipo, String status ) throws IOException {
		
		sql = "insert into tarefa values ("+codigo+",'"+descricao+"',"+tipo+","+status+"');"; 
		conn = new ConexaoSQLite();
		
		try { 
			st = conn.getConexaoSQLite().prepareStatement(sql);
			rs = st.executeQuery();
		} 
		catch (SQLException sqlE) { 
			System.out.println(sqlE.getMessage()); 
		}
	}

	/*@Override
	public boolean autenticaCod(String cod) {
		return cod.length() != 11;
	}

	@Override
	public boolean autenticaTel(String tel) {
		return tel.length() < 8 && tel.length() > 13;	
	}

	@Override
	public boolean autenticaDifTel(String tel1, String tel2) {
		return tel1.equals(tel2);
	}
	 */
}
