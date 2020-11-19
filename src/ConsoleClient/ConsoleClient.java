package ConsoleClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient {
    public static void main(String[] args) {
        final String SERVER = "127.0.0.1";
        final int PORT = 11235;
        Socket socket = null;
        Scanner in;
        PrintWriter out;

        try {
            socket = new Socket(SERVER, PORT);
            in = new Scanner(socket.getInputStream());
            out  = new PrintWriter(socket.getOutputStream());

            new Thread(() -> {
                while (true) {
                    String str = in.nextLine();
                    if (str.equals("/end")) {
                        break;
                    }
                    System.out.println("Server: " + str);
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
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
