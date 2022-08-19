import javax.swing.JFrame;

public class converter {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CurrencyPanel panel = new CurrencyPanel();
		frame.setJMenuBar(panel.setupMenu());

		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);
	}
}
