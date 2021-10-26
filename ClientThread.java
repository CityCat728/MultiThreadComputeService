import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class ClientThread extends Thread
{	//set up variables and use property 'final' to make sure the value of object unchangeable
	final DataInputStream InputS;
	final DataOutputStream OutputS;
	final Socket socket;

	//Constructor of Client 
	public ClientThread(Socket socket, DataInputStream newInputS, DataOutputStream newOutputS) 
	{
		this.socket = socket;
		this.InputS = newInputS;
		this.OutputS = newOutputS;
	}

	//Describe how the client thread will execute and wok 
	@Override
	public void run() 
	{
		String choice;  	 //store the user chosen
		String unSortNum; 		//store the input data string
		String sort_number;  //store the data string after some process
		while (true) 
		{
			try 
			{
				//Guide user to enter in the correct form of data string  
				OutputS.writeUTF("Please enter a sequence of number separated by space :");
				OutputS.writeUTF("Please enter 'n' when you want to leave.");
				unSortNum = InputS.readUTF();	
				//If there is character 'n' read,the server will affirm the client is about to leave
				if (unSortNum.equals("n")) 
				{	//write down the activity to record 
					System.out.println("Client " + this.socket + " has quitted.");
					this.socket.close();
					break;
				}
				
				
				Scanner input = new Scanner(System.in);
				//If there is anything wrong in input 
				boolean flag=true;
				String [] arrInputFir=null;
				//Ignore the separate character " ",and store into a variable
				arrInputFir=unSortNum.split(" ");
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
				
				//The input fault occurs,check again to guide the user enter correctly next time
				while(flag==false) 
				{
					OutputS.writeUTF("Wrong entry!!!Please check again.");
					unSortNum=InputS.readUTF();
					
					String [] arrInputSec=null;
					arrInputSec=unSortNum.split(" ");
					
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
				
				
				//Offer the user to have a choice on three functions
				OutputS.writeUTF("Please choose which action you want to take :  ");
				OutputS.writeUTF("(1) Sorting from Small to Large.");
				OutputS.writeUTF("(2) Sorting from Large to Small.");
				OutputS.writeUTF("(3) Calculate Average.");
				
				choice = InputS.readUTF();	
				
				//choose 1 or 2 respectively represent
				//Sorting from small to large or inverse
				if(choice.equals("1")||choice.equals("2"))
				{
					String temp, sortedSL = "",sortedLS="";
					String[] arrayNum = null;
					
					
					arrayNum = unSortNum.split(" ");	
					//use two dimension array sort the number first
					for (int i = 0; i < arrayNum.length; i++) {
						for (int j = 0; j < arrayNum.length; j++) {
							if (Float.parseFloat(arrayNum[i]) < Float.parseFloat(arrayNum[j])) {
								temp = arrayNum[j];
								arrayNum[j] = arrayNum[i];
								arrayNum[i] = temp;
							}
						}
					}
					
					//Then depend on what the user want to see
					//sequence of number is from small to large in array after sorting previously 
					if(choice.equals("1"))
					{
						for (int s = 0; s < arrayNum.length; s++)
						{
							sortedSL += arrayNum[s]+" ";						
						}
						OutputS.writeUTF("The sequenceof number after sorting from Small to Large :");
						OutputS.writeUTF(sortedSL+" ");
					}
					
					else if(choice.equals("2"))
					{
						for(int k=arrayNum.length-1;k>=0;k--)
						{
							sortedLS += arrayNum[k]+" ";
						}
						OutputS.writeUTF("The sequenceof number after sorting from Large to Small :");
						OutputS.writeUTF(sortedLS);
					}
					
				}

				//Calculate the average value of number
				else if(choice.equals("3"))
				{
					float sum = 0; 
					String[] Num = null;
					String Result = "";  
					//Store the separated number in array
					Num = unSortNum.split(" ");	
					//use variable typed float to execute calculation
					for(int i=0;i<Num.length;i++)
						sum += Float.parseFloat(Num[i]);
					//division in the final step
					float total=sum/Num.length;
					Result=String.valueOf(total);
					//transfer data type and print the result out
					OutputS.writeUTF("The average value of sequence of number after calculating :");
					OutputS.writeUTF("Average is "+ Result + ".");
				}
				//The choice don't exist
				else
					OutputS.writeUTF("Wrong entry!!!Please check again.");
						
						
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//In the end of client use,close the I/O stream in thread
		try 
		{
			this.InputS.close();
			this.OutputS.close();

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
}
