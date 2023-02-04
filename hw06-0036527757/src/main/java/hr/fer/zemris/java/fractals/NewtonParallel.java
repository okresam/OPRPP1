package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Newton-Raphson iteration-based fractal viewer with multithreading.
 */
public class NewtonParallel {

	public static void main(String[] args) {
		int workers = 0;
		int tracks = 0;
		
		for (int i = 0; i < args.length; i++) {
			String[] argsSplit = args[i].split("=");
			if (argsSplit.length == 2) {
				if (argsSplit[0].equals("--workers") || argsSplit[0].equals("-w")) {
					try {
						if (workers != 0)
							throw new IllegalArgumentException();
						workers = Integer.parseInt(argsSplit[1]);
					} catch(Exception e) {
					}
				}
				if (argsSplit[0].equals("--tracks") || argsSplit[0].equals("-t")) {
					try {
						if (tracks != 0)
							throw new IllegalArgumentException();
						tracks = Integer.parseInt(argsSplit[1]);
					} catch(Exception e) {
					}
				}
			}
		}
		
		if (workers <= 0)
			workers = Runtime.getRuntime().availableProcessors();
		
		if (tracks <= 0)
			tracks = 4 * workers;
		
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
		
		FractalViewer.show(new MyProducer(roots, workers, tracks));
	}
	
	public static class CalculationJob implements Runnable {

		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		Complex[] roots;
		public static CalculationJob NO_JOB = new CalculationJob();
		
		private CalculationJob() {
		}
		
		public CalculationJob(double reMin, double reMax, double imMin, double imMax,
							int width, int height, int yMin, int yMax, int m,
							short[] data, AtomicBoolean cancel, Complex[] roots) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.roots = roots;
		}
		
		@Override
		public void run() {
			int offset = width * yMin;
			ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(
												Complex.ONE, roots);
			
			for (int y = yMin; y <= yMax; y++) {
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
		}
		
	}
	
	public static class MyProducer implements IFractalProducer {
		private Complex[] roots;
		private int numberOfWorkers;
		private int numberOfTracks;
		
		public MyProducer(List<Complex> roots, int numberOfWorkers, int numberOfTracks) {
			this.roots = roots.toArray(new Complex[0]);
			this.numberOfWorkers = numberOfWorkers;
			this.numberOfTracks = numberOfTracks;
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, 
				int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			
			if (numberOfTracks > height)
				numberOfTracks = height;
			
			System.out.println("Number of workers: " + numberOfWorkers);
			System.out.println("Number of jobs: " + numberOfTracks);
			
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			ComplexPolynomial polynom = new ComplexPolynomial(roots);
			int numberYPerTrack = height / numberOfTracks;
			
			final BlockingQueue<CalculationJob> queue = new LinkedBlockingQueue<>();
			
			Thread[] workers = new Thread[numberOfWorkers];
			for (int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							CalculationJob p = null;
							try {
								p = queue.take();
								if (p == CalculationJob.NO_JOB) break;
							} catch(InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			
			for (int i = 0; i < workers.length; i++) {
				workers[i].start();
			}
			
			for (int i = 0; i < numberOfTracks; i++) {
				int yMin = i * numberYPerTrack;
				int yMax = (i + 1) * numberYPerTrack - 1;
				if (i == numberOfTracks - 1) {
					yMax = height - 1;
				}
				CalculationJob job = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, roots);
			    while(true) {
			    	try {
			    		queue.put(job);
			    		break;
			    	} catch(InterruptedException e) {
			    	}
			    }
			}
			
			for (int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						queue.put(CalculationJob.NO_JOB);
						break;
					} catch(InterruptedException e) {
					}
				}
			}
			
			for (int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						workers[i].join();
						break;
					} catch(InterruptedException e) {
					}
				}
			}
			
			System.out.println("Calculation done!");
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);
		}
		
	}

}
