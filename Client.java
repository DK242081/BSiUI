import java.net.*;
import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws UnknownHostException, IOException {
        this.clientSocket = new Socket(ip, port);
        this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
    }

    public void diffieHellman() throws JSONException, IOException {
        JSONObject jsonSendMsg = new JSONObject().put("request", "keys");
        this.out.println(jsonSendMsg.toString());
        JSONObject jsonReceivedMsg = new JSONObject(this.in.readLine());
        long a = 6;
        long p = (int)jsonReceivedMsg.get("p");
        long g = (int) jsonReceivedMsg.get("g");
        jsonSendMsg = new JSONObject().put("a", Math.pow(g, a) % p);
        this.out.println(jsonSendMsg.toString());
        long B = (int)new JSONObject(this.in.readLine()).get("b");
        long s = (long) Math.pow(B, a) % p;
    }

    public String sendMessage(String msg) {
        JSONObject jsonMsg = new JSONObject().put("msg", msg).put("from", "Mark");
        this.out.println(jsonMsg.toString());
        return msg;
    }

    public String receiveMessage() throws JSONException, IOException {
        JSONObject jsonMsg = new JSONObject(this.in.readLine());
        System.out.println(jsonMsg.getString("from") + ": " + jsonMsg.getString("msg"));
        return jsonMsg.getString("msg");
    }

    public void stopConnection() {
        try {
            this.in.close();
            this.out.close();
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.startConnection("127.0.0.1", 8080);
            client.diffieHellman();
            Scanner scanInput = new Scanner(System.in);
            System.out.print("me: ");
            client.sendMessage(scanInput.nextLine());
            while (client.receiveMessage() != "") {
                System.out.print("me: ");
                if (client.sendMessage(scanInput.nextLine()) == "") {
                    break;
                }
            }
            scanInput.close();
            client.stopConnection();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
    }
}