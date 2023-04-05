package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Newton-Raphson iteration-based fractal viewer.
 */
public class Newton {

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		String input;
		int counter = 1;
		List<Complex> roots = new ArrayList<>();
		do {
			System.out.printf("Root %d> ", counter);
			input = sc.nextLine();
			if (input.equals("done")) continue;
			String[] rootElements = input.split(" ");
			double re = 0;
			double im = 0;
			try {
				re = Double.parseDouble(rootElements[0]);
			} catch(Exception e) {
				if (rootElements.length > 0) {
					if (rootElements[0].equals("i")) {
						im = 1;
						roots.add(new Complex(re, im));
						counter++;
						continue;
					}
					
					try {
						im = Double.parseDouble(rootElements[0].substring(1));
					} catch(Exception ex) {
						System.out.println("Invalid input!");
						continue;
					}
					
					roots.add(new Complex(re, im));
					counter++;
					continue;
				}
			}
			
			if (rootElements.length == 1) {
				roots.add(new Complex(re, im));
				counter++;
			} else if (rootElements.length == 3) {
				if (rootElements[1].equals("+") || rootElements[1].equals("-")) {
					if (rootElements[2].equals("i")) {
						im = 1;
					} else {
						try {
							im = Double.parseDouble(rootElements[2].substring(1));
						} catch(Exception ex) {
							System.out.println("Invalid input!");
							continue;
						}
					}
					im *= (rootElements[1].equals("+") ? 1 : -1);
					roots.add(new Complex(re, im));
					counter++;
				} else {
					System.out.println("Invalid input!");
				}
			} else {
				System.out.println("Invalid input!");
			}
		}while (!input.equals("done"));
		
		sc.close();
		
		if (roots.size() < 2) {
			System.out.println("Invalid number of roots!");
			System.exit(0);
		}
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		FractalViewer.show(new MyProducer(roots));
	}

	public static class MyProducer implements IFractalProducer {
		private Complex[] roots;
		
		public MyProducer(List<Complex> roots) {
			this.roots = roots.toArray(new Complex[0]);
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, 
				int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			
			int m = 16 * 16 * 16;
			int offset = 0;
			short[] data = new short[width * height];
			
			ComplexPolynomial polynom = new ComplexPolynomial(roots);
			ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(
					Complex.ONE, roots);
			
			for (int y = 0; y < height; y++) {
				if (cancel.get()) break;
				
				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim= (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex c = new Complex(cre, cim);
					Complex zn = c;
					Complex znold;
					double module = 0;
					int iter = 0;
					do {
						Complex numerator = polynomial.toComplexPolynom().apply(zn);
						Complex denominator = polynomial.toComplexPolynom().derive().apply(zn);
						znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;
					} while(module > 0.001 && iter < m);
					int index = polynomial.indexOfClosestRootFor(zn, 0.002);
					data[offset++] = (short) (index + 1);
				}
			}
			
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);
		}
		
	}
}
