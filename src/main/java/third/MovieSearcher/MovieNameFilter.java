package third.MovieSearcher;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class MovieNameFilter {
	private String regex;
	private Settings settings;

	public void setSettings(Settings settings) {
		this.settings = settings;
		regex = settings.getSettings("movie.name.filter.regex");
	}

	public MovieNameFilter() {
		setSettings(SettingsDefaultImpl.getInstance());
	}

	public String filterMovieName(String input) {
		return input.toLowerCase().replaceAll(regex.toLowerCase()," ").trim().replaceAll(" +"," ");
	}
}
