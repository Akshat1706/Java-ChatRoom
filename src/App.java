import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
   private static final int PORT = 5555;
   private static List<PrintWriter> clients = new ArrayList<>();
    
   public static void main(String[] args) {
    try {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Chat room is running on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            clients.add(writer);

            Thread clientHandler = new Thread(new ClientHandler (clientSocket, writer));
            clientHandler.start();
        } 
    } catch (IOException e) {
        e.printStackTrace();
    }
   }

   static class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter writer;

    public ClientHandler(Socket socket, PrintWriter writer) {
        this.clientSocket = socket;
        this.writer = writer;


    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(clientSocket.getInputStream());


            while (true) {
                String clientMessage = scanner.nextLine();
                broadcastMessage(clientMessage);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(String message) {
        for (PrintWriter client: clients) {
            client.println(message);
        }
    }
   }

}
