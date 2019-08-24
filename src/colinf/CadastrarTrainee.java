package colinf;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FontFormatException;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastrarTrainee extends JInternalFrame {

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
	private JTextField matricula;
	private JPasswordField passwordField;
	private JFormattedTextField telefone;
	private MaskFormatter mask(String mascara){ 
		MaskFormatter mask = null;
		try{ 
			mask = new MaskFormatter(mascara); 
			}catch(java.text.ParseException ex){
				
			} 
		return mask; 
		}
	/**
	 * Launch the application.
	 * @throws java.text.ParseException 
	 */
	public static void main(String[] args) throws java.text.ParseException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastrarTrainee frame = new CadastrarTrainee();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public CadastrarTrainee() throws FontFormatException {
		setClosable(true);
		setTitle("CADASTRAR TRAINEE");
		Font defaultFont = getFont(Font.PLAIN, 15);
		setBounds(100, 100, 400, 350);
		getContentPane().setLayout(null);
		JLabel lblCadastrarTrainee = new JLabel("CADASTRAR TRAINEE");
		lblCadastrarTrainee.setIcon(new ImageIcon(CadastrarTrainee.class.getResource("/util/training.png")));
		lblCadastrarTrainee.setFont(defaultFont);
		lblCadastrarTrainee.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastrarTrainee.setBounds(10, 10, 364, 36);
		getContentPane().add(lblCadastrarTrainee);
		
		JLabel lblNome = new JLabel("NOME");
		lblNome.setFont(defaultFont);
		lblNome.setBounds(10, 98, 108, 30);
		getContentPane().add(lblNome);
		
		nome = new NameField();
		nome.setBounds(128, 98, 246, 30);
		nome.setFont(defaultFont);
		getContentPane().add(nome);
		nome.setColumns(10);
		
		JLabel lblTelefone1 = new JLabel("TELELEFONE");
		lblTelefone1.setFont(defaultFont);
		lblTelefone1.setBounds(10, 180, 108, 30);
		getContentPane().add(lblTelefone1);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setFont(defaultFont);
		lblEmail.setBounds(10, 139, 108, 30);
		getContentPane().add(lblEmail);
		
		email = new JTextField();
		email.setBounds(128, 139, 246, 30);
		email.setFont(defaultFont);
		getContentPane().add(email);
		email.setColumns(10);
		
		matricula = new JTextField();
		matricula.setBounds(128, 57, 246, 30);
		matricula.setFont(defaultFont);
		getContentPane().add(matricula);
		matricula.setColumns(10);
		
		JLabel lblMatrcula = new JLabel("MATR\u00CDCULA");
		lblMatrcula.setFont(defaultFont);
		lblMatrcula.setBounds(10, 57, 106, 30);
		getContentPane().add(lblMatrcula);
		
		telefone = new JFormattedTextField(mask("(##)#####-####"));
		telefone.setColumns(10);
		telefone.setFont(defaultFont);
		telefone.setBounds(128, 180, 246, 30);
		getContentPane().add(telefone);
		
		JLabel lblSenha = new JLabel("SENHA");
		lblSenha.setFont(defaultFont);
		lblSenha.setBounds(10, 221, 108, 30);
		getContentPane().add(lblSenha);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(128, 221, 246, 30);
		passwordField.setFont(defaultFont);
		getContentPane().add(passwordField);
		
		JButton btnCadastrar = new JButton("");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pass = new String(passwordField.getPassword());
				if(matricula.getText().isEmpty() || nome.getText().isEmpty() || pass.isEmpty()){
					JOptionPane.showMessageDialog(null, "CAMPO OBRIGATÓRIO VAZIO!", "ERRO", 2);
				}
				else{
					try {
						new Trainee(matricula.getText().toString().toUpperCase(), nome.getText().toString().toUpperCase(), email.getText().toString().toUpperCase(), telefone.getText().toString().toUpperCase(), pass.toUpperCase());
						dispose();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnCadastrar.setIcon(new ImageIcon(CadastrarTrainee.class.getResource("/util/addTraining.png")));
		btnCadastrar.setBounds(10, 262, 364, 36);
		getContentPane().add(btnCadastrar);
	}
}
