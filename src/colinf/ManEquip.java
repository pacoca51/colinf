package colinf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManEquip{
	
	Statement st;
	ResultSet rs;
	ConexaoSQLite conn;
	private String tipo;
	private String equip;
	private String matricula;
	private String descricao;
	private ResultSet rsT;
	
	public String getEquip() {
		return equip;
	}
	public void setEquip(String equip) {
		this.equip = equip;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public ManEquip(String mat, String desc, String tipo, String equip) throws SQLException {
		conn = new ConexaoSQLite();
		setDescricao(desc);
		setEquip(equip);
		setMatricula(mat);
		setTipo(tipo);
		st = conn.getConexaoSQLite().createStatement();
		st.executeUpdate("insert into realizada (equi_codigo) values ('"+getEquip()+"')");
		rs = st.executeQuery("select rea_codigo from realizada order by rea_codigo DESC");
		if(rs.next()){
			st.executeUpdate("insert into tarefa (tar_descricao, tar_tipo, tar_status, rea_codigo, data_criacao) values ('"+getDescricao()+"','"+getTipo()+"','P', "+rs.getObject(1).toString()+", CURRENT_TIMESTAMP)");
			rsT = st.executeQuery("select tar_codigo from tarefa order by tar_codigo DESC");
			if(rsT.next()){
				st.executeUpdate("insert into faz (tar_codigo, fun_criacao) values ("+rsT.getObject(1).toString()+", '"+getMatricula()+"')");
			}
			st.close();
			rs.close();
			rsT.close();
		}
		conn.closeConexao();
	}
}