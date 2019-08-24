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

public class ListarEquiLab extends JInternalFrame {

	ResultSet rsEquip, rsLab, rs;
	ConexaoSQLite conn;
	Statement st;
	PreparedStatement stEquip, stLab;
	String[][] dummyData = {};
	String[] columnNamesEquip = {"CÓDIGO", "DESCRIÇÃO", "LAB.", "TIPO", "STATUS"};
	private JTable tableEquip = new JTable(dummyData, columnNamesEquip);
	String[] columnNamesLab = {"LABORATÓRIO", "QUANTIDADE DE MÁQUINAS"};
	private JTable tableLab = new JTable(dummyData, columnNamesLab);
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
					ListarEquiLab frame = new ListarEquiLab();
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
	public ListarEquiLab() throws SQLException, FontFormatException {
		setTitle("EQUIPAMENTOS E LABORAT\u00D3RIOS");
		setClosable(true);
		conn = new ConexaoSQLite();
		stEquip = conn.getConexaoSQLite().prepareStatement("select * from equi;");
		stLab = conn.getConexaoSQLite().prepareStatement("select * from lab;");
		rsEquip = stEquip.executeQuery();
		rsLab = stLab.executeQuery();
		tableEquip.setModel(DbUtils.resultSetToTableModel(rsEquip));
		tableLab.setModel(DbUtils.resultSetToTableModel(rsLab));
		stEquip.close();
		stLab.close();
		st.close();
		rsEquip.close();
		rsLab.close();
		conn.closeConexao();
		Font size12 = getFont(Font.PLAIN, 12);
		tableEquip.setFont(size12);
		tableLab.setFont(size12);
		setBounds(100, 100, 800, 446);
		getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(size12);
		tabbedPane.setBounds(10, 11, 764, 384);
		getContentPane().add(tabbedPane);

		JPanel panelEquip = new JPanel();
		tabbedPane.addTab("EQUIPAMENTO", null, panelEquip);
		panelEquip.setLayout(null);
		JButton btnRemover = new JButton("REMOVER");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					st = conn.getConexaoSQLite().createStatement();
					st.executeUpdate("delete from equipamento where equi_codigo = '"+tableEquip.getValueAt(tableEquip.getSelectedRow(), 0).toString()+"';");
					st.close();
					conn.closeConexao();
					JOptionPane.showMessageDialog(null, "EQUIPAMENTO REMOVIDO COM SUCESSO!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});

		JScrollPane scrollPaneEquip = new JScrollPane(tableEquip);
		scrollPaneEquip.setBounds(10, 11, 739, 286);
		panelEquip.add(scrollPaneEquip);
		btnRemover.setFont(size12);
		btnRemover.setIcon(new ImageIcon(ListarEquiLab.class.getResource("/util/deleteRow.png")));
		btnRemover.setBounds(609, 308, 140, 36);
		panelEquip.add(btnRemover);
		
		JButton btnAtualizarStatus = new JButton("ATUALIZAR STATUS");
		btnAtualizarStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					st = conn.getConexaoSQLite().createStatement();
					st.executeUpdate("update equipamento set equi_status = 'D' where equi_codigo = '"+tableEquip.getValueAt(tableEquip.getSelectedRow(), 0).toString()+"';");
					st.close();
					conn.closeConexao();
					JOptionPane.showMessageDialog(null, "STATUS ATUALIZADO COM SUCESSO!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});
		btnAtualizarStatus.setIcon(new ImageIcon(ListarEquiLab.class.getResource("/util/refresh.png")));
		btnAtualizarStatus.setFont(size12);
		btnAtualizarStatus.setBounds(404, 308, 195, 36);
		panelEquip.add(btnAtualizarStatus);

		JPanel panelLab = new JPanel();
		tabbedPane.addTab("LABORATÓRIO", null, panelLab);
		panelLab.setLayout(null);

		JScrollPane scrollPaneLab = new JScrollPane(tableLab);
		scrollPaneLab.setBounds(10, 11, 739, 333);
		panelLab.add(scrollPaneLab);

	}
}