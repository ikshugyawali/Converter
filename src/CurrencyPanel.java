import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;//import jpannel for container class
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.io.*;
import java.text.DecimalFormat;
import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class CurrencyPanel extends JPanel {
	JComboBox<String> combo;
	private JTextField textfeild;
	private JLabel resultLabel;
	private JLabel countLabel;
	private int count;
	private JCheckBox reverse;
	String converter[] = new String[20];
	DecimalFormat numFormat = new DecimalFormat("#0.00");
	currency[] list= new currency[100];
	
	String error = "";

	JMenuBar setupMenu() {

		JMenuBar menuBar = new JMenuBar();// creates a menu named menubar

		JMenu menuFile = new JMenu("File");// creates menu item named file
		menuFile.setIcon(new ImageIcon("images/files.png"));

		JMenu menuHelp = new JMenu("Help");// creates menu item named help
		menuHelp.setIcon(new ImageIcon("images/help.png"));

		menuBar.add(menuFile);// add menu item named file on menubar

		menuBar.add(menuHelp);// add menu item named help on menubar

		// addding "About" option to help menu
		ImageIcon iconAbout = new ImageIcon("images/about.png");
		JMenuItem menuAbout = new JMenuItem("About", iconAbout);// create menu item named about
		menuAbout.setToolTipText("About Author");// display message while hovering on menu item about
		menuHelp.add(menuAbout);// add menu item about inside help

		// setting mnemonics to about: "ctrl+a"
		menuAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

		ImageIcon iconImport = new ImageIcon("images/import.png");
		JMenuItem menuImport = new JMenuItem("Import", iconImport);// create menu item named exit
		menuImport.setToolTipText("Click to import files");
		menuFile.add(menuImport);// adding exit menu item inside file

		// setting mnemonic to exit: "ctrl+i"
		menuImport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

		// adding exit icon to exit menu item
		ImageIcon iconExit = new ImageIcon("images/exit.png");
		JMenuItem exit = new JMenuItem("Exit", iconExit);// create menu item named exit
		exit.setToolTipText("Click to exit the pannel");
		menuFile.add(exit);// adding exit menu item inside file

		// setting mnemonic to exit: "ctrl+e"
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

		// adding menu item exit to the action listener
		exit.addActionListener(e -> {
			{
				System.exit(0);// exists the panel
			}
		});

		/*
		 * adding menu item about to the action listener and displaying message about
		 * author and the purpose of the application
		 */
		menuAbout.addActionListener(e -> {
			String message = "About Author\n\nName:Ikshu Gyawali\nLeeds "
					+ "ID:77227178\n\n\nPurpose of Application\n\nThis application is used for different kinds of conversion by the help of different GUI."
					+ "\nIt mainly focus on JAVA GUI.\n\n\n_____________________________"
					+ "\n\u00a9 copyright 2020, Ikshu Gyawali";
			JOptionPane.showMessageDialog(null, message, "About Author", 1);
		});

		return menuBar;
	}

	CurrencyPanel() {

		// creates an object called action listener
		ActionListener listener = new ConvertListener();

		loadFile(0);
		// creates an object called combo
		combo = new JComboBox<String>(converter);

		// creates a JLabel object
		JLabel inputLabel = new JLabel("Enter value:");

		JButton convertButton = new JButton("Convert");
		convertButton.addActionListener(listener); // convert values when pressed
		convertButton.setToolTipText("perform the listed conversion");

		JButton clearButton = new JButton("Clear");// clearing he count
		clearButton.setToolTipText("clear the counter");
		clearButton.addActionListener(e -> {
			textfeild.setText("");
			resultLabel.setText("---");
			count = 0; // resetting count
			countLabel.setText("Convert count :" + count);
			combo.setSelectedItem("inches/cm");// on clearing the options will reset to inches/cm
		});

		countLabel = new JLabel("Converter count: " + count);
		count = 0;

		resultLabel = new JLabel("---");// output
		textfeild = new JTextField(5);
		textfeild.addActionListener(listener);
		textfeild.setToolTipText("Enter a valid number");
		reverse = new JCheckBox("Reverse conversion");
		reverse.addActionListener(listener);
		reverse.setToolTipText("Convert vice versa");
		reverse.setOpaque(false);// remove color

		// display in order
		add(combo);
		add(inputLabel);
		add(textfeild);
		add(convertButton);
		add(resultLabel);
		add(clearButton);
		add(reverse);
		add(countLabel);

		setPreferredSize(new Dimension(500, 100));// dimension of frame
		setBackground(Color.WHITE);// backgroung color

	}

	void loadFile(int checker) {

		File txtfile = new File("src/currency.txt");

		converter = new String[converter.length];

		if (checker != 0) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showOpenDialog(null);
			System.out.print(status);
			txtfile = chooser.getSelectedFile();
		}

		try {
			BufferedReader filereader = new BufferedReader(new InputStreamReader(new FileInputStream(txtfile), "UTF8"));

			String content = filereader.readLine();

			int index = 0;
			while (content != null) {

				String[] parts = content.split(",");

				if (parts.length != 3) {
					System.out.print("This file contens error");
				} else {

					try {
						currency currency = new currency(parts[0].trim(), Double.parseDouble(parts[1]),
								parts[2].trim());
						list[index] = currency;

						System.out.println(converter[index]);
						index++;
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "this file contains error", "Error", 0);

					}
					error += "Line no. " + index;
					converter[index] = parts[0];
					

				}
				// validate, store somewhere etc.
				content = filereader.readLine(); // read next line (if available)
			}

			filereader.close();
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "File cannot be found", "Error", 0);

		}

		combo.setModel(new DefaultComboBoxModel<String>(converter));
	}

	private class ConvertListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String text = textfeild.getText().trim();
			String action = event.getActionCommand();

			if (action.equals("Load")) {
				loadFile(1);
			} else {
				if (!text.isEmpty()) {

					try {

						double value = Double.parseDouble(text);
						int index = combo.getSelectedIndex();
						double result = value * list[index].getFactor();
						String setSymbol = list[index].getSymbol();
						String setName = list[index].getName();

						if (reverse.isSelected()) {
							result = value * 1 / (list[index].getFactor());
							setSymbol = "\u00A3";
						}

						if (setSymbol.equals("kr")) {
							resultLabel.setText("Result : " + numFormat.format(result) + " " + setSymbol);
						} else {
							resultLabel.setText("Result : " + setSymbol + " " + numFormat.format(result));
						}
						if (setName.equals("Canadian Dollars (CAD)")) {
							setSymbol = "C$";
							resultLabel.setText("Result : " + setSymbol + " " + numFormat.format(result));

						}
						if (setName.equals("Australian Dollars (AUD)")) {
							setSymbol = "A$";
							resultLabel.setText("Result : " + setSymbol + " " + numFormat.format(result));

						}

						// error handling for invalid input
						if (value < 0) {
							JOptionPane.showMessageDialog(null, "Enter valid number", "Error ", 0);
						}
						count++;
						countLabel.setText("Converter count: " + count);

					} catch (Exception err) {
						JOptionPane.showMessageDialog(null, "Invalid Input !!", "Error ", 0);
					}
				}

				else {
					JOptionPane.showMessageDialog(null, "Please enter number to proceed calculation.", "Error ", 0);
				}

			}
		}
	}
}