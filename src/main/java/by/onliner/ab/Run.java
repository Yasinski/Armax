package by.onliner.ab;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class Run {

	public static void main(String[] args) throws Exception {
		String url0 = "http://ab.onliner.by/car/";
		int end = 75440;

		Parser parser = new Parser();
		for (int i = 75438; i <= end; i++) {
			String url = url0 + i;
			parser.parseIt(url);
		}

	}
}
