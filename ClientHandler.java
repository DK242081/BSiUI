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
            this.diffieHellman();
            this.scanInput = new Scanner(System.in);
            JSONObject msg;
            msg = new JSONObject(this.in.readLine());
            while (receiveMessage() != "") {
                System.out.print("me: ");
                if (sendMessage(scanInput.nextLine()) == "") {
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

    public void diffieHellman() throws JSONException, IOException {
        JSONObject jsonReceivedMsg = new JSONObject(this.in.readLine());
        JSONObject jsonSendMsg = new JSONObject().put("p", (long)23).put("g", (long)5);
        this.out.println(jsonSendMsg.toString());     
        long b = 15;
        System.out.println("b " + b);
        long p = 23;
        System.out.println("p " + p);
        long g = 5;
        System.out.println("g " + g);
        long A = (int) new JSONObject(this.in.readLine()).get("a");
        System.out.println("A " + A);
        long s = (long) Math.pow(A, b) % p;
        jsonSendMsg = new JSONObject().put("b", Math.pow(g, b) % p);
        this.out.println(jsonSendMsg.toString());     
        System.out.println("server secret: " + s);
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
