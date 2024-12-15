package Seguros;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class Ventas {
    static Scanner sc = new Scanner(System.in);
    static Connection cb = ConexionBD.conectarBD("capitalBank_bd");

    private int id;
    private int idCliente;
    private int idSeguro;
    private double monto;
    private String fechaCompra;


    public Ventas(int idCliente, int idSeguro, double monto, String fechaCompra) {
        this.idCliente = idCliente;
        this.idSeguro = idSeguro;
        this.monto = monto;
        this.fechaCompra = fechaCompra;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(int idSeguro) {
        this.idSeguro = idSeguro;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }


    public static void realizarVenta(int idCliente, int idSeguro, double monto, String fechaCompra) {
        String sql = "INSERT INTO ventas (id_cliente, id_seguro, monto, fecha_compra) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = cb.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idSeguro);
            ps.setDouble(3, monto);
            ps.setString(4, fechaCompra);

            int result = ps.executeUpdate();

            if (result == 1) {
                System.out.println("Venta realizada con exito.");
            } else {
                System.out.println("Error al realizar la venta.");
            }
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static void comprarProducto(Connection con, int idCliente) {
        String sql = "SELECT * FROM seguros";
        Statement statement;
        ResultSet rs;
        System.out.println("Ingrese el seguro que desea comprar:");

        String seguroComprado = sc.next();
        System.out.println("Ingrese el ID de cliente (asegurese de tener una cuenta registrada):");
        int idClienteInput = sc.nextInt();

        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sql);

            boolean seguroEncontrado = false;
            while (rs.next()) {
                if (rs.getString("tipo").equalsIgnoreCase(seguroComprado)) {
                    seguroEncontrado = true;
                    int idSeguro = rs.getInt("id");
                    double costo = rs.getDouble("costo");
                    String fechaFin = rs.getString("fecha_fin");


                    System.out.println("Has comprado el seguro de " + seguroComprado + " por un valor de " + costo +
                            " con fecha de vencimiento " + fechaFin);


                    String fechaCompra = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


                    realizarVenta(idClienteInput, idSeguro, costo, fechaCompra);

                    break;
                }
            }

            if (!seguroEncontrado) {
                System.out.println("No se encontro el seguro especificado.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static void verVentas(Connection con, int idCliente) {
        String sql = "SELECT * FROM ventas WHERE id_cliente = ?";
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idVenta = rs.getInt("id");
                int idSeguro = rs.getInt("id_seguro");
                double monto = rs.getDouble("monto");
                String fechaCompra = rs.getString("fecha_compra");

                System.out.println("Venta ID: " + idVenta + ", Seguro ID: " + idSeguro +
                        ", Monto: " + monto + ", Fecha de Compra: " + fechaCompra);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
