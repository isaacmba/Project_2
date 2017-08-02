import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

class Driver{

	public static void main(String[] args) throws Exception{

		// load dummy accounts into new bank
		Bank b = new Bank("Backboard Bank");
		BankMenu.landingMenu(b);
	}
}
