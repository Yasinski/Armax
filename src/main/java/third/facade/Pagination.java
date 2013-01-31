package third.facade;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class Pagination {

	public Integer currentPage;
	public Integer overallCount;
	public Integer numberOfPages;
	public Integer firstOnPage;

	//Construstors
	public Pagination() {
	}

	public Pagination(Integer currentPage, Integer overallCount, Integer numberOfPages, Integer firstOnPage) {
		this.currentPage = currentPage;
		this.overallCount = overallCount;
		this.numberOfPages = numberOfPages;
		this.firstOnPage = firstOnPage;
	}

	 //Getters&Setters
	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getOverallCount() {
		return overallCount;
	}

	public void setOverallCount(Integer overallCount) {
		this.overallCount = overallCount;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public Integer getFirstOnPage() {
		return firstOnPage;
	}

	public void setFirstOnPage(Integer firstOnPage) {
		this.firstOnPage = firstOnPage;
	}


	//Methods
	public Integer countNumberOfPages(Integer overallCount, Integer rowsOnPage) {
		numberOfPages = (overallCount - (overallCount % rowsOnPage)) / rowsOnPage + 1;
		return numberOfPages;
	}

	public Integer countFirstOnPage(Integer currentPage, Integer rowsOnPage){
		firstOnPage = (currentPage - 1) * rowsOnPage;
		return firstOnPage;
	}

}