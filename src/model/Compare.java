package model;

import java.util.Comparator;

public class Compare implements Comparator<Contact> {
    public int compare(Contact c1, Contact c2){
        return c1.getBirthday().compareTo(c2.getBirthday());
    }
    public int compare2(Contact c1, Contact c2){
        return c1.getEmail().compareTo(c2.getEmail());
    }
}

