package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;

public class check implements KeyListener {
	static String first_part = "", second_part = "", previous_command = "", textName;
	static int count = 0, left_to_excute_end, left_to_excute_begin;
	static Boolean question = false, check = false;

	// find Super test.txt C:\Users\Mohamed\Desktop

	static void get_command(String Command) {
		first_part = "";
		second_part = "";
		int index = 0;
		// ========================================================
		for (int i = 0; i < Command.length(); i++, index++)
			if (Command.charAt(i) != ' ')
				break;
		// ========================================================
		for (int i = index; i < Command.length(); i++, index++)
			if (Command.charAt(i) != ' ')
				first_part += Command.charAt(i);
			else
				break;
		// ========================================================
		for (int i = index; i < Command.length(); i++, index++)
			if (Command.charAt(i) != ' ')
				break;
		// ========================================================
	    
		int end=Command.length()-1;
		for(int i=Command.length()-1;i>=0;i--,end=i)
			if(Command.charAt(i)!=' ') break;
		
		for (int i = index; i <=end; i++)
			second_part += Command.charAt(i);

	}

	@Override
	public void keyPressed(KeyEvent eve) {
		if (eve.getKeyCode() == KeyEvent.VK_ENTER) {
			//////////////////////////////////////////////////////////
			if (previous_command.equals("more") && count == 1) {
				Command.MORE(second_part, count++);
				return;
			} else if (previous_command.equals("more") && count == 2) {
				Command.MORE(second_part, count++);
				previous_command = "";
			} else if (previous_command.equals("less") && count == 1) {
				Command.LESS(second_part, count++);
				return;
			} else if (previous_command.equals("less") && count == 2) {
				Command.LESS(second_part, count++);
				previous_command = "";
			}
			int start, end;

			if (left_to_excute_begin == 0 && !check)
				start = Main.textArea.getText().lastIndexOf(":-$ ") + 4;
			else
				start = left_to_excute_begin;

			if (left_to_excute_end == 0 && !check)
				end = Main.textArea.getText().length();
			else
				end = left_to_excute_end;

			String command = "";
			System.out.println(start + " " + end);
			for (int I = start; I < end; I++) {

				while (I < end && Main.textArea.getText().charAt(I) != ';') {
					command += Main.textArea.getText().charAt(I);
					I++;
				}
				System.out.println(command);
				get_command(command);
				command = "";
				System.out.println(first_part + "- oooooo -" + second_part);
				//////////////////////////////////////////////////////////
				if (first_part.equals("args"))
					Command.args(second_part);
				//////////////////////////////////////////////////////////
				else if (first_part.equals("ls"))
					try {
						Command.LS(second_part);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				///////////////////////////////////////////////////////////
				else if (first_part.equals("help"))
					Command.HELP();

				//////////////////////////////////////////////////////////
				else if (first_part.equals("clear"))
					Command.CLEAR();
				//////////////////////////////////////////////////////////
				else if (first_part.equals("date"))
					Command.DATE();
				//////////////////////////////////////////////////////////
				else if (first_part.equals("pwd"))
					Command.PWD();
				//////////////////////////////////////////////////////////
				else if (first_part.equals("more")) {
					Command.MORE(second_part, count++);
					if (Command.is_more_or_less==true) {
						left_to_excute_begin = I;
						left_to_excute_end = end;
						check = true;
						previous_command = "more";
						return;
					}
				}

				/////////////////////////////////////////////////////////
				else if (first_part.equals("less")) {

					Command.LESS(second_part, count++);
					if (Command.is_more_or_less) {
					left_to_excute_begin = I;
					check = true;
					left_to_excute_end = end;
					previous_command = "less";
					return;
					}
					
				}
				/////////////////////////////////////////////////////////
				else if (first_part.equals("cd"))
					Command.CD(second_part);
				/////////////////////////////////////////////////////////
				else if (first_part.equals("cat"))
					Command.CAT(second_part);
				/////////////////////////////////////////////////////////
				else if (first_part.equals("rm"))
					Command.RM(second_part);
				/////////////////////////////////////////////////////////
				else if (first_part.equals("mkdir"))
					Command.MKDIR(second_part);
				/////////////////////////////////////////////////////////
				else if (first_part.equals("rmdir"))
					try {
						Command.RMDIR(second_part);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else if (first_part.equals("cp"))
					Command.COPY();
				//////////////////////////////////////////////////////////
				else if (first_part.equals("mv"))
					Command.MOVE();
				///////////////////////////////////////////////////////////
				else if (first_part.equals("find")) {
					int index = second_part.lastIndexOf(".txt") + 4;
					if (index == 3)
						continue;

					String fileName = second_part.substring(0, index);
					for (int i = index; i < second_part.length(); i++, index++)
						if (second_part.charAt(i) != ' ')
							break;

					File path = new File(second_part.substring(index, second_part.length())+"\\");
					
					Main.textArea.append("\n");

					try {
						Main.textArea.append(Command.find(fileName, path) + "\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				///////////////////////////////////////////////////////////
				else if (first_part.equals("grep")) {
					String part[] = { /* "C:","D:", */"E:\\"/* ,"F:","G:" */ };
					int index = second_part.indexOf(":");
					String fileName = second_part.substring(index + 1, second_part.length());
					String ans = "";
					textName = second_part.substring(0, index);
					index = fileName.indexOf(' ');
					for (int i = Math.max(index, 0); i < fileName.length(); i++, index++)
						if (fileName.charAt(i) != ' ')
							break;

					fileName = fileName.substring(Math.max(index, 0), fileName.length());
					question = true;
					Main.textArea.append("\n");
					for (int i = 0; i < part.length; i++) {
						File path = new File(part[i]);
						if (path.exists())
							try {
								ans += Command.find(fileName, path) + "\n";
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
					question = false;
					Main.textArea.append(ans);
				}

				////////////////////////////////////////////////////////////////////////
				else if (first_part.equals("yes") && question) {
					if (previous_command.equals("cp")) {
						Command.dest_.delete();
						try {
							if (Command.src_.isDirectory())
								FileUtils.moveDirectory(Command.src_, Command.dest_);
							else
								FileUtils.moveFile(Command.src_, Command.dest_);

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (previous_command.equals("mv")) {
						Command.dest_.delete();

						try {
							if (Command.src_.isDirectory())
								FileUtils.moveDirectory(Command.src_, Command.dest_);
							else
								FileUtils.moveFile(Command.src_, Command.dest_);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			
			else 
				Main.textArea.append("\nNo such command found\n");
			
			if (check) {
				check = false;
				left_to_excute_begin = 0;
				left_to_excute_end = 0;
			}
		

		}
			Main.textArea.setText(Main.textArea.getText() + "\nAssassin@Assassins's server:-$ ");	
			Main.textArea.requestFocusInWindow();
			Main.textArea.setCaretPosition(Main.textArea.getText().length() + 1);

			return;
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}