package de.saremox.swing;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class JNumberField extends JTextField
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6286786630441804971L;
	
	public JNumberField(int cols)
	{
		super(cols);
	}

	

	protected Document createDefaultModel()
	{
		return new NumberDocument();
	}
	
	public int getValue()
	{
		if(getText().equals(""))
		{
			return 0;
		}
		try
		{
			return Integer.parseInt(getText());
		}
		catch(NumberFormatException e)
		{
			return -1;
		}
	}

	static class NumberDocument extends PlainDocument
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1594158457704096352L;
		
		
		public NumberDocument()
		{
			super();
		}
		
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException
		{
			if (str == null)
			{
				return;
			}
			char[] numbers = str.toCharArray();
			int lenght = numbers.length;
			for (int i = 0; i < numbers.length; i++)
			{
				if(!Character.isDigit(numbers[i]))
				{
					for (int copy = 1;copy < numbers.length-1; copy++)
					{
						numbers[copy] = numbers[copy+1];
					}
					numbers[numbers.length-1] = 0x00;
					lenght--;
				}
				
			}
			
			super.insertString(offs, new String(numbers, 0, lenght), a);
		}
		
	}
}
