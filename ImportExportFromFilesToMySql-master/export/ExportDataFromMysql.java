package export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Class that export table data to files
 * @author alex_alokhin
 *
 */
public class ExportDataFromMysql {

	private final String login, password, host,db;
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsmd =null;
    private final String SQL_FOR_EXPORT = "SELECT * FROM";
    private StringBuilder b;
    
    public ExportDataFromMysql(String host,String db,String login, String password){
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
     * Get table data in XML format
     * @param tableName - table that holds data
     * @return - String with XML representation
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String getTableDataXML(String tableName) throws SQLException, ClassNotFoundException {
        st = con.createStatement();
        rs = st.executeQuery(SQL_FOR_EXPORT + tableName);
        rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        b = new StringBuilder("<table>");
            while (rs.next()) {
                b.append("<row>");
                for (int i = 1; i <= colCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    b.append("<").append(columnName).append(">");
                    b.append(rs.getObject(i));
                    b.append("</").append(columnName).append(">");
                }
                b.append("</row>");
            }
        b.append("</table>");
        
        return b.toString();
    }

    /**
     * Get table data in CSV format
     * @param tableName - table that holds data
     * @return - String with CSV representation
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String getTableDataCSV(String tableName,String separator) throws SQLException{
        st = con.createStatement();
        rs = st.executeQuery(SQL_FOR_EXPORT + tableName);
        rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        b = new StringBuilder();
            while (rs.next()) {
                for (int i = 1; i <= colCount; i++) {
                    b.append(rs.getObject(i));
                    if(i!=colCount)
                    	b.append(separator);
                }
                b.append("\n");
            }
            
        return b.toString();
    }
    
    /**
     * Get table data in JSON format
     * @param tableName - table that holds data
     * @return - String with JSON representation
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String getTableDataJSON(String table) throws SQLException, JSONException{
    	LinkedHashMap<String, Object> jsonOrderedMap = new LinkedHashMap<String, Object>();
    	JSONObject json ;
    	st = con.createStatement();
        rs = st.executeQuery(SQL_FOR_EXPORT + table);
    	ResultSetMetaData rsmd = rs.getMetaData();
    	int numColumns  = rsmd.getColumnCount();
    	
    	String key[] = new String[numColumns];
    	int columnNumber = 0;
    	for(int i=0;i<numColumns;i++) { 
    	    columnNumber = i + 1; 
    	    key[i] = rsmd.getColumnName(columnNumber); 
    	}
    	
    	while(rs.next()) {
    		
    		for(int i=0;i<numColumns;i++) {
    			jsonOrderedMap.put(key[i], rs.getObject(key[i]));
    		}
    		
    		json = new JSONObject(jsonOrderedMap);
    		//b.append(json+"\n");
    		//to use pretty format without order use this
    		b.append(json.toString(3)+"\n");
    	}
    	
    	return b.toString();
    }
    
    
    public void dataToXMLFileParsing(String xmlSource,String path) 
            throws SAXException, ParserConfigurationException, IOException, TransformerException {
    	File file = new File(path);
    	if(!file.exists()){
    		file.createNewFile();
    	}

    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result =  new StreamResult(file);
        transformer.transform(source, result);
        System.out.println("Data stored successfully");
    }
    
    public File storeDataToFile(String content, String path){
    	FileOutputStream fop = null;
		File file = null;

		try {
			file = new File(path);
			if (!file.exists()){
				file.createNewFile();
			}
			
			fop = new FileOutputStream(file);

			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Data stored successfully");
		}
		
	return file;
}	

    
   /* public static void main(String args[]) throws ClassNotFoundException, SQLException, SAXException, ParserConfigurationException, IOException, TransformerException, JSONException {
    	final String HOST = "localhost:3306";
    	final String DB_NAME = "trainee";
    	final String USER = "sanya";
    	final String PASSWORD = "1";
    	final String TABLE_NAME = "users";
    	final String FILE_NAME = "/home/sanya/usa.csv";
		ExportDataFromMysql data = new ExportDataFromMysql(HOST,DB_NAME, USER, PASSWORD);
		String contentForJson = data.getTableDataJSON(TABLE_NAME);
		data.storeDataToFile(contentForJson, FILE_NAME);

    }*/
}
