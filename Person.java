package person;

public class Person {

    private String name;
    private String id;

    public Person() {}

    public Person(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getId(){
        return id;
    }

    public void display(){
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
    }
}
   

