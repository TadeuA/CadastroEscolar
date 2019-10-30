/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

/**
 *
 * @author Tadeu
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexao {
    //TODO Teste
    //atributo con da classe Connection: para estabelcer a conexao com o SGBD
    private static Connection con;
    //atributo stm da classe Statement: para elaborar as expressoes SQL
    private static Statement stm;
    
    /** Creates a new instance of Conexao */
    private static void conectar(){
        //TODO tentar fazer conexao com banco MySQL
        String driver = "jdbc:mysql:";
        String bancoDados = "//localhost:3306/db_cursos";
        String user = "root";
        String pass = "";
        
        try {
            //Acessar a classe Driver mysql
            Class.forName("com.mysql.jdbc.Driver");
            //estabelecimento da conexao atraves da invocacao do metodo getConnection
            con = DriverManager.getConnection(driver + bancoDados, user, pass);
            //habilitando stm para execucao de expressoes SQL
            stm = con.createStatement();            
        }
        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Excecao ClassNotFound !");
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Excecao SQL ! Conexao nao pode ser realizada");
        }
    }
    
    public static void encerrar() {
        try {
            con.close();    
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Excecao SQL !");
        }
        
    }
    
    public static Statement getStm() {
        return stm;
    }
    
    public static Connection getCon() {
        if (con == null) {
            conectar();
        } 
        return con;
    }
    
    public static void setStm(Statement s) {
        stm = s;
    }
    
    public static void setCon(Connection c) {
        con = c;
    }
}
