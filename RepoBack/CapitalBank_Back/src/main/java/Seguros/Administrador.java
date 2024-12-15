package Seguros;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Administrador extends Usuario {
    static Connection cb = ConexionBD.conectarBD("capitalBank_bd");
    static Scanner sc = new Scanner(System.in);
    public Administrador(String nombre, String contrasena, String email, String telefono) {
        super(nombre, contrasena, email, telefono);
    }
    public Administrador(){

    }
    public static void eliminarProducto(Connection con,int idElim){
        String sql = "delete from seguros where id ='"+idElim+"'";
        Statement statement;
        int result;
        try {
            statement=con.createStatement();
            result=statement.executeUpdate(sql);
            if (result==1){
                System.out.println("Se borro el seguro con id :"+idElim);
            }else{
                System.out.println("No se encuentra el seguro con id :"+idElim);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void verProductos(Connection con){
        String sql ="SELECT * FROM seguros";
        Statement statement;
        ResultSet rs;
        int id;
        String tipo;
        String compania;
        int costo;
        String fecha_inicio;
        String fecha_fin;


        try{
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                id =rs.getInt("id");
                tipo =rs.getString("tipo");
                compania =rs.getString("compania");
                costo = rs.getInt("costo");
                fecha_inicio = rs.getString("fecha_inicio");
                fecha_fin = rs.getString("fecha_fin");

                System.out.println("Id :"+id+"| Tipo :"+tipo+"| Compañia :"+compania+"| Costo :"+costo+
                        "| Fecha Inicio :"+fecha_inicio+"| Fecha Fin :"+fecha_fin);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void actualizarProduct(Connection con,int id,String campo,String valor){
        String sql = "update seguros set "+campo+" ='"+valor+"' where id = "+id;
        Statement statement ;
        int result;

        try {
            statement = con.createStatement();
            result =statement.executeUpdate(sql);
            if (result==1){
                System.out.println("Se actualizaron los datos de seguro con id :"+id);
            }else{
                System.out.println("No se encuentra el Seguro con id :"+id);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void crearProducto(Connection con,String tipo,String compania,String costo,String fecha_inicio,String fecha_fin){

        String sql ="insert into seguros(tipo,compania,costo,fecha_inicio,fecha_fin) values('"+tipo+"','"+compania+"','"+costo+"','"+fecha_inicio+"','"+fecha_fin+"')";
        Statement statement;

        int result;

        try {
            statement =con.createStatement();
            result = statement.executeUpdate(sql);
            if (result==1){
                System.out.println("Se agrego exitosamente el producto ");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void menuAdministrador(){
        int opc = 0;
        while (opc != 5){
            System.out.println("1.CREAR SEGURO\n" +
                    "2.VER SEGUROS\n" +
                    "3.ACTUALIZAR SEGUROS\n" +
                    "4.ELIMINAR SEGURO\n" +
                    "5.SALIR");
            opc =sc.nextInt();
            switch (opc){
                case 1:
                    System.out.println("CREAR SEGURO\n" +
                            "========================================");
                    System.out.println("Ingrese el tipo de seguro");
                    String tipo = sc.next();
                    System.out.println("Ingrese comapañia del seguro");
                    String compania = sc.next();
                    System.out.println("Ingrese costo del seguro");
                    String costo = sc.next();
                    System.out.println("Ingrese fecha de inicio del seguro");
                    String fecha_inicio = sc.next();
                    System.out.println("Ingrese fecha de fin del seguro");
                    String fecha_fin = sc.next();
                    crearProducto(cb,tipo,compania,costo,fecha_inicio,fecha_fin);
                    break;
                case 2:
                    Administrador.verProductos(cb);
                    break;
                case 3:
                    System.out.println("ACTUALIZACION DE PRODUCTO");
                    System.out.println("Ingrese id del producro a actualizar :");
                    int id = sc.nextInt();
                    System.out.println("Ingrese campo a modificar exactamente como se definen:\n" +
                            "tipo \n" +
                            "compañia\n" +
                            "fecha_inicio\n" +
                            "fecha_fin");
                    String campo=sc.next();
                    System.out.println("Ingrese valor a actualizar del campo "+campo);
                    String valor = sc.next();
                    actualizarProduct(cb,id,campo,valor);
                    break;
                case 4:
                    System.out.println("ELIMINAR SEGURO");
                    System.out.println("Ingrese id del seguro a eliminar");
                    int idElim = sc.nextInt();

                    eliminarProducto(cb,idElim);
                    break;
                case 5:
                    System.out.println("VUELVA PRONTO");
                    break;
                default:
                    System.out.println("OPCION INVALIDA");
                    break;
            }
        }

    }

    public static void login(Connection con, String emailAdm, String contrasenaAdm){
        String sql ="SELECT * FROM administrador";
        Statement statement;
        ResultSet rs;

        try{
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                if (rs.getString("email").equals(emailAdm)){
                    if (rs.getString("contrasena").equals(contrasenaAdm)) {
                        System.out.println("BIENVENIDO "+rs.getString("nombre"));
                        Administrador.menuAdministrador();
                        break;
                    }else {
                        System.out.println("Contraseña Incorrecta");
                    }
                }else {
                    System.out.println("No se Encontro el Correo");
                }

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
