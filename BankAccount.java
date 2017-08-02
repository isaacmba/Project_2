import java.util.Scanner;
import java.lang.Character;
import java.lang.Exception;
import java.io.PrintWriter;
import java.io.File;
// BankAccount class
//
class BankAccount{

    static Scanner terminal = new Scanner(System.in);

    // #Fields
    // firstName -- First Name
    // lastName -- Last Name
    // address -- Address
    // balance -- Account Balance (in whole dollars)
    // userName -- Account Username (unique)
    // pin -- PIN (used as a password)
    //
    private String firstName;
    private String lastName;
    private String address;
    private String username;
    private int balance;
    // private String pin;
    private String hashedPin;

//////////////////////////////////////CONSTRUCTORS /////////////////////////////////////////////

    // #Constructors
    public BankAccount(String fn,String ln){
        firstName = fn;
        lastName = ln;
    }
      // BankAccount(in) -- a new constructor that will accept a Scanner as an argument 
    // and read the account information from one line of the scanner. 
    // The format it should read is equivalent to the format used in writeToFile method.
    // public static void readFromFile(Scanner sc) throws Exception{
    //     System.out.println(sc.nextLine());    
    //     return;    
    // }
    // parses the string and creates new account
    public BankAccount(Scanner sc){
        String line = sc.nextLine();
        // String f = "yo|no|null|better|491960149011494921499831|0";
        String[] x = line.split("\\|",6);

        firstName = x[0];
        lastName = x[1];
        address = x[2];
        username = x[3];
        balance = Integer.valueOf(x[5]);
        hashedPin = x[4];


    }
 
////////////////////////////////////// GETTERS ///////////////////////////////////////////////
    // #Getters
    //
    // getFirstName()
    // getLastName()
    // getAddress()
    // getBalance()
    // getUserName()
    // getPIN()
    public String getFirstName(){
        return firstName;
    };

    public String getLastName(){
        return lastName;
    };

    public String getAddress(){
        ifNull(address);
        // if(address == null){
        //     address = "none";
        // }
        return address;
    };

    public int getBalance(){
        // System.out.println("Getting balance");
        return balance;
    };

    public String getUsername(){
        // System.out.println("Getting username");
        if(username == null){
            username = "none";
        }
        return username;
    };
    public String getHashedPIN(){
        // System.out.println("Getting pin");
        return hashedPin;
    };

////////////////////////////////////// SETTERS /////////////////////////////////////////////////

    // #Setters
    //
    // setFirstName(firstName)
    // setLastName(lastName)
    // setAddress(address)
    // setUserName(username)
    // setPIN(pin)
    public void setFirstName(String fn){
        // validate if the name is correct first
        firstName = fn;
    }
    public void setLastName(String ln){
        lastName = ln;
    }
    public void setAddress(String ad){
        address = ad;
    }
    public void setUsername(String un){
        username = un;
    }
    // public void setPIN(String p){
    //     pin = p;
    // }
    public void setPIN(String p){
        hashedPin = hashOf(p);
    }
    // #Other
    //
    public void addBalance(int amount){
        balance += amount;
        // System.out.println(amount);
    }

    public void subtractBalance(int amount){
      
            balance -= amount;
            System.out.println("Your current balance is " + balance);
 
    }

///////////////////////////////////// hash of //////////////////////////////////////////////

    public static String hashOf(String pin) {
        // Ignore if the PIN is invalid
        if ( pin == null || pin.length() != 4 ) {
            return null;
        }

        // Run a silly, cryptic algorithm
        String hash = "";
        for ( int i = 0; i < 4; ++i ) {
            int digit = (int)pin.charAt(i);
            hash += "" + (digit | digit%3);
            hash += "" + (((digit+i)%5) * digit) + i;
            hash += "" + digit%2;
        }

        // Return the hash
        return hash;
    }

///////////////////////////////////// writeToFile ////////////////////////////////////
    // writeToFile(out) -- a new method write the account information as one line in the provided file.
    // All of the values should be separated by the vertical bar character(see example further below).
    public static void writeToFile(PrintWriter pw, BankAccount ba) throws Exception {
        // System.out.println("BankAccount Class : writeToFile method");
        String account = ba.toString();
        // System.out.println(account);
        pw.println(account);
    }

//////////////////////////////////// toString /////////////////////////////////////////////
    public String toString(){
        return getFirstName() + "|" +getLastName() + "|" + getAddress() + "|" + getUsername() + "|" + getHashedPIN() + "|" + getBalance();
    }

///////////////////////////////// HELPERS ///////////////////////////////////////////////

    public static boolean isValidAddress(String ad){
        
        if(ad == null){
            return false;
        }

        for(int i = 0; i < ad.length(); i++ ){
            
            char c = ad.charAt(i);
            // if char isnt letter or space or dash, reject
            if(!Character.isLetter(c) && c != '-' && c != ' ' && !Character.isDigit(c)){
                return false;
            }
        }
        return true;

    }//isValidAddress

    public static boolean isValidUsername(String username) {
        
       ifNull(username);

        for(int i = 0; i < username.length(); i++ ){    
            char c = username.charAt(i);
            // if char isnt letter, dash, underscore, number 
            if(!Character.isLetter(c) && c != '_' && c != '-' && c != '|' && !Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public static boolean isValidName(String name){
        // if name is null
        if (name == null) {
            return false;
        }
        // otherwise...
        for(int i = 0; i < name.length(); i++ ){
            
            char c = name.charAt(i);
            // if char isnt letter or space or dash, reject
            if(!Character.isLetter(c) && c != '-' && c != ' '){
                return false;
            }
        }
        return true;
    }
 
    public static boolean isValidPIN(String pin) {

        // invalid if its not 4 digits and doesnt equal pin
        if(pin == null || pin.length() != 4){
            return false;
        }
        else return true;
    }

    public static void ifNull(String s){
        if(s == null){
            s = "none";
        }
    }

////////////////////////////////  CONFIRM PIN ///////////////////////////////////////////

    public boolean confirmPIN(){
        
        System.out.println("Confirm your PIN, " + getUsername() + ": ");
        String pin = terminal.nextLine().trim();
    
        if(!(getHashedPIN().equals(hashOf(pin)))){
            System.out.println("PIN's do not match ");
            return false;
        }
        return true;
        


    }

//////////////////////////////// CLEARBUFFER ///////////////////////////////////////////

    public static void clearBuffer(Scanner in){
        if(in.hasNextLine()){
            in.nextLine();
        }
    }

/*
    public static void updateField(String field){

        System.out.print("Enter the updated field : ");
        newField = terminal.nextLine().trim();
        while(!BankAccount.isValidName(s.trim())){
            System.out.print("Invalid input!");
            System.out.print("Please only use letters or dashes or spaces: ");
            newField = terminal.next();
        }

     }
*/





}
