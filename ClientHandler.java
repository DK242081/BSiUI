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
    private Encryptor encryptor;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.encryptor = new Encryptor();
            this.diffieHellman();
            this.scanInput = new Scanner(System.in);
            while (receiveMessage() != "") {
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
        JSONObject jsonSendMsg = new JSONObject().put("p", (long) 23).put("g", (long) 5);
        this.out.println(jsonSendMsg.toString());
        long b = 15;
        long p = 23;
        long g = 5;
        long A = (int) new JSONObject(this.in.readLine()).get("a");
        long s = (long) Math.pow(A, b) % p;
        jsonSendMsg = new JSONObject().put("b", Math.pow(g, b) % p);
        this.out.println(jsonSendMsg.toString());
        this.encryptor.setKey(s);
    }

    public String sendMessage(String msg) {
        JSONObject jsonMsg;
        if (msg.matches("encryption:.+")) {
            jsonMsg = new JSONObject().put("encryption", msg.split(":")[1]);
            if (jsonMsg.get("encryption") == "xor") {
                this.encryptor.setMethod(EncryptionMethod.XOR);
            } else if (jsonMsg.get("encryption") == "cesar") {
                this.encryptor.setMethod(EncryptionMethod.CESAR);
            } else {
                this.encryptor.setMethod(EncryptionMethod.NONE);
            }
        } else {
            jsonMsg = new JSONObject().put("msg", this.encryptor.encrypt(msg)).put("from", "Mark");
        }
        this.out.println(jsonMsg.toString());
        return msg;
    }

    public String receiveMessage() throws JSONException, IOException {
        JSONObject jsonMsg = new JSONObject(this.in.readLine());
        if (jsonMsg.has("encryption")) {
            if (jsonMsg.get("encryption") == "xor") {
                this.encryptor.setMethod(EncryptionMethod.XOR);
            } else if (jsonMsg.get("encryption") == "cesar") {
                this.encryptor.setMethod(EncryptionMethod.CESAR);
            } else {
                this.encryptor.setMethod(EncryptionMethod.NONE);
            }
            System.out.println("encryption changed to " + this.encryptor.getMethod().value);
            return "encryption";
        }
        System.out.println(jsonMsg.getString("from") + ": " + this.encryptor.decrypt(jsonMsg.getString("msg")));
        return this.encryptor.decrypt(jsonMsg.getString("msg"));
    }
}
