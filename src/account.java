import java.util.ArrayList;

public class account {
	
	//Info about user's account
	private String name;
		
	private String uuid;
	
	private user holder;
	
	private ArrayList<transaction> transactions;

	
	public account(String name, user holder, bank theBank) {
		
		//set the account name and holder
		this.name = name;
		this.holder = holder;
		
		this.uuid = theBank.getNewAccountUUID();
		
		//transactions
		this.transactions = new ArrayList<transaction>();
		
	}
	
	public String getUUID() {
		return this.uuid;
	}
	public String getSummaryLine() {
		
		//get the account's balance
		double balance = this.getBalance();
		
		
		//format the summary line, depending on whether the balance is negative
		if(balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		}else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
		}
	}

	public double getBalance() {
		double balance = 0;
		for(transaction t : this.transactions) {
			balance += t.getAmount();
		}
				return balance;
	}
	
	//Print transaction history
	public void printTransHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for(int t = this.transactions.size()-1; t >= 0; t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}
	
	public void addTransaction(double amount, String memo) {
		transaction newTrans = new transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}
	
}
