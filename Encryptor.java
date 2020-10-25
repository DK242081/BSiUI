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
                return msg;
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
                return msg;
            case CEZAR:
                return msg;
            default:
                return msg;
        }
    }
}
