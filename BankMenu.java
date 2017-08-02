import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

	class BankMenu{

	public static Scanner terminal = new Scanner(System.in);

	///////////////////////////////////////////////// LANDING MENU ////////////////////////////////////////////////////////////
		// landingMenu(bank)
		// This method should show this menu:
		//
		// Log in
		// Sign up
		// Terminate account
		// Exit
		public static void landingMenu(Bank b) throws Exception{
			
			boolean exit = false;
			String userInput;

			while(!exit){
				printLandingMenu(b);
				userInput = terminal.nextLine().trim();

				switch(userInput){
					case "1":
						loginMenu(b);
						break;
					case "2":
						signupMenu(b);
						break;
					case "3": 
						terminateAccountMenu(b);
						break;
					case "4": 
						saveToFileMenu(b);
						break;
					case "5": 
						loadFromFileMenu(b);
						break;
					case "6":
						exit = true;
						break;
				}
			}
		}
		public static void printLandingMenu(Bank b){
			System.out.println("------------------------------");
			System.out.println("Welcome to " + b.getBankName() + ". What would you like to do? ");
			System.out.println("------------------------------");
			System.out.println("1. LOG IN");
			System.out.println("2. SIGN UP");
			System.out.println("3. Terminate Account");
			System.out.println("4. Save Bank Account List to File");
			System.out.println("5. Load Bank Account List from a File");
			System.out.println("6. EXIT");
			System.out.println("------------------------------");

		}
	// 
	// 
	///////////////////////////////////////////////// LOG IN MENU /////////////////////////////////////////////////////////////
		// loginMenu(bank)
		// This method should prompt the user for their username and PIN. 
		// It should enter the main menu if the entered information is correct, 
		// and it should return to login menu otherwise.
		public static void loginMenu(Bank b){
			boolean exit = false;
			String userInput;
			while(!exit){

				printLoginMenu(b);
				userInput = terminal.nextLine();

				switch(userInput.trim()){
					case "1": 
						login(b);
						break;
					case "2": 
						exit = true;
						break;
					default: 
						System.out.println("You gotta choose one of the options listed. ");
						break;
				}
			}	
		}

		public static void printLoginMenu(Bank b){
			System.out.println("------------------------------");
			System.out.println("1. LOG IN to " + b.getBankName()); 
			System.out.println("2. EXIT");
			System.out.println("------------------------------");
		}

		public static void login(Bank b){

			System.out.print("USERNAME: ");
			String username = terminal.nextLine();
			while(!BankAccount.isValidUsername(username) ){
				System.out.print("USERNAME: ");
				username = terminal.nextLine();
			}

			System.out.print("PIN: ");
			String pin = terminal.nextLine().trim();

			while(!isNumeric(pin)){
				System.out.print("PIN: ");
				pin = terminal.nextLine().trim();
			}

			// if input isnt number or contains letters or is longer than 4 digits --> keep asking 
			while(!BankAccount.isValidPIN(pin) && !invalidPIN(pin)){
				System.out.print("Invalid. \nPIN: ");
				pin = terminal.nextLine();
			}
			// if account exists get the account and compare pins
			if(b.accountExists(username) == -1){
				// System.out.println("HEEEEEERRRRE" + pin);
				System.out.println("Account does not exist. ");
				return;
			}
			//compare pins of account and 
			BankAccount ba = b.getAccount(username,pin);
			if(ba == null){
				return;
			}

			mainMenu(ba);
		}
	// 
// TODO: Work on pin handling during sign up	
	///////////////////////////////////////////////// SIGN UP MENU ////////////////////////////////////////////////////////////
		// signupMenu(bank)
		// This method should prompt the user to enter all of the necessary details to register a new account. 
		// For the username, the method should search the bank to make sure that such username hasn't already been registered.
		public static void signupMenu(Bank b){
			
			System.out.println("----------------------");
			System.out.println("Welcome to " + b.getBankName() + ". Lets sign you up.");
			System.out.println("----------------------");

			System.out.print("What is your first name? ");
			String firstName = terminal.nextLine();		
			System.out.println("----------------------");

			// lastName
			System.out.print("What is your last name? ");
			String lastName = terminal.nextLine();
			System.out.println("----------------------");

			// address
			System.out.print("What is your address? ");
			String address = terminal.nextLine();
			System.out.println("----------------------");

			// username
			System.out.print("Chose your username: ");
			String username = terminal.nextLine();
			

			// // For the username, the method should search the bank to make sure that such username hasn't already been registered.
			// while(b.accountExists(username)){
			// 	System.out.println("Username already exists, try something else: ");
			// 	username = terminal.nextLine();
			// }
			System.out.println("----------------------");
			// pin
			System.out.println("Enter desired 4 digit PIN: ");
			String pin = terminal.nextLine().trim();
			while(!isNumeric(pin)){
				System.out.print("Enter 4 digit PIN, please: ");
				pin = terminal.nextLine();
			}

			while(!BankAccount.isValidPIN(pin)){
				System.out.print("Enter 4 digit PIN, please: ");
				pin = terminal.nextLine();
			}
			System.out.println("----------------------");

			// register account
			BankAccount ba = new BankAccount(firstName,lastName);
			ba.setAddress(address);
			ba.setUsername(username);
			ba.setPIN(pin);
			if(!ba.confirmPIN()){
				System.out.println("PIN confirmation incorrect, try siging up again soon.");
				return;
			}
			mainMenu(ba);
		}	
	// 
	//
	///////////////////////////////////////////////// TERMINATE ACCOUNT MENU //////////////////////////////////////////////////

		// terminateAccountMenu(bank)
		// This method shoud prompt the user to enter account username and PIN. 
		// A final confirmation dialog ("Are you sure you want to terminate your account? [Y/N]") 
		// should be printed before terminating (removing) the account.
		public static void terminateAccountMenu(Bank b){
			System.out.println("------------------------------");
			System.out.println("Before we delete your account, enter your username and password. Or enter 0 anytime to exit.");

			// prompt useername and passowrd and check if the account exists
			System.out.print("USERNAME:  ");
			String username = terminal.nextLine();
			if(username.equals("0"))return;
			System.out.print("PIN:  ");
			String pin = terminal.nextLine().trim();
			if(pin.equals("0"))return;

			// System.out.println("------------------------------");
			//account is found and you compare pins
			BankAccount ba= null;

			ba = b.getAccount(username,pin);	

			
			if(ba == null){
				System.out.println("Account does not exist, try again");
				return;
			}
			System.out.print("Are you sure you want to terminate your account? \n1. Yes \n2. No ");
			String userInput = terminal.nextLine().trim();
		
			boolean exit = false;
			while(!exit){

				switch(userInput){

					case "1": 
						b.terminateAccount(username, pin);
						System.out.println("Account Terminated.");
						return;
						// break;
					case "2":
						exit = true;
						System.out.println("Bye.....");
						break;
					default:
						System.out.print("Enter a 1 or 2");
						break;
				}
			}	
		
			System.out.println("------------------------------");

			
		}
	// 
	// 
	///////////////////////////////////////////////// READ / WRITE ////////////////////////////////////////////////////////////
		// saveToFileMenu(bank) -- will prompt the user to enter the name of the file to save bank records to. 
		// Note: to avoid unnecessary complexity when dealing with files, you can assume that the user will enter a 
		// simple file name without any additional path information in it 
		// (e.g. the file name will be something like bank.txt or records.txt, 
		// but not something like ../bankAccounts/newFolder/bank.txt). 
		// Once the file name is retrieved, this method should create a PrintWriter object and call the 
		// saveToFile method of Bank class to perform the saving.
		public static void saveToFileMenu(Bank b) throws Exception{
		
			System.out.print("Enter file name, but NOT the extension:   ");
			// System.out.println("BankMenu Class : saveToFileMenu method");
			File f = new File(terminal.nextLine().trim() + ".txt");
			System.out.println("File " + f + " created.");
			PrintWriter pw = new PrintWriter(f);
			System.out.println("Saving.......");
			b.writeToFile(pw);
			pw.flush();
			pw.close();

		}
		// loadFromFileMenu(bank) -- will prompt the user to enter the name of the file to load from. 
		// Similarly to saveToFileMenu, you can assume that the user will enter a simple file name 
		// without any additional path information in it. This method should first check if the file actually exists, and if it doesn't,
		// it should print an appropriate error message and return back to the landing menu. If the file does exist,
		// this method should create a Scanner object and call loadFromFile method of Bank class to perform the loading.
		public static void loadFromFileMenu(Bank b) throws Exception{
			// System.out.println("BankMenu Class : loadFromFileMenu method");
			System.out.print("Enter file name, but NOT the extension:   ");

			String fileName = terminal.nextLine().trim() + ".txt";
			File f = new File(fileName);
			while(!f.exists()){
				System.out.println("File does not exist. Back to the Landing Menu sucka ");
				return;
			}
			Scanner sc = new Scanner(f);
	
			System.out.println("Loading.....");
			b.readFromFile(sc);
			sc.close();

		}
	// 
	// 
	///////////////////////////////////////////////// MAIN MENU ///////////////////////////////////////////////////////////////
			
		
		// mainMenu(account)
		// This method should greet the user ("Welcome, ...!") and then print this menu:
		//
		// Deposit Money
		// Withdraw Money
		// Account Details
		// Exit
		public static void mainMenu(BankAccount ba){
			System.out.println("------------------------------");
			System.out.println("Welcome " + ba.getFirstName() + ", its lovely to see. ");
			boolean exit = false;
			String userInput;

			while(!exit){
				printMainMenu(ba);

				userInput = terminal.nextLine();
				// clearBuffer(terminal);

				switch(userInput.trim()){
					case "1":
						depositMenu(ba);
						break;
					case "2": 
						withdrawMenu(ba);
						break;
					case "3": 
						accountMenu(ba);
						break;
					case "4": 
						exit = true;
						System.out.println("Exiting...");
						break;
					default:
						System.out.println("Baaaaaaaaad input biotch");
						break;
				}

			}
		}

		public static void printMainMenu(BankAccount ba){
			System.out.println("------------------");
			System.out.printf("First Name: %s, Last Name: %s, Address: %s, Username: %s, Balance: %d\n", ba.getFirstName(), ba.getLastName(),ba.getAddress(), ba.getUsername(), ba.getBalance());
			System.out.println("------------------");
			System.out.println("Main Menu:");
			System.out.println("1. Deposit Money");
			System.out.println("2. Withdraw Money");
			System.out.println("3. Account Details");
			System.out.println("4. Exit");
			System.out.println("------------------------------");
	
		}
		// depositMenu(account)
		// This method should prompt the user to enter the amount of money to deposit and 
		// add the entered amount appropriately. Only positive numbers should be allowed.
		public static void depositMenu(BankAccount ba){
			
			printBalance(ba);
			System.out.print("How much do you want to deposit:  ");
			String amount = terminal.nextLine();
			// make sure input is numbers 
			while(!isNumber(amount)){
				System.out.print("Enter numbers, no letters:    ");
				amount = terminal.nextLine();
			}
			int amountAsInt = STRING_TO_INT(amount);
			// make sure amount is positive
			while(amountAsInt < 0){
				System.out.print("No negatives! \nHow much do you want to deposit today:  ");
				amount = terminal.nextLine();
				amountAsInt = STRING_TO_INT(amount);
			}

			if(!promptPIN(ba)){
					System.out.println("Cannot complete deposit, try again soon. ");
					return;
			}
			ba.addBalance(amountAsInt);
			printBalance(ba);

		}

		// withdrawMenu(account)
		// This method should prompt the user to enter the amount of money to withdraw and withdraw 
		// the entered amount appropriately. Only positive numbers should be allowed. 
		// Quite obviously, the user should not be allowed to withdraw more money than they currently 
		// have in their account.
		public static void withdrawMenu(BankAccount ba){
		
			if(ba.getBalance() == 0){
				System.out.println("Empty Balance. Bye bye");
				return;
			}
			printBalance(ba);
			System.out.print("How much do you want to withdraw:   ");
			String amount = terminal.nextLine().trim();
			// clearBuffer(terminal);
			int amountAsInt = STRING_TO_INT(amount);

			while(amountAsInt < 0){
				System.out.print("Enter a correct amount:   ");
				amount = terminal.nextLine().trim();
			}

			while((ba.getBalance() - amountAsInt) < 0){
				System.out.println("Insufficient Funds. \nYou have " + ba.getBalance() + " in your account. ");
				System.out.print("You can't take more money out of your account than is currently in your account, so how much do you want to withdraw? ");
				amount = terminal.nextLine().trim();
				amountAsInt = STRING_TO_INT(amount);
			}

			
			if(!promptPIN(ba)){
				System.out.println("Cannot complete withdrawal, try again soon.");
				return;
			}
			ba.subtractBalance(amountAsInt);
			printBalance(ba);

		}
	// 
	// 
	///////////////////////////////////////////////// ACCOUNT MENU ////////////////////////////////////////////////////////////
			// accountMenu(account)
			// This method should print all of the account details and then show the user the following menu:
			//
			// Change First Name
			// Change Last Name
			// Change Address
			// Change Username
			// Change PIN
			// Exit
			public static void accountMenu(BankAccount ba){

				// if(!promptPIN(ba)){
				// 	return;
				// }
				boolean exit = false;
				String userInput;
				while(!exit){
					// print menu
					printAccountMenu(ba);

					userInput = terminal.nextLine();
					// clearBuffer(terminal);

					switch(userInput.trim()){
						case "1":
							changeFirstNameMenu(ba);
							break;
						case "2": 
							changeLastNameMenu(ba);
							break;
						case "3": 
							changeAddressMenu(ba);
							break;
						case "4": 
							changeUsernameMenu(ba);
							break;
						case "5": 
							changePINMenu(ba);
							break;
						case "6":
							exit = true;
							System.out.println("Exiting...");
							break;
						default:
							System.out.println("Baaaaaaaaad input.");
							break;
					}
				}
			}	

			private static void printAccountMenu(BankAccount ba){

				System.out.println("------------------");
				System.out.printf("First Name: %s, Last Name: %s, Address: %s, Username: %s, Balance: %d\n", ba.getFirstName(), ba.getLastName(),ba.getAddress(), ba.getUsername(), ba.getBalance());
				System.out.println("------------------");
				System.out.println("Account Menu:");
				System.out.println("1. Change First Name");
				System.out.println("2. Change Last Name");
				System.out.println("3. Change Address");
				System.out.println("4. Change Username");
				System.out.println("5. Change PIN");
				System.out.println("6. Exit");
				System.out.println("------------------------------");

			}
			// changeFirstNameMenu(account)
			// Prompt the user to enter their PIN again and then print a prompt that asks the user 
			// for their new First Name. Validate the name using the isValidName method from the BankAccount class.
			// user should only be able to access throught the main menu but leave as public for testing the method
// Lots and lots of repetitive code in the following 4 sections, fix it
// use update field func in BankAccount class

		///////////////////////////////////////////////// CHANGE FIRST NAME MENU ///////////////////////////////////////////////////
				public static void changeFirstNameMenu(BankAccount ba){
					
					System.out.println("Your current first name is: '" + ba.getFirstName()+"'");
					String newName;
					// ba.updateField(field);
					System.out.print("Please enter your updated first name:   ");
					newName = terminal.nextLine();
					isInvalidName(newName);

					if(!promptPIN(ba)){
						System.out.println("Cannot change your first name, try again soon");
						return;
					}
					System.out.println("First name changed!");
					ba.setFirstName(newName);
				
				}
		// 
		//
		///////////////////////////////////////////////// CHANGE LAST NAME MENU ///////////////////////////////////////////////////
				// changeLastNameMenu(account)
				// Same as changeFirstNameMenu, except that the information to change is the Last Name.
				public static void changeLastNameMenu(BankAccount ba){

					System.out.println("Your current last name is: '" + ba.getLastName() +"'");

					String newName;
					System.out.print("Please enter your updated new last name: ");
					newName = terminal.nextLine();
				 	isInvalidName(newName);
					if(!promptPIN(ba)){
						System.out.println("Cannot change last name, try again soon.");
						return;
					}
					System.out.println("Last Name changed!");
					ba.setLastName(newName);
				}
		// 
		// 
		///////////////////////////////////////////////// CHANGE ADDRESS MENU /////////////////////////////////////////////////////

				// changeAddressMenu(account)
				public static void changeAddressMenu(BankAccount ba){

					String newAddress;
					System.out.print("Please enter your updated address here: ");
					newAddress = terminal.nextLine();
					// clearBuffer(terminal);

				 	while(!BankAccount.isValidAddress(newAddress.trim())){
					   	System.out.print("Invalid input!");
				      	System.out.print("Please only use letters or dashes or spaces: ");
						newAddress = terminal.next();
						clearBuffer(terminal);
					}
					if(!promptPIN(ba)){
						System.out.println("Cannot change your address, try again soon");
						return;
					}

					ba.setAddress(newAddress);
					System.out.println("Address changed!");
					return;
				}
		// 
		// 
		///////////////////////////////////////////////// CHANGE USERNMAME MENU ///////////////////////////////////////////////////

				public static void changeUsernameMenu(BankAccount ba){

					String newUsername;
					System.out.println("Enter your new username: ");
					newUsername = terminal.nextLine();
					// clearBuffer(terminal);

					while(!BankAccount.isValidUsername(newUsername.trim())){
						System.out.print("Invalid input!");
				      	System.out.print("Please only use letters or dashes or underscores or numbers this time. \nEnter your new username here: ");
						newUsername = terminal.next();
						clearBuffer(terminal);
					}

					if(!promptPIN(ba)){
						System.out.println("Cannot change your username, try again soon");
						return;
					}

					ba.setUsername(newUsername);
					System.out.println("Username changed!");
					return;
				}
	 	

	/////////////////////////////////////////////////// CHANGE PIN MENU /////////////////////////////////////////////////////////

			public static void changePINMenu(BankAccount ba){
				// prompts user for pin, if it is correct pin, let them change otherwise keep prompting pin
				System.out.println("Before choosing a new PIN, lets make sure this is you. Enter your current PIN");
				if(!promptPIN(ba)){
					return;
				}
				// confirm new pin
				String newPIN, confirmedPIN;
				System.out.println("Enter your new PIN: ");
				newPIN = terminal.nextLine().trim();
				System.out.println("Confirm your new PIN: ");
				confirmedPIN = terminal.nextLine();
				while(!newPIN.equals(confirmedPIN)){
					System.out.println("Confirm your new PIN: ");
					confirmedPIN = terminal.nextLine().trim();
				}
				// set new pin
				ba.setPIN(newPIN);
			 	System.out.println("PIN successfully changed.");
			}
	// 
	// 
	///////////////////////////////////////////////// HELPERS /////////////////////////////////////////////////////////////////
		// helpers
		public static void clearBuffer(Scanner in){
			if(in.hasNextLine()){
				in.nextLine();
			}
		}

		public static void isInvalidName(String s){
				while(!BankAccount.isValidName(s.trim())){
			   	System.out.print("Invalid input!");
		      	System.out.print("Please only use letters or dashes or spaces: ");
				s = terminal.next();
				clearBuffer(terminal);
			}
		}
		public static void printBalance(BankAccount ba){
			System.out.println("You currently have " + ba.getBalance() + " in your account");
		}

		public static boolean insufficientFunds(int amount, BankAccount ba){
			if((ba.getBalance() - amount) < 0){
				System.out.println("Insufficient Funds. \nYou have " + ba.getBalance() + " in your account. ");
				return true;
			}
			return false;
		}
		// returns true if input is string of ints
		public static boolean isNumeric(String str){
		    for (char c : str.toCharArray())
		    {
		        if (!Character.isDigit(c)) return false;
		    }
		    return true;
		}

		public static boolean isNumber(String input){
		    try{
		        Integer.parseInt(input);
		    } catch(NumberFormatException nfe){
		        return false;
		    }
		    return true;
		}
		// turns string into in
		public static int STRING_TO_INT(String s){
			// int x = ;
			return Integer.parseInt(s);
		}

		// cheks if balance is at 0

		public static boolean invalidPIN(String pin){
			for (char c : pin.toCharArray()) {
	    		if (Character.isAlphabetic(c)){
	        		System.out.println("INVALID");
	        		return false;
	    		}
			}
			return true;
		}
	// 
	// 
	///////////////////////////////////////////////// PROMPT PIN //////////////////////////////////////////////////////////////
		// promptPIN(account)
			// Prompt the user for their PIN and return true 
			// if the entered PIN matches the correct pin. Return false otherwise.
		public static boolean promptPIN(BankAccount ba){
			System.out.print("Enter 0 at any time if you forgot you PIN. \nPIN: ");
			
			String pin = terminal.nextLine().trim();
			if(pin.equals("0"))return false;

			while(pin.length() != 4){
				System.out.println("PIN must be 4 digits, try again.");
				return false;
			}
	
			String hashPIN = ba.hashOf(pin);

			while(!hashPIN.equals(ba.getHashedPIN())){
				System.out.print("Try again, PIN: ");
				pin = terminal.nextLine().trim();
				if(pin.equals("0"))return false;
				hashPIN = ba.hashOf(pin);

			}
			System.out.println("Entered PIN : " + pin);
		
			System.out.println("Entered PIN hashed: " + hashPIN);
			System.out.println("Saved PIN : " + ba.getHashedPIN());
	
		return true;
		}

		// public static boolean confirmChanges(BankAccount ba){
		// 	if(!promptPIN(ba)){
		// 		return false;
		// 	}
		// 	return true;
		// }		
	
}
