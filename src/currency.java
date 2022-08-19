import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;

import java.io.*;
import javax.swing.JFileChooser;

public class currency{
	
		String name;
		double factor;
		String symbol;
		
		currency (String n, double f, String s){
			name = n;
			factor = f;
			symbol = s;
		}
		
		String getName() {
			return name;
		}
		
		double getFactor() {
			return factor;
		}
		
		String getSymbol() {
			return symbol;
		}
}