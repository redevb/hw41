import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoServer {
    private final int port;

    private EchoServer(int port) {
        this.port = port;
    }

    static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            try (var clientSocket = server.accept()) {
                handle(clientSocket);
            }
        } catch (IOException e) {
            String fmtMSg = "Most likely the port %s is busy.%n";
            System.out.printf(fmtMSg, port);
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) throws IOException {
        Scanner scan = new Scanner(System.in, "UTF-8");
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream());
             var isr = new InputStreamReader(socket.getInputStream(), "UTF-8")) {
            scan = new Scanner(isr);
            while (true) {
                var msg = scan.nextLine().strip();
                System.out.printf("GOT FROM CLIENT: %s%n", msg);
                String msgg = Reply.Act(msg);
                if (msgg == null) {
                    System.out.printf("SeYAA%n");
                    return;
                } else {
                    writer.write(msgg);
                    writer.write(System.lineSeparator());
                    writer.flush();
                }
            }
        } catch (NoSuchElementException e) {
            System.out.printf("CLIENT DROPPED TH CONNECTION%n");
        }
    }
}
