package Main;

import Main.Main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.util.*;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import org.apache.commons.io.FileUtils;

public class Command {
	static String out = "", command1, dest, src, already_written, onFile;
	static File Curr_path = new File("C:\\Users");
	static File src_, dest_;
	static Scanner in;
	static int index;
	static boolean is_more_or_less;
	//////////////////////// LS //////////////////////////////
	static void LS(String check) throws IOException 
	{
		String list = ((Character) '\n').toString();
		if (check.length() == 0) {
			for (File i : Curr_path.listFiles())
				list += i.getName() + ((Character) '\n').toString();

			Main.textArea.append(list);
			return;
		}

		if (check.indexOf(">") != -1 && check.indexOf(">>") == -1)
			_print(check.substring(1, check.length()), true);

		else if (check.indexOf(">>") != -1)
			_print(check.substring(2, check.length()), false);
		else
			Main.textArea.append("\nWrong command");
	}
	////////////////////////// HELP ////////////////////////////////////
	static void HELP() {
		String help = "\nclear : clear the console window\n";
		help += "cd : change the current Directory\n";
		help += "ls : Show all file(s)'s name in the current path\n";
		help += "cp : copy folder/file\n";
		help += "mv : move folder/file\n";
		help += "mkdir : make folder/file\n";
		help += "rm : remove folder/file\n";
		help += "rmdir : remove directory\n";
		help += "cat : show text file contents\n";
		help += "more : show more of text file's data\n";
		help += "less : show less of text file's data\n";
		help += "pwd : show the current directory\n";
		help += "date : show the date of today\n";
		help += "find : find a file in a given directory\n";
		help += "grep : find a text in a textfile and search for the textfile in the whole system\n";
		Main.textArea.append(help);
	}
	///////////////////// CLEAR //////////////////////////////////////////
	static void CLEAR() {
		Main.textArea.setText("");
	}
	///////////////////////// CD //////////////////////////////////////////
	static void CD(String path) {
		String folder = path;
		out = "";
		
		Main.textArea.append("\n");
		if(path.length()==0)
		{
			Curr_path=new File("C:\\Users");
			return ;
		}
		
		if (folder.equals("..") == true) {
			int ind = Curr_path.toString().lastIndexOf("\\");
			folder = Curr_path.toString();
			folder = folder.substring(0, ind);
			if (folder.length() < 2)
				folder = "";
			Curr_path = new File(folder + "\\");
			System.out.println(Curr_path);
			return;
		}

		File Path;
		if (folder.indexOf("\\") == -1 && folder.indexOf(":") == -1)
			Path = new File(Curr_path.getPath() + "\\" + folder);
		else
			Path = new File(folder + "\\");
if (Path == null || !Files.exists(Path.toPath())) {
			Main.textArea.append("Folder Not Found\n");
			return;
		}

		else if (Path.exists() == true)
			Curr_path = Path;

		return;
	}
	//////////////////////////// RM //////////////////////////////////////////////////
	static void RM(String src) {
		File src_ = new File(src);

		if (src_.toString().indexOf("\\") == -1 && src_.toString().indexOf(":") == -1)
			src_ = new File(Curr_path.getAbsoluteFile() + src_.getPath());

		if (src_.exists() == false)
			Main.textArea.append(src_ + " : not found\n");
		else if (src_.isDirectory() == true)
			Main.textArea.append("\ncannot remove " + src + " : is a directory\n");
		else
			Main.textArea.append("\n");
		src_.delete();

	}
	//////////////////////// MKDIR ////////////////////////////////////////////////////
	static void MKDIR(String src) {

		String[] arr = src.split(" ");

		File src_;
		for (int i = 0; i < arr.length; i++) {

			if (arr[i].lastIndexOf("\\") == -1 && arr[i].lastIndexOf("/") == -1)
				src_ = new File(Command.Curr_path.toString() + "\\" + arr[i]);
			else
				src_ = new File(arr[i]);
			if (src_.exists()) {
				Main.textArea.append("\ncannot create directory " + src_ + ": File exists");
				continue;
			}

			src_.mkdir();
		}
	}
	////////////////////////////// RMDIR ////////////////////////////////////////
	static void RMDIR(String toDelete) throws IOException {

		String path = "";
		File currentFile;
		boolean Check = false;
		for (int i = 0; i < toDelete.length(); i++)
			if (toDelete.charAt(i) == '\\') {
				path += "\\";
				Check = true;
			}

			else
				path += toDelete.charAt(i);

		if (Check == true)
			currentFile = new File(path);
		else
			currentFile = new File(Curr_path + "\\" + path);
		if (currentFile.isDirectory()) {
			FileUtils.deleteDirectory(currentFile);
			Main.textArea.append("\nDeleted Successfully");
		}

		else
			Main.textArea.append("\nFolder is not found");
			
	}
	///////////////////// DATE ///////////////////////////////////////
	static void DATE() {
		Date date = new Date();
		Main.textArea.append("\n" + date.toString() + "\n");
	}
	/////////////////////// CAT ////////////////////////////////////
	static void CAT(String toRead) {
		String temp = "";
		in = null;
		if (!toRead.contains(".txt")) {
			Main.textArea.append("\nCan not read this format\n");
			return;
		}

		try {
			if (toRead.contains("\\"))
				in = new Scanner(new File(toRead));
			else
				in = new Scanner(new File(Curr_path + "\\" + toRead));

		} catch (FileNotFoundException e) {
			Main.textArea.append("\nFile not found\n");

		}

		while (in.hasNextLine())
			temp += in.nextLine() + '\n';

		Main.textArea.append('\n' + temp);
	}
	//////////////////////// MORE ///////////////////////////////////////
	static void MORE(String toRead, int Check) {
		String temp = "", toPrint = "";
		in = null;
		int i;
		is_more_or_less=true;
		if (Check == 0) {

			if (!toRead.contains(".txt")) {
				is_more_or_less=false;
				check.count = 0;
				Main.textArea.append("\nCan not read this format\n");
				return;
			}
			// ===================================================================
			try {
				
                
				if (toRead.contains("\\"))
					in = new Scanner(new File(toRead.substring(0,toRead.indexOf(".txt")+4)));
				else
					in = new Scanner(new File(Curr_path + "\\" + toRead.substring(0,toRead.indexOf(".txt")+4)));

			} catch (FileNotFoundException e) {
				is_more_or_less=false;
				Main.textArea.append("\nFile not found\n");
				check.count = 0;
				return;
			}
			// ===================================================================
			while (in.hasNextLine())
				temp += in.nextLine()+"\n";

			i = 0;
			onFile = temp;
			while ((double) toPrint.length() / onFile.length() < 0.5) {
				toPrint += onFile.charAt(i);
				i++;
			}
			already_written = toPrint;
			index = i;
			Main.textArea.append("\n" + toPrint + "\n--More--(50%)\n");
			onFile = temp;
			in.close();
			return;

		}

		else if (Check == 1) {
			i = index;
			toPrint = already_written;
			// ===================================================================
			while ((double) toPrint.length() / onFile.length() < 0.75) {
				toPrint += onFile.charAt(i);
				i++;
			}
			// ===================================================================
			already_written = toPrint;
			index = i;
			Main.textArea.append("\n" + toPrint + "\n--More--(75%)\n");
			return;
		}

		else {
			Main.textArea.append("\n" + onFile + "\n");
			onFile = "";
			check.first_part = "";
			check.count = 0;
			is_more_or_less=false;
			return;
		}

	}
	//////////////////////// LESS ///////////////////////////////////////////
	static void LESS(String toRead, int Check) {
		String temp = "", toPrint = "";
		in = null;
		int i;
		is_more_or_less=true;	

		System.out.println(toRead);
		if (Check == 0) {

			if (!toRead.contains(".txt")) {
				is_more_or_less=false;
				check.count = 0;
				Main.textArea.append("\nCan not read this format\n");
				return;
			}
			// ===================================================================
			try {
				if (toRead.contains("\\"))
					in = new Scanner(new File(toRead.substring(0,toRead.indexOf(".txt"+5))));
				else
					in = new Scanner(new File(Curr_path + "\\" + toRead.substring(0,toRead.indexOf(".txt"+5))));

			} catch (FileNotFoundException e) {
				is_more_or_less=false;
				check.count = 0;
				Main.textArea.append("\nFile not found\n");
				return;
			}
			// ===================================================================
			while (in.hasNextLine())
				temp += in.nextLine();

			i = 0;
			onFile = temp;
			while ((double) toPrint.length() / onFile.length() < 0.75) {
				toPrint += onFile.charAt(i);
				i++;
			}
			Main.textArea.append("\n" + toPrint + "\n--Less--(75%)\n");

			in.close();
			return;

		}

		else if (Check == 1) {
			i = 0;
			toPrint = "";
			// ===================================================================
			while ((double) toPrint.length() / onFile.length() < 0.5) {
				toPrint += onFile.charAt(i);
				i++;
			}
			// ===================================================================
			index = i;
			Main.textArea.append("\n" + toPrint + "\n--Less--(50%)\n");
			return;
		}

		else {
			i = 0;
			toPrint = "";
			// ===================================================================
			while ((double) toPrint.length() / onFile.length() < 0.25) {
				toPrint += onFile.charAt(i);
				i++;
			}
			Main.textArea.append("\n" + toPrint + "\n");
			onFile = "";
			check.first_part = "";
			check.count = 0;
			is_more_or_less=false;
			return;
		}

	}
	///////////////////////////////// PWD /////////////////////////////
	static void PWD() {
		Main.textArea.append("\n" + Curr_path.toString() + "\n");
	}
	///////////////////////////// COPY ////////////////////////////////
	static void COPY() {
		
		int index_for_delm=-1; // to know index where the char " is in text

		for(int i=0;i<check.second_part.length();i++)
			if( ((Character)check.second_part.charAt(i) ).hashCode()==34)
			{
				
				index_for_delm=i;
				break;
			}
		
		if(index_for_delm!=-1) // found the ""
		{
			if(index_for_delm==0) // if src conatain ""
			{
				check.second_part=check.second_part.substring(1,check.second_part.length());// remove the first char "
				
				for(int i =0;i<check.second_part.length();i++,index++)
				
					if(((Character)check.second_part.charAt(i)).hashCode()==34) // found the other char of ""
					{
						src=check.second_part.substring(0,i); // make the source and remove "" 
						
						check.second_part=check.second_part.substring(i+1,check.second_part.length()); 
						// take the other string and work in it
					
						for( i=0;i<check.second_part.length();i++) // for remove space 
							if(check.second_part.charAt(i)!=' ')
							{
								check.second_part=check.second_part.substring(i,check.second_part.length());
								break;
							}
							break;
					}
				
				
				index_for_delm=-1;
				
				for(int i=0;i<check.second_part.length();i++) // to find if there char "" in destination or not
					if( ((Character)check.second_part.charAt(i) ).hashCode()==34)// found the char ""
					{
						index_for_delm=i;
						break;
					}
				
				if(index_for_delm!=-1) // after we make src maybe we found "" in dest
				{
					check.second_part=check.second_part.substring(1,check.second_part.length());
					// remove the first "" in the string 
					
					for(int i =0;i<check.second_part.length();i++,index++)
						if(((Character)check.second_part.charAt(i)).hashCode()==34)//found last ""
						{
							dest=check.second_part.substring(0,i);
							break;
						}
				}
				
				else  // not found the "" in dest
					dest=check.second_part;
			}
			
			else  // if "" in the destination  and not found in the source
			{
				String tmp=check.second_part.substring(index_for_delm+1,check.second_part.length());
				//take the destination 
			
				check.second_part=check.second_part.substring(0,index_for_delm);
				//take the source 
				
				int index=check.second_part.length()-1;
				// remove the space from the source 
				for(int i=check.second_part.length()-1;i>=0;i--)
					if(check.second_part.charAt(i)!=' ')
					{
						index=i;
						break;
					}
				
				src=check.second_part.substring(0,index+1); // make the source
				
				for(int i =0;i<tmp.length();i++)
					if(((Character)tmp.charAt(i)).hashCode()==34) // found the char "" in the end of destination
					{
						dest=tmp.substring(0,i);
						break;
					}	
			}
			
		}
		
		else // not found "" in the source and destination
		{
			int space = check.second_part.indexOf(" "), space1 = check.second_part.lastIndexOf(" ");
			src = check.second_part.substring(0, space);
			dest = check.second_part.substring(space1 + 1, check.second_part.length());
		}
		
		src_ = new File(src);
		dest_ = new File(dest);
		
		if (src_.toString().indexOf("\\") == -1 && src_.toString().indexOf(":") == -1)
			src_ = new File(Command.Curr_path.getAbsoluteFile() + src_.getPath());

		if (dest_.toString().indexOf("\\") == -1 && dest_.toString().indexOf(":") == -1)
			dest_ = new File(Command.Curr_path.getAbsoluteFile() + dest_.getPath());
		else
			dest_ = new File(dest + "\\" + src_.toPath().getFileName());

		if (src_.exists() == false) {
			Main.textArea.append("\n" + src_ + " : not found\n");
			return;
		}

		if (dest_.exists() == true) {
			check.question = true;
			Main.textArea.append("directory is found\nreplace it ?(yes/no)");
			return;
		}

		try {
			if(src_.isDirectory())
				FileUtils.copyDirectory(src_, dest_);
			else 
				FileUtils.copyFile(src_, dest_);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//////////////////////////////// MOVE /////////////////////////////////////////
	static void MOVE() {
		int index_for_delm=-1; // to know index where the char " is in text

		for(int i=0;i<check.second_part.length();i++)
			if( ((Character)check.second_part.charAt(i) ).hashCode()==34)
			{
				
				index_for_delm=i;
				break;
			}
		
		if(index_for_delm!=-1) // found the ""
		{
			if(index_for_delm==0) // if src conatain ""
			{
				check.second_part=check.second_part.substring(1,check.second_part.length());// remove the first char "
				
				for(int i =0;i<check.second_part.length();i++,index++)
				
					if(((Character)check.second_part.charAt(i)).hashCode()==34) // found the other char of ""
					{
						src=check.second_part.substring(0,i); // make the source and remove "" 
						
						check.second_part=check.second_part.substring(i+1,check.second_part.length()); 
						// take the other string and work in it
					
						for( i=0;i<check.second_part.length();i++) // for remove space 
							if(check.second_part.charAt(i)!=' ')
							{
								check.second_part=check.second_part.substring(i,check.second_part.length());
								break;
							}
							break;
					}
				
				
				index_for_delm=-1;
				
				for(int i=0;i<check.second_part.length();i++) // to find if there char "" in destination or not
					if( ((Character)check.second_part.charAt(i) ).hashCode()==34)// found the char ""
					{
						index_for_delm=i;
						break;
					}
				
				if(index_for_delm!=-1) // after we make src maybe we found "" in dest
				{
					check.second_part=check.second_part.substring(1,check.second_part.length());
					// remove the first "" in the string 
					
					for(int i =0;i<check.second_part.length();i++,index++)
						if(((Character)check.second_part.charAt(i)).hashCode()==34)//found last ""
						{
							dest=check.second_part.substring(0,i);
							break;
						}
				}
				
				else  // not found the "" in dest
					dest=check.second_part;
			}
			
			else  // if "" in the destination  and not found in the source
			{
				String tmp=check.second_part.substring(index_for_delm+1,check.second_part.length());
				//take the destination 
			
				check.second_part=check.second_part.substring(0,index_for_delm);
				//take the source 
				
				int index=check.second_part.length()-1;
				// remove the space from the source 
				for(int i=check.second_part.length()-1;i>=0;i--)
					if(check.second_part.charAt(i)!=' ')
					{
						index=i;
						break;
					}
				
				src=check.second_part.substring(0,index+1); // make the source
				
				for(int i =0;i<tmp.length();i++)
					if(((Character)tmp.charAt(i)).hashCode()==34) // found the char "" in the end of destination
					{
						dest=tmp.substring(0,i);
						break;
					}	
			}
			
		}
		
		else // not found "" in the source and destination
		{
			int space = check.second_part.indexOf(" "), space1 = check.second_part.lastIndexOf(" ");
			src = check.second_part.substring(0, space);
			dest = check.second_part.substring(space1 + 1, check.second_part.length());
		}

		src_ = new File(src);
		dest_ = new File(dest);

		if (src_.toString().indexOf("\\") == -1 && src_.toString().indexOf(":") == -1)
			src_ = new File(Command.Curr_path.getAbsoluteFile() + src_.getPath());

		if (dest_.toString().indexOf("\\") == -1 && dest_.toString().indexOf(":") == -1)
			dest_ = new File(Command.Curr_path.getAbsoluteFile() + dest_.getPath());
		else
			dest_ = new File(dest + "\\" + src_.toPath().getFileName());

		if (src_.exists() == false) {
			Main.textArea.append("\n" + src_ + " : not found\n");
			return;
		}

		if (dest_.exists() == true) {
			check.question = true;
			Main.textArea.append("directory is found\nreplace it ?(yes/no)");
			return;
		}

		try {
			if(src_.isDirectory())
				FileUtils.moveDirectory(src_, dest_);
			else
				FileUtils.moveFile(src_, dest_);
			
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
	////////////////// LS > and >> /////////////////////////////////////
	static void _print(String path, boolean ok) throws IOException {
		int index=0;
		for(int i=0;i<path.length();i++,index++)
			if(path.charAt(i)!=' ') break;
			
		path = path.substring(index, path.length());

		if (path.indexOf("\\") == -1 && path.indexOf(":") == -1)
			path = Command.Curr_path.getAbsolutePath() + path;

		if (new File(path).exists() == false) {
			Main.textArea.append("\nFile not found");
			return;
		}

		PrintWriter wr;
		if (ok)
			wr = new PrintWriter(new FileWriter(path, false));
		else
			wr = new PrintWriter(new FileWriter(path, true));
		System.out.println(path);
		for (File i : Command.Curr_path.listFiles()) {
			char[] arr = i.getName().toString().toCharArray();
			if (ok)
				wr.println(arr);
			else
				wr.println(i.getName().toString());
		}
		wr.close();

	}
	///////////////////// FIND and GREP //////////////////////////////
	static String find(String src, File now) throws IOException {

		if (now == null || !Files.exists(now.toPath()))
			return "";
		File path;
		String ans = "";
		System.out.println(now);

		for (int i = 0; i < now.listFiles().length; i++) {
			path = now.listFiles()[i];
			if (path.toString().contains(src)) {
				if (check.question && findInText(path.toString()))
					ans += path.toString() + "\n";
				else if (!check.question)
					ans += path.toString() + "\n";
			} else if (path.isDirectory() == true)
				ans += find(src, path);
		}
		return ans;
	}
	//////////////////// FOR GREP /////////////////////////////////
	static boolean findInText(String name) throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(name));
		while (true) {
			String line = read.readLine();
			if (line.equals(null))
				break;
			else if (line.contains(check.textName))
				return true;		
		}

		return false;
	}
	////////////////////////// ARGS //////////////////////////////
	static void args(String command) {
		if (command.equals("ls")) {
			Main.textArea.append("\nls,null\n");
			Main.textArea.append("\nls , > ,  file name/file path\n");
			Main.textArea.append("\nls , >> , file name/file path\n");
		}
		//////////////////////////////////////////////////////////
		else if (command.equals("help"))
			Main.textArea.append("\nhelp , null\n");
		//////////////////////////////////////////////////////////
		else if (command.equals("clear"))
			Main.textArea.append("\nclear , null\n");
		//////////////////////////////////////////////////////////
		else if (command.equals("date"))
			Main.textArea.append("\ndate , null\n");
		//////////////////////////////////////////////////////////
		else if (command.equals("pwd"))
			Main.textArea.append("\npwd , null\n");
		//////////////////////////////////////////////////////////
		else if (command.equals("more"))
			Main.textArea.append("\nmore , null\n");
		/////////////////////////////////////////////////////////
		else if (command.equals("less"))
			Main.textArea.append("\nless , null\n");
		/////////////////////////////////////////////////////////
		else if (command.equals("cd")) {
			Main.textArea.append("\ncd , null\n");
			Main.textArea.append("\ncd , ..\n");
			Main.textArea.append("\ncd , directory_path\n");
		}
		/////////////////////////////////////////////////////////
		else if (command.equals("cat"))
			Main.textArea.append("\ncat , file name/file path\n");
		/////////////////////////////////////////////////////////
		else if (command.equals("rm"))
			Main.textArea.append("\nrm , file name/file path\n");
		/////////////////////////////////////////////////////////
		else if (command.equals("mkdir"))
			Main.textArea.append("\nmkdir , file name/file path\n");
		/////////////////////////////////////////////////////////
		else if (command.equals("rmdir"))
			Main.textArea.append("\nrkdir , file name/file path\n");
		/////////////////////////////////////////////////////////
		else if (command.equals("cp"))
			Main.textArea.append("\ncp , file name/file path , file name/file path\n");
        /////////////////////////////////////////////////////////
		else if (command.equals("mv"))
			Main.textArea.append("\nmv , file name/file path , file name/file path\n");
        /////////////////////////////////////////////////////////
		else if (command.equals("find"))
			Main.textArea.append("\nfind , file name/file path , directory\n");
		////////////////////////////////////////////////////////////////////////
		else if (command.equals("grep"))
			Main.textArea.append("\ngrep , txt , textFile/directory\n");

		else
			Main.textArea.append("\nNo such command to show its parameters\n");

	}

}
