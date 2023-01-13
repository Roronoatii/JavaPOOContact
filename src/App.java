import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Predicate;

import model.Compare;
import model.Contact;

public class App {

    private static Scanner _scan =  new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        while(true){
            afficherMenu();
            String choix = _scan.nextLine();
            switch(choix){
                case "1":
                    addcontact();
                    break;
                case "2":
                    contactslisting();
                    break;
                case "3":
                    Contact.chercherPrenom();
                    break;
                case "4":
                    System.out.print("DDN ? : ");
                    Contact.chercherDDN(_scan.nextLine());
                    break;
                case "5":
                    System.out.println("Entrer adresse mail:");
                    edit(_scan.nextLine());
                    break;
                case "6":
                    System.out.print("Email ? : ");
                    contactsuppr(_scan.nextLine());
                    break;
                case "7":
                    triDDN();
                    break;
                case "8":
                    triNOM();
                    break;
                case "9":
                    triPRENOM();
                    break;
                case "10":
                    triMAIL();
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Entrez un caractère valide");
                    break;
            }
        }
    }



    private static void afficherMenu() {
        ArrayList<String> menus = new ArrayList<>();
        menus.add("--------------- MENU ---------------");
        menus.add("/ 1- Ajouter un contact            /");
        menus.add("/ 2- Lister les contacts           /");
        menus.add("/ 3- Chercher contact avec prénom  /");
        menus.add("/ 4- Chercher contact avec DDN     /");
        menus.add("/ 5- Modifier contact              /");
        menus.add("/ 6- Supprimer contact             /");
        menus.add("/ 7- Tri DDN                       /");
        menus.add("/ 8- Tri NOM                       /");
        menus.add("/ 9- Tri PRENOM                    /");
        menus.add("/ 10- Tri MAIL                     /");
        menus.add("/ q- quitter                       /");
        menus.add("------------------------------------");
        for(String menu : menus){
            System.out.println(menu);
        }


    }

    private static void addcontact() throws IOException{
        Contact c =  new Contact();
        System.out.print("Enter first name: ");
        Scanner input = new Scanner(System.in);
        c.setFirstname(input.nextLine());
        System.out.print("Enter last name: ");
        c.setLastname(input.nextLine());

        while(true){
            try{
                System.out.print("Enter email: ");
                c.setEmail(input.nextLine());
                break;
            }catch(ParseException e){
                System.out.println("Incorrect email");
            }
        }

        while(true){
            try{
                System.out.print("Enter number: ");
                c.setNumber(input.nextLine());
                break;
            }catch(ParseException e){
                System.out.println("Incorrect number");
            }
        }

        while(true){

            try{
                System.out.print("Enter birthdate: ");
                c.setBirthday(input.nextLine());
                break;
            }catch(ParseException e){
                System.out.println("Incorrect birthdate");
            }
        }

        c.enregistrer();
        System.out.println("Contact added");

    }
    private static void contactslisting() throws IOException{
        ArrayList<Contact> list = Contact.lister();
        String str = list.toString().replaceAll(",","\n").replaceAll(";"," ");
        System.out.println(str);
    }
    private static void contactsuppr(String contacttosupr) throws IOException{
        ArrayList<Contact> list = Contact.lister();
        Predicate<Contact> condition = contact -> contact.getEmail().startsWith(contacttosupr);

        list.removeIf(condition);
        Contact.refreshlist(list);
        System.out.println(list);
    }
    public static void triDDN()throws IOException{
        try{
            ArrayList<Contact> list = Contact.lister();
            Compare compare = new Compare();
            System.out.println(compare.toString());
            Collections.sort(list, compare);
            String str = list.toString().replaceAll(",","\n").replaceAll(";"," ");
            System.out.println(str);
        }catch(IOException e){
            System.out.println("Error");
        }
    }
    public static void triNOM() throws IOException{
        try{
            ArrayList<Contact> list = Contact.lister();
            Collections.sort(list, new Comparator<Contact>() {
                @Override
                public int compare(Contact c1, Contact c2) {
                    return c1.getLastname().compareTo(c2.getLastname());
                }
            });
            String str = list.toString().replaceAll(",", "\n").replaceAll(";", " ");
            System.out.println(str);
        }catch (IOException exception){
            System.out.println("Error");
        }
    }

    public static void triPRENOM() throws IOException{
        try{
            ArrayList<Contact> list = Contact.lister();
            Collections.sort(list, new Comparator<Contact>() {
                @Override
                public int compare(Contact c1, Contact c2) {
                    return c1.getFirstname().compareTo(c2.getFirstname());
                }
            });
            String str = list.toString().replaceAll(",", "\n").replaceAll(";", " ");
            System.out.println(str);
        }catch (IOException exception){
            System.out.println("Error");
        }
    }

    public static void triMAIL() throws IOException{
        try{
            ArrayList<Contact> list = Contact.lister();
            Collections.sort(list, new Comparator<Contact>() {
                @Override
                public int compare(Contact c1, Contact c2) {
                    return c1.getEmail().compareTo(c2.getEmail());
                }
            });
            String str = list.toString().replaceAll(",", "\n").replaceAll(";", " ");
            System.out.println(str);
        }catch (IOException exception){
            System.out.println("Error");
        }
    }
    private static void edit(String contacttoedit) throws IOException, Exception{
        try {
            ArrayList<Contact> list = Contact.lister();
            for (Contact contact : list) {
                System.out.println(contact);
                String str = contact.toString();
                String[] contactList = str.split(";");
                System.out.println(contactList[2]);
                if (contactList[2].equals(contacttoedit)){
                    System.out.println("fzfzzefzef");
                    String[] table = contactList;
                    contactsuppr(contacttoedit);
                    list.add(addeditedcontact(table));
                }
            }
            Contact.refreshlist(list);
            System.out.println("Contact edited");
        } catch (IOException exception) {
            System.out.println("Error");
        }catch (Exception exception) {
            System.out.println("Error");
        }
    }
    private static Contact addeditedcontact(String[] contacttoedit) throws ParseException{
        Contact contact = new Contact();

        System.out.println("Enter last name :");
        System.out.println(contacttoedit[0]);
        String name = _scan.nextLine();
        if(name == ""){
            contact.setLastname(contacttoedit[0]);
        }else{
            contact.setLastname(name);
        }

        System.out.println("Enter name :");
        System.out.println(contacttoedit[1]);
        String firstname = _scan.nextLine();
        if(firstname == ""){
            contact.setFirstname(contacttoedit[1]);
        }else{
            contact.setFirstname(firstname);
        }

        do {
            try {
                System.out.println("Enter number");
                System.out.println(contacttoedit[3]);
                String number = _scan.nextLine();
                if(number == ""){
                    contact.setNumber(contacttoedit[3]);
                }else{
                    contact.setNumber(number);
                }
                break;
            } catch (Exception exception) {
                System.out.println("Error");
            }
        } while (true);

        do {
            try {
                System.out.println("Saisir le mail :");
                System.out.println(contacttoedit[2]);
                String mail = _scan.nextLine();
                if(mail == ""){
                    contact.setEmail(contacttoedit[2]);
                }else{
                    contact.setEmail(mail);
                }
                break;
            } catch (ParseException exception) {
                System.out.println("Error");
            }
        } while (true);
        do {
            try {
                System.out.println("Birthday :");
                System.out.println(contacttoedit[4]);
                String date = _scan.nextLine();
                if(date == ""){
                    contact.setBirthday(contacttoedit[4]);
                }else{
                    contact.setBirthday(date);
                }
                break;
            } catch (ParseException exception) {
                System.out.println("invalid birthday");
            }
        } while (true);

        try {
            contact.enregistrer();
            System.out.println("Contact registered");
        } catch (IOException exception) {
            System.out.println("Error");
        }
        return contact;
    }




}