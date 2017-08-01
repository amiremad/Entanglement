package eg.edu.guc.entanglement.gui;

import java.awt.*;

public class EntanglementGUI {
	
	public static void main(String[] args) {
		
		MyWindow window1 = new MyWindow();
		
		window1.setTitle("FirstWindow");
		window1.getContentPane().setBackground(Color.BLUE);
		window1.getContentPane().add(new MyButton());
		//window1.getContentPane().setLayout(new GridLayout(2, 3));
		window1.setVisible(true);
		
		}

}
