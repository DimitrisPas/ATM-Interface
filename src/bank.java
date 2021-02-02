import java.util.ArrayList;
import java.util.Random;

public class bank {

	private String name;
	
	private ArrayList<user> users;
	
	private ArrayList<account> accounts;
	
	public bank(String name) {
		
		this.name = name;
		this.users = new ArrayList<user>();
		this.accounts = new ArrayList<account>();
	}
	
	//Generate a unique ID
	public String getNewUserUUID() {
		
		String uuid;
		Random rng = new Random();
		int len =6;
		boolean nonUnique;
		
		//looping until we get a unique ID
		do {
			uuid = "";
			for(int c = 0; c < len; c++ ) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			nonUnique = false;
			for(user u : this.users) {
				if(uuid.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while(nonUnique);
		
		return uuid;
		
	}
	
	public String getNewAccountUUID() {
	
		String uuid;
		Random rng = new Random();
		int len = 10 ;
		boolean nonUnique;
		
		//looping until we get a unique ID
		do {
			uuid = "";
			for(int c = 0; c < len; c++ ) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			nonUnique = false;
			for(account a : this.accounts) {
				if(uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while(nonUnique);
		
		return uuid;
		
		
	}
	
	public void addAccount(account anAcct) { 
		this.accounts.add(anAcct);
	}
	
	public user addUser(String firstName, String lastName, String pin) {
		
		user newUser = new user(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		//savings account for the user
		account newAccount = new account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser;
		}
	
	
	public user userLogin(String userID, String pin) {
		
		for(user u : this.users) {
			if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
		
				return u;
			}
		}
			//if we havent found the user or have an incorrect pin
		return null;
	}
	
	public String getName() {
		return this.name;
	}
	
	
}
