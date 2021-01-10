import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BabyNames {
	
	private static String name = "";
	private static int year = 0;
	private static String gender = "";
	private static String query;
	private static String line;
	private static String lineName; //lines are given as Name,Gender, number of babies born with name and gender combination
	private static String lineGender = "";
	private static String babiesBorn;
	private static int fileYear = 0;
	private static double lineCount = 0;
	private static int fileCount = 0;
	private static double rankOfNameForYear = 0;
	private static String mostPopularLine;
	private static String prevMostPopularLine;
	private static double prevHighestRank = 0;
	private static File file;
	

	public static void main(String[] args) throws FileNotFoundException {
		
		BabyNames baby = new BabyNames();
		
		getUserQuery();
		if (query.toUpperCase().equals("Y")) {
			askForName();
			getFile();
		}
		if (query.toUpperCase().equals("P")) {
			askForYear();
			getFile();
		}
		if (query.toUpperCase().equals("R")) {
			askForName();
			askForYear();
			getFile();
		}
		if (query.toUpperCase().equals("Quit")) {
			System.out.println("byebye");
			System.exit(0);
		}
		System.out.println(name + gender + year);
	}
	
	private static void getUserQuery() {
		Scanner inputScan = new Scanner(System.in); 
		System.out.println("Choose an option:");
		System.out.println("P  Show most popular names for a given year and gender.");
		System.out.println("R  Show rank for a given name, gender, and year.");
		System.out.println("Y  Find the year in which the given name, gender combination was most popular.");
		System.out.println("Quit");
		query = inputScan.nextLine();
	}
	
	private static void askForName() { //getting baby info from user
		Scanner inputScan = new Scanner(System.in);
		System.out.println("Enter a first name");
		name = inputScan.nextLine().toUpperCase();
		if (gender == "") {
			System.out.println("Enter a gender");
			gender = inputScan.nextLine().toUpperCase();
		}
	}
	
		private static void askForYear() { //getting baby info from user
		Scanner inputScan = new Scanner(System.in);
		System.out.println("Enter a year");
		year = Integer.parseInt(inputScan.nextLine());
		if (gender == "") {
			System.out.println("Enter a gender");
			gender = inputScan.nextLine().toUpperCase();
		}
	}
		
	private static void getFile() throws FileNotFoundException { // finding name and gender in files and finding year compared to file title
		File dir = new File("ssa_complete");
		File[] directory = dir.listFiles();
		for (File file : directory) {
			if (fileCount > 0) { //first file says where the other files come from
				getLine(file);
			}
			fileCount ++;
		}
		System.out.println(prevMostPopularLine);
	}

	private static void getLine(File file) throws FileNotFoundException {
		fileYear = fileCount + 1879;
		lineCount = 0.0;
		if (year == 0) {
		    getNameGenderCombination(file);
		}
		else if (year == fileYear) {
			getYearCombination(file);
		}
	    keepTrackOfMostPopularYearForName();
	}

	private static void getNameGenderCombination(File file) throws FileNotFoundException {
		Scanner scanFile = new Scanner(file);
		while (scanFile.hasNextLine()) {
			breakDownLine(scanFile);
		    if (gender.equals(lineGender)) {
		    	lineCount ++;
		    }
		    if ((name + gender).equals(lineName.toUpperCase() + lineGender)) { //name gender combination
		   		setFormatForOutput();
		   		rankOfNameForYear = lineCount;
		   	}
		}
		mostPopularLine = mostPopularLine + lineCount + " other names";
	}
	
	private static void getYearCombination(File file) throws FileNotFoundException {
		Scanner scanFile = new Scanner(file);
		while (scanFile.hasNextLine()) {
			breakDownLine(scanFile);
		    if (gender.equals(lineGender)) {
		    	lineCount ++;
		    }
		    if (gender.equals(lineGender) && lineCount == 1.0 && name.equals("")) { //gender year combination
		    	setFormatForOutput();
		    	rankOfNameForYear = lineCount;
		    }
		    else if ((name + gender).equals(lineName.toUpperCase() + lineGender)) { //gender year name combination
		    	setFormatForOutput();
		    }
		}
		mostPopularLine = mostPopularLine + lineCount + " other names";
	}

	private static void breakDownLine(Scanner scanFile) {
		line = scanFile.nextLine();
		Scanner lineScanner = new Scanner(line);
		lineScanner.useDelimiter(",");
		lineName = lineScanner.next();
		lineGender = lineScanner.next();
		babiesBorn = lineScanner.next();
	}
	
	private static void setFormatForOutput() {
		mostPopularLine = (lineName + " " + lineGender + " Babies Born: " + babiesBorn + " Year: " + fileYear + " Rank: " + lineCount + " out of: ");
	}
	
	private static void keepTrackOfMostPopularYearForName() { //find most popular year for a name
		if (prevHighestRank == 0) {
			prevMostPopularLine = mostPopularLine;
			prevHighestRank = rankOfNameForYear/lineCount;
		}
		if (prevHighestRank < rankOfNameForYear/lineCount) {
			prevHighestRank = rankOfNameForYear/lineCount;
			prevMostPopularLine = mostPopularLine;
		}
	}
}