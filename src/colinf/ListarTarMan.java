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

public class ListarTarMan extends JInternalFrame {

	ConexaoSQLite conn;
	private ResultSet rsTar;
	private ResultSet rsMan;
	private PreparedStatement stTar;
	private PreparedStatement stMan;
	Statement st;
	String[][] dummyData = {};
	String[] columnNamesTar = {"CÓDIGO", "DESCRIÇÃO", "STATUS", "CRIADA POR", "FINALIZADA POR"};
	private JTable tableTar = new JTable(dummyData, columnNamesTar);
	String[] columnNamesMan = {"CÓDIGO", "DESCRIÇÃO", "STATUS", "CRIADA POR", "FINALIZADA POR", "LAB.", "EQUIP."};
	private JTable tableMan = new JTable(dummyData, columnNamesMan);


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
					ListarTarMan frame = new ListarTarMan();
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
	public ListarTarMan() throws SQLException, FontFormatException {
		setTitle("TAREFAS");
		setClosable(true);
		conn = new ConexaoSQLite();
		stTar = conn.getConexaoSQLite().prepareStatement("select * from tar;");
		stMan = conn.getConexaoSQLite().prepareStatement("select * from man;");
		st = conn.getConexaoSQLite().createStatement();
		rsTar = stTar.executeQuery();
		rsMan = stMan.executeQuery();
		tableTar.setModel(DbUtils.resultSetToTableModel(rsTar));
		tableMan.setModel(DbUtils.resultSetToTableModel(rsMan));
		Font size12 = getFont(Font.PLAIN, 12);
		tableTar.setFont(size12);
		tableMan.setFont(size12);
		setBounds(100, 100, 800, 446);
		getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(size12);
		tabbedPane.setBounds(10, 11, 764, 384);
		getContentPane().add(tabbedPane);

		JPanel panelTar = new JPanel();
		tabbedPane.addTab("TAREFA", null, panelTar);
		panelTar.setLayout(null);
		JButton btnRemover = new JButton("REMOVER");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					st = conn.getConexaoSQLite().createStatement();
					st.executeUpdate("delete from tarefa where tar_codigo = "+tableTar.getValueAt(tableTar.getSelectedRow(), 0).toString()+";");
					conn.closeConexao();
					JOptionPane.showMessageDialog(null, "TAREFA REMOVIDA COM SUCESSO!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});

		JScrollPane scrollPaneTar = new JScrollPane(tableTar);
		scrollPaneTar.setBounds(10, 11, 739, 286);
		panelTar.add(scrollPaneTar);
		btnRemover.setFont(size12);
		btnRemover.setIcon(new ImageIcon(ListarTarMan.class.getResource("/util/deleteRow.png")));
		btnRemover.setBounds(609, 308, 140, 36);
		panelTar.add(btnRemover);

		JButton btnAtualizarStatus = new JButton("FINALIZAR");
		btnAtualizarStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					try{
						st = conn.getConexaoSQLite().createStatement();
						st.executeUpdate("update tarefa SET tar_status = 'F' where tar_codigo = "+tableTar.getValueAt(tableTar.getSelectedRow(), 0).toString()+" data_termino = CURRENT_TIMESTAMP;");
						conn.closeConexao();
						JOptionPane.showMessageDialog(null, "STATUS ATUALIZADO COM SUCESSO!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
					catch (Exception e) {
						JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
						dispose();
					}
				}
				catch(ArrayIndexOutOfBoundsException aex){
					JOptionPane.showMessageDialog(null, "NENHUMA ATIVIDADE SELECIONADA!", "FALHA", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnAtualizarStatus.setFont(size12);
		btnAtualizarStatus.setIcon(new ImageIcon(ListarTarMan.class.getResource("/util/finishFlag.png")));
		btnAtualizarStatus.setBounds(419, 308, 180, 36);
		panelTar.add(btnAtualizarStatus);

		JPanel panelMan = new JPanel();
		tabbedPane.addTab("MANUTENÇÃO", null, panelMan);
		panelMan.setLayout(null);

		JScrollPane scrollPaneMan = new JScrollPane(tableMan);
		scrollPaneMan.setBounds(10, 11, 739, 286);
		panelMan.add(scrollPaneMan);

		JButton btnRemoverMan = new JButton("REMOVER");
		btnRemoverMan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					st = conn.getConexaoSQLite().createStatement();
					st.executeUpdate("delete from tarefa where tar_codigo = "+tableTar.getValueAt(tableTar.getSelectedRow(), 0).toString()+";");
					conn.closeConexao();
					JOptionPane.showMessageDialog(null, "MANUTENÇÃO REMOVIDA COM SUCESSO!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});
		btnRemoverMan.setIcon(new ImageIcon(ListarTarMan.class.getResource("/util/deleteRow.png")));
		btnRemoverMan.setFont(size12);
		btnRemoverMan.setBounds(609, 308, 140, 36);
		panelMan.add(btnRemoverMan);

		JButton btnFinalizarMan = new JButton("FINALIZAR");
		btnFinalizarMan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					st = conn.getConexaoSQLite().createStatement();
					st.executeUpdate("update tarefa SET tar_status = 'F' where tar_codigo = "+tableTar.getValueAt(tableTar.getSelectedRow(), 0).toString()+";");
					st.close();
					conn.closeConexao();
					JOptionPane.showMessageDialog(null, "STATUS ATUALIZADO COM SUCESSO!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});
		btnFinalizarMan.setIcon(new ImageIcon(ListarTarMan.class.getResource("/util/finishFlag.png")));
		btnFinalizarMan.setFont(size12);
		btnFinalizarMan.setBounds(419, 308, 180, 36);
		panelMan.add(btnFinalizarMan);

	}
}