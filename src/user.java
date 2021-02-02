import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class user {
	
	//Info of the user.
	private String firstName;
	
	private String lastName;
	
	private String uuid;
	
	//MD5 hash of user's pin
	private byte pinHash[];
	
	private ArrayList<account> accounts;
	
	
	public user(String fn,String ln,String pin,bank theBank) {
		
		this.firstName = fn;
		this.lastName = ln;
		
		//store the pin's MD5 hash rather than the original value
	
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());

		 } catch (NoSuchAlgorithmException e) {
			System.out.println("error, no such algorith!");
			e.printStackTrace();
			System.exit(1);
		}
	
		//get a new, unique universal ID for the user
		this.uuid = theBank.getNewUserUUID();
		
		//Empty list of accounts
		this.accounts = new ArrayList<account>();
		
		//print message
		System.out.printf("New user %s, %s with ID %s created. \n", firstName, lastName, this.uuid);
		
		
	}

	public void addAccount(account anAcct) {
		this.accounts.add(anAcct);
	}
	
	public String getUUID() {
		return this.uuid;
	}
		public boolean validatePin(String aPin) {
			
			try {
			MessageDigest md = MessageDigest.getInstance("MD5");
				return MessageDigest.isEqual(md.digest(aPin.getBytes()), 
						this.pinHash);
			}catch(NoSuchAlgorithmException e) {
				System.out.println("error, no such algorith!");
				e.printStackTrace();
				System.exit(1);
			}
			return false;
		}

		
	public String getFirstName() {
		return this.firstName;
		
	}
	
	public void printAccountsSummary() {
		
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for(int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
	public int numAccounts() {
		return this.accounts.size();
	}
	public void printAcctTransHistory(int accIdx) {
		this.accounts.get(accIdx).printTransHistory();
	}
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();	
	}
	
	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}
	
	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
}
