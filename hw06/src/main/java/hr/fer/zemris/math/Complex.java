package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a complex number.
 */
public class Complex {
	
	/**
	 * Real part of the complex number.
	 */
	private double re;
	
	/**
	 * Imaginary part of the complex number.
	 */
	private double im;
	
	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);
	
	/**
	 * Default constructor for Complex.
	 */
	public Complex() {
		this(0, 0);
	}
	
	/**
	 * Constructor for Complex that sets the values of the complex number to the given values.
	 * 
	 * @param re - real part of the number
	 * @param im - imaginary part of the number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Getter for real part of complex number.
	 * 
	 * @return real value
	 */
	public double getRe() {
		return re;
	}
	
	/**
	 * Getter for imaginary part of complex number.
	 * 
	 * @return imaginary value
	 */
	public double getIm() {
		return im;
	}
	
	/**
	 * Calculates the module of this complex number.
	 * 
	 * @return module of this complex number.
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}
	
	/**
	 * Multiplies this complex number with the given complex number.
	 * 
	 * @param c - complex number to be multiplied with
	 * @return a complex number, result of the multiplication
	 */
	public Complex multiply(Complex c) {
		double newRe = re * c.re - im * c.im;
		double newIm = im * c.re + re * c.im;
		
		return new Complex(newRe, newIm);
	}
	
	/**
	 * Divides this complex number with the given complex number.
	 * 
	 * @param c - complex number to be divided with
	 * @return a complex number, result of the division
	 */
	public Complex divide(Complex c) {
		Complex conjugateC = new Complex(c.re, -c.im);
		
		Complex numerator = multiply(conjugateC);
		Complex denominator = new Complex(c.im * c.im + c.re * c.re, 0);
		
		double newRe = numerator.re / denominator.re;
		double newIm = numerator.im / denominator.re;
		
		return new Complex(newRe, newIm);
	}
	
	/**
	 * Adds the given complex number to this complex number.
	 * 
	 * @param c - complex number to be added
	 * @return a complex number, result of the addition
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}
	
	/**
	 * Subtracts the given complex number from this complex number.
	 * 
	 * @param c - complex number to be subtracted
	 * @return a complex number, result of the subtraction
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}
	
	/**
	 * Negates this complex number.
	 * 
	 * @return negated complex number
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Calculates this complex number to the power of n.
	 * 
	 * @param n - exponent
	 * @return this complex number to the power of n
	 */
	public Complex power(int n) {
		double angle = Math.atan2(im, re);
		double modul = module();
		
		return new Complex(Math.pow(modul, n) * Math.cos(n * angle), Math.pow(modul, n) * Math.sin(n * angle));
	}
	
	/**
	 * Calculates the nth root of this complex number.
	 * 
	 * @param n - root number
	 * @return nth root of this complex number
	 */
	public List<Complex> root(int n) {
		ArrayList<Complex> roots = new ArrayList<>();
 		double angle = Math.atan2(im, re);
 		double modul = Math.round(Math.pow(module(), 1. / n));
 		
		for (int i = 0; i < n; i++) {
			double cosPart = (angle + 2 * i * Math.PI) / n;
			double sinPart = (angle + 2 * i * Math.PI) / n;
			
			roots.add(new Complex(modul * Math.cos(cosPart) , modul * Math.sin(sinPart)));
		}
		
		return roots;
	}
	
	@Override
	public String toString() {
		return re + (im < 0 ? "-i" : "+i")  + Math.abs(im);
	}
	
}
