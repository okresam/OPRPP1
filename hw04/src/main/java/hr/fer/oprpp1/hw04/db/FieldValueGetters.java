package hr.fer.oprpp1.hw04.db;

/**
 * Concrete implementations of FieldValueGetters.
 */
public class FieldValueGetters {
	
	/**
	 * FieldValueGetter for the first name of the student.
	 */
	public static final IFieldValueGetter FIRST_NAME = (sr) -> sr.getFirstName();
	
	/**
	 * FieldValueGetter for the last name of the student.
	 */
	public static final IFieldValueGetter LAST_NAME = (sr) -> sr.getLastName();
	
	/**
	 * FieldValueGetter for the jmbag of the student.
	 */
	public static final IFieldValueGetter JMBAG = (sr) -> sr.getJmbag();
}
