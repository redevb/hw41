import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoClient {
    private final int port;
    private final String host;

    private EchoClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public static EchoClient connectTo(int port) {
        var localhost = "127.0.0.1";
        return new EchoClient(port, localhost);
    }

    public void run() {
        // handle
        // and info about client
        System.out.printf("WRITE \"Bye\" TO QUIT%n%n");
        try (Socket socket = new Socket(host, port)) {
            directMessage(socket);
        } catch (IOException e) {
            System.out.printf("IMPOSSIBLE CONNECT TO %s:%s%n", host, port);
            e.printStackTrace();
        }
    }

    private void directMessage(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream());
             var isr = new InputStreamReader(socket.getInputStream(), "UTF-8")) {
            Scanner sc = new Scanner(isr);
            while (true) {
                String message = scanner.nextLine();
                writer.write(message);
                writer.write(System.lineSeparator());
                writer.flush();
                if ("bye".equalsIgnoreCase(message)) {
                    System.out.println("SEE YAAAAA");
                    return;
                }
                var msg = sc.nextLine().strip();
                System.out.printf("GOT FROM SERVER: %s%n", msg);
            }
        } catch (NoSuchElementException e) {
            System.out.println("CONNECTION DROPPED");
        }
    }
}
