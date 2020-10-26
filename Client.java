import java.net.*;
import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Encryptor encryptor;

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
        long p = (int) jsonReceivedMsg.get("p");
        long g = (int) jsonReceivedMsg.get("g");
        jsonSendMsg = new JSONObject().put("a", Math.pow(g, a) % p);
        this.out.println(jsonSendMsg.toString());
        long B = (int) new JSONObject(this.in.readLine()).get("b");
        long s = (long) Math.pow(B, a) % p;
        this.encryptor.setKey(s);
    }

    public String sendMessage(String msg) {
        JSONObject jsonMsg;
        if (msg.matches("encryption:.+")) {
            jsonMsg = new JSONObject().put("encryption", msg.split(":")[1]);
            System.out.println(msg.split(":")[1]);
            if (jsonMsg.getString("encryption").equals("xor")) {
                this.encryptor.setMethod(EncryptionMethod.XOR);
            } else if (jsonMsg.getString("encryption").equals("cesar")) {
                this.encryptor.setMethod(EncryptionMethod.CESAR);
            } else {
                this.encryptor.setMethod(EncryptionMethod.NONE);
            }
        } else {
            jsonMsg = new JSONObject()
                    .put("msg", Base64.getEncoder().encodeToString(this.encryptor.encrypt(msg).getBytes()))
                    .put("from", "Mark");
        }
        this.out.println(jsonMsg.toString());
        return msg;
    }

    public String receiveMessage() throws JSONException, IOException {
        JSONObject jsonMsg = new JSONObject(this.in.readLine());
        if (jsonMsg.has("encryption")) {
            System.out.println(jsonMsg.get("encryption"));
            if (jsonMsg.getString("encryption").equals("xor")) {
                this.encryptor.setMethod(EncryptionMethod.XOR);
            } else if (jsonMsg.getString("encryption").equals("cesar")) {
                this.encryptor.setMethod(EncryptionMethod.CESAR);
            } else {
                this.encryptor.setMethod(EncryptionMethod.NONE);
            }
            System.out.println("encryption changed to " + this.encryptor.getMethod().value);
            return "encryption";
        }
        System.out.print(jsonMsg.getString("from") + ": ");
        System.out.println(new String(Base64.getDecoder().decode(this.encryptor.decrypt(jsonMsg.getString("msg")))));
        return this.encryptor.decrypt(jsonMsg.getString("msg"));
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
            client.encryptor = new Encryptor(EncryptionMethod.NONE);
            client.diffieHellman();
            Scanner scanInput = new Scanner(System.in);
            System.out.println("===========================================================");
            System.out.println(
                    "To set encryption send msg in format \"encryption:method\" \nsupported methods: none, xor, cesar.");
            System.out.println("To close chat send empty message.");
            System.out.println("===========================================================");
            do {
                if (client.sendMessage(scanInput.nextLine()) == "") {
                    break;
                }
            } while (client.receiveMessage() != "");
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