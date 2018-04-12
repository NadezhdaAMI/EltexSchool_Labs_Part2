package ru.mitina.orders;

import java.io.Serializable;
import java.util.UUID;

public class Credentials implements Serializable {

    public UUID IDClient;
    public String Surname;
    public String Name;
    public String MiddleName;
    public String Email;

    public UUID getIDClient() {
        return IDClient;
    }

    @Override
    public String toString() {
        return /*IDClient + ", " + */ Surname + ", "+ Name + ", "+ MiddleName + ", "+ Email;
    }
}
