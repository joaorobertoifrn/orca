package importing;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import enteties.Book;

/**
 * Class that imports data from files to database
 * @author alex_alokhin
 *
 */
public class ImportDataToMysql {
	
	private final String login, password, host,db;
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsmd = null;
    private PreparedStatement preparedStatement;
    private StringBuilder b;
    private BufferedReader br;
    private String line="";
    
    public ImportDataToMysql(String host,String db,String login, String password){
        this.login = login;
        this.password = password;
        this.host = host;
        this.db = db;
        getConnection();
    }
    /**
     * Create connection to database
     */
	public void getConnection(){
    	try {
    	    Class.forName("com.mysql.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    	    System.out.println("Where is your MySQL JDBC Driver?");
    	    e.printStackTrace();
    	}
        
		try {
			con = DriverManager.getConnection("jdbc:mysql://"+host+'/'+db,login,password);
			System.out.println("Connection established");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
    /**
     * @param jsonPath - path to file 
     * @param table - table from database to write data
     * @throws SQLException
     * @throws IOException
     * @throws ParseException
     * If you are using incorrect data type or format the program will throw exception
     */
    public void writeToDBFromJSON(String jsonPath,String table) throws SQLException, IOException, ParseException{
    	Object obj;
    	JSONObject jsonObject;
    	JSONParser parser=new JSONParser();
    	ArrayList<String> columnNames = new ArrayList<>();
    	
    	b = getAllDataAndAppendTokens(table);
    	preparedStatement = (PreparedStatement) con.prepareStatement("Insert into "+table+" values("+b+")");
    	br = new BufferedReader(new FileReader(jsonPath));

    	while ((line = br.readLine()) != null) {
    		
    		obj = parser.parse(line);
            jsonObject = (JSONObject) obj;
            int i = 1;
            for (String string : columnNames) {
				preparedStatement.setObject(i++, jsonObject.get(string));
			}
            preparedStatement.executeUpdate();
    	}
    	
    	System.out.println("Data stored successfully");

    }
    
    
    /**
     * 
     * @param csvFilePath - path to file
     * @param table - table from database to write data
     * @param split - symbol that split data in file
     * @throws SQLException
     * @throws IOException
     * If you are using incorrect data type or format program will throw exception
     */
    public void writeToDBFromCSV(String csvFilePath,String table,String split) throws SQLException, IOException{
    	b = getAllDataAndAppendTokens(table);
    	
    	preparedStatement = con.prepareStatement("Insert into "+table+" values("+b+")");
    	br = new BufferedReader(new FileReader(csvFilePath));
        
    	while ((line = br.readLine()) != null) {
            	List<Object> myList = new ArrayList<Object>(Arrays.asList(line.split(split)));
            	int i = 1;
            	for (int j = 0; j < myList.size(); j++) {
            		if("".equals(myList.get(j))){
            			myList.set(j, null);
            		}
            		preparedStatement.setObject(i++, myList.get(j));
				}
            	preparedStatement.executeUpdate();
        }
            
        System.out.println("Data stored successfully");
    }
    
    /**
     * 
     * @param xmlFilePath - path to file
     * @param table - table from database to write date
     * @throws SQLException
     * If you are using incorrect data type or format program will throw exception
     */
	public void writeToDBFromXML(String xmlFilePath, String table) throws SQLException {
		ArrayList<Book> books;
	    Book book;
		b = new StringBuilder();
		books = new ArrayList<>();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(xmlFilePath);
		  try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("row");

			for (int i = 0; i < list.size(); i++) {
				book = new Book();
				Element node = (Element) list.get(i);

				book.setId(Integer.valueOf(node.getChildText("id")));
				book.setName(node.getChildText("name"));
				book.setYear(Integer.valueOf(node.getChildText("year")));
				book.setAuthor(node.getChildText("author"));
			   
				books.add(book);
			}
			
		  } catch (IOException io) {
			System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		  }
		  
		  b = getAllDataAndAppendTokens(table);
		  preparedStatement = con.prepareStatement("Insert into "+table+" values("+b+")");
		  
		  for (Book bookTemp : books) {
			  int i = 1;
			  
			  preparedStatement.setObject(i++, bookTemp.getId());
			  preparedStatement.setObject(i++, bookTemp.getName());
			  preparedStatement.setObject(i++, bookTemp.getAuthor());
			  preparedStatement.setObject(i++, bookTemp.getYear());
			  preparedStatement.executeUpdate();
		  }
		  
		  System.out.println("Data stored successfully");
      }


	/**
	 * Method that works with table and number of columns to work with prepared statement
	 * @param table - table that uses
	 * @return - String to append to prepared statement
	 * @throws SQLException
	 */
	public StringBuilder getAllDataAndAppendTokens(String table) throws SQLException{
		st = con.createStatement();
		rs = st.executeQuery("select * from "+table);
		rsmd  = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
	    	
		  for (int i = 1; i <= columnCount; i++) {
	    		if(i==columnCount) 
	    			b.append("?");
	    		else 
	    			b.append("?,");
		  }
		  return b;
	}
		  
	
/*	public static void main(String[] args) throws IOException, SQLException, ParseException, SAXException, ParserConfigurationException {
		final String HOST = "localhost:3306";
    	final String DB_NAME = "trainee";
    	final String USER = "sanya";
    	final String PASSWORD = "1";
    	final String TABLE_NAME = "users";
    	final String FILE_NAME = "/home/sanya/usa.csv";
    	final String SEPARATOR = ";";
		ImportDataToMysql data = new ImportDataToMysql(HOST,DB_NAME, USER, PASSWORD);
		data.writeToDBFromCSV(FILE_NAME, TABLE_NAME, SEPARATOR);
	}*/
			
}
