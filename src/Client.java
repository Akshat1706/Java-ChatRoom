import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Connected To The Server");

            Scanner serverInput  = new Scanner(socket.getInputStream());
            PrintWriter clientOutput = new PrintWriter(socket.getOutputStream(), true);


            Thread serverListener = new Thread(() -> {
                while (true) {
                    String message = serverInput.nextLine();
                    System.out.println("Server: " + message);
                    
                }
            });

            serverListener.start();

            Scanner userInput = new Scanner(System.in);
            while (true) {
                String message = userInput.nextLine();
                clientOutput.println(message);
                
            }
            
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }
   
   

}
