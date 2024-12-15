package Seguros;

import java.sql.*;


public class ConexionBD {
    public  static Connection conectarBD(String bd){
        Connection conectar=null;
        String host ="jdbc:mysql://localhost:3306/";
        String user ="root";
        String pass ="";


        System.out.println("Conectando.....");
        try {
            conectar = DriverManager.getConnection(host+bd,user,pass);
            System.out.println("CONEXION EXITOSA.......");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conectar;

    }
    public static void desconexion (Connection cb){
        try {
            cb.close();
            System.out.println("Desconectado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
