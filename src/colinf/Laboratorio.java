package colinf;

import java.sql.SQLException;
import java.sql.Statement;

public class Laboratorio {
	
	Statement st;
	ConexaoSQLite conn;
	private String numlab;
	private String quantmaquinas;

	public String getNumlab() {
		return numlab;
	}

	public void setNumlab(String numlab) {
		this.numlab = numlab;
	}

	public String getQuantmaquinas() {
		return quantmaquinas;
	}

	public void setQuantmaquinas(String quantmaquinas) {
		this.quantmaquinas = quantmaquinas;
	}

	
	public Laboratorio(String numlab, String quantmaquinas) throws SQLException{
		conn = new ConexaoSQLite();
		setNumlab(numlab);
		setQuantmaquinas(quantmaquinas);
		st = conn.getConexaoSQLite().createStatement();
		st.executeUpdate("insert into laboratorio values ('LAB"+getNumlab()+"', "+getQuantmaquinas()+");");
		st.close();
		conn.closeConexao();
		
	}

	

}