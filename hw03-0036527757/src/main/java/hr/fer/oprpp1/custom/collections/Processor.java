package hr.fer.oprpp1.custom.collections;

/**
 * An Processor processes an given argument.
 * (performs some operation over an given argument)
 */
public interface Processor<T> {
	
	/**
	 * Performs an operation on the passed argument.
	 * 
	 * @param value - argument on which an operation is performed
	 */
	void process(T value);
}
