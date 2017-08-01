package eg.edu.guc.entanglement.gui;

import javax.swing.*;
import java.awt.*;
public class MyButton extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		 MyButton buttonGUI = new MyButton();
		buttonGUI.setVisible(true); }
public MyButton() {
super();
setSize(400,100);
JButton button = new JButton("Red");
getContentPane().add(button,BorderLayout.NORTH);
setTitle("Second Window");
getContentPane().setBackground(Color.RED);
WindowDestroyer myListener = new WindowDestroyer();
addWindowListener(myListener); }
}
