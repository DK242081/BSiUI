import java.net.*;
import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanInput; 

    public void start(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clientSocket = this.serverSocket.accept();
        this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.scanInput = new Scanner(System.in);
        while (receiveMessage() != "") {
            System.out.print("me: ");
            if(sendMessage(scanInput.nextLine()) == ""){
                break;
            }
        }
        scanInput.close();
        stop();
    }

    public String receiveMessage() throws JSONException, IOException {
        JSONObject jsonMsg = new JSONObject(this.in.readLine());
        System.out.println(jsonMsg.getString("from") + ": " + jsonMsg.getString("msg"));
        return jsonMsg.getString("msg");
    }

    public String sendMessage(String msg) {
        JSONObject jsonMsg = new JSONObject().put("msg", msg).put("from", "Daniel");
        this.out.println(jsonMsg.toString());
        return msg;
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
