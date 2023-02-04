package hr.fer.zemris.math;

/**
 * Class that represents a complex rooted polynomial. (z0 * (z - z1) * (z - z2) * ... * (z - zn))
 */
public class ComplexRootedPolynomial {
	
	/**
	 * The constant of the complex rooted polynomial.
	 */
	private Complex constant;
	
	/**
	 * The roots of the complex rooted polynomial.
	 */
	private Complex[] roots;
	
	/**
	 * Constructor that sets the constant and roots to the given values.
	 * 
	 * @param constant - wanted constant 
	 * @param roots - wanted roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * Calculates the value of the complex rooted polynomial at the given point.
	 * 
	 * @param z - a complex point
	 * @return the value of the complex rooted polynomial at point z
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		
		for (Complex c : roots) {
			result = result.multiply(z.sub(c));
		}
		
		return result;
	}
	
	/**
	 * Converts this complex rooted polynomial to a complex polynomial.
	 * 
	 * @return this complex rooted polynomial as a complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial cp = new ComplexPolynomial(roots[0], Complex.ONE);
		
		for (int i = 1; i < roots.length; i++) {
			cp = cp.multiply(new ComplexPolynomial(roots[i], Complex.ONE));
		}
		
		cp = cp.multiply(new ComplexPolynomial(constant));
		return cp;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + constant.toString() + ")");
		
		for (Complex c : roots) {
			sb.append("*(z-(" + c.toString() + "))");
		}
		
		return sb.toString();
	}
	
	/**
	 * Finds the index of the closest root for the given complex number that is within the given treshold.
	 * 
	 * @param z - a complex number
	 * @param treshold - wanted treshold
	 * @return index of the closest root for the given complex number z, or -1 if there is no such root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double closest = Double.MAX_VALUE;
		
		for (int i = 0; i < roots.length; i++) {
			double distance = Math.sqrt(Math.pow(roots[i].getRe() - z.getRe(), 2) + Math.pow(roots[i].getIm() - z.getIm(), 2));
			if (distance < closest && distance < treshold) {
				closest = distance;
				index = i;
			}
		}
		
		return index;
	}
}
