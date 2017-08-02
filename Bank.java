import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;

// Bank class
class Bank {
	static Scanner terminal = new Scanner(System.in);
// TODO:
// 
// 
// 
// 
//////////////////////////////// FILEDS ///////////////////////////////////////////
	private ArrayList<BankAccount> accounts;
	private String name;


//////////////////////////////// CONSTRUCTOR ///////////////////////////////////////////

	public Bank(String name){
		this.accounts = new ArrayList<BankAccount>();
		this.name = name;
	}


//////////////////////////////// GETTERS ///////////////////////////////////////////

	public String getBankName(){
		return name;
	}
	public ArrayList<BankAccount> getAccounts(){
		return accounts;
	}


//////////////////////////////// REGISTER ACCOUNT //////////////////////////////////////////

	// registerAccount(account) -- add a bank account. Make sure the username is not a duplicate. 
	// Validate fields where appropriate.
	public void registerAccount(BankAccount ba){
		// System.out.println("Register Account func in Bank class");
		// make sure username isnt duplicates
		// System.out.println(accountExists(ba));
		if((accountExists(ba.getUsername())) != -1){
			// System.out.println("Account already exists.");
			return;
		}
		// System.out.println(ba.getUsername() + ", your registered!");
		accounts.add(ba);
	}


//////////////////////////////// GET ACCOUNT ///////////////////////////////////////////

		// getAccount(userName, pin) -- 
		// return a BankAccount identified by the name userName (if found and confirmed with PIN). 
		// Return null otherwise (and print appropriate error messages).
		public BankAccount getAccount(String un, String p){
			// search accounts arraylist for matching username then confirm the password
			// initialize 
			BankAccount ba = null;
			// user exists, idx gets the index in accounts where the username exists
			int idx = accountExists(un);
			if(idx == -1){
				return ba;
			}

			boolean y = accounts.get(idx).confirmPIN();

			if(!y){
				return ba;
			}

			ba = accounts.get(idx);
			return ba;
		}


//////////////////////////////// DELETE ACCOUNT ///////////////////////////////////////////
		// terminateAccount(userName, pin) -- terminate (remove) a bank account. 
		// Print an error message if the username doesn't exist or the PIN is incorrect.
		public void terminateAccount(String un, String p){
			int idx = accountExists(un);
			if(idx == -1){
				System.out.println("-----Account not terminated.------");
				return;
			}
			// accounts exists so we get it and confirm pin is good
			BankAccount ba = accounts.get(idx);
			// remove account 
			accounts.remove(ba);
			return;
		}


//////////////////////////////// ACCOUNT EXISTS ///////////////////////////////////////////
		// accountExists(userName) -- return index of accounts ArrayList if account exists, returns -1 otherwise
		public int accountExists(String username){

			for (BankAccount ba : accounts) {
				if(ba.getUsername().equals(username)){
					return accounts.indexOf(ba);
				}
			}
			return -1;
		}


//////////////////////////////// CLEARBUFFER ///////////////////////////////////////////
		public static void clearBuffer(Scanner in){
			if(in.hasNextLine()){
				in.nextLine();
			}
		}


//////////////////////////////// READ / WRITE ///////////////////////////////////////////
	// writeToFile(out) -- a new method that will take a PrintWriter as an argument and write all of the bank accounts to it. 
	// Note that this method will just repeatedly call writeToFile(out) method of the BankAccount class for each BankAccount item 
	// in the accounts array.
	public void writeToFile(PrintWriter pw)throws Exception {
		// loop through array list
		for(int i = 0; i < accounts.size(); i++){
			if(accounts.get(i) == null) return;
			// write each account to specified file
			accounts.get(i).writeToFile(pw,accounts.get(i));
			System.out.println(accounts.get(i).toString());
		}
		System.out.println("Saved!");
	}
	// readFromFile(in) -- a new method that will take a Scanner as an argument and read all of the bank accounts from it. 
	// Note that this method will just repeatedly call readFromFile(in) method of the BankAccount class as long as there are lines 
	// remaining in the provided scanner.
	public void readFromFile(Scanner sc) throws Exception{

		if(!sc.hasNext()){
			System.out.println("empty file");
			return;
		}


		while(sc.hasNextLine()){
			// System.out.println(sc.nextLine());
			BankAccount ba = new BankAccount(sc);
			registerAccount(ba);
			System.out.println(ba.toString());

		}
		System.out.println("Loaded.");
		return;
	}


}