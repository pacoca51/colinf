
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

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CadastrarEquip extends JInternalFrame {

	ResultSet rs;
	ConexaoSQLite conn;
	PreparedStatement stLab;
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
	private NameField nome;
	private JTextField email;
	private JComboBox comboBox;
	private JTextField codigo;
	/**
	 * Launch the application.
	 * @throws java.text.ParseException 
	 */
	public static void main(String[] args) throws java.text.ParseException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastrarEquip frame = new CadastrarEquip();
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
	public CadastrarEquip() throws FontFormatException, SQLException {
		setClosable(true);
		setTitle("REGISTRAR EQUIPAMENTO");
		Font defaultFont = getFont(Font.PLAIN, 15);
		setBounds(100, 100, 400, 520);
		conn = new ConexaoSQLite();
		stLab = conn.getConexaoSQLite().prepareStatement("select * from lab;");
		rs = stLab.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(rs));
		table.setFont(defaultFont);
		stLab.close();
		rs.close();
		conn.closeConexao();
		getContentPane().setLayout(null);
		JLabel lblCadastrarCoord = new JLabel("REGISTRAR EQUIPAMENTO");
		lblCadastrarCoord.setIcon(new ImageIcon(CadastrarEquip.class.getResource("/util/equip.png")));
		lblCadastrarCoord.setFont(defaultFont);
		lblCadastrarCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastrarCoord.setBounds(10, 10, 364, 36);
		getContentPane().add(lblCadastrarCoord);

		JLabel lblNome = new JLabel("DESCRI\u00C7\u00C3O");
		lblNome.setFont(defaultFont);
		lblNome.setBounds(10, 98, 108, 30);
		getContentPane().add(lblNome);

		nome = new NameField();
		nome.setBounds(128, 98, 246, 30);
		nome.setFont(defaultFont);
		getContentPane().add(nome);
		nome.setColumns(10);

		JLabel lblEmail = new JLabel("MARCA");
		lblEmail.setFont(defaultFont);
		lblEmail.setBounds(10, 139, 108, 30);
		getContentPane().add(lblEmail);

		email = new JTextField();
		email.setBounds(128, 139, 246, 30);
		email.setFont(defaultFont);
		getContentPane().add(email);
		email.setColumns(10);

		codigo = new JTextField();
		codigo.setBounds(128, 57, 246, 30);
		codigo.setFont(defaultFont);
		getContentPane().add(codigo);
		codigo.setColumns(10);

		JLabel lblMatrcula = new JLabel("PATRIM\u00D4NIO");
		lblMatrcula.setFont(defaultFont);
		lblMatrcula.setBounds(10, 57, 106, 30);
		getContentPane().add(lblMatrcula);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"AR CONDICIONADO", "CADEIRA", "COMPUTADOR", "ESTABILIZADOR", "MESA", "NOBREAK", "NOTEBOOK", "PROJETOR", "TELA PROJE\u00C7\u00C3O"}));
		comboBox.setBounds(128, 180, 246, 30);
		comboBox.setFont(defaultFont);
		getContentPane().add(comboBox);

		JLabel lblSenha = new JLabel("TIPO");
		lblSenha.setFont(defaultFont);
		lblSenha.setBounds(10, 180, 108, 30);
		getContentPane().add(lblSenha);

		JButton btnCadastrar = new JButton("");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(codigo.getText().isEmpty() || nome.getText().isEmpty() || email.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "CAMPO OBRIGATÓRIO VAZIO!", "ERRO", 2);
				}
				else{
					try {
						new Equipamento(codigo.getText().toString().toUpperCase(), nome.getText().toString().toUpperCase(), email.getText().toString().toUpperCase(), comboBox.getSelectedItem().toString().toUpperCase(), table.getValueAt(table.getSelectedRow(), 0).toString().toUpperCase());
						dispose();
					}
					catch(ArrayIndexOutOfBoundsException aex){
						JOptionPane.showMessageDialog(null, "NENHUM LAB. SELECIONADO!", "FALHA", JOptionPane.INFORMATION_MESSAGE);
					}
					catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
						dispose();
					}
				}
			}
		});
		btnCadastrar.setIcon(new ImageIcon(CadastrarEquip.class.getResource("/util/addTask.png")));
		btnCadastrar.setBounds(10, 425, 364, 36);
		getContentPane().add(btnCadastrar);

		JLabel lblLab = new JLabel("LAB.");
		lblLab.setFont(defaultFont);
		lblLab.setBounds(10, 221, 46, 30);
		getContentPane().add(lblLab);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 262, 364, 152);
		getContentPane().add(scrollPane);

	}
}