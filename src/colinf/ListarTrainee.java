package colinf;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListarTrainee extends JInternalFrame {
	
	ResultSet rsTrainee, rs;
	ConexaoSQLite conn;
	Statement st;
	PreparedStatement stTrainee;
	String[][] dummyData = {};
	String[] columnNames = {"MATRÍCULA", "NOME", "EMAIL"};
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListarTrainee frame = new ListarTrainee(true);
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
	public ListarTrainee(boolean removeTrai) throws SQLException, FontFormatException {
		setTitle("TRAINEE");
		setClosable(true);
		conn = new ConexaoSQLite();
		stTrainee = conn.getConexaoSQLite().prepareStatement("select * from trainee;");
		rsTrainee = stTrainee.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(rsTrainee));
		Font size12 = getFont(Font.PLAIN, 12);
		table.setFont(size12);
		setBounds(100, 100, 800, 440);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 764, 333);
		getContentPane().add(scrollPane);
		
		JButton button = new JButton("REMOVER");
		button.setEnabled(removeTrai);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					st = conn.getConexaoSQLite().createStatement();
					st.executeUpdate("delete from funcionario where fun_matricula = '"+table.getValueAt(table.getSelectedRow(), 0).toString()+"';");
					conn.closeConexao();
					JOptionPane.showMessageDialog(null, "TRAINEE REMOVIDO COM SUCESSO!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
				
			}
		});
		button.setIcon(new ImageIcon(ListarEquiLab.class.getResource("/util/deleteRow.png")));
		button.setFont(size12);
		button.setBounds(634, 355, 140, 36);
		getContentPane().add(button);
	}
}
