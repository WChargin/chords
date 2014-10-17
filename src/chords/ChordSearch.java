package chords;

public final class ChordSearch {

	public static final class Result {
		public final int tree;
		public final boolean isRoot;
		public final Chord position;

		private Result(int tree, boolean isRoot, Chord position) {
			super();
			this.tree = tree;
			this.isRoot = isRoot;
			this.position = position;
		}

		@Override
		public String toString() {
			return "Result [tree=" + tree + ", isRoot=" + isRoot
					+ ", position=" + position + "]";
		}
	}

	private static Chord findPair(Chord chord) {
		if (chord.a == 1) {
			return new Chord(chord.b - 1, 1);
		}

		int c = chord.b % chord.a;
		int t = (chord.b - c) / chord.a;

		final Chord recurseArgument = new Chord(c, chord.a);
		final Chord recursed = findPair(recurseArgument);

		c = 1;
		for (int i = 1; i <= t - 1; i++) {
			c *= 2;
		}
		return new Chord(recursed.a + t, (2 * recursed.b - 1) * c + 1);
	}

	private static int gcd(int a, int b) {
		int c;
		while (a != 0) {
			c = a;
			a = b % a;
			b = c;
		}
		return b;
	}

	public static Result search(final int a, final int b) {
		if (a == b) {
			return new Result(a, true, null);
		}
		if (a > b) {
			throw new IllegalArgumentException("Must have a <= b");
		}

		final int gcd = gcd(a, b);
		final Chord pair = findPair(new Chord(a / gcd, b / gcd));
		return new Result(gcd, false, pair);
	}

	private ChordSearch() {
		throw new UnsupportedOperationException();
	}

}
