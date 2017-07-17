package ifrn.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ifrn.modelo.Composicoes;
import static java.lang.System.out;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

//public class CompoDAO {
//
//    private static CompoDAO instancia = new CompoDAO();
//
//    public static CompoDAO getInstancia() {
//        return instancia;
//    }
//
//    private CompoDAO() {
//    }
//
//    public int grava(Composicoes compo) {
//
//        int n = 0;
//        Connection conn = Conexao.getInstancia().criaConexao();
//
//        if (conn != null) {
//            PreparedStatement pstmt = null;
//            try {
//                pstmt = conn.prepareStatement(
//                        "INSERT INTO composicoes " +
//                        "(codigo_composicao, "
//                                + "classe"
//                                + "agrupador"
//                                + "descricao_agrupador"
//                                + "descricao_composicao"
//                                + "unidade"
//                                + "custo_mao_obra"
//                                + "mao_obra"
//                                + "custo_material"
//                                + ", link)" +
//                        " VALUES (?, ?, ?)");
//                pstmt.setInt(1, filmes.getId());
//                pstmt.setString(2, filmes.getTitulo());
//                pstmt.setString(3, filmes.getLink());
//                n = pstmt.executeUpdate();
//                
//                
//                CREATE TABLE composicoes (
//  codigo_composicao INT PRIMARY KEY NOT NULL,
//  classe VARCHAR(100),
//  agrupador INT,
//  descricao_agrupador VARCHAR(100),
//  descricao_composicao VARCHAR(100),
//  unidade FLOAT,
//  custo_mao_obra FLOAT,
//  mao_obra FLOAT,
//  custo_material FLOAT,
//  material FLOAT,
//  custo_equipamento FLOAT,
//  equipamento FLOAT,
//  custo_servicos_terceiros FLOAT,
//  servicos_terceiros FLOAT,
//  custo_outros FLOAT,
//  outros FLOAT
//  ); 
//                
//                
//                
//                
//                
//            } catch (SQLException e) {
//                out.println("Inclusao Falhou!!!\n" + e.getMessage());
//            } finally {
//                try {
//                    if (pstmt != null) {
//                        pstmt.close();
//                    }
//                    if (conn != null) {
//                        conn.close();
//                    }
//                } catch (SQLException e) {
//                    out.println(e.getMessage());
//                }
//            }
//        }
//
//        return n;
//
//    }
//
//    public List<Filmes> leTodos() {
//
//        Statement stmt = null;
//        List<Filmes> listaFilmes = new ArrayList<>();
//        Connection conn = Conexao.getInstancia().criaConexao();
//
//        if (conn != null) {
//            try {
//                stmt = conn.createStatement(TYPE_SCROLL_SENSITIVE, CONCUR_READ_ONLY);
//                ResultSet rs = stmt.executeQuery(
//                        "SELECT * FROM filmes");
//                
//                while (rs.next()) {
//                	
//                    Filmes filmes = new Filmes();
//                    
//                    filmes.setId(rs.getInt("id"));
//                    filmes.setTitulo(rs.getString("titulo"));
//                    filmes.setLink(rs.getString("link"));
//
//                    listaFilmes.add(filmes);
//                }
//                conn.close();
//                
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            } finally {
//                try {
//                    if (stmt != null) {
//                        stmt.close();
//                    }
//                    if (conn != null) {
//                        conn.close();
//                    }
//                } catch (SQLException e) {
//                    out.println(e.getMessage());
//                }
//            }
//        }
//        return listaFilmes;
//    }
//
//    public Filmes le(int id) {
//
//        Statement stmt = null;
//        Filmes filmes = null;
//        Connection conn = Conexao.getInstancia().criaConexao();
//        if (conn != null) {
//            try {
//                stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery(
//                        "SELECT * FROM filmes WHERE id = " + id);
//                if (rs.next()) {
//                    filmes = carregaDadosNoObjeto(rs);
//                } else {
//                    filmes = null;
//                }
//                conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            } finally {
//                try {
//                    if (stmt != null) {
//                        stmt.close();
//                    }
//                    if (conn != null) {
//                        conn.close();
//                    }
//                } catch (SQLException e) {
//                    out.println(e.getMessage());
//                }
//            }
//
//        }
//        return filmes;
//
//    }
//
//    private Filmes carregaDadosNoObjeto(ResultSet rs) throws SQLException {
//
//    	Filmes filmes = new Filmes();
//        filmes.setId(rs.getInt("id"));
//        filmes.setTitulo(rs.getString("titulo"));
//        filmes.setLink(rs.getString("link"));
//
//        return filmes;
//    }
//
//    public int apaga(int id) {
//
//        int n = 0;
//        Statement stmt = null;
//        Connection conn = Conexao.getInstancia().criaConexao();
//
//        if (conn != null) {
//            try {
//                stmt = conn.createStatement();
//                n = stmt.executeUpdate(
//                        "DELETE FROM filmes WHERE id = " + id);
//                conn.close();
//            } catch (SQLException ex) {
//            } finally {
//                try {
//                    if (stmt != null) {
//                        stmt.close();
//                    }
//                    if (conn != null) {
//                        conn.close();
//                    }
//                } catch (SQLException e) {
//                    out.println(e.getMessage());
//                }
//            }
//        }
//        return n;
//
//    }
//
//    public int altera(Filmes filmes) {
//
//        int n = 0;
//        Connection conn = Conexao.getInstancia().criaConexao();
//
//        if (conn != null) {
//            PreparedStatement pstmt = null;
//            try {
//                pstmt = conn.prepareStatement(
//                        "UPDATE filmes SET " +
//                        "id = ?, titulo = ?, link= ? " +
//                        "WHERE id = ?");
//                pstmt.setInt(1, filmes.getId());
//                pstmt.setString(2, filmes.getTitulo());
//                pstmt.setString(3, filmes.getLink());
//                pstmt.setInt(4, filmes.getId());
//                n = pstmt.executeUpdate();
//                conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            } finally {
//                try {
//                    if (pstmt != null) {
//                        pstmt.close();
//                    }
//                    if (conn != null) {
//                        conn.close();
//                    }
//                } catch (SQLException e) {
//                    out.println(e.getMessage());
//                }
//            }
//        }
//        return n;
//    }
//}