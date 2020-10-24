import java.net.*;
import java.io.*;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port)  throws UnknownHostException, IOException{
        this.clientSocket = new Socket(ip, port);
        this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        this.out.println(msg);
        String response = this.in.readLine();
        return response;
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
            String response = client.sendMessage("hello from client");
            System.out.println(response);
        } catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
        };
    }
}