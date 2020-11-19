package ConsoleServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleServer {
    final int PORT = 11235;

    public ConsoleServer() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server UP!");
            socket = serverSocket.accept();
            System.out.println("Client connected");
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            new Thread(() -> {
                while (true) {
                    String str = in.nextLine();
                    if (str.equals("/end")) {
                        break;
                    }
                    System.out.println("Client: " + str);
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    Scanner scanner = new Scanner(System.in);
                    String str = scanner.nextLine();
                    out.println(str);
                    System.out.println("Myself: " + str);
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
