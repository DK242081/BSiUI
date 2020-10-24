public enum EncryptionMethod {
    NONE("none"), XOR("xor"), CEZAR("cezar");

    String value;

    EncryptionMethod(String value) {
        this.value = value;
    }
}
