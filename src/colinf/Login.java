package colinf;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import colinf.ConexaoSQLite;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class Login extends JFrame {

	ConexaoSQLite conn;
	LoginTela loginTela;
	private JLabel lblSenha;
	private JTextField login;
	private JButton btnEntrar;
	private JLabel lblMatrcula;
	private JPanel contentPane;
	private JPasswordField password;
	Statement st;
	ResultSet rs;

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
					Login frame = new Login();
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
	public Login() throws SQLException, FontFormatException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/util/icon.png")));
		setTitle("COLINF");
		setResizable(false);
		conn = new ConexaoSQLite();
		Font defaultFont = getFont(Font.PLAIN, 15);
		setBounds(100, 100, 260, 290);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblMatrcula = new JLabel("");
		lblMatrcula.setHorizontalAlignment(SwingConstants.CENTER);
		lblMatrcula.setIcon(new ImageIcon(Login.class.getResource("/util/user.png")));
		lblMatrcula.setBounds(10, 118, 30, 30);
		contentPane.add(lblMatrcula);

		login = new JTextField(13);
		login.setBounds(50, 118, 194, 30);
		login.setFont(defaultFont);
		contentPane.add(login);
		login.setColumns(10);

		lblSenha = new JLabel("");
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setIcon(new ImageIcon(Login.class.getResource("/util/key.png")));
		lblSenha.setBounds(10, 159, 30, 30);
		contentPane.add(lblSenha);

		password = new JPasswordField(16);
		password.setBounds(50, 159, 194, 30);
		password.setFont(defaultFont);
		contentPane.add(password);

		btnEntrar = new JButton("");
		btnEntrar.setIcon(new ImageIcon(Login.class.getResource("/util/enter.png")));
		btnEntrar.setFont(defaultFont);

		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String senha = new String(password.getPassword()).toUpperCase();
				String sql ="select MATRÍCULA, SENHA, CARGO from login where MATRÍCULA = '"+login.getText().toString().toUpperCase()+"' and SENHA = '"+senha.toString().toUpperCase()+"';";
				try {
					st = conn.getConexaoSQLite().createStatement();
					rs = st.executeQuery(sql);
					if(rs.next()){
						loginTela = new LoginTela(rs.getString(1), rs.getString(3));
						conn.closeConexao();
						st.close();
						rs.close();
						dispose();
					}
					else{
						JOptionPane.showMessageDialog(null, "MATRÍCULA OU SENHA INCORRETOS");
						login.setText(null);
						password.setText(null);
						conn.closeConexao();
					}
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEntrar.setBounds(10, 200, 234, 36);
		contentPane.add(btnEntrar);

		JLabel lblLogin = new JLabel("");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setIcon(new ImageIcon(Login.class.getResource("/util/login.png")));
		lblLogin.setBounds(10, 11, 234, 96);
		contentPane.add(lblLogin);
	}
}