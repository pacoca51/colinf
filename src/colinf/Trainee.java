package colinf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Trainee {

	Statement st;
	ResultSet rs;
	ConexaoSQLite conn;
	private String nome;
	private String senha;
	private String email;
	private String matricula;
	private String telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Trainee(String matricula, String nome, String email, String telefone, String senha) throws SQLException{
		conn = new ConexaoSQLite();
		setEmail(email);
		setMatricula(matricula);
		setNome(nome);
		setSenha(senha);
		setTelefone(telefone);
		st = conn.getConexaoSQLite().createStatement();
		st.executeUpdate("insert into funcionario values ('"+getMatricula()+"', '"+getNome()+"', 'ALU', '"+getEmail()+"', '"+getTelefone()+"', '"+getSenha()+"')");
		st.close();
		conn.closeConexao();

	}



}