package ifrn;

/**
 * Class that represent table object
 * @author alex_alokhin
 *
 */
public class Book {
	
	private Integer id;
	
	private String name;
	
	private String author;
	
	private Integer year;
	
	public Book() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
}	