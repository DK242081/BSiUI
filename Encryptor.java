public class Encryptor {
    private EncryptionMethod method;
    private int key;

    public void setMethod(EncryptionMethod newMethod) {
        this.method = newMethod;
    }

    public void setKey(int newKey) {
        this.key = newKey;
    }

    public String encrypt(String msg) {
        switch (this.method) {
            case NONE:
                return msg;
            case XOR:
                String encryptedMsg = "";
                for (int i = 0; i < msg.length(); i++) {
                    encryptedMsg = encryptedMsg + Character.toString(msg.charAt(i) ^ (byte)(this.key % 128)); 
                }
                return encryptedMsg;
            case CEZAR:
                return msg;
            default:
                return msg;
        }
    }

    public String decrypt(String msg) {
        switch (this.method) {
            case NONE:
                return msg;
            case XOR:
                String decryptedMsg = "";
                for (int i = 0; i < msg.length(); i++) {
                    decryptedMsg = decryptedMsg + Character.toString(msg.charAt(i) ^ (byte)(this.key % 128)); 
                }
                return decryptedMsg;
            case CEZAR:
                return msg;
            default:
                return msg;
        }
    }
}
