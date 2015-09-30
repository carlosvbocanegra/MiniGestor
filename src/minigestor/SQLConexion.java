/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minigestor;

/**
 *
 * @author Carlos
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author Carlos
 */
public class SQLConexion extends Thread{
    protected Socket socketCliente;
    protected BufferedReader entrada;
    protected String consulta;
    protected String database;
    private String salida;
    public SQLConexion(String consulta, String database){
        this.consulta=consulta;
        this.database=database;
        this.salida=salida;
        start();
    }
    @Override
    public void run(){
        
            System.out.println("Aqui va la consulta:");
            System.out.println("Consulta a Ejecutar: " +consulta+ ";");
            ejecutaSQL();
    }
    public void ejecutaSQL(){
        Connection cnn; //Objeto de conexión a la base de datos
        Statement st; //Objeto con la sentencia SQL
        String driver = "com.mysql.jdbc.Driver";
        String usuario = "root", clave = "";
        JFrame frame=new JFrame();
        try{
            Class.forName(driver);
            cnn = DriverManager.getConnection("jdbc:mysql://localhost/"+database,usuario, clave);
            st = cnn.createStatement();
            st.executeUpdate(consulta);
             salida="Registros Actualizados";
            st.close();
            cnn.close();
        }catch(Exception e){
            System.out.println(e.toString());
            salida=e.toString();

        }
        JOptionPane.showMessageDialog(frame,salida);
    }
    public String getSalida(){
        return salida;
    }
    /*private static final int PUERTOESCUCHA = 6666;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt){
        abrirSocket();
    }
    private void abrirSocket(){
        Socket socket = null;
        try{
            socket = Socket("localhost", PUERTOESCUCHA);
            PrintStream salida = new PrintStream(socket.getOutputStream());
            salida.println(jTextField1.getText());
        }catch(IOException e){
            e.printStackTrace();;
        }finally{
            try{
                socket.close();
            }catch(IOException ee){
            }
        }
    }*/
}

