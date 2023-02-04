package hr.fer.zemris.math;

/**
 * Class that represents a complex polynomial. (zn * z^n ... z2 * z^2 + z1 * z + z0)
 */
public class ComplexPolynomial {

	/**
	 * Factors of the complex polynomial.
	 */
	private Complex[] factors;
	
	/**
	 * Constructor that sets the factors of the complex polynomial to the given values.
	 * 
	 * @param factors - factors of the complex polynomial
	 */
	public ComplexPolynomial(Complex ... factors) {
		this.factors = factors;
	}
	
	/**
	 * Gets the order of this complex polynomial.
	 * 
	 * @return order of complex polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * Multiplies this complex polynomial with the given complex polynomial.
	 * 
	 * @param p - complex polynomial to be multiplied with
	 * @return a complex polynomial, result of the multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[factors.length + p.factors.length - 1];
		
		for (int i = 0; i < newFactors.length; i++) {
			newFactors[i] = Complex.ZERO;
		}
		
		for (int i = 0; i < p.factors.length; i++) {
			int z = 0;
			for (int j = i; j < factors.length + i; j++) {
				newFactors[j] = newFactors[j].add(p.factors[i].multiply(factors[z++]));
			}
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Derives this complex polynomial.
	 * 
	 * @return complex polynomial, first derivative of this complex polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length - 1];
		
		for (int i = 0; i < newFactors.length; i++) {
			newFactors[i] = new Complex(0, 0).add(factors[i + 1].multiply(new Complex(i + 1, 0)));
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Calculates the complex polynomial value at given point.
	 * 
	 * @param z - a complex point
	 * @return value of the complex polynomial at point z
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		
		for (int i = 0; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = factors.length - 1; i >= 0; i--) {
			sb.append("(" + factors[i].toString() + ")" + (i > 0 ? "*z^" + i + "+" : ""));
		}
		
		return sb.toString();
	}
	
}
