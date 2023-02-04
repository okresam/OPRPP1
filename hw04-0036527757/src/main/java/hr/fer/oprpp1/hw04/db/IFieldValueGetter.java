package hr.fer.oprpp1.hw04.db;

/**
 * Object that gets the value of a field in the StudentRecord.
 */
public interface IFieldValueGetter {
	
	/**
	 * Gets the value of the field of the StudentRecord
	 * 
	 * @param record - StudentRecord from whose field the value will be taken
	 * @return the value of the field in the StudentRecord
	 */
	public String get(StudentRecord record);
}
