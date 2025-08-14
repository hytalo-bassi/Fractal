import java.util.function.Function;
import java.util.*;


// IMPORTANT: This is ad-hoc generalization of parametrized D0L-Systems. It'll probably
// be changed after proper research to be more optimized and easy to use.
// This class is meant to help when dealing with
// parametrized D0L-Systems
//public class Lsystem {
//	private String axiom;
//	private List<String> symbols;
//	private List<Rule> rules;
//
//	public Lsystem(String axiom_, List<String> symbols_, List<Rule> rules_) {
//		this.axiom = axiom_;
//		if (symbols.size() != rules.size()) {
//			return;
//		}
//		this.rules = rules_;
//		this.symbols = symbols_;
//	}
//	
//	private int findFuncEnd(String input, int start) {
//		for (int i = start; i < input.length(); i++) {
//			if (input.charAt(i) == ')') {
//				return i;
//			}
//		}
//		return -1;
//	}
//
//	private String getString(String input, int n) {
//		String currentString = "";
//		if (n == 0) {
//			return input;
//		}
//		
//		for (int i = 0; i < input.length(); i++) {
//			char c = input.charAt(i);
//			int end = findFuncEnd(input, c);
//			if (end < 0) {
//				currentString += rewriteChar(c);
//			} else {
//				final String func = input.substring(i, end);
//				final int symbolIndex = getSymbolIndex(func);
//				if (symbolIndex < 0) {
//					System.out.println("Err: LSYSTEM");
//					return "";
//				}
//				currentString += rules.get(symbolIndex).execute(func.split(","));
//			}
//		}
//
//		return getString(input, --n);
//	}
//	
//	private int getSymbolIndex(String s) {
//		for (int i = 0; i < symbols.size(); i++) {
//			if (s == symbols.get(i)) {
//				return i;
//			}
//		}
//		return -1;
//	}
//	// Gets the string after n iterations
//	public String iterate(int n) {
//		return getString(this.axiom, n);
//	}
//
//	static class Rule {
//		private ArrayList<Function<double[], Boolean>> predicates
//			= new ArrayList<Function<double[], Boolean>>();
//		private ArrayList<Function<double[], String>> rewriteCallbacks =
//			new ArrayList<Function<double[], String>>();
//
//		void addRule(Function<double[], String> callback, Function<double[], Boolean> predicate) {
//			rewriteCallbacks.add(callback);
//			if (predicate != null) {
//				predicates.add(predicate);
//			}
//		}
//
//		String execute(double... parameters) {
//			for (int i = 0; i < predicates.size(); i++) {
//				if (predicates.get(i).apply(parameters)) {
//					return rewriteCallbacks.get(i).apply(parameters);
//				}
//			}
//
//			return rewriteCallbacks.get(0).apply(parameters);
//		}
//	}
//}
