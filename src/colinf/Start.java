package colinf;

import java.io.IOException;
import javax.swing.UIManager;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;

public class Start {

	public static void main(String[] args) throws IOException {
		try {
			try {
			    UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
			} 
			catch (Exception e) {
			    e.printStackTrace();
			}
			Login l = new Login();
			l.setVisible(true);
			l.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}