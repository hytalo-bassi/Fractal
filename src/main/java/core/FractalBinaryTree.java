package core;

public class FractalBinaryTree {
	// String input: the actual input
	// int n: number of iterations to be performed
	protected static String recursiveGetString(String input, int n) {
		String currentString = "";
		if (n == 0) {
			return input;
		} else {
			for (int i = 0; i < input.length(); i++) {
				char c = input.charAt(i);
				currentString += rewriteChar(c);
			}

		}
		return recursiveGetString(currentString, --n);
	}
	
	protected static boolean isConst(char c) {
		if (c == '[' || c == ']') {
			return true;
		}
		return false;
	}

	protected static String rewriteChar(char c) {
		if (isConst(c)) {
			return String.valueOf(c);
		} else {
			if (c == '0') {
				return "F[0]0";
			} else {
				return "FF";
			}
		}
	}

	// int n: number of iterations
	public static String getString(int n) {
		String axiom = "0";
		return recursiveGetString(axiom, n);
	}
}
