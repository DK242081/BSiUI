
/**
 * Available encryption methods between
 * client and server: 
 * <ul>
 * {@link #NONE},
 * {@link #XOR},
 * {@link #CESAR},
 * 
 */
public enum EncryptionMethod {
    NONE("none"), XOR("xor"), CESAR("cesar");

    String value;

    EncryptionMethod(String value) {
        this.value = value;
    }
}
