import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) throws IOException {
		//Set up to establish contact between Server and client
		ServerSocket sever = new ServerSocket(8591);
		//Whenever a new client connecting,work through the processes
		while (true) 
		{	//preparing for record the information about client
			Socket socket = null;
			
			try 
			{ 	//using object socket,obtain the relational information
				socket = sever.accept();
				//input,output streams that allowing fetch data from the bottom level
				//even if it is nothing to do with machine
				DataInputStream InputS = new DataInputStream(socket.getInputStream());
				DataOutputStream OutputS = new DataOutputStream(socket.getOutputStream());
				//Server side will record the log in activity from client
				System.out.println("A new client is connected : " + socket);
				//Set up a new thread aisle that provide the using of client
				Thread thread = new ClientThread(socket, InputS, OutputS);
				thread.start();
			} 
			//another side is to close the services of server,which the client may use
			catch (Exception e) 
			{
				socket.close();
				e.printStackTrace();
			}
		}
	}
}
