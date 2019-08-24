package colinf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Tarefa {
	
	Statement st;
	ResultSet rs;
	ConexaoSQLite conn;

	public Tarefa(String mat, String desc, String tipo) throws SQLException{
		conn = new ConexaoSQLite();
		st = conn.getConexaoSQLite().createStatement();
		st.executeUpdate("insert into tarefa (tar_descricao, tar_tipo, tar_status, data_criacao) values ('"+desc+"','"+tipo+"','P', CURRENT_TIMESTAMP)");
		rs = st.executeQuery("select tar_codigo from tarefa order by tar_codigo DESC;");
		if(rs.next()){
			st.executeUpdate("insert into faz (tar_codigo, fun_criacao) values ("+rs.getObject(1).toString()+", '"+mat+"')");
			st.close();
			rs.close();
		}
		conn.closeConexao();
	}
}