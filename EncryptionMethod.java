
/**
 * Available encryption methods between
 * client and server: 
 * <p>
 * {@link #NONE},
 * {@link #XOR},
 * {@link #CESAR},
 * 
 * @author Dominik Kinastowski
 */
public enum EncryptionMethod {
    NONE("none"), XOR("xor"), CESAR("cesar");

    String value;

    EncryptionMethod(String value) {
        this.value = value;
    }
}
