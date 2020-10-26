public class Encryptor {
    private EncryptionMethod method;
    private int key;

    public Encryptor (EncryptionMethod method) {
        this.method = method;
    }

    /**
     * Setter for encryption method
     * 
     * @param newMethod new method value
     */
    public void setMethod(EncryptionMethod newMethod) {
        this.method = newMethod;
    }

    /**
     * Getter for encryption method
     * 
     * @return encryption method attribute
     */
    public EncryptionMethod getMethod() {
        return this.method;
    }

    /**
     * Setter for key
     * 
     * @param newKye new key value
     */
    public void setKey(long s) {
        this.key = (int) s;
    }

    /**
     * Method for encrypting string. Uses encryption method and key from object
     * attributes
     * 
     * @param msg string to be encrypted
     * @return encrypted string
     */
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

    /**
     * Method for decrypting string. Uses decryption method and key from object
     * attributes
     * 
     * @param msg string to be decrypted
     * @return decrypted string
     */
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

    /**
     * Method for encrypting/decrypting string using XOR method and key from object
     * attribute
     * 
     * @param msg string to be encrypted/decrypted
     * @return encrypted/decrypted string accordingly
     */
    private String XOREncrypt(String msg) {
        String encryptedMsg = "";
        for (int i = 0; i < msg.length(); i++) {
            encryptedMsg = encryptedMsg + Character.toString(msg.charAt(i) ^ (byte) (this.key % 128));
        }
        return encryptedMsg;
    }

    /**
     * Method for encrypting string using Cesar cypher and key from object attribute
     * 
     * @param msg string to be encrypted
     * @return encrypted string
     */
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

    /**
     * Method for decrypting string using Cesar cypher and key from object attribute
     * 
     * @param msg string to be decrypted
     * @return decrypted string
     */
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
