/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so_proyecto1;

import java.util.ArrayList;

/**
 *
 * @author luism
 */
class BCP {//20 por BCP 

    private String nombre; // Nombre = nombre del archivo abierto
    private String estado;  // Estados; nuevo, preparado, ejecución, en espera, finalizado
    private int pc; // contador del programa puede ser string  Contador del programa (ubicación del programa cargado en memoria)
    private int ax = 0;  // Registros AC, AX, BX, CX, DX
    private int bx = 0;
    private int cx = 0;
    private String dx = "";
    private int ac = 0;
    private ArrayList<Integer> pila = new ArrayList<>();// Información de la pila: definir tamaño de 5, y tomar en cuenta error de desbordamiento
    private long tiempoinicial = 0;// Información contable; el cpu donde se está ejecutando, tiempo de inicio, tiempo empleado.
    private long tiempofinal = 0;
    private long tiempototal = 0;
    // Información del estado de E/S; lista de archivos abiertos
    private int siguiente; // Enlace al siguiente BPC
    private int inicio;// Dirección inicio (Base) 
    private int alcance;// Tamaño del proceso (Alcance)
    private int prioridad;// Prioridad ***/
    private int inicioMemoriaS = -1;
    private int tiempoArribo = 0;
    private int rafaga;
    private int fila = -1; //fila pero en los graficas 

    

    public BCP(String nombrebpc, String estadobcp, int sig, int alcancebcp, int prioridadbcp, int iniciobcp, int pcbcp, int ini) {
        nombre = nombrebpc;
        pc = pcbcp;
        estado = estadobcp;
        siguiente = sig;
        alcance = alcancebcp;
        rafaga = alcancebcp;
        prioridad = prioridadbcp;
        inicio = iniciobcp;
        inicioMemoriaS = ini;
        
        
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getInicioMemoriaS() {
        return inicioMemoriaS;
    }

    public void setInicioMemoriaS(int inicioMemoriaS) {
        this.inicioMemoriaS = inicioMemoriaS;
    }
    public int getTiempoArribo() {
        return tiempoArribo;
    }

    public void setTiempoArribo(int tiempoArribo) {
        this.tiempoArribo = tiempoArribo;
    }

    public int getRafaga() {
        return rafaga;
    }

    public void setRafaga(int rafaga) {
        this.rafaga = rafaga;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTiempoinicial(long tiempoinicial) {
        this.tiempoinicial = tiempoinicial;
    }

    public void setTiempofinal(long tiempofinal) {
        this.tiempofinal = tiempofinal;
    }

    public void setTiempototal(long tiempototal) {
        this.tiempototal = tiempototal;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public void setPila(ArrayList<Integer> pila) {
        this.pila = pila;
    }

    public void setAlcance(int alcance) {
        this.alcance = alcance;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public void setEstado(String esta) {
        estado = esta;
    }

    public void setSig(int sig) {
        siguiente = sig;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public void setAx(int ax) {
        this.ax = ax;
    }

    public void setBx(int bx) {
        this.bx = bx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public void setDx(String dx) {
        this.dx = dx;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    //   GETTERS 
    public String getNombre() {
        return nombre;
    }

    public long getTiempoinicial() {
        return tiempoinicial;
    }

    public long getTiempofinal() {
        return tiempofinal;
    }

    public long getTiempototal() {
        return tiempototal;
    }

    public int getInicio() {
        return inicio;
    }

    public ArrayList<Integer> getPila() {
        return pila;
    }

    public int getSizePila() {
        return pila.size();
    }

    public Integer popPila() {
        int pop = pila.get(pila.size() - 1);
        pila.remove(pila.size() - 1);
        return pop;
    }

    public int pushPila(Integer push) {
        if (pila.size() < 5) {
            pila.add(push);
            return 1;
        }
        return -1;
    }

    public int getAlcance() {
        return alcance;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getPc() {
        return pc;
    }

    public int getAx() {
        return ax;
    }

    public int getBx() {
        return bx;
    }

    public int getCx() {
        return cx;
    }

    public String getDx() {
        return dx;
    }

    public int getAc() {
        return ac;
    }

    public String getEstado() {
        return estado;
    }

    public int getSig() {
        return siguiente;
    }
}
