import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clientSocket = this.serverSocket.accept();
        this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        String greeting = this.in.readLine();
        System.out.println(greeting);
        out.println("hello form server");
    }

    public void stop() throws IOException {
        this.in.close();
        this.out.close();
        this.clientSocket.close();
        this.serverSocket.close();
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
