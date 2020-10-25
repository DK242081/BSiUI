import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        while(true){
            new ClientHandler(serverSocket.accept()).start();;
        }
        // stop();
    }

    public void stop() throws IOException {
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
