package patient;

import person.Person;

public class Patient extends Person {


    // Stores patient contact number
    private String contact;

    // Stores next of kin information
    private String nextOfKin;

    public Patient()
    {
    	
    	super();
    }
    
    // Constructor used when creating a new patient
    public Patient(String name, String id, String contact, String nextOfKin) {
        super(name,id); // send shared attributes to Person
        this.contact = contact;
        this.nextOfKin = nextOfKin;
    }

    
    //
    public void setContact(String contact) {
		this.contact = contact;
	}
    
    // Returns contact
    public String getContact() {
        return contact;
    }
     
    
    //
	public void setNextOfKin(String nextOfKin) {
		this.nextOfKin = nextOfKin;
	}

	

    // Returns next of kin
    public String getNextOfKin() {
        return nextOfKin;
    }

    // Displays patient information
    @Override
    public void display() {
    	super.display();// display inherited Person details
        System.out.println("Contact: " + contact);
        System.out.println("Next of Kin: " + nextOfKin);
    }
}