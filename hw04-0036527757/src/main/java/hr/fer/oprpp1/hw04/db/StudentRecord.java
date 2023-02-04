package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Object that represents a student record in the database.
 */
public class StudentRecord {
	
	/**
	 * The jmbag of the student.
	 */
	private String jmbag;
	
	/**
	 * The last name of the student.
	 */
	private String lastName;
	
	/**
	 * The first name of the student.
	 */
	private String firstName;
	
	/**
	 * The final grade of the student.
	 */
	private String finalGrade;
	
	/**
	 * Constructor for the StudentRecord. Sets all the attributes to the given values.
	 * 
	 * @param jmbag - jmbag of the student
	 * @param lastName - last name of the student
	 * @param firstName - first name of the student
	 * @param finalGrade - final grade of the student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}

	/**
	 * Getter for the jmbag.
	 * 
	 * @return jmbag of the student
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for the last name.
	 * 
	 * @return last name of the student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for the first name.
	 * 
	 * @return first name of the student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for the final grade.
	 * 
	 * @return final grade of the student
	 */
	public String getFinalGrade() {
		return finalGrade;
	}
	
}
