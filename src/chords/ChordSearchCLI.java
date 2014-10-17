package chords;

import java.util.Scanner;

public class ChordSearchCLI {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter two integers:");
			int a = sc.nextInt(), b = sc.nextInt();
			System.out.println(ChordSearch.search(a, b));
		}
	}

}
