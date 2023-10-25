package so_proyecto1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import sun.security.util.Length;
import so_proyecto1.window_principal;

/**
 *
 * @author luism
 */
public class SO_Proyecto1 {

    static boolean banderaEqual = false;
    static boolean bandera09h = false;
    static boolean startTime = false;
    static boolean autoTiempoArribo = false;

    static int ax = 0;
    static String al = "";
    static int bx = 0;
    static int cx = 0;
    static String dx = "";
    static int ac = 0;
    static int ah = 0;
    static int pc = 0;
    static int ir = 0;
    static int mpSize = 256;
    static int msSize = 512;

    static BCP bcpActual;
    static ArrayList<String> listLines = new ArrayList<>();

    static ArrayList<String> memoria_P = new ArrayList<>();
    static ArrayList<String> memoria_S = new ArrayList<>();

    static ArrayList<String[]> programas = new ArrayList<>();
    static ArrayList<BCP> colaNuevo = new ArrayList<>();
    static ArrayList<BCP> colaPreparado = new ArrayList<>(); //
    static ArrayList<BCP> colaEnEspera = new ArrayList<>();
    static ArrayList<BCP> colaEjecucion = new ArrayList<>();
    static ArrayList<BCP> colaFinalizado = new ArrayList<>();

    static ArrayList<ArrayList> colasCPU = new ArrayList<>();

    static window_principal ventanaPrincipal = new window_principal();

    //memorias info 
    static int memoria_Sec_Limite_Inicial = 20; // los primeros 10 son para el indice y los otros 100 son para guardar 5 bcp, el resto es para los programas. 
    static int memoria_P_Limite_Inicial = 110; // los primeros 10 son para el indice y los otros 100 son para guardar 5 bcp, el resto es para los programas. 
    static int memoria_S_Restante = msSize - memoria_Sec_Limite_Inicial; // los primeros 10 son para el indice y los otros 100 son para guardar 5 bcp, el resto es para los programas. 
    static int memoria_P_Restante = mpSize - memoria_P_Limite_Inicial; // los primeros 10 son para el indice y los otros 100 son para guardar 5 bcp, el resto es para los programas. 

    static int quantum;
    static int cantCPU = 1;
    static int tiemposActual = 0; // tiempo total en "segundos"
    static int tiempoRestante; // tiempo que falta para cambiar de proceso
    static int tiempoTotal = 0; //tiempo maximo para mostrar en los cpus
    static String algoritmo = "FSCS";

    public static void main(String[] args) {
        iniciar();

    }

    public static void cambiarAlgoritmo(String alg) {
        algoritmo = alg;
    }

    public static void iniciar() {
        modificarMemoriaPrincipal(mpSize);
        modificarMemoriaSecundaria(msSize);
        ventanaPrincipal.setVisible(true);
    }

    public static void modificarMemoriaPrincipal(int size) {
        memoria_P.clear();
        mpSize = size;
        memoria_P_Restante = msSize - memoria_P_Limite_Inicial;
        for (int i = 0; i < size; i++) {
            memoria_P.add(null); // Agrega elementos nulos al ArrayList
        }
    }

    public static void modificarMemoriaSecundaria(int size) {
        memoria_S.clear();
        msSize = size;
        memoria_S_Restante = msSize - memoria_Sec_Limite_Inicial;
        for (int i = 0; i < size; i++) {
            memoria_S.add(null); // Agrega elementos nulos al ArrayList
        }
    }

    public static void ejecutar(int modo) {
        int res = -1;
        if (!startTime) {
            startTime = true;
            bcpActual.setTiempoinicial(System.nanoTime());
        }
        if (modo == 1) { //modo paso ventanaPrincipal paso 
            res = ejecutarPasos(1);
        } else { //modo automatico
            res = ejecutarPasos(bcpActual.getInicio() + bcpActual.getAlcance() - bcpActual.getPc());
        }
        if (bcpActual.getPc() == bcpActual.getInicio() + bcpActual.getAlcance() || res == 7) {// si llega al final del programa o encontro un int 20H
            postProceso();
        }
    }
    static int pasosTotales;

    public static int pasos() {
        int res = -1;
        int cont = 0;
        BCP act;
        ArrayList<BCP> CpuN;
        for (int i = 0; i < colasCPU.size(); i++) {
            CpuN = colasCPU.get(i);
            for (int j = 0; i < CpuN.size(); j++) {
                //BCP_actual = colasCPU.get(i).get(j);
            }
        }
        return res;
    }

    public static int ejecutarPasos(int pasos) {
        int res = -1;
        for (int i = 0; i < colasCPU.size(); i++) {

        }
        return res;
    }

    public static int ejecutarPasos2(int pasos) {
        int res = -1;
        for (int i = 0; i < pasos; i++) {
            if (bandera09h == false) {
                String linea = memoria_P.get(bcpActual.getPc());
                System.out.println(linea);
                res = validarOperaciones(linea.split(" "));
                if (res == 4) { //Interrupcion del teclado 
                    pasos = 0;
                    res = 4;
                } else if (res == 7 || res == -1) {//in 20H terminar progrma o el programa tiro error
                    pasos = 0;
                    res = 7;
                }
                pc = pc + 1;
                //ver que tipo de algoritmo se usa para saber que es lo que toca ejecutar
                //   bcpActual.setPc(bcpActual.getPc() + 1);
            }
        }
        return res;
    }

    public static int validarAlgoritmoSigInstruccion() {
        int res = -1;
        switch (algoritmo) {
            case "FCFS": //POR EL QUE LLEGA PRIMERO ejecutar la inst. sig de cada cpu (actuales)

                break;
            case "SJF": //EL MAS CORTO PRIMERO

                break;
            case "SRT": //EL MAS CORTO PRIMERO CON INTERRUPCIONES A LOS GRANDES

                break;
            case "RR": // CON QUANTUS 

                break;
            case "HRRN": // CON EL TIEMPO RELATIVO 

                break;
            default:
                String msj = "Error en la  Linea:" + bcpActual.getPc();
                System.out.println(msj);// sino es invalido el comando.
                cargarGraficosPantalla(msj);

                return res;
        }
        return res;
    }

    /*
    Seccion para ordenar un CPU segun el modo selecionado   */
    public static int ordenarCPU(ArrayList<BCP> cpuActual) {
        int res = -1;
        switch (algoritmo) {
            case "FCFS": //POR EL QUE LLEGA PRIMERO ejecutar la inst. sig de cada cpu (actuales)
                ordenarFCFS(cpuActual);
                break;
            case "SJF": //EL MAS CORTO PRIMERO
                ordenarSJF(cpuActual);
                break;
            case "SRT": //EL MAS CORTO PRIMERO CON INTERRUPCIONES A LOS GRANDES
                ordenarSRT(cpuActual);
                break;
            case "RR": // CON QUANTUS 
                ordenarRR(cpuActual);
                break;
            case "HRRN": // CON EL TIEMPO RELATIVO 
                ordenarHRRN(cpuActual);
                break;
            default:
                String msj = "Error en la  Linea:" + bcpActual.getPc();
                System.out.println(msj);// sino es invalido el comando.
                cargarGraficosPantalla(msj);

                return res;
        }
        return res;
    }

    public static int ordenarFCFS(ArrayList<BCP> cpuActual) {
        Collections.sort(cpuActual, Comparator.comparing(BCP::getTiempoArribo));
        return 1;
    }

    public static int ordenarSJF(ArrayList<BCP> cpuActual) {
        Collections.sort(cpuActual, Comparator.comparing(BCP::getRafaga));
        return 1;
    }

    public static int ordenarSRT(ArrayList<BCP> cpuActual) {
        Collections.sort(cpuActual, Comparator.comparing(BCP::getRafaga));
        return 1;
    }

    public static int ordenarRR(ArrayList<BCP> cpuActual) {

        return 1;
    }

    public static int ordenarHRRN(ArrayList<BCP> cpuActual) {

        return 1;
    }

    /**
     * Seccion de ejecutar las instrucciones
     *
     * @param parts
     * @return
     */
    public static int validarOperaciones(String[] parts) {
        int res = -1;
        switch (parts[0]) {
            case "LOAD":
                res = validarRegistroLoad(parts);
                break;
            case "STORE"://solo esta no puede ser un entero
                res = validarRegistroStore(parts);
                break;
            case "MOV": //validar MOV 
                res = validarRegistroMov(parts);
                break;
            case "SUB":
                res = validarRegistroSub(parts);
                break;
            case "ADD":
                res = validarRegistroAdd(parts);
                break;
            case "INC":

                res = validarRegistroInc(parts);

                break;
            case "DEC":

                res = validarRegistroDec(parts);

                break;
            case "SWAP":
                res = validarRegistroSwap(parts);
                break;
            case "INT":
                res = validarRegistroInt(parts);
                break;
            case "JMP":

                res = validarRegistroJmp(parts[1]); //valida el salto
                break;
            case "CMP":
                banderaEqual = getValorRegistros(parts[1]) == getValorRegistros(parts[2]);
                res = 1;
                break;
            case "JE":
                res = validarRegistroJe(parts[1]);

                break;
            case "JNE":
                res = validarRegistroJne(parts[1]);

                break;
            case "PARAM":
                if (parts.length < 5) {
                    //no se valida que sean datos validos
                    res = validarRegistroParam(parts);
                } else {

                }
                break;
            case "PUSH":
                res = validarRegistroPush(parts);
                break;
            case "POP":
                res = validarRegistroPop(parts);
                break;

            default:
                String msj = "Error en la linea " + Arrays.toString(parts) + "  Linea:" + bcpActual.getPc();
                System.out.println(msj);// sino es invalido el comando.
                cargarGraficosPantalla(msj);

                return res;
        }
        cargarGraficosBCP();
        return res;

    }

    public static int validarRegistroParam(String[] parts) {
        int res = -1;
        for (int i = 1; i < parts.length; i++) {
            int num = esEsntero(parts[i]);
            if (num > -999999999) {
                if (bcpActual.pushPila(num) == -1) {
                    System.out.println("Pila desbordada El programa tiene un error la entrada de parametros en la linea: " + bcpActual.getPc());
                    cargarGraficosPantalla("Pila desbordada El programa tiene un error la entrada de parametros en la linea: " + bcpActual.getPc());
                    return -1;
                }
            } else {
                System.out.println("No es entero: El programa tiene un error la entrada de parametros en la linea: " + bcpActual.getPc());
                cargarGraficosPantalla("No es entero: El programa tiene un error la entrada de parametros en la linea: " + bcpActual.getPc());
            }
        }
        return res;
    }

    // valida el comando load si el registro para cargar en ac existe, lo realiza y
    // devuelve 1 sino devuelve -1 cuando la lista no tiene nada y cuando no es ningun registro ax..
    public static int validarRegistroLoad(String[] parts) {

        if (null == parts[1]) {
            // sino es invalido el comando.
            return -1;
        } else {
            switch (parts[1]) {
                case "AX":
                    bcpActual.setAc(ax);
                    ac = ax;
                    break;
                case "BX":
                    bcpActual.setAc(bx);
                    ac = bx;
                    break;
                case "CX":
                    bcpActual.setAc(cx);
                    ac = cx;
                    break;
                case "DX":
                    bcpActual.setAc(Integer.valueOf(dx));
                    ac = Integer.valueOf(dx);
                    break;
                case "AC":
                    bcpActual.setAc(ac);
                    //ac = ac;
                    break;
                case "AH":
                    bcpActual.setAc(ah);//*****revsia
                    ac = ah;
                    break;
                default:
                    String msj = "Error en la linea " + parts[0] + " " + parts[1] + "  Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return -1;
            }
        }
        return 1;
    }

    public static int validarRegistroPush(String[] parts) {
        int res;
        if (null == parts[1]) {
            // sino es invalido el comando.
            return -1;
        } else {
            switch (parts[1]) {
                case "AX":
                    res = bcpActual.pushPila(ax);
                    break;
                case "BX":
                    res = bcpActual.pushPila(bx);
                    break;
                case "CX":
                    res = bcpActual.pushPila(cx);
                    break;
                case "DX":
                    res = bcpActual.pushPila(Integer.valueOf(dx));
                    break;
                case "AC":
                    res = bcpActual.pushPila(ac);
                    break;
                case "AH":
                    res = bcpActual.pushPila(ah);
                    break;
                default:
                    String msj = "Error en la linea " + parts[0] + " " + parts[1] + "  Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return -1;
            }
            if (res == -1) {
                System.out.println("Pila LLeva: No se hizo el push. En la linea: " + bcpActual.getPc());
                cargarGraficosPantalla("Pila LLeva: No se hizo el push. En la linea: " + bcpActual.getPc());
                return -1;
            }
        }
        return 1;
    }

    public static int validarRegistroPop(String[] parts) {
        int res = -1;
        if (null == parts[1]) {
            // sino es invalido el comando.
            return res;
        } else if (bcpActual.getSizePila() > 0) {
            switch (parts[1]) {
                case "AX":
                    bcpActual.setAx(bcpActual.popPila());
                    ax = bcpActual.getAx();
                    break;
                case "BX":
                    bcpActual.setBx(bcpActual.popPila());
                    bx = bcpActual.getBx();
                    break;
                case "CX":
                    bcpActual.setCx(bcpActual.popPila());
                    cx = bcpActual.getCx();
                    break;
                case "DX":
                    bcpActual.setDx(String.valueOf(bcpActual.popPila()));
                    dx = bcpActual.getDx();
                    break;
                case "AC":
                    bcpActual.setAc(bcpActual.popPila());
                    ac = bcpActual.getAc();
                    break;
                case "AH":

                    ah = bcpActual.popPila();
                    break;
                default:
                    String msj = "Error en la linea " + parts[0] + " " + parts[1] + "  Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return res;
            }
            res = 1;
        }
        return res;
    }

    public static int validarRegistroStore(String[] parts) {
        String res = "";
        if (null == parts[1]) {
            // sino es invalido el comando.
            return -1;
        } else {
            switch (parts[1]) {
                case "AX":
                    bcpActual.setAx(ac);
                    ax = ac;
                case "BX":
                    bcpActual.setBx(ac);
                    bx = ac;
                    break;
                case "CX":
                    bcpActual.setCx(ac);
                    cx = ac;
                    break;
                case "DX":
                    bcpActual.setDx(String.valueOf(ac));
                    dx = String.valueOf(ac);
                    break;
                case "AC":
                    bcpActual.setAc(ac);
                    ac = ac;
                    break;
                case "AH":

                    ah = ac;
                    break;
                default:
                    String msj = "Error en la linea " + parts[0] + " " + parts[1] + " " + parts[1] + "  Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return -1;
            }
        }
        return 1;
    }

    public static int validarRegistroMov(String[] parts) {
        String res = "";
        int num;
        if (null == parts[1] || parts.length < 3 || parts.length > 3) {
            // sino es invalido el comando.
            return -1;
        } else {
            switch (parts[1]) {
                case "AX":
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {
                        bcpActual.setAx(num);
                        ax = num;
                    }
                    break;
                case "BX":
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {
                        bcpActual.setBx(num);
                        bx = num;
                    }
                    break;
                case "CX":
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {
                        bcpActual.setCx(num);
                        cx = num;
                    }
                    break;
                case "DX":
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {
                        bcpActual.setDx(String.valueOf(num));
                        dx = String.valueOf(num);
                    }
                    break;
                case "AC":
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {
                        bcpActual.setAc(num);
                        ac = num;
                    }
                    break;
                case "AL":
                    al = parts[2];
                    break;
                case "AH":
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {

                        ah = num;
                    }

                    break;
                default:
                    String msj = "Error en la linea " + parts[0] + " " + parts[1] + " " + parts[2] + "  Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return -1;
            }
        }
        return 1;
    }

    public static int validarRegistroSub(String[] parts) {
        String res = "";
        if (null == parts[1]) {
            // sino es invalido el comando.
            return -1;
        } else {
            switch (parts[1]) {
                case "AX":
                    bcpActual.setAc(ac - ax);
                    ac -= ax;
                    break;
                case "BX":
                    bcpActual.setAc(ac - bx);
                    ac -= bx;
                    break;
                case "CX":
                    bcpActual.setAc(ac - cx);
                    ac -= cx;
                    break;
                case "DX":
                    bcpActual.setAc(ac - Integer.valueOf(dx));
                    ac -= Integer.valueOf(dx);
                    break;
                case "AC":
                    bcpActual.setAc(ac - ac);
                    ac -= ac;
                    break;
                case "AH":
                    bcpActual.setAc(ac - ah);//*****revsia
                    ac -= ah;
                    break;
                default:
                    String msj = "Error en la linea " + parts[0] + " " + parts[1] + "  Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return -1;
            }
        }
        return 1;
    }

    public static int validarRegistroSwap(String[] parts) {
        int res = -1;
        int aux;
        int num;
        if (null == parts[1]) {
            // sino es invalido el comando.
            return -1;
        } else {
            switch (parts[1]) {
                case "AX":
                    aux = ax;
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {
                        bcpActual.setAx(num);
                        ax = num;
                        res = validarRegistroSwap2(parts[2], aux);
                    }
                    break;
                case "BX":
                    aux = bx;
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {
                        bcpActual.setBx(num);
                        bx = num;
                        res = validarRegistroSwap2(parts[2], aux);
                    }
                    break;
                case "CX":
                    aux = cx;
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {
                        bcpActual.setCx(num);
                        cx = num;
                        res = validarRegistroSwap2(parts[2], aux);
                    }
                    break;
                case "DX":
                    aux = Integer.valueOf(dx);
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {
                        bcpActual.setDx(String.valueOf(num));
                        dx = String.valueOf(num);
                        res = validarRegistroSwap2(parts[2], aux);
                    }
                    break;
                case "AC":
                    aux = ac;
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {
                        bcpActual.setAc(num);
                        ac = num;
                        res = validarRegistroSwap2(parts[2], aux);
                    }
                    break;
                case "AH":
                    aux = ah;
                    num = getValorRegistros(parts[2]);
                    if (num > -999999999) {

                        ah = num;
                        res = validarRegistroSwap2(parts[2], aux);
                    }
                    break;
                default:
                    String msj = "Error en la linea " + parts[0] + " " + parts[1] + " " + parts[1] + "  Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return -1;
            }
        }
        return 1;
    }

    public static int validarRegistroSwap2(String parts, int num) {
        String res = "";
        if (null == parts) {
            // sino es invalido el comando.
            return -1;
        } else {
            switch (parts) {
                case "AX":
                    bcpActual.setAx(num);
                    ax = num;
                    break;
                case "BX":
                    bcpActual.setBx(num);
                    bx = num;
                    break;
                case "CX":
                    bcpActual.setCx(num);
                    cx = num;
                    break;
                case "DX":
                    bcpActual.setDx(String.valueOf(num));
                    dx = String.valueOf(num);
                    break;
                case "AC":
                    bcpActual.setAc(num);
                    ac = num;
                    break;
                case "AH":
                    //*****revsia
                    ah = num;
                    break;
                default:
                    String msj = "Error en la Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return -1;
            }
        }
        return 1;
    }

    public static int validarRegistroAdd(String[] parts) {
        String res = "";
        if (null == parts[1]) {
            // sino es invalido el comando.
            return -1;
        } else {
            switch (parts[1]) {
                case "AX":
                    bcpActual.setAc(ac + ax);
                    ac += ax;
                    break;
                case "BX":
                    bcpActual.setAc(ac + bx);
                    ac += bx;
                    break;
                case "CX":
                    bcpActual.setAc(ac + cx);
                    ac += cx;
                    break;
                case "DX":
                    bcpActual.setAc(ac + Integer.valueOf(dx));
                    ac += Integer.valueOf(dx);
                    break;
                case "AC":
                    bcpActual.setAc(ac + ac);
                    ac += ac;
                    break;
                case "AH":
                    bcpActual.setAc(ac + ah);//*****revsia
                    ac += ah;
                    break;
                default:
                    String msj = "Error en la linea " + parts[0] + " " + parts[1] + "  Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return -1;
            }
        }
        return 1;
    }

    public static int validarRegistroInc(String[] parts) {
        int res = -1;
        if (parts.length == 1) {
            bcpActual.setAc(ac + 1);
            ac++;

            res = 2;
        } else if (null == parts[1]) {
            // sino es invalido el comando.
            return -1;
        } else {
            switch (parts[1]) {
                case "AX":
                    System.out.println("inc ax");
                    bcpActual.setAx(ax + 1);
                    ax++;
                    break;
                case "BX":
                    bcpActual.setBx(bx + 1);
                    bx++;
                    break;
                case "CX":
                    bcpActual.setCx(cx + 1);
                    cx++;
                    break;
                case "DX":
                    bcpActual.setDx(String.valueOf(Integer.valueOf(dx) + 1));
                    dx = String.valueOf(Integer.valueOf(dx) + 1);
                    break;
                case "AC":
                    bcpActual.setAc(ac + 1);
                    ac++;
                    break;
                case "AH":
                    //bcpActual.setAl(al++);//*****revsia
                    ah++;
                    break;
                default:
                    String msj = "Error en la linea " + parts[0] + " " + parts[1] + "  Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return -1;
            }
        }
        cargarGraficosBCP();
        return 1;
    }

    public static int validarRegistroDec(String[] parts) {
        int res = -1;
        if (parts.length == 1) {
            bcpActual.setAc(ac - 1);
            ac--;
            res = 2;
        } else if (null == parts[1]) {
            // sino es invalido el comando.
            return -1;
        } else {
            switch (parts[1]) {
                case "AX":
                    bcpActual.setAx(ax - 1);
                    ax--;
                    break;
                case "BX":
                    bcpActual.setBx(bx - 1);
                    bx--;
                    break;
                case "CX":
                    bcpActual.setCx(cx - 1);
                    cx--;
                    break;
                case "DX":
                    bcpActual.setDx(String.valueOf(Integer.valueOf(dx) - 1));
                    dx = String.valueOf(Integer.valueOf(dx) - 1);
                    break;
                case "AC":
                    bcpActual.setAc(ac - 1);
                    ac--;
                    break;
                case "AH":
                    //bcpActual.setAl(al++);//*****revsia
                    ah--;
                    break;
                default:
                    String msj = "Error en la linea " + parts[0] + " " + parts[1] + "  Linea:" + bcpActual.getPc();
                    System.out.println(msj);// sino es invalido el comando.
                    cargarGraficosPantalla(msj);
                    return -1;
            }
        }
        cargarGraficosBCP();
        return 1;
    }

    public static void guardarDx(String linea) {
        int res = validarEntero(linea);
        if (res > -1) {
            dx = linea;
            bcpActual.setDx(linea);
            cargarGraficosPantalla("Presione Ejecutar o Paso a Paso");

        }

    }

    public static int validarRegistroJe(String stringNum) {
        int res = -1;
        if (banderaEqual) {
            res = validarRegistroJmp(stringNum);
        } else {
            res = 2;
        }
        return res;
    }

    public static int validarRegistroJne(String stringNum) {
        int res = -1;
        if (!banderaEqual) {
            res = validarRegistroJmp(stringNum);
        } else {
            res = 2;
        }
        return res;
    }

    public static int validarRegistroJmp(String stringNum) {
        int res = esEsntero(stringNum);
        String msj = "";
        if (res > -256 && res < 256) {//numero valido 
            if (res > 0) {//mayor que cero que no pase el final del programa 
                if ((res + bcpActual.getPc() < bcpActual.getInicio() + bcpActual.getAlcance())) {//verificar que no se pase del final 
                    System.out.println("mas pasos " + res);
                    bcpActual.setPc(bcpActual.getPc() + res - 1);
                    pc = bcpActual.getPc();
                    return 1;
                } else {
                    msj = "Error de desbordamiento Superior, Linea: " + (bcpActual.getPc() - bcpActual.getInicio());
                    System.out.println(msj);
                    cargarGraficosPantalla(msj);
                    return -1;
                }
            } else {
                if (bcpActual.getPc() + res >= bcpActual.getInicio()) {//es menor que 0 y si la posicion es igual o mayor de la inicio puede 
                    System.out.println("menos pasos " + res);
                    bcpActual.setPc(bcpActual.getPc() + res - 1);
                    pc = bcpActual.getPc();
                    return 1;
                } else {
                    msj = "Error de desbordamiento Inferior, Linea: " + (bcpActual.getPc() - bcpActual.getInicio());
                    System.out.println(msj);
                    cargarGraficosPantalla(msj);
                    return -1;
                }
            }

        } else {
            msj = "Error numero invalido" + (bcpActual.getPc() - bcpActual.getInicio());
            System.out.println(msj);
            cargarGraficosPantalla(msj);
            return -1;
        }

    }

    public static int validarRegistroInt(String[] parts) {
        int res = -1;
        System.out.println(parts[1] + " leng" + parts[1].length());
        if (null != parts[1]) {
            switch (parts[1]) {
                case "20H":
                    //termina el programa

                    res = 7;//
                    break;
                case "10H":
                    //imprime en pantalla el dx
                    cargarGraficosPantalla("Valor de DX: " + dx);
                    res = 1;
                    break;
                case "09H":
                    //entrada teclado

                    cargarGraficosPantalla("Digite el número deseado y precione la tecla Enter.(0-255)");
                    //bandera09h = true;
                    res = 4;
                    break;
                case "21H":
                    res = validarRegistro21h(parts[1]);
                    break;
                default:
                    break;
            }
        }
        return res;
    }

    //AH <= 3ch crear archivo
    //AH <= 3dh abre archivo
    //AH <= 4dh lee archivo
    //AH <= 40h escribir archivo
    //AH <= 41h eliminar archivo //
    public static int validarRegistro21h(String stringNum) {

        return 1;
    }

    public static int getValorRegistros(String stringRegistro) {
        String res = "";
        if (null == stringRegistro) {
            // sino es invalido el comando.
            return -999999999;
        } else {
            switch (stringRegistro) {
                case "AX":
                    return ax;
                case "BX":
                    return bx;
                case "CX":
                    return cx;
                case "DX":
                    return Integer.valueOf(dx);
                case "AC":
                    return ac;
                case "AH":
                    return ah;
                default:
                    int num = validarEntero(stringRegistro);
                    if (num >= -255 && num <= 255) {
                        return num;
                    }

                    return -999999999;
            }
        }

    }

    public static int validarEntero(String numString) {
        int num = Integer.valueOf(numString);
        if (num <= 255 && num >= -255) {
            return num;
        } else {
            return -999;
        }
    }

    public static int esEsntero(String numString) {
        try {
            int numero = Integer.parseInt(numString);
            //System.out.println(number); // output = 25
            return numero;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return -99999;
        }
    }

    // cargar los archivos en la memoria secundaria con el indice de cada programa. listos para ejecutarse en 
    public static int memoria_Sec_Indice_ZonaR = 0;

    public static void guardarProgramasEnMemoriaSecundaria(ArrayList<String> listaLineas, boolean fin) {
        int largo = listaLineas.size();
        System.out.println(listaLineas.get(0));
        int resultado = guardarEnMemoria(largo, listaLineas, memoria_S, memoria_Sec_Limite_Inicial, msSize, memoria_S_Restante);
        // si el largo del programa mas la posicion actual del ultimo programa es menor que el tamaño de la memoria y menos de 5 bcp en ready 
        if (resultado > 0) { //si lo puede guardar da > 0
            System.out.println(" Se guardo en memoria secundaria res: " + resultado);

            ArrayList<String> lineaArray = new ArrayList();
            String line = listaLineas.get(0) + "---" + String.valueOf(resultado) + "---" + String.valueOf(resultado + largo - 1);
            lineaArray.add(line);

            int resultadoIndice = guardarIndiceMemoria(line, memoria_S, memoria_Sec_Indice_ZonaR);
            if (resultadoIndice > 0) {
                System.out.println("Guardo indice memoria Secundaria");
                memoria_Sec_Indice_ZonaR += 1;
            } else {
                System.out.println("No guardo indice memoria Secundaria");
            }
            memoria_S_Restante -= largo;
        } else {//BCP(String nombrebpc, String estadobcp, BCP sig, int alcancebcp, int prioridadbcp, int iniciobcp, int pcbcp)
            System.out.println(" No pudo guardarse en memoria secundaria");
        }
        if (fin) { //si es el ultimo BCP Carga todo en la pantalla 
            //cargar pantalla 
            System.out.println("Preproceso: Se cargaron programas a la memoria secundaria " + memoria_Sec_Indice_ZonaR);
            preProceso();
        }
    }

    public static void preProceso() { // crea bcp

        cargarGraficosMemoriaSecundaria();
        preBCP();
        verCPUs();

        cargarGraficosMemoriaPrincipal();
        System.out.println("Se cargaron programas a la memoria Principal Y Crearon BCP's en estado preparado");

        cargarColumnasCPU();
        cargarFilasCPU();
        //bcpActual = colaPreparado.get(0); // agarra el primero
        guardarProgramas();// *** cambiar por que no guarda los que estan en los CPUS 

        //cargarGraficosBCP();
        cargarGraficosProgramas();

    }

    public static void cargarColumnasCPU() {
        ventanaPrincipal.agregarColumna("Archivo");
        ventanaPrincipal.agregarColumna("CPU");
        for (int i = 0; i < tiempoTotal; i++) {
            ventanaPrincipal.agregarColumna(String.valueOf(i));
        }
    }

    public static void cargarFilasCPU() {
        ArrayList<BCP> colaCpuAct = new ArrayList<>();
        BCP bcpAct;
        int fila = 1;
        for (int i = 0; i < cantCPU; i++) {
            colaCpuAct = colasCPU.get(i);
            for (int j = 0; j < colaCpuAct.size(); j++) {
                bcpAct = colaCpuAct.get(j);
                bcpAct.setFila(fila);
                fila++;
                ventanaPrincipal.agregarfila(bcpAct.getNombre(), String.valueOf(i + 1));
            }
        }
        for (int i = 0; i < colaEnEspera.size(); i++) {
            bcpAct = colaEnEspera.get(i);
            bcpAct.setFila(fila);
            fila++;
            ventanaPrincipal.agregarfila(bcpAct.getNombre(), "N");
        }
    }

    public static void agregarCPUs(int cantCpus) {
        cantCPU = cantCpus;
        ArrayList<BCP> nuevoCpu; //lista de listas cada lista es un cpu que puede tener 5 bcp cada una 
        for (int i = 0; i < cantCPU; i++) {
            nuevoCpu = new ArrayList<>();
            colasCPU.add(nuevoCpu);
        }
    }

    public static void verCPUs() {
        ArrayList<BCP> colaCpuAct = new ArrayList<>();
        for (int i = 0; i < cantCPU; i++) {
            ordenarCPU(colasCPU.get(i));
            colaCpuAct = colasCPU.get(i);
            for (int j = 0; j < colaCpuAct.size(); j++) {
                BCP bcpact = colaCpuAct.get(j);
                System.out.println("CPU " + i + " guardo " + bcpact.getNombre() + " tiempor Arribo " + bcpact.getTiempoArribo() + " Rafaga " + bcpact.getRafaga());
            }
        }
    }

    public static int postProceso() {
        bcpActual.setTiempofinal(System.nanoTime());
        bcpActual.setTiempototal(bcpActual.getTiempofinal() - bcpActual.getTiempoinicial());

        /*
        AQUI HAY QUE VER COMO IR METIENDO A CADA cpu */
        int res = finalizarBcpActual(); // quita el bcp actual de la memoria y lo guarda en bcp finalizados tambien trae un bcp en preparado si peued sino tira -1
        if (res == -1) {// si es -1 es pq no hay nada en cola preparado. 
            System.out.println("No hay mas programas para procesar");
            cargarGraficosPantalla("No hay mas programas para procesar");
        } else {
            cargarGraficosBCP();

            cargarGraficosPantalla("Nuevo programa pulse Ejecutar o Paso a Paso");

        }
        cargarGraficosMemoriaPrincipal();
        guardarProgramas();
        cargarGraficosProgramas();

        return -1;
    }

    /*static ArrayList<BCP> colaNuevo = new ArrayList<>();
    static ArrayList<BCP> colaPreparado = new ArrayList<>(); //
    static ArrayList<BCP> colaEnEspera = new ArrayList<>();
    static ArrayList<BCP> colaEjecucion = new ArrayList<>();
    static ArrayList<BCP> colaFinalizado = new ArrayList<>();*/
    public static void cargarGraficosEstadisticas() {
        BCP actual;
        for (int i = 0; i < colaFinalizado.size(); i++) {
            actual = colaFinalizado.get(i);
            cargarGraficosPantalla("***");
            cargarGraficosPantalla("Archivo: " + actual.getNombre());
            cargarGraficosPantalla("Tiempo Inicial: " + actual.getTiempoinicial() / 1000000 + " milisegundos."); // Convertir a milisegundos);
            cargarGraficosPantalla("Tiempo Final: " + actual.getTiempofinal() / 1000000 + " milisegundos."); // Convertir a milisegundos);
            cargarGraficosPantalla("Tiempo Total: " + actual.getTiempototal() / 1000000 + " milisegundos."); // Convertir a milisegundos);
        }
    }

    public static int finalizarBcpActual() {
        BCP fin;
        int res = -1;
        if (colaPreparado.size() > 0) {
            fin = colaPreparado.get(0);
            fin.setEstado("Finalizado");
            colaPreparado.remove(0);
            colaFinalizado.add(fin);
            borrarProgramaMemoria(fin.getInicio(), fin.getAlcance(), memoria_P);
            memoria_P_Restante += fin.getAlcance();
            startTime = false;

            if (colaPreparado.size() == 0) {//ya no queda nada en ready
                res = esperaToPreparado();
                if (res == 1) {
                    bcpActual = colaPreparado.get(0);
                }
            } else {
                bcpActual = colaPreparado.get(0);
                res = 1;
            }
        }
        return res;
    }
    //long startTime = System.nanoTime();
    // Tu código que deseas medir
    //long endTime = System.nanoTime();
    //long tiempoDeEjecucion = (endTime - startTime) / 1000000; // Convertir a milisegundos

    public static void preBCP() { // agrega las lineas 
        ArrayList<String> listaLineas = new ArrayList();
        for (int i = 0; i < memoria_Sec_Indice_ZonaR; i++) {
            listaLineas.clear();

            String linea = memoria_S.get(i);

            String[] linea2 = linea.split("---");
            System.out.println(linea2[0] + " **** " + linea2[1] + " **** " + linea2[2]);
            int inicio = Integer.valueOf(linea2[1]);
            int largo = Integer.valueOf(linea2[2]) - inicio;
            tiempoTotal += largo;
            listaLineas = getProgramaMemoria(inicio, largo, memoria_S);
            guardarBCP(listaLineas, inicio);
        }
    }

    public static int esperaToPreparado() {
        ArrayList<String> listaLineas = new ArrayList();
        BCP actual;
        int colaLargo = colaEnEspera.size();
        int i = 0;
        int res = -1;
        while (i < colaLargo) {
            actual = colaEnEspera.get(i);
            listaLineas.clear();
            int inicio = actual.getInicioMemoriaS();
            int largo = actual.getAlcance();
            listaLineas = getProgramaMemoria(inicio, largo, memoria_S);
            int resultado = guardarEnMemoria(largo, listaLineas, memoria_P, memoria_P_Limite_Inicial, mpSize, memoria_P_Restante);
            if (resultado > 0 && colaPreparado.size() < 5) { //si lo puede guardar y hay menos de 5 bcp en ready, crea el bcp y lo guarda en ready sino en wait 
                System.out.println(" BCP Se guardo en memoria Principal cola de espera a Ready " + actual.getNombre());
                actual.setInicio(resultado);
                actual.setPc(resultado + 1);
                actual.setEstado("Preparado");
                colaPreparado.add(actual);
                colaEnEspera.remove(i);
                colaLargo--;
                memoria_P_Restante -= largo;
                res = 1;
            } else if (colaPreparado.size() == 5) {
                System.out.println("BCP No pudo guardarse en memoria en Memoria principal cola ready llena (cola de espera a Ready): " + actual.getNombre());
                colaLargo = 0;

            } else {//
                System.out.println("BCP No pudo guardarse en memoria en Memoria principal (cola de espera a Ready): " + actual.getNombre());
                i++;
            }

        }
        return res;
    }

    public static ArrayList<String> getProgramaMemoria(int inicio, int largo, ArrayList<String> memoria) {
        ArrayList<String> listaLineas = new ArrayList();
        String linea;
        for (int j = 0; j <= largo; j++) {
            linea = memoria.get(inicio + j);
            listaLineas.add(linea);
        }
        return listaLineas;
    }

    public static void borrarProgramaMemoria(int inicio, int largo, ArrayList<String> memoria) {
        for (int j = 0; j <= largo; j++) {
            memoria.set(inicio + j, null);
        }
    }

    public static void guardarBCP(ArrayList<String> listaLineas, int ini) {
        BCP nuevo;
        int cpuAleatorio;
        Random rand = new Random();
        int largo = listaLineas.size();
        ArrayList<Integer> cpuDisponibles = new ArrayList<>();
        // si el largo del programa mas la posicion actual del ultimo programa es menor que el tamaño de la memoria 
        int resultado = guardarEnMemoria(largo, listaLineas, memoria_P, memoria_P_Limite_Inicial, mpSize, memoria_P_Restante);
        /*if (cantCPU == 1) {

            if (resultado > 0 && colaPreparado.size() < 5) { //si lo puede guardar y hay menos de 5 bcp en ready, crea el bcp y lo guarda en ready sino en wait 
                System.out.println(" BCP Se guardo en memoria Principal " + listaLineas.get(0));

                nuevo = new BCP(listaLineas.get(0), "Preparado", resultado + largo, largo, 1, resultado, resultado + 1, ini);
                colaPreparado.add(nuevo);
                memoria_P_Restante -= largo;
            } else {//
                System.out.println(" BCP No pudo guardarse en memoria ");
                nuevo = new BCP(listaLineas.get(0), "Esperando", -1, largo, 1, -1, -1, ini);
                colaEnEspera.add(nuevo);
            }
        } else {*/
        if (resultado > 0) { // se puede guardar

            boolean siDisponibles = false;
            for (int i = 0; i < cantCPU; i++) { //se revisa si hay espacio en alguna CPU 
                if (colasCPU.get(i).size() < 5) { // si hay espacio guarda el numero de indice de esa CPU en la cola de CPUS 
                    siDisponibles = true;
                    cpuDisponibles.add(i);
                }
            }
            if (siDisponibles) { // si hay almenos una cpu disponible 

                cpuAleatorio = rand.nextInt(cpuDisponibles.size());
                System.out.println(" BCP Se guardo en memoria Principal " + listaLineas.get(0) + " En el CPU #" + cpuDisponibles.get(cpuAleatorio));
                nuevo = new BCP(listaLineas.get(0), "Preparado", resultado + largo, largo, 1, resultado, resultado + 1, ini);
                nuevo.setTiempoArribo(tiempoDeArribo());
                colasCPU.get((cpuDisponibles.get(cpuAleatorio))).add(nuevo);
                memoria_P_Restante -= largo;
            } else {
                System.out.println(" BCP No pudo guardarse en memoria ");
                nuevo = new BCP(listaLineas.get(0), "Esperando", -1, largo, 1, -1, -1, ini);
                nuevo.setTiempoArribo(tiempoDeArribo());
                colaEnEspera.add(nuevo);
            }
        } else {
            System.out.println(" BCP No pudo guardarse en memoria ");
            nuevo = new BCP(listaLineas.get(0), "Esperando", -1, largo, 1, -1, -1, ini);
            nuevo.setTiempoArribo(tiempoDeArribo());
            colaEnEspera.add(nuevo);
        }
    }
    // busca un espacio libre secuencial en memoria del tama;o de valor en la variable largo 
    static int tiempoArriboAuto = -1;

    public static int tiempoDeArribo() {
        Random rand = new Random();
        int tiempoarribo;
        if (autoTiempoArribo) {
            if (tiempoArriboAuto == -1) {
                tiempoarribo = 0;
                tiempoArriboAuto = 0;
            } else {
                tiempoarribo = tiempoArriboAuto + rand.nextInt(10) + 1;
                tiempoArriboAuto = tiempoarribo;
            }

        } else {
            tiempoarribo = ventanaPrincipal.pedirTiempodeArribo("sda");
        }
        return tiempoarribo;
    }

    public static int verificareEspacioMemoria(int largo, ArrayList<String> memoria, int limiteInicial, int size) {
        int cont = 0;
        int inicio = -1;
        for (int i = limiteInicial; i < size; i++) { // busca en memoria un espacio secuencial libre del tamano indicado, inicia desde el espacio libre para usarse
            if (memoria.get(i) == null) {   // si la casilla esta libre la suma 
                if (inicio < 0) {
                    inicio = i; //y verifica si ya el inicio se definio sino lo define pq es la primera casilla libre
                }
                cont++;
                if (cont < largo) {
                    return inicio;
                }
            } else {
                inicio = -1;
            }
        }
        return -1;
    }

    /**
     * *
     *
     * Funcion de guardado y borrado en alguna de las memorias que se pasa por
     * parametro ( falta verificar si funciona***)
     *
     **guardarEnMemoria devuelve la posicion inicial donde guardo el programa
     * secuencialmente
     */
    public static int guardarEnMemoria(int largo, ArrayList<String> listaLineas, ArrayList<String> memoria, int limite, int size, int restante) {

        if (largo < restante) {
            int inicio = verificareEspacioMemoria(largo, memoria, limite, size);
            int inicioAux = inicio;
            if (inicio > 0) {
                for (int i = 0; i <= largo - 1; i++) { // falta verificar si 
                    String linea = listaLineas.get(i);
                    memoria.set(inicio, linea);
                    inicio++;
                }
                return inicioAux;
            } else { // si inicio no es mayor a 0 es que no hay espacio
                return -1;
            }
        } else {
            return -1;
        }
    }

    public static int guardarEnMemoriaPrincipal(int largo, ArrayList<String> listaLineas, ArrayList<String> memoria, int limite, int size, int restante) {
        int limiteBCPs = cantCPU * 5 * 22;
        switch (algoritmo) {
            case "FCFS": //POR EL QUE LLEGA PRIMERO particionamiento fijo y dinamico 

                break;
            case "SJF": //EL MAS CORTO PRIMERO /Particionamiento dinamico 

                break;
            case "SRT": //EL MAS CORTO PRIMERO CON INTERRUPCIONES A LOS GRANDES Particionamiento Fija

                break;
            case "RR": // CON QUANTUS 

                break;
            case "HRRN": // CON EL TIEMPO RELATIVO 

                break;
            default:

                return res;
        }

        if (largo < restante) {
            int inicio = verificareEspacioMemoria(largo, memoria, limite, size);
            int inicioAux = inicio;
            if (inicio > 0) {
                for (int i = 0; i <= largo - 1; i++) { // falta verificar si 
                    String linea = listaLineas.get(i);
                    memoria.set(inicio, linea);
                    inicio++;
                }
                return inicioAux;
            } else { // si inicio no es mayor a 0 es que no hay espacio
                return -1;
            }
        } else {
            return -1;
        }
    }

    public static int guardarIndiceMemoria(String linea, ArrayList<String> memoria, int indice) {
        memoria.set(indice, linea);
        return 5;
    }

    public static void cargarGraficosMemoriaSecundaria() {
        ventanaPrincipal.borrarMemoriaSecundaria();
        for (int i = 0; i < msSize; i++) {
            String act = memoria_S.get(i);
            if (act == null) {
                ventanaPrincipal.setMemoriaSecundaria("L#" + i + ": NULL");
            } else {
                ventanaPrincipal.setMemoriaSecundaria("L#" + i + ": " + act.toString());
            }
        }
    }

    public static void cargarGraficosMemoriaPrincipal() {
        ventanaPrincipal.borrarMemoriaPrimaria();
        for (int i = 0; i < mpSize; i++) {
            String act = memoria_P.get(i);
            if (act == null) {
                ventanaPrincipal.setMemoriaPrimaria("L#" + i + ": NULL");
            } else {
                ventanaPrincipal.setMemoriaPrimaria("L#" + i + ": " + act.toString());
            }
        }
    }

    public static void cargarGraficosBCP() {
        ventanaPrincipal.borrarBCP();
        ventanaPrincipal.setBCP("Archivo: " + bcpActual.getNombre());
        ventanaPrincipal.setBCP("Estado: " + bcpActual.getEstado());
        String text = "LINEA#" + memoria_P.get(bcpActual.getPc());
        ventanaPrincipal.setBCP("PC: " + bcpActual.getPc() + text);
        ventanaPrincipal.setBCP("ax: " + bcpActual.getAx());
        ventanaPrincipal.setBCP("bx: " + bcpActual.getBx());
        ventanaPrincipal.setBCP("cx: " + bcpActual.getCx());
        ventanaPrincipal.setBCP("dx: " + bcpActual.getDx());
        ventanaPrincipal.setBCP("ac: " + bcpActual.getAc());
        ventanaPrincipal.setBCP("Tiempo Inicial: " + bcpActual.getTiempoinicial());
        ventanaPrincipal.setBCP("Tiempo Final: " + bcpActual.getTiempofinal());
        ventanaPrincipal.setBCP("Tiempo Total: " + bcpActual.getTiempototal());
        ventanaPrincipal.setBCP("Alcance: " + bcpActual.getAlcance());
        ventanaPrincipal.setBCP("Inicio: " + bcpActual.getInicio());
        ventanaPrincipal.setBCP("Prioridad: " + bcpActual.getPrioridad());
        ventanaPrincipal.setBCP("Pila: ");
        ArrayList<Integer> pilaAux = bcpActual.getPila();
        for (int i = 0; i < pilaAux.size(); i++) {
            ventanaPrincipal.setBCP("Valor " + (i + 1) + " de la pila: " + pilaAux.get(i));
        }
        ventanaPrincipal.setBCP("BCP siguiente: " + bcpActual.getSig());

    }

    public static void cargarGraficosProgramas() {
        int largo = programas.size();
        for (int i = 0; i < largo; i++) {
            String nombre = programas.get(i)[0];
            String estado = programas.get(i)[1];
            String numeroStr = programas.get(i)[2];
            ventanaPrincipal.setFilaProgramas(nombre, estado, Integer.valueOf(numeroStr));
        }

    }

    public static void cargarGraficosPantalla(String mensaje) {
        ventanaPrincipal.setPantalla(mensaje);

    }

    public static void guardarProgramas() {
        programas.clear();
        guardarProgramas(colaNuevo);
        guardarProgramas(colaEjecucion);
        guardarProgramas(colaEnEspera);
        guardarProgramas(colaPreparado);
        guardarProgramas(colaFinalizado);
    }

    public static void guardarProgramas(ArrayList<BCP> cola) {
        for (int i = 0; i < cola.size(); i++) {
            String nombre = cola.get(i).getNombre();
            String estado = cola.get(i).getEstado();
            String strNumero = String.valueOf(programas.size() + 1);
            String[] prog = {nombre, estado, strNumero};
            programas.add(prog); // guarda el nombre del programa en una lista para el procesador de programas
        }

    }

}
