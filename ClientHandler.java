import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanInput; 
 
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.scanInput = new Scanner(System.in);
            while (receiveMessage() != "") {
                System.out.print("me: ");
                if(sendMessage(scanInput.nextLine()) == ""){
                    break;
                }
            }
            this.clientSocket.close();
            this.in.close();
            this.out.close();
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
