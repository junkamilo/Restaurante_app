package Restaurante_app.Models;

import java.sql.Date;

public class Pedido {
	private int id_pedido;
    private int id_usuario;
    private int id_mesa;
    private Date fecha_hora;
    private Enum estado;
    private String observaciones;
    
    public Pedido(int id_pedido,int id_usuario, int id_mesa, Date fecha_hora, Enum estado, String observaciones) {
        this.id_pedido = id_pedido;
        this.id_usuario = id_usuario;
        this.id_mesa = id_mesa;
        this.fecha_hora = fecha_hora;
        this.estado = estado;
        this.observaciones = observaciones;
    }
    
    //Getters
    public int getId() {
    	return id_pedido;
    }
    public int getIdUser() {
    	return id_usuario;
    }
    public int getIdMesa() {
    	return id_mesa;
    }
    public Date getFecha() {
    	return fecha_hora;
    }
    public Enum getEstado() {
    	return estado;
    }
    public String getObservaciones() {
    	return observaciones;
    }
    
    //Setters
    public void setId(int id_pedido) {
        this.id_pedido = id_pedido;
    }
    public void setIdUser(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public void setIdMesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }
    public void setFecha(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
    public void setEstado(Enum estado) {
        this.estado = estado;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}

