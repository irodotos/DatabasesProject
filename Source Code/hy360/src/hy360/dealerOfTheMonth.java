package hy360;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;

public class dealerOfTheMonth extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public JTextArea id_area;
	public static long dealer_of_the_month=0;

	public dealerOfTheMonth() {
		setTitle("CCC");
		setResizable(false);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 465, 203);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dealer of the month account ID : ");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		lblNewLabel.setBounds(77, 11, 288, 43);
		getContentPane().add(lblNewLabel);
		
		id_area = new JTextArea();
		id_area.setBounds(77, 77, 288, 30);
		getContentPane().add(id_area);

	}
}