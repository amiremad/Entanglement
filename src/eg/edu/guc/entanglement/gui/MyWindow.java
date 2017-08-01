package eg.edu.guc.entanglement.gui;

import javax.swing.*;
import java.awt.*;
public class MyWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public MyWindow() {
		super();
		setSize(400,100);
		getContentPane().setLayout(new BorderLayout());
		JLabel label1 = new JLabel("My name is");
		getContentPane().add(label1, BorderLayout.NORTH);
		JLabel label2 = new JLabel("Slim");
		getContentPane().add(label2, BorderLayout.SOUTH);
		setTitle("Second Window");
		getContentPane().setBackground(Color.BLUE);
		WindowDestroyer myListener = new WindowDestroyer();
		addWindowListener(myListener);
	}

}
