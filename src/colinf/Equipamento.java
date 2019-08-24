package colinf;

import java.sql.SQLException;
import java.sql.Statement;

public class Equipamento {
	private String patrimonio;
	private String descricao;
	private String marca;
	private String tipo;
	private String lab;
	Statement st;
	ConexaoSQLite conn;
	
	public Equipamento(String patrimonio, String descricao, String marca, String tipo, String lab) throws SQLException{
		conn = new ConexaoSQLite();
		setDescricao(descricao);
		setPatrimonio(patrimonio);
		setMarca(marca);
		setTipo(tipo);
		setLab(lab);
		st = conn.getConexaoSQLite().createStatement();
		st.executeUpdate("insert into equipamento values ('"+getPatrimonio()+"', '"+getDescricao()+"', '"+getMarca()+"', '"+getTipo()+"', 'D', '"+getLab()+"');");
		st.close();
		conn.closeConexao();
	}
	
	public String getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getLab() {
		return lab;
	}
	public void setLab(String lab) {
		this.lab = lab;
	}
	
}