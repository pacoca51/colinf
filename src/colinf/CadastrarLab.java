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
import javax.swing.ImageIcon;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastrarLab extends JInternalFrame {

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
	private JTextField lab;
	private NumberField qnt;
	/**
	 * Launch the application.
	 * @throws java.text.ParseException 
	 */
	public static void main(String[] args) throws java.text.ParseException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastrarLab frame = new CadastrarLab();
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
	public CadastrarLab() throws FontFormatException {
		setClosable(true);
		setTitle("REGISTRAR LAB.");
		Font defaultFont = getFont(Font.PLAIN, 15);
		setBounds(100, 100, 400, 240);
		getContentPane().setLayout(null);
		JLabel lblCadastrarTarefa = new JLabel("REGISTRA LAB.");
		lblCadastrarTarefa.setIcon(new ImageIcon(CadastrarLab.class.getResource("/util/lab.png")));
		lblCadastrarTarefa.setFont(defaultFont);
		lblCadastrarTarefa.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastrarTarefa.setBounds(10, 10, 364, 36);
		getContentPane().add(lblCadastrarTarefa);
		
		JLabel lblEmail = new JLabel("QUANT. MAQ.");
		lblEmail.setFont(defaultFont);
		lblEmail.setBounds(10, 98, 108, 30);
		getContentPane().add(lblEmail);
		
		lab = new JTextField();
		lab.setBounds(128, 57, 246, 30);
		lab.setFont(defaultFont);
		getContentPane().add(lab);
		lab.setColumns(10);
		
		JLabel lblMatrcula = new JLabel("LAB.");
		lblMatrcula.setFont(defaultFont);
		lblMatrcula.setBounds(10, 57, 106, 30);
		getContentPane().add(lblMatrcula);
		
		qnt = new NumberField();
		qnt.setColumns(10);
		qnt.setBounds(128, 98, 246, 30);
		qnt.setFont(defaultFont);
		getContentPane().add(qnt);
		
		JButton btnCadastrar = new JButton("");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lab.getText().isEmpty() || qnt.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "CAMPO OBRIGATÓRIO VAZIO!", "ERRO", 2);
				}
				else{
					try {
						new Laboratorio(lab.getText().toUpperCase(), qnt.getText().toUpperCase());
						dispose();
					}
					catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "OPERAÇÃO MAL SUCEDIDA!", "ERRO", JOptionPane.ERROR_MESSAGE);
						dispose();
					}
					catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		});
		btnCadastrar.setIcon(new ImageIcon(CadastrarLab.class.getResource("/util/addTask.png")));
		btnCadastrar.setBounds(10, 144, 364, 36);
		getContentPane().add(btnCadastrar);
		
	}
}