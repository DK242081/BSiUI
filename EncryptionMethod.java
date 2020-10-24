
/**
 * Available encryption methods between
 * client and server: 
 * <p>
 * {@link #NONE},
 * {@link #XOR},
 * {@link #CEZAR},
 * 
 * @author Dominik Kinastowski
 */
public enum EncryptionMethod {
    NONE("none"), XOR("xor"), CEZAR("cezar");

    String value;

    EncryptionMethod(String value) {
        this.value = value;
    }
}
