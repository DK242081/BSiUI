import java.net.*;
import java.io.*;
import org.json.JSONObject;
import java.util.Scanner; 

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
        Scanner scanInput = new Scanner(System.in);
        
        JSONObject msg = new JSONObject(this.in.readLine());
        System.out.println(msg);
        msg.put("msg", scanInput.nextLine());
        this.out.println(msg.toString());
        // } while(msg.isNull("msg"));
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
