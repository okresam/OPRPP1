package hr.fer.oprpp1.hw04.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentDB {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		try {
			List<String> lines = Files.readAllLines(
				Paths.get("./database.txt"),
				StandardCharsets.UTF_8
			);
			
			StudentDatabase sd = new StudentDatabase(lines);
			while (true) {
				System.out.printf("> ");
				String query = sc.nextLine();
				
				if (query.equals("exit")) {
					System.out.println("Goodbye!");
					break;
				}
				
				if (query.length() < 4 || !query.substring(0, 5).equals("query")) {
					System.out.println("Wrong command or no command given! (commands start without empty spaces)");
					continue;
				}
				
				query = query.substring(5);
				
				try {
					QueryParser parser = new QueryParser(query);
					if (parser.isDirectQuery()) {
						StudentRecord r = sd.forJMBAG(parser.getQueriedJMBAG());
						
						System.out.println("Using index for record retrieval.");
						
						List<String> output = formatQueryOutput(Arrays.asList(r), false, null);
						output.forEach(System.out::println);
						System.out.println();
					} else {
						List<StudentRecord> records = selectFromDatabaseFiltered(sd, new QueryFilter(parser.getQuery()));
						List<String> output = formatQueryOutput(records, parser.isOrderByPresent(), parser.getOrderByAttributes());
						output.forEach(System.out::println);
						System.out.println();
					}
					
				} catch (Exception e) {
					System.out.println("Invalid query!");
				}

			}
		} catch(Exception e) {
			System.out.println("Could not read the file!");
		}
		
		sc.close();
	}

	/**
	 * Returns a list of filtered StudentRecords.
	 * 
	 * @param db - StudentDatabase to be filtered
	 * @param f - filter by which the StudentDatabase will be filtered
	 * @return list of filtered StudentRecords
	 */
	public static List<StudentRecord> selectFromDatabaseFiltered(StudentDatabase db, QueryFilter f) {
		return db.filter(f);
	}
	
	/**
	 * Formats the output of the query.
	 * 
	 * @param records - list of StudentRecords filtered by the ConditionalExpressions of the query
	 * @return list of output lines of the query
	 */
	public static List<String> formatQueryOutput(List<StudentRecord> records, boolean orderByPresent, List<String> OrderByAtt) {
		List<String> list = new ArrayList<>();
		int size = 0;
		
		if (orderByPresent) {
			for (String att : OrderByAtt) {
				switch(att) {
					case "firstName":
						records.sort((s1, s2) -> s1.getFirstName().compareTo(s2.getFirstName()));
						break;
					case "lastName":
						records.sort((s1, s2) -> s1.getLastName().compareTo(s2.getLastName()));
						break;
					case "jmbag":
						records.sort((s1, s2) -> s1.getJmbag().compareTo(s2.getJmbag()));
						break;
					case "finalGrade":
						records.sort((s1, s2) -> s1.getFinalGrade().compareTo(s2.getFinalGrade()));
						break;
				}
			}
		}
		
		if (!records.isEmpty()) {
			int longestJmbag = 0;
			int longestLastName = 0;
			int longestFirstName = 0;
			int longestGrade = 0;
			
			for (StudentRecord r : records) {
				if (longestJmbag < r.getJmbag().length()) {
					longestJmbag = r.getJmbag().length();
				}
				if (longestLastName < r.getLastName().length()) {
					longestLastName = r.getLastName().length();
				}
				if (longestFirstName < r.getFirstName().length()) {
					longestFirstName = r.getFirstName().length();
				}
				if (longestGrade < r.getFinalGrade().length()) {
					longestGrade = r.getFinalGrade().length();
				}
			}
			
			String jmbagFormat = "%-" + longestJmbag + "s";
			String lastNameFormat = "%-" + longestLastName + "s";
			String firstNameFormat = "%-" + longestFirstName + "s";
			String finalGradeFormat = "%-" + longestGrade + "s";
			
			list.add("+" + "=".repeat(longestJmbag+2) 
					+ "+" + "=".repeat(longestLastName+2)
					+ "+" + "=".repeat(longestFirstName+2)
					+ "+" + "=".repeat(longestGrade+2) + "+");
			
			for (StudentRecord r : records) {
				list.add("| " + String.format(jmbagFormat, r.getJmbag()) + " | " 
						+ String.format(lastNameFormat, r.getLastName()) + " | "
						+ String.format(firstNameFormat, r.getFirstName()) + " | " 
						+ String.format(finalGradeFormat, r.getFinalGrade()) + " |");
				size++;
			}
			
			list.add("+" + "=".repeat(longestJmbag+2) 
					+ "+" + "=".repeat(longestLastName+2)
					+ "+" + "=".repeat(longestFirstName+2)
					+ "+" + "=".repeat(longestGrade+2) + "+");
		}
		
		list.add("Records selected: " + size);
		return list;
	}
}
