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
        this.key = 121411;
        this.method = EncryptionMethod.CESAR;
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
        String encryptedMsg = "";
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) >= 65 && msg.charAt(i) < 91) {
                encryptedMsg = encryptedMsg + Character.toString((msg.charAt(i) - 65 + this.key) % 26 + 65);
                continue;
            }
            if (msg.charAt(i) >= 97 && msg.charAt(i) < 123) {
                encryptedMsg = encryptedMsg + Character.toString((msg.charAt(i) - 97 + this.key) % 26 + 97);
                continue;
            }
            encryptedMsg = encryptedMsg + Character.toString(msg.charAt(i));
        }
        return encryptedMsg;
    }

    private String CesarDecrypt(String msg) {
        String decryptedMsg = "";
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) >= 65 && msg.charAt(i) < 91) {
                decryptedMsg = decryptedMsg + Character.toString((msg.charAt(i) - 65 + (26 - this.key % 26)) % 26 + 65);
                continue;
            }
            if (msg.charAt(i) >= 97 && msg.charAt(i) < 123) {
                decryptedMsg = decryptedMsg + Character.toString((msg.charAt(i) - 97 + (26 - this.key % 26)) % 26 + 97);
                continue;
            }
            decryptedMsg = decryptedMsg + Character.toString(msg.charAt(i));
        }
        return decryptedMsg;
    }
}
