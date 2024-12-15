package Seguros;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cliente extends Usuario {
    static Scanner sc = new Scanner(System.in);
    static Connection cb = ConexionBD.conectarBD("capitalBank_bd");

    public Cliente(String nombre, String contrasena, String email, String telefono) {
        super(nombre, contrasena, email, telefono);
    }

    public Cliente() {
    }

    public static void menuCliente(int idCliente) {
        int opc = 0;
        while (opc != 4) {
            System.out.println("1.VER PRODUCTOS\n" +
                    "2.COMPRAR PRODUCTO\n" +
                    "3.VER MIS COMPRAS\n" +
                    "4.SALIR");

            opc = sc.nextInt();
            switch (opc) {
                case 1:
                    verProductos(cb);
                    break;
                case 2:
                    comprarProducto(cb, idCliente);
                    break;
                case 3:
                    Ventas.verVentas(cb, idCliente);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("OPCION INVALIDA");
                    break;
            }
        }
    }

    public static void login(Connection con, String emailCli, String contrasenaCli) {
        String sql = "SELECT * FROM cliente";
        Statement statement;
        ResultSet rs;

        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("email").equals(emailCli)) {
                    if (rs.getString("contrasena").equals(contrasenaCli)) {
                        System.out.println("BIENVENIDO");
                        int idCliente = rs.getInt("id");
                        menuCliente(idCliente);
                        break;
                    } else {
                        System.out.println("Contraseña Incorrecta");
                    }
                } else {
                    System.out.println("No se encontro el Correo");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void registro(Connection con, String nombre, String contrasena, String email, String telefono) {
        Cliente cliente = new Cliente(nombre, contrasena, email, telefono);
        String sql = "insert into cliente(nombre,contrasena,email,telefono) values('" + nombre + "','" + contrasena + "','" + email + "','" + telefono + "')";
        Statement statement;
        int result;

        try {
            statement = con.createStatement();
            result = statement.executeUpdate(sql);
            if (result == 1) {
                System.out.println("Los datos fueron ingresados correctamente");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void comprarProducto(Connection con, int idCliente) {
        String sql = "SELECT * FROM seguros";
        Statement statement;
        ResultSet rs;
        System.out.println("Ingrese el seguro que desea comprar:");

        String seguroComprado = sc.next();


        if (!clienteRegistrado(idCliente)) {
            System.out.println("El cliente no esta registrado. Por favor, regístrese primero.");
            return;
        }
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sql);

            boolean seguroEncontrado = false;
            while (rs.next()) {
                if (rs.getString("tipo").equalsIgnoreCase(seguroComprado)) {
                    seguroEncontrado = true;
                    int idSeguro = rs.getInt("id");
                    double costo = rs.getDouble("costo");
                    String fechaInicio = rs.getString("fecha_inicio");
                    String fechaFin = rs.getString("fecha_fin");


                    System.out.println("Has comprado el seguro de " + seguroComprado + " por un valor de " + costo +
                            " con fecha de inicio: " + fechaInicio + " y fecha de vencimiento: " + fechaFin);

                    String fechaCompra = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    Ventas.realizarVenta(idCliente, idSeguro, costo, fechaCompra);

                    break;
                }
            }
            if (!seguroEncontrado) {
                System.out.println("No se encontro el seguro especificado.");
                System.out.println("Por favor, intente con otro seguro.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void verProductos(Connection con) {
        String sql = "SELECT * FROM seguros";
        Statement statement;
        ResultSet rs;
        int id;
        String tipo;
        String compania;
        double costo;
        String fecha_inicio;
        String fecha_fin;

        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                id = rs.getInt("id");
                tipo = rs.getString("tipo");
                compania = rs.getString("compania");
                costo = rs.getDouble("costo");
                fecha_inicio = rs.getString("fecha_inicio");
                fecha_fin = rs.getString("fecha_fin");


                System.out.println("Seguro de Tipo: " + tipo + " | Costo: " + costo +
                        " | Fecha Inicio: " + fecha_inicio + " | Fecha Fin: " + fecha_fin);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static boolean clienteRegistrado(int idCliente) {
        return true;
    }
}

