package colinf;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class CadastrarManLab extends JFrame {
	
	ResultSet rs;
	Statement st;
	ConexaoSQLite conn;
	private String tipo;
	PreparedStatement stLab;
	private String matricula;
	private String descricao;
	String[][] dummyData = {};
	String[] columnNames = {"LABORATÓRIO", "QUANTIDADE DE MÁQUINAS"};
	private JTable table = new JTable(dummyData, columnNames);
	
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastrarManLab frame = new CadastrarManLab(null, null, null);
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
	 * @throws FontFormatException 
	 */
	public CadastrarManLab(String mat, String desc, String tipo) throws SQLException, FontFormatException {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTipo(tipo);
		setMatricula(mat);
		setDescricao(desc);
		Font defaultFont = getFont(Font.PLAIN, 15);
		conn = new ConexaoSQLite();
		stLab = conn.getConexaoSQLite().prepareStatement("select * from lab;");
		rs = stLab.executeQuery();
		table.setFont(defaultFont);
		table.setModel(DbUtils.resultSetToTableModel(rs));
		stLab.close();
		rs.close();
		conn.closeConexao();
		setBounds(100, 100, 440, 323);
		getContentPane().setLayout(null);
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon(CadastrarManLab.class.getResource("/util/confirm.png")));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new ManLab(getMatricula(), getDescricao(), getTipo(), table.getValueAt(table.getSelectedRow(), 0).toString());
					dispose();
				}
				catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "NENHUM LABORATÓRIO SELECIONA", "FALHA", JOptionPane.INFORMATION_MESSAGE);
				} 
				catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});
		button.setBounds(324, 236, 100, 36);
		getContentPane().add(button);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 414, 214);
		getContentPane().add(scrollPane);

	}
}
