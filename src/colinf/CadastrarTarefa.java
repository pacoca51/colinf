package colinf;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FontFormatException;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastrarTarefa extends JInternalFrame {

	private static Font getFont(int style, int size) throws FontFormatException{
		InputStream i;
		Font font, sizedFont = null;
		try {
			i = Principal.class.getResourceAsStream("RobotoLight.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, i);
			sizedFont = font.deriveFont(style, size);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sizedFont;
	}
	ResultSet rs;
	Statement st;
	ConexaoSQLite conn;
	PreparedStatement stLab;
	PreparedStatement stEquip;
	private JLabel lblDesc;
	private JLabel lblTipo;
	private String matricula;
	private JComboBox comboBox;
	private JTextField descricao;
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	/**
	 * Launch the application.
	 * @throws java.text.ParseException 
	 */
	public static void main(String[] args) throws java.text.ParseException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastrarTarefa frame = new CadastrarTarefa(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public CadastrarTarefa(String mat) throws FontFormatException, SQLException {
		setMatricula(mat);
		setClosable(true);
		setTitle("REGISTRAR TAREFA");
		Font defaultFont = getFont(Font.PLAIN, 15);
		setBounds(100, 100, 400, 229);
		getContentPane().setLayout(null);
		JLabel lblCadastrarTarefa = new JLabel("REGISTRAR TAREFA");
		lblCadastrarTarefa.setIcon(new ImageIcon(CadastrarTarefa.class.getResource("/util/task.png")));
		lblCadastrarTarefa.setFont(defaultFont);
		lblCadastrarTarefa.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastrarTarefa.setBounds(10, 11, 364, 36);
		getContentPane().add(lblCadastrarTarefa);
		
		lblTipo = new JLabel("TIPO");
		lblTipo.setFont(defaultFont);
		lblTipo.setBounds(10, 98, 108, 30);
		getContentPane().add(lblTipo);
		
		descricao = new JTextField();
		descricao.setBounds(128, 57, 246, 30);
		descricao.setFont(defaultFont);
		getContentPane().add(descricao);
		descricao.setColumns(10);
		
		lblDesc = new JLabel("DESCRI\u00C7\u00C3O");
		lblDesc.setFont(defaultFont);
		lblDesc.setBounds(10, 58, 106, 30);
		getContentPane().add(lblDesc);
		
		conn = new ConexaoSQLite();
		stEquip = conn.getConexaoSQLite().prepareStatement("select * from equi;");
		
		JButton btnCadastrar = new JButton("");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!descricao.getText().isEmpty()){
					switch(comboBox.getSelectedItem().toString()){
					case "DIVERSOS":
						try {
							new Tarefa(getMatricula(), descricao.getText().toUpperCase(), "D");
							dispose();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
							dispose();
						}
						break;
					case "MANUTENÇÃO EM LABORATÓRIO":
						try{
							stLab = conn.getConexaoSQLite().prepareStatement("select * from lab;");
							rs = stLab.executeQuery();
							if(rs.next()){
								CadastrarManLab frameL;
								try {
									frameL = new CadastrarManLab(getMatricula(), descricao.getText().toUpperCase(), "M");
									frameL.setVisible(true);
									frameL.setLocationRelativeTo(null);
									dispose();
								} catch (SQLException e) {
									JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "NENHUM LABORATÓRIO REGISTRADO!", "FALHA", JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}
							stLab.close();
							rs.close();
							conn.closeConexao();
						}
						catch(Exception e){
							JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "MANUTENÇÃO EM MÁQUINA":
						try{
							stEquip = conn.getConexaoSQLite().prepareStatement("select * from equi;");
							rs = stEquip.executeQuery();
							if(rs.next()){
								CadastrarManEquip frameE;
								try {
									frameE = new CadastrarManEquip(getMatricula(), descricao.getText().toUpperCase(), "M");
									frameE.setVisible(true);
									frameE.setLocationRelativeTo(null);
									dispose();
								} catch (SQLException e) {
									JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "NENHUM EQUIPAMENTO REGISTRADO!", "FALHA", JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}
							stEquip.close();
							rs.close();
							conn.closeConexao();
						}
						catch(Exception e){
							JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
						}
						break;
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "CAMPO VAZIO!", "FALHA", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnCadastrar.setIcon(new ImageIcon(CadastrarTarefa.class.getResource("/util/addTask.png")));
		btnCadastrar.setBounds(10, 139, 364, 36);
		getContentPane().add(btnCadastrar);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"DIVERSOS", "MANUTEN\u00C7\u00C3O EM LABORAT\u00D3RIO", "MANUTEN\u00C7\u00C3O EM M\u00C1QUINA"}));
		comboBox.setBounds(128, 99, 246, 30);
		comboBox.setFont(defaultFont);
		getContentPane().add(comboBox);
	}
}