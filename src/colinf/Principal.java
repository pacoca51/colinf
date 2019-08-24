package colinf;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import net.proteanit.sql.DbUtils;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import java.awt.Color;
import javax.swing.JMenuItem;

public class Principal extends JFrame {

	ResultSet rs;
	ConexaoSQLite conn;
	PreparedStatement st;
	private String matricula;
	String[][] dummyData = {};
	String[] columnNames = {"DESDE", "DESCRIÇÃO"};
	private JTable table = new JTable(dummyData, columnNames);
	private JPanel contentPane;

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

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
				Principal frame;
				try {
					frame = new Principal(null, true, true);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				}
				catch (FontFormatException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws FontFormatException 
	 * @throws SQLException 
	 */
	public Principal(String matricula, boolean addCoo, boolean booTrai) throws FontFormatException, SQLException {
		conn = new ConexaoSQLite();
		st = conn.getConexaoSQLite().prepareStatement("select * from main;");
		rs = st.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(rs));
		conn.closeConexao();
		Font defaultFont = getFont(Font.PLAIN, 15);
		setMatricula(matricula);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/util/icon.png")));
		setTitle("COLINF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1100, 600);
		setResizable(false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon fundo = new ImageIcon(Principal.class.getResource("/util/background.jpg"));
		Image imageFundo = fundo.getImage();
		JDesktopPane desktopPane = new JDesktopPane(){
			@Override
			public void paintComponent (Graphics g){
				g.drawImage(imageFundo, 0, 0, getWidth(), getHeight(), this);
			}};
			desktopPane.setBounds(0, 0, 1094, 561);
			contentPane.add(desktopPane);
			desktopPane.setLayout(null);

			JMenuBar menuBar = new JMenuBar();
			menuBar.setBounds(0, 0, 794, 80);
			desktopPane.add(menuBar);

			JMenu mnCadastrar = new JMenu("CADASTRAR");
			mnCadastrar.setIcon(new ImageIcon(Principal.class.getResource("/util/addUserBlank.png")));
			mnCadastrar.setForeground(Color.WHITE);
			mnCadastrar.setFont(defaultFont);
			menuBar.add(mnCadastrar);

			JMenuItem mntmCoordenador = new JMenuItem("COORDENADOR");
			mntmCoordenador.setEnabled(addCoo);
			mnCadastrar.add(mntmCoordenador);
			mntmCoordenador.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CadastrarCoord frame;
					try {
						frame = new CadastrarCoord();
						desktopPane.add(frame);
						frame.setVisible(true);
					} catch (FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mntmCoordenador.setFont(defaultFont);

			JMenuItem menuItem = new JMenuItem("TRAINEE");
			menuItem.setEnabled(booTrai);
			mnCadastrar.add(menuItem);
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CadastrarTrainee frame;
					try {
						frame = new CadastrarTrainee();
						desktopPane.add(frame);
						frame.setVisible(true);
					} catch (FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			menuItem.setFont(defaultFont);

			JMenu mnRegistrar = new JMenu("REGISTRAR");
			mnRegistrar.setIcon(new ImageIcon(Principal.class.getResource("/util/form.png")));
			mnRegistrar.setForeground(Color.WHITE);
			mnRegistrar.setFont(defaultFont);
			menuBar.add(mnRegistrar);

			JMenuItem mntmTarefa = new JMenuItem("TAREFA");
			mntmTarefa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CadastrarTarefa frame;
					try {
						frame = new CadastrarTarefa(getMatricula());
						desktopPane.add(frame);
						frame.setVisible(true);
					} catch (FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			JMenuItem mntmEquipamento = new JMenuItem("EQUIPAMENTO");
			mntmEquipamento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CadastrarEquip frame;
					try {
						frame = new CadastrarEquip();
						desktopPane.add(frame);
						frame.setVisible(true);
					} catch (FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mntmEquipamento.setFont(defaultFont);
			mnRegistrar.add(mntmEquipamento);

			JMenuItem mntmLaboratrio = new JMenuItem("LABORAT\u00D3RIO");
			mntmLaboratrio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CadastrarLab frame;
					try {
						frame = new CadastrarLab();
						desktopPane.add(frame);
						frame.setVisible(true);
					} catch (FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mntmLaboratrio.setFont(defaultFont);
			mnRegistrar.add(mntmLaboratrio);
			mntmTarefa.setFont(defaultFont);
			mnRegistrar.add(mntmTarefa);

			JMenu mnListar = new JMenu("LISTAR");
			mnListar.setForeground(Color.WHITE);
			mnListar.setIcon(new ImageIcon(Principal.class.getResource("/util/list.png")));
			mnListar.setFont(defaultFont);
			menuBar.add(mnListar);

			JMenuItem mntmEquipamentoELaboratrio = new JMenuItem("EQUIPAMENTO E LABORAT\u00D3RIO");
			mntmEquipamentoELaboratrio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ListarEquiLab frame;
					try {
						frame = new ListarEquiLab();
						desktopPane.add(frame);
						frame.setVisible(true);
					} catch (SQLException | FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mntmEquipamentoELaboratrio.setFont(defaultFont);
			mnListar.add(mntmEquipamentoELaboratrio);

			JMenuItem mntmTrainee = new JMenuItem("TRAINEE");
			mntmTrainee.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ListarTrainee frame;
					try {
						frame = new ListarTrainee(booTrai);
						desktopPane.add(frame);
						frame.setVisible(true);
					} catch (SQLException | FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			JMenuItem mntmAtividades = new JMenuItem("TAREFA E MANUTENÇÃO");
			mntmAtividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ListarTarMan frame;
					try {
						frame = new ListarTarMan();
						desktopPane.add(frame);
						frame.setVisible(true);
					} catch (SQLException | FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mntmAtividades.setFont(defaultFont);
			mnListar.add(mntmAtividades);
			mntmTrainee.setFont(defaultFont);
			mnListar.add(mntmTrainee);

			JPanel panel = new JPanel();
			panel.setBounds(794, 0, 300, 561);

			desktopPane.add(panel);
			panel.setLayout(null);

			JLabel lblTarefasPendentes = new JLabel("TAREFAS PENDENTES");
			lblTarefasPendentes.setFont(defaultFont);
			lblTarefasPendentes.setBounds(10, 11, 230, 36);
			panel.add(lblTarefasPendentes);

			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(10, 58, 280, 492);
			panel.add(scrollPane);
			JButton btnAtualizar = new JButton();
			btnAtualizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try{
						rs = st.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
						conn.closeConexao();
						rs.close();
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex.toString());
					}
				}
			});
			btnAtualizar.setIcon(new ImageIcon(Principal.class.getResource("/util/refresh.png")));
			btnAtualizar.setBounds(250, 11, 40, 36);
			panel.add(btnAtualizar);
			setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane, desktopPane}));
	}
}