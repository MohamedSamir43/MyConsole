package Main;

import java.io.*;
import java.nio.file.Files;

import java.nio.file.StandardCopyOption;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.awt.event.*;



//clear, cd , ls , cp , mv , rm ,mkdir,rmdir, cat, more, less, pwd. 
//     ,Done,Done,Done,Done,Done,Done,  Done,
//String commands[]={"ls","cd","cd ..","cd (~)","mkdir","rmdir","cpy"};

public class Main extends JFrame{
   public static String command1,dest,src;
   public static JPanel window = new JPanel();
   public static JTextArea textArea ;
   public static JScrollPane scrollPane;
	public Main() {
		
		setTitle("Console");
		setSize(new Dimension(600, 300));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		window.setLayout(gridBagLayout);
		window.repaint();
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		scrollPane.repaint();

		window.add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea("Assassin@Assassins's server:-$ ");
		scrollPane.setViewportView(textArea);
		textArea.setForeground(Color.GREEN);
		textArea.setCaretColor(Color.BLUE);
		textArea.setBackground(Color.BLACK);
		textArea.setLineWrap(true);
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		
		textArea.setSize(new Dimension(600, 300));
		textArea.addKeyListener(new check());
		textArea.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int index=textArea.getText().lastIndexOf("$ ")+1;
				if(check.previous_command.equals("more")||check.previous_command.equals("less"))
					textArea.setCaretPosition(textArea.getText().length()+1);
				if(textArea.getCaretPosition()<=index+2&&e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
					textArea.setCaretPosition(index+2);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
		setContentPane(window);;
		
	  window.revalidate();
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      try {
		setIconImage(ImageIO.read(this.getClass().getResource("image.png")));
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      
	}
	public static void main(String args[]) {
		new Main();

		window.repaint();

	}
}