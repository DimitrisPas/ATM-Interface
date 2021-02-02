import java.util.Scanner;

public class atm {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		bank theBank = new bank("Bank of Swag");
		
		//add user to bank which also creates a savings acc
		user aUser = theBank.addUser("John", "Doe", "1234");
		
		//checking account
		account newAccount = new account("Checking",aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		user curUser;
		while(true) {
			//stay in the login prompt until successful login
			curUser = atm.mainMenuPrompt(theBank, sc);
			
			//stay in the menu until user quits
			atm.printUserMenu(curUser, sc);
			
		}
	}
	
public static user mainMenuPrompt(bank theBank, Scanner sc) {
	
	String userID;
	String pin;
	user authUser;
	
	//prompt the user for id/pin until correct one is reached
	do {
		
		System.out.printf("Welcome to %s\n\n", theBank.getName());
		System.out.printf("Enter user ID: ");
		userID = sc.nextLine();
		System.out.printf("\"Enter pin: ");
		pin = sc.nextLine();
		
		//try to get the user object corresponding to the id/pin combo
		authUser = theBank.userLogin(userID, pin);
		if(authUser == null) {
			System.out.println("Incorrect ID/PIN combination. " + "Please try again.");
			
		}
		
	}while(authUser == null); //continue looping until successful login
		return authUser;
  }

public static void printUserMenu(user theUser, Scanner sc) {
	//print a summary of the user's account
	theUser.printAccountsSummary();
	
	int choice;
	
	//user menu
	do {
		System.out.printf("Welcome %s, what would you like to do?\n",
				theUser.getFirstName());
		System.out.println(" 1) Show account's transaction history");
		System.out.println(" 2) Withdrawl");
		System.out.println(" 3) Deposit");
		System.out.println(" 4) Transfer");
		System.out.println(" 5) Quit");
		System.out.println();
		System.out.print("Enter choice: ");
		choice = sc.nextInt();
		
		if(choice < 1 || choice > 5) {
			System.out.println("Invalid choice, choose 1-5");
		}
	}while(choice < 1 || choice > 5);
	
	//process the choice
	switch(choice) {
	
	case 1:
		atm.showTransHistory(theUser, sc);
		break;
	case 2:
		atm.withdrawlFunds(theUser, sc);
		break;
	case 3:
		atm.depositFunds(theUser, sc);
		break;
	case 4:
		atm.transferFunds(theUser, sc);
		break;
	case 5:
		sc.nextLine();
		break;
	}
	
	//redisplay this menu unless user wants to quit
	if(choice != 5) {
		atm.printUserMenu(theUser, sc);
	}
 }
public static void showTransHistory(user theUser, Scanner sc) {
	
	int theAcct;
	
	// get account whose transaction history to look at 
	do {
		System.out.printf("Enter the number (1-%d) of the account\n " + "whose transactions you want to see: ",
						theUser.numAccounts());
		theAcct = sc.nextInt()-1;
		if(theAcct < 0 || theAcct >= theUser.numAccounts()) {
			System.out.println("Invalid account. Please try again.");
		}
	}while(theAcct < 0 || theAcct >= theUser.numAccounts());
	
	//print transaction history
	theUser.printAcctTransHistory(theAcct);
  }

public static void transferFunds(user theUser, Scanner sc) {
	
	int fromAcct;
	int toAcct;
	double amount;
	double acctBal;
	
	//get the account to transfer from
	do{
		System.out.printf("Enter the number (1-%d) of the account\n" + "to tranfer from: ",theUser.numAccounts());
		fromAcct = sc.nextInt()-1;
		if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
			
			System.out.println("Invalid account. Please try again.");
		}
		
	}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
	acctBal = theUser.getAcctBalance(fromAcct);
	
	//get the account to tranfer to
	do{
		System.out.printf("Enter the number (1-%d) of the account\n" + "to tranfer to: ",theUser.numAccounts());
		toAcct = sc.nextInt()-1;
		if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
			
			System.out.println("Invalid account. Please try again.");
		}
		
	}while(toAcct < 0 || toAcct >= theUser.numAccounts());
	
	//amount to transfer
	do {
		System.out.printf("Enter the amount to tranfer (max $%.02f): $",acctBal);
		amount = sc.nextDouble();
		if(amount < 0) {
			System.out.println("Amount must be greater than zero!");
		}else if(amount > acctBal) {
			System.out.printf("Amount must not be greater than your balance of $%.02f.\n",acctBal);
		}
	}while(amount < 0 || amount > acctBal);
	
	//do the transfer
	theUser.addAcctTransaction(fromAcct, -1*amount, String.format("transfer to account %s", theUser.getAcctUUID(toAcct)));
	
	theUser.addAcctTransaction(toAcct, amount, String.format("transfer to account %s", theUser.getAcctUUID(fromAcct)));

  }
	public static void withdrawlFunds(user theUser, Scanner sc) {
		
        int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		//get the account to transfer from
		do{
			System.out.printf("Enter the number (1-%d) of the account\n" + "to withdraw from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				
				System.out.println("Invalid account. Please try again.");
			}
			
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		do {
			System.out.printf("Enter the amount to tranfer (max $%.02f): $",acctBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero!");
			}else if(amount > acctBal) {
				System.out.printf("Amount must not be greater than your balance of $%.02f.\n",acctBal);
			}
		}while(amount < 0 || amount > acctBal);
		
		sc.nextLine();
		System.out.println("Enter a memo:");
		memo = sc.nextLine();
		
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
	}
	
	public static void depositFunds(user theUser, Scanner sc) {
		
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		
		//get the account to transfer from
		do{
			System.out.printf("Enter the number (1-%d) of the account\n" + "to deposit in: ",theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				
				System.out.println("Invalid account. Please try again.");
			}
			
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(toAcct);
		
		do {
			System.out.printf("Enter the amount to tranfer (max $%.02f): $",acctBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero!");
			}
		}while(amount < 0);
		
		sc.nextLine();
		System.out.print("Enter a memo:");
		memo = sc.nextLine();
		
		theUser.addAcctTransaction(toAcct, amount, memo);
	}
	
}
	

