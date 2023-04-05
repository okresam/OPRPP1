package hr.fer.oprpp1.custom.collections;

/**
 * An Processor is a object that processes an given object 
 * (performs some operation over an given object)
 */
public interface Processor {
	/**
	 * Performs an operation on the passed object.
	 * 
	 * @param value object on which an operation is performed
	 */
	void process(Object value);
}
