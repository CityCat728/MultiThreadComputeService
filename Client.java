import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {
	public static void main(String[] args) throws IOException 
	{
		try {
			//Set up the name of local host,the port number used for binding interface
			InetAddress localIp = InetAddress.getByName("localhost");
			Socket socket = new Socket(localIp, 8591);

			//input,output streams that allowing fetch data from the bottom level
			//even if it is nothing to do with machine
			DataInputStream InputS = new DataInputStream(socket.getInputStream());
			DataOutputStream OutputS = new DataOutputStream(socket.getOutputStream());

			//store the function chosen,sequence of number user entered,and interactive results 
			String choice, sequenceNum,result;

			
			while (true) 
			{   //Print the message to make user knowing how to use and execute program
				System.out.println(InputS.readUTF()); 
				System.out.println(InputS.readUTF()); 
				//A container to receive the input from keyboard
				Scanner input = new Scanner(System.in);
				//transfer the sequence of number through I/O stream
				sequenceNum = input.nextLine(); 
				OutputS.writeUTF(sequenceNum);
				
				//Whenever the client want to leave,and enter the character 'n'
				if (sequenceNum.equals("n")) 
				{	//shut down the socket and print the disconnection message
					socket.close();
					System.out.println("Connection closed");
					break;
				}
				
				//Set up to make sure the form of input is correct
				boolean flag=true;
				String [] arrInputFir=null;
				//Ignore the space in string and cut data into pieces
				arrInputFir=sequenceNum.split(" ");
				
				//Determine if there is anything illegal on input data string
				for (int i=0; i< arrInputFir.length; i++) 
				 {
					 for(int j=0;j<arrInputFir[i].length();j++) 
					 {
				      if (!Character.isDigit(arrInputFir[i].charAt(j))) 
				      {
				    	  flag=false;
				    	  break;
				      }   
				     }
				 }
				//Whenever the form is wrong,make it correctly till it usable
				while(flag==false) 
				{
					System.out.println(InputS.readUTF());
					sequenceNum = input.nextLine();
					OutputS.writeUTF(sequenceNum);
					
					String [] arrInputSec=null;
					arrInputSec=sequenceNum.split(" ");
					
					flag=true;
					for (int i=0; i< arrInputSec.length; i++) 
					 {
						 for(int j=0;j<arrInputSec[i].length();j++) 
						 {
					      if (!Character.isDigit(arrInputSec[i].charAt(j))) 
					      {
					    	  flag=false;
					    	  break;
					      }   
					     }
					 }
				}
				
				
				
				//Print the information about functions
				//The instruction and options provided for client
				for(int repeat=0;repeat<4;repeat++)
					System.out.println(InputS.readUTF());
				//store the choice from keyboard
				choice = input.nextLine();
				OutputS.writeUTF(choice);

				//print out sorted number from small to large 
				if (choice.equals("1")) 
				{
					result = InputS.readUTF();
					System.out.println(result);
				}
				//print out sorted number from large to small
				else if (choice.equals("2")) 
				{
					result = InputS.readUTF();
					System.out.println(result);
				}
				//print out average of sequence of number after calculating
				else if (choice.equals("3")) 
				{
					result = InputS.readUTF();
					System.out.println(result);
				}
				
				result = InputS.readUTF();  
				System.out.println(result + "\n");
			}

			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
