//EMHR Group PROJECT//
// Rohan Edmond 2000186
// Ryan Cole	2106111
// Malachi-Shavario Shouta	2404920
// Trev-ann Cameron	2405379
//Gabriel Francis	2205720
package person;

public class Person {

    private String name;
    private String id;

    public Person() {
    }

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void display() {
        System.out.println("\n===== Display =====");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
    }
}


