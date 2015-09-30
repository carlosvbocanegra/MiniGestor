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
import javax.swing.JTable;
/**
 *
 * @author Carlos
 */
public class SQLConexion extends Thread{
    protected Socket socketCliente;
    protected BufferedReader entrada;
    protected String consulta;
    private Vector columns;
    private Vector data;
    public SQLConexion(){
        start();
    }
    @Override
    public void run(){
        
            System.out.println("Aqui va la consulta:");
            consulta = "select * from usuario";
            System.out.println("Consulta a Ejecutar: " +consulta+ ";");
            ejecutaSQL();
    }
    public void ejecutaSQL(){
        Connection cnn; //Objeto de conexión a la base de datos
        Statement st; //Objeto con la sentencia SQL
        ResultSet rs; //Objeto con el resultado de la consulta SQL
        ResultSetMetaData resultadoMetaData;
        boolean existenMasFilas; //Indicador de si hay más filas
        String driver = "com.mysql.jdbc.Driver";
        String usuario = "root", clave = "", registro;
        int numeroColumnas, i;
        try{
            Class.forName(driver);
            cnn = DriverManager.getConnection("jdbc:mysql://localhost/demo",usuario, clave);
            st = cnn.createStatement();
            rs = st.executeQuery(consulta);
            
            existenMasFilas = rs.next();
            if(!existenMasFilas){
                System.out.println("No hay mas filas");
                return;
            }
            resultadoMetaData = rs.getMetaData();
            numeroColumnas = resultadoMetaData.getColumnCount();
            System.out.println(numeroColumnas + "columnas en el resultado.");
            while(existenMasFilas){
                registro = "";
                for(i=1;i<=numeroColumnas;i++){
                    registro = registro.concat(rs.getString(i)+" ");
                }
                System.out.println(registro); 
                existenMasFilas = rs.next();
            }
            //
            rs.close();
            st.close();
            cnn.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
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

