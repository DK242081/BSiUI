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
            case CESAR:
                return CesarEncrypt(msg);
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
            case CESAR:
                return CesarDecrypt(msg);
            default:
                return msg;
        }
    }

    private String XOREncrypt(String msg) {
        String encryptedMsg = "";
        for (int i = 0; i < msg.length(); i++) {
            encryptedMsg = encryptedMsg + Character.toString(msg.charAt(i) ^ (byte) (this.key % 128));
        }
        return encryptedMsg;
    }

    private String CesarEncrypt(String msg) {
        System.out.println(this.key % 26);
        String encryptedMsg = "";
        for (int i = 0; i < msg.length(); i++) {
            encryptedMsg = encryptedMsg + Character.toString((msg.charAt(i) - 65 + this.key) % 26 + 65);
        }
        return encryptedMsg;
    }

    private String CesarDecrypt(String msg) {
        String decryptedMsg = "";
        for (int i = 0; i < msg.length(); i++) {
            decryptedMsg = decryptedMsg + Character.toString((msg.charAt(i) - 65 + (26 - this.key % 26)) % 26 + 65);
        }
        return decryptedMsg;
    }
}
