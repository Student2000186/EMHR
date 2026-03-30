//EMHR Group PROJECT//
// Rohan Edmond 2000186
// Ryan Cole	2106111
// Malachi-Shavario Shouta	2404920
// Trev-ann Cameron	2405379
//Gabriel Francis	2205720
package patient;

import person.Person;

public class Patient extends Person {

    private String contact;
    private String nextOfKin;

    public Patient() {
        super();
    }

    public Patient(String name, String id, String contact, String nextOfKin) {
        super(name, id);
        this.contact = contact;
        this.nextOfKin = nextOfKin;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public String toFileString() {
        return escape(getId()) + "|" + escape(getName()) + "|" + escape(contact) + "|" + escape(nextOfKin);
    }

    public static Patient fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 4) {
            return null;
        }
        return new Patient(unescape(parts[1]), unescape(parts[0]), unescape(parts[2]), unescape(parts[3]));
    }

    private static String escape(String value) {
        return value == null ? "" : value.replace("|", "/");
    }

    private static String unescape(String value) {
        return value == null ? "" : value;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Contact: " + contact);
        System.out.println("Next of Kin: " + nextOfKin);
    }
}
