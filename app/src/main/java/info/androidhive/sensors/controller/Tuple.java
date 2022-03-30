package info.androidhive.sensors.controller;

/**
 * Tupla genérica de dos objetos
 * @author Martín Gascón
 * @author Eduardo Ruiz
 * @author Daniel Huici
 * @version 1.0
 */
public class Tuple<A, B> {
    public final A a;
    public final B b;

    /**
     *  Construye una tupla
     *
     */
    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }
}