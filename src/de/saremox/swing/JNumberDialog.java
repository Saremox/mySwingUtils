package de.saremox.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Dialog that only let you click OK if the Entered Number is between minInt and
 * maxInt
 * 
 * @author Saremox <saremox@linux.com>
 * 
 */
public class JNumberDialog extends JDialog
{
	/**
	 * Button Listener for our Cancel Button
	 * Sets success and visible to false
	 * 
	 * @author Saremox <saremox@linux.com>
	 *
	 */
	private class cancelListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			success = false;
			setVisible(false);
		}
	}

	/**
	 * This Document Listener is triggering the calculation for difference
	 * Whenever something has been added/removed/changed in our JNumberField
	 * @author Saremox <saremox@linux.com>
	 *
	 */
	private class inputFieldListener implements DocumentListener, KeyListener
	{
		@Override
		public void changedUpdate(DocumentEvent arg0)
		{
			updateValues();
		}

		@Override
		public void insertUpdate(DocumentEvent arg0)
		{
			updateValues();
		}

		@Override
		public void removeUpdate(DocumentEvent arg0)
		{
			updateValues();
		}

		@Override
		public void keyPressed(KeyEvent arg0)
		{
			checkForEnter(arg0);
		}

		@Override
		public void keyReleased(KeyEvent arg0)
		{
			checkForEnter(arg0);
		}

		@Override
		public void keyTyped(KeyEvent arg0)
		{
			checkForEnter(arg0);
		}
		
		public void checkForEnter(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if (inputField.getValue() >= getMinInt()
						&& inputField.getValue() <= getMaxInt())
				{
					setModal(false);
					setVisible(false);
				}
			}
		}

	}

	/**
	 * OK button Listener. This Button should only be clickable than the entered
	 * Value is between minInt - maxInt
	 * @author  Saremox <saremox@linux.com>
	 *
	 */
	private class okButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (inputField.getValue() >= getMinInt()
					&& inputField.getValue() <= getMaxInt())
			{
				setModal(false);
				setVisible(false);
			}
		}
	}

	private String msg;
	private int minInt, maxInt, visibleRows;
	private JNumberField inputField;
	private JTextField minField, maxField, diffField;
	private JLabel minMsg, maxMsg, diffMsg, inputMsg;

	private JButton okButton, cancelButton;

	private boolean success;
	private GridLayout myGridLayout;
	private Component _parentComponent;

	private static final long serialVersionUID = 2891341810952787988L;
	
	/**
	 * Creates a default JNumberDialog without customization
	 * Title: Input A Number
	 * inputMsg: Input:
	 * Integer between Integer.MIN_VALUE, Integer.MAX_VALUE
	 */
	public JNumberDialog(Frame parent)
	{
		this("Input a Number");
	}

	/**
	 * Creates a default JNumberDialog
	 * Title: Input A Number
	 * inputMsg: Input:
	 * @param minInt that should be written
	 * @param maxInt that should be written
	 */
	public JNumberDialog(int minInt, int maxInt)
	{
		this("Input a Number", "Input:", minInt, maxInt);
	}


	/**
	 * Creates a default JNumberDialog
	 * inputMsg: Input:
	 * @param title Title of the Dialog Window
	 */
	public JNumberDialog(String title)
	{
		this(title, "Input:");
	}

	/**
	 * Creates a default JNumberDialog
	 * @param title Title of the Dialog Window
	 * @param Labelmsg for the JNumberField
	 */
	public JNumberDialog(String title, String msg)
	{
		this(title, msg, Integer.MIN_VALUE);
	}
	
	public JNumberDialog(String title, String msg, int minInt)
	{
		this(title, msg, minInt, Integer.MAX_VALUE);
	}

	public JNumberDialog(String title, String msg, int minInt, int maxInt)
	{
		this(null,title,msg,minInt,maxInt);
	}
	
	/**
	 * 
	 * @param title Title of the Dialog Window
	 * @param Labelmsg for the JNumberField
	 * @param minInt that should be written
	 * @param maxInt that should be written
	 */
	public JNumberDialog(Frame parent,String title, String msg, int minInt, int maxInt)
	{
		// Initializing fields
		super(parent);
		
		this.getRootPane().setDoubleBuffered(true);
		this.setTitle(title);
		this.msg = msg;
		this.minInt = minInt;
		this.maxInt = maxInt;
		this.inputField = new JNumberField(6);
		this.minField = new JTextField(6);
		this.maxField = new JTextField(6);
		this.diffField = new JTextField(6);
		this.minMsg = new JLabel("Minimal NumberInput");
		this.maxMsg = new JLabel("Maximal NumberInput");
		this.diffMsg = new JLabel("Diffrence");
		this.inputMsg = new JLabel(msg);
		this.okButton = new JButton("OK");
		this.cancelButton = new JButton("Cancel");

		// Setting Values
		this.minField.setText(Integer.toString(minInt));
		this.maxField.setText(Integer.toString(maxInt));
		this.diffField.setText(Integer.toString(0));
		this.okButton.addActionListener(new okButtonListener());
		this.okButton.setEnabled(false);
		this.cancelButton.addActionListener(new cancelListener());
		this.inputField.getDocument().addDocumentListener(
				new inputFieldListener());
		this.inputField.addKeyListener(new inputFieldListener());
		this.minMsg.setHorizontalAlignment(JLabel.RIGHT);
		this.maxMsg.setHorizontalAlignment(JLabel.RIGHT);
		this.inputMsg.setHorizontalAlignment(JLabel.RIGHT);
		this.diffMsg.setHorizontalAlignment(JLabel.RIGHT);
		visibleRows = 5;
		
		// Some Important stuff
		this.diffMsg.setLabelFor(diffField);
		this.minMsg.setLabelFor(minField);
		this.maxMsg.setLabelFor(maxField);
		this.inputMsg.setLabelFor(inputField);

		// Setup textfields
		this.minField.setEditable(false);
		this.maxField.setEditable(false);
		this.diffField.setEditable(false);

		// Do Layouting
		myGridLayout = new GridLayout(visibleRows, 2, 5, 10);
		this.setLayout(myGridLayout);
		this.add(minMsg, 0);
		this.add(minField, 1);
		this.add(maxMsg, 2);
		this.add(maxField, 3);
		this.add(inputMsg, 4);
		this.add(inputField, 5);
		this.add(diffMsg, 6);
		this.add(diffField, 7);
		this.add(okButton, 8);
		this.add(cancelButton, 9);
		
		this.rootPane.setLayout(new BorderLayout());
		this.rootPane.setBackground(this.getContentPane().getBackground());
		this.rootPane.add(this.getContentPane(),BorderLayout.CENTER);
		this.rootPane.add(Box.createHorizontalStrut(10),BorderLayout.WEST);
		this.rootPane.add(Box.createHorizontalStrut(10),BorderLayout.EAST);
		this.rootPane.add(Box.createVerticalStrut(10),BorderLayout.NORTH);
		this.rootPane.add(Box.createVerticalStrut(10),BorderLayout.SOUTH);

		this.setFontSize(18.0f);
		
		this.pack();
	}

	/**
	 * @return the maximum integer that can be written
	 */
	public int getMaxInt()
	{
		return maxInt;
	}

	/**
	 * @return the minimum Integer that can be written
	 */
	public int getMinInt()
	{
		return minInt;
	}

	/**
	 * @return the JLabel text thats describes the JNumberField
	 */
	public String getMsg()
	{
		return msg;
	}

	/**
	 * @return the current Integer written in JNumberField
	 */
	public int getValue()
	{
		return inputField.getValue();
	}

	public void setDiffMsg(String msg)
	{
		this.diffMsg.setText(msg);
	}

	public void setDiffVisible(boolean bool)
	{
		if (!bool && diffField.isVisible() && diffMsg.isVisible())
		{
			this.remove(diffField);
			this.remove(diffMsg);
			this.diffField.setVisible(false);
			this.diffMsg.setVisible(false);

			visibleRows--;
		} else if (!(diffField.isVisible() && diffMsg.isVisible()))
		{
			this.add(diffMsg, 6);
			this.add(diffField, 7);
			this.diffField.setVisible(true);
			this.diffMsg.setVisible(true);

			visibleRows++;
		}
		myGridLayout.setRows(visibleRows);
		this.pack();
	}

	public void setFontSize(float size)
	{
		Font newFont = new Font("Dialog", Font.BOLD, (int) size);
		System.out.println(newFont);
		this.setFont(newFont);
		for (Component comp : this.getRootPane().getContentPane().getComponents())
		{
			comp.setFont(newFont);
		}
		this.pack();
		this.repaint();
	}

	public void setMaxInt(int maxInt)
	{
		this.maxInt = maxInt;
	}

	public void setMaxMsg(String msg)
	{
		this.maxMsg.setText(msg);
	}

	public void setMaxVisible(boolean bool)
	{
		if (!bool && maxField.isVisible() && maxMsg.isVisible())
		{
			this.remove(maxField);
			this.remove(maxMsg);
			this.maxField.setVisible(false);
			this.maxMsg.setVisible(false);

			visibleRows--;
		} else if (!(maxField.isVisible() && maxMsg.isVisible()))
		{
			this.add(maxMsg, 2);
			this.add(maxField, 3);
			this.maxField.setVisible(true);
			this.maxMsg.setVisible(true);

			visibleRows++;
		}
		myGridLayout.setRows(visibleRows);
		this.pack();
	}

	public void setMinInt(int minInt)
	{
		this.minInt = minInt;
	}

	public void setMinMsg(String msg)
	{
		this.minMsg.setText(msg);
	}

	/**
	 * This toggles the Visibility of the minimum Integer Field
	 * @param bool visibility of minimum Integer Field
	 */
	public void setMinVisible(boolean bool)
	{
		if (!bool && minField.isVisible() && minMsg.isVisible())
		{
			this.remove(minField);
			this.remove(minMsg);
			this.minField.setVisible(false);
			this.minMsg.setVisible(false);

			visibleRows--;
			myGridLayout.setRows(visibleRows);
		} else if (!(minField.isVisible() && minMsg.isVisible()))
		{
			visibleRows++;
			myGridLayout.setRows(visibleRows);

			this.add(minMsg, 0);
			this.add(minField, 1);
			this.minField.setVisible(true);
			this.minMsg.setVisible(true);
		}

		this.pack();
	}

	/**
	 * this will set the Message that is been showed for JNumberField
	 * @param msg
	 */
	public void setMsg(String msg)
	{
		this.msg = msg;
		this.inputMsg.setText(msg);
	}

	/**
	 * This will show the Dialog and pause the entire UI
	 * until cancelButton or okButton has been clicked
	 * @return boolean if a valid number has been entered. False on Cancel
	 */
	public boolean showDialog()
	{
		this.success = false;
		this.inputField.setText("");
		this.diffField.setText("0");
		this.minField.setText(Integer.toString(minInt));
		this.maxField.setText(Integer.toString(maxInt));
		this.setResizable(true);
		this.setModal(true);
		this.pack();
		this.setLocationRelativeTo(_parentComponent);

		this.setVisible(true);
		this.inputField.requestFocus();

		return success;
	}

	public void setParentComponent(Component _parentComponent)
	{
		this._parentComponent = _parentComponent;
	}

	private void updateValues()
	{
		int curInput = inputField.getValue();
		if (curInput >= getMinInt() && curInput <= getMaxInt())
		{
			this.okButton.setEnabled(true);
			success = true;
		} else
		{
			this.okButton.setEnabled(false);
		}
		this.diffField.setText(Integer.toString(curInput - getMinInt()));
	}
}
