package colinf;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NameField extends JTextField {
    public NameField() {
        this(null);
    }
    public NameField(String text){
        super(text);
        setDocument( new PlainDocument() {
	    @Override
    	    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                for( int i = 0; i < str.length(); i++ )
                    if(!Character.isAlphabetic(str.charAt(i)) && !Character.isSpaceChar(str.charAt(i)))
                        return;
                super.insertString(offs, str, a);
            }
        });
    }
}