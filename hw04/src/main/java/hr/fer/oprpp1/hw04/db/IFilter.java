package hr.fer.oprpp1.hw04.db;

/**
 * Object that filters StudentRecords given to it.
 */
public interface IFilter {
	
	/**
	 * Function that determines if the given StudentRecord is accepted by the filter or not.
	 * 
	 * @param record - StudentRecord to be filtered
	 * @return true if the StudentRecord is accepted, false otherwise
	 */
	public boolean accepts(StudentRecord record);
}
