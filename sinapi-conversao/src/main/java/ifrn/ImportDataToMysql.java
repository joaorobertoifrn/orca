package ifrn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import jdk.internal.org.xml.sax.SAXException;


public class ImportDataToMysql {

    private final String login, password, host, db;
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsmd = null;
    private PreparedStatement preparedStatement;
    private StringBuilder b;
    private BufferedReader br;
    private String line = "";

    public ImportDataToMysql(String host, String db, String login, String password) {
        this.login = login;
        this.password = password;
        this.host = host;
        this.db = db;
        getConnection();
    }

    /**
     * Create connection to database
     */
    public void getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + '/' + db, login, password);
            System.out.println("Connection established");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param csvFilePath - path to file
     * @param table - table from database to write data
     * @param split - symbol that split data in file
     * @throws SQLException
     * @throws IOException If you are using incorrect data type or format
     * program will throw exception
     */
    public void writeToDBFromCSV(String csvFilePath, String table, String split) throws SQLException, IOException {
        b = getAllDataAndAppendTokens(table);

        preparedStatement = con.prepareStatement("Insert into " + table + " values(" + b + ")");
        br = new BufferedReader(new FileReader(csvFilePath));

        while ((line = br.readLine()) != null) {
            List<Object> myList = new ArrayList<Object>(Arrays.asList(line.split(split)));
            int i = 1;
            for (int j = 0; j < myList.size(); j++) {
                if ("".equals(myList.get(j))) {
                    myList.set(j, null);
                }
                preparedStatement.setObject(i++, myList.get(j));
            }
            preparedStatement.executeUpdate();
        }

        System.out.println("Data stored successfully");
    }

    /**
     * Method that works with table and number of columns to work with prepared
     * statement
     *
     * @param table - table that uses
     * @return - String to append to prepared statement
     * @throws SQLException
     */
    public StringBuilder getAllDataAndAppendTokens(String table) throws SQLException {
        st = con.createStatement();
        rs = st.executeQuery("select * from " + table);
        rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            if (i == columnCount) {
                b.append("?");
            } else {
                b.append("?,");
            }
        }
        return b;
    }

    public static void main(String[] args) throws IOException, SQLException, ParseException, SAXException, ParserConfigurationException {
        final String HOST = "localhost:3306";
        final String DB_NAME = "sinapi";
        final String USER = "root";
        final String PASSWORD = "1234";
        final String TABLE_NAME = "composicoes";
        final String FILE_NAME = "c://SINAPI_Composicoes.csv";
        final String SEPARATOR = ";";
        ImportDataToMysql data = new ImportDataToMysql(HOST, DB_NAME, USER, PASSWORD);
        data.writeToDBFromCSV(FILE_NAME, TABLE_NAME, SEPARATOR);
    }
}
