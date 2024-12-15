package Seguros;

public class Seguro {
    private String tipo;
    private String compania;
    private int costo;
    private String fecha_inicio;
    private String fecha_fin;

    public Seguro(String tipo, String compania, int costo, String fecha_inicio, String fecha_fin) {
        this.tipo = tipo;
        this.compania = compania;
        this.costo = costo;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}
