package Restaurante_app.Models;

public class Mesa {
	private int id;
    private String nombre;
    private String estado;
    
    public Mesa(int id, String nombre, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }
    
 // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return estado;
    }
    
 // Setter para estado (por si luego lo actualizas)
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        // Lo que se mostrará en el ComboBox
        return nombre + " (" + estado + ")";
    }
}
