package hr.fer.oprpp1.custom.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		String[] arguments = args[0].split(" ");

		ObjectStack stack = new ObjectStack();
		
		for (String s : arguments) {
			try {
				stack.push(Integer.valueOf(s));
			}catch(Exception e) {
				Object arg2 = stack.pop();
				Object arg1 = null;
				
				if (!s.equals("sqr")) {
					arg1 = stack.pop();
				}
				
				int result = switch(s) {
					case "-" -> Integer.valueOf(arg1.toString()) - Integer.valueOf(arg2.toString());
					case "+" -> Integer.valueOf(arg1.toString()) + Integer.valueOf(arg2.toString());
					case "/" -> {
						if (Integer.valueOf(arg2.toString()) == 0) {
							System.out.println("Error! You tried to divide by zero!");
							System.exit(0);
						}
						int res = Integer.valueOf(arg1.toString()) / Integer.valueOf(arg2.toString());
						yield res;
					}
					case "*" -> Integer.valueOf(arg1.toString()) * Integer.valueOf(arg2.toString());
					case "%" -> Integer.valueOf(arg1.toString()) % Integer.valueOf(arg2.toString());
					case "pow" -> (int) Math.pow(Integer.valueOf(arg1.toString()), Integer.valueOf(arg2.toString()));
					case "sqr" -> Integer.valueOf(arg2.toString()) * Integer.valueOf(arg2.toString());
					default -> {
						System.out.println("Error! Invalid operator!");
						System.exit(0);
						yield 0;
					}
				};
				
				stack.push(result);
			}
		}
		
		if (stack.size() != 1) {
			System.out.println("Error!");
		} else {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}
	}

}
