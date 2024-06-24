package model;

import Interfaz.IProceso;

public class Proceso implements IProceso {
    private String nombre;
    private int tiempoEjecucion;

    public Proceso(String nombre, int tiempoEjecucion) {
        this.nombre = nombre;
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }
}
