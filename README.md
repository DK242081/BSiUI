Wymagania systemowe:
    - Java SE 15

Kompilacja i wywołanie:
    Kompilacja serwera: 
        javac -cp json-20200518.jar ClientHandler.java EncryptionMethod.java Encryptor.java  Server.java
    Wywołanie serwera:
        java -classpath .:json-20200518.jar Server 
    Kompilacja klienta:
        javac -cp json-20200518.jar EncryptionMethod.java Encryptor.java  Client.java
    Wywołanie klienta:
        java -classpath .:json-20200518.jar Client

Dla ułatwienia kompilacji został również załączony użyty plik jar.

W pierwszej kolejności należy wywołać serwer, uruchamianie Klienta bez aktywnego serwera na zadanym porcie
nie zostało zabezpieczone.

Projekt jest implementacją prostego czatu zabezpieczonego protokołem Diffiego-Hellmana. W aktualnej wersji
program ma stale zakodowane parametry DH, ale przy niewielkich zmianach można ustawić ich dynamiczne definiowanie, co znacząco usprawni zabezpieczenie komunikacji.
