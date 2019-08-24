package colinf;

import java.awt.FontFormatException;
import java.sql.SQLException;

import javax.swing.JFrame;

public class LoginTela {
	
	Principal frame;
	
	public JFrame getTela(String mat, boolean coo, boolean trai){
		try {
			frame = new Principal(mat, coo, trai);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
		}
		catch (FontFormatException | SQLException e) {
			e.printStackTrace();
		}
		return frame;
	}
	
	public LoginTela(String mat, String car){
		switch(car){
		case "ADM":
			getTela(mat, true, true);
			break;
		case "COO":
			getTela(mat, false, true);
			break;
		case "ALU":
			getTela(mat, false, false);
			break;
		}
		
	}
}
