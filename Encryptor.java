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
                return XOREncrypt(msg);
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
                return XOREncrypt(msg);
            case CEZAR:
                return msg;
            default:
                return msg;
        }
    }

    private String XOREncrypt(String msg) {
        String encryptedMsg = "";
        for (int i = 0; i < msg.length(); i++) {
            encryptedMsg = encryptedMsg + Character.toString(msg.charAt(i) ^ (byte)(this.key % 128)); 
        }
        return encryptedMsg;
    }

    // private String CesarEncode
}
