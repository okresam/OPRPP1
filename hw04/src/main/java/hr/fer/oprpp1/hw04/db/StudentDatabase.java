package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object that represents a student database.
 */
public class StudentDatabase {
	
	/**
	 * Internal list of all student records in the database.
	 */
	private List<StudentRecord> studentRecords;
	
	/**
	 * Map for storing student records with jmbag as the key.
	 * Used for getting a student record in O(1) when having a jmbag.
	 */
	private Map<String, StudentRecord> index;
	
	/**
	 * Constructor for the StudentDatabase. 
	 * Creates the internal list and map for the student database.
	 * Checks for duplicate jmbag or invalid grade in the given data.
	 * 
	 * @param dbData - list of strings containing data about the students
	 */
	public StudentDatabase(List<String> dbData) {
		studentRecords = new ArrayList<>();
		index = new HashMap<>();
		
		for (String row : dbData) {
			String[] splitRow = row.split("\t");
			
			if (splitRow.length != 4) {
				System.out.println("Invalid row found in the provided file!");
				System.exit(0);
			}
			
			if (index.get(splitRow[0]) != null) {
				System.out.println("Duplicate jmbag found in the provided file!");
				System.exit(0);
			}
			
			if (!(splitRow[3].equals("1") || 
				splitRow[3].equals("2") ||
				splitRow[3].equals("3") ||
				splitRow[3].equals("4") ||
				splitRow[3].equals("5"))) {
				System.out.println("Invalid final grade found in the provided file!");
				System.exit(0);
			}
			
			StudentRecord newRecord = new StudentRecord(splitRow[0], splitRow[1], splitRow[2], splitRow[3]);
			
			index.put(newRecord.getJmbag(), newRecord);
			studentRecords.add(newRecord);
		}
	}
	
	/**
	 * Gets the student record for the given jmbag.
	 * 
	 * @param jmbag - jmbag of the student we want to get
	 * @return StudentRecord of the student that has the given jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	/**
	 * Filters all StudentRecords and returns a list of accepted StudentRecords.
	 * 
	 * @param filter - filter by which we want to filter the StudentRecords
	 * @return list of filtered StudentRecords
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> srList = new ArrayList<>();
		
		for (StudentRecord sRec : studentRecords) {
			if (filter.accepts(sRec))
				srList.add(sRec);
		}
		
		return srList;
	}
}
