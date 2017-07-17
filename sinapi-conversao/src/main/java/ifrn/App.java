package ifrn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import au.com.bytecode.opencsv.*;

public class App {

    public static final String csvFile = "c:\\SINAPI_composicoes.csv";

    public static void main(String[] args) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://127.0.0.1:3306/sinapi";
        String user = "root";
        String password = "1234";
        int count = 0;
        int totalcount = 0;
        String values = "(0,0,0,0,0,0,0,0,0,0,0,0)";
        String query = "";
        try {

            CSVReader reader = new CSVReader(new FileReader(csvFile));
            Object[] nextLine;
            String[] firstLine = reader.readNext();
            // firstLine = reader.readNext();
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
                
            while ((nextLine = reader.readNext()) != null) {
 
                for (int i = 0; i < nextLine.length; i++) {
                    System.out.println(nextLine[i]);
                }
    
                
                // if (nextLine[0] == null) continue;
                /*
                if (nextLine[7] == "COMPOSICAO") {
                
                
                    values  = "('" + nextLine[0] 
                            + "','" + nextLine[1] 
                            + "','" + nextLine[2] 
                            + "','" + nextLine[3] 
                            + "','" + nextLine[4] 
                            + "','" + nextLine[5] 
                            + "','" + nextLine[6] 
                            + "','" + nextLine[14] 
                            + "','" + nextLine[15] 
                            + "','" + nextLine[16] 
                            + "','" + nextLine[17] 
                            + "','" + nextLine[18] 
                            + "','" + nextLine[19] 
                            + "','" + nextLine[20] 
                            + "','" + nextLine[21] 
                            + "','" + nextLine[22] 
                            + "','" + nextLine[23] 
                            + "')";

                
                    query = "INSERT INTO  `sinapi`.`composicoes` ("
                            + "`classe` ,"
                            + "`agrupador` ,"
                            + "`descricao_agrupador` ,"
                            + "`codigo_composicao` ,"
                            + "`descricao_composicao` ,"
                            + "`unidade` ,"
                            + "`custo_total` ,"
                            + "`custo_mao_obra` ,"
                            + "`mao_obra` ,"
                            + "`custo_material` ,"
                            + "`material` ,"
                            + "`custo_equipamento` ,"
                            + "`equipamento` ,"
                            + "`custo_servicos_terceiros)"
                            + "`servicos_terceiros)"
                            + "`custo_outros)"
                            + "`outros)"
                            + "VALUES " + values + ";";

                    st.executeUpdate(query);
                    // values = "(0,0,0,0,0,0,0,0,0,0,0,0)";
                } 
                if (nextLine[7] == "INSUMO") {
                
                    values  = "('" + nextLine[3] 
                            + "','" + nextLine[7] 
                            + "','" + nextLine[8] 
                            + "','" + nextLine[9] 
                            + "','" + nextLine[10] 
                            + "','" + nextLine[11] 
                            + "','" + nextLine[12] 
                            + "','" + nextLine[13] 
                            + "')";
                
                    query = "INSERT INTO  `sinapi`.`compo_itens` ("
                            + "`FK_codigo_composicao` ,"
                            + "`tipo_item` ,"
                            + "`codigo_item` ,"
                            + "`descricao_item` ,"
                            + "`unidade_item` ,"
                            + "`coeficiente` ,"
                            + "`preco_unitario` ,"
                            + "`custo_total` ,"
                            + "VALUES " + values + ";";

                    st.executeUpdate(query);
                     
                } 
                */
            }
            query = "DELETE FROM `composicoes` WHERE `codigo_composicao` = '0'";
            st.executeUpdate(query);

             
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(App.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(App.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        System.out.println(totalcount);
    }
}
