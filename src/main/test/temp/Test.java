//package temp;
//
//import third.model.Movies;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
///**
// * writeme: Should be the description of the class
// *
// * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
// */
//
//public class FacebookController {
//	public static void main(String[] args) {
//		List<Movies> movies = new ArrayList<Movies>();
//		movies.add(new Movies("Title a", "Genre 111", "1993", 4));
//		movies.add(new Movies("Title d", "Genre 111", "1993", 3));
//		movies.add(new Movies("Title b", "Genre 111", "1993", 1));
//		movies.add(new Movies("Title c", "Genre 111", "1993", 2));
//
//		for (Movies movie : movies) {
//			System.out.println(movie.getId() + " " + movie.getTitle());
//		}
//
//		Collections.sort(movies, new MovieComparatorTitle());
//
//		System.out.println("");
//		System.out.println("Sorted title");
//		for (Movies movie : movies) {
//			System.out.println(movie.getId() + " " + movie.getTitle());
//		}
//
//		Collections.sort(movies, new Comparator<Movies>() {
//			@Override
//			public int compare(Movies o1, Movies o2) {
//				if (o1.getId() < o2.getId()) {
//					return -1;
//				}
//				if (o1.getId() > o2.getId()) {
//					return 1;
//				}
//				return 0;
//			}
//		});
//
//		System.out.println("");
//		System.out.println("Sorted ID");
//		for (Movies movie : movies) {
//			System.out.println(movie.getId() + " " + movie.getTitle());
//		}
//
//
//		Collections.sort(movies);
//
//		System.out.println("");
//		System.out.println("Sorted default");
//		for (Movies movie : movies) {
//			System.out.println(movie.getId() + " " + movie.getTitle());
//		}
//
//	}
//
//	static class MovieComparatorTitle implements Comparator<Movies> {
//		@Override
//		public int compare(Movies o1, Movies o2) {
//			return o1.getTitle().compareTo(o2.getTitle());
//		}
//	}
//}
