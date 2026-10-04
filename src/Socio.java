/**
 * Clase base que representa a un socio del club deportivo Leo Rey.
 * Contiene los datos comunes a todas las categorías de socio y el
 * cálculo básico de la cuota mensual.
 *
 * @author Martin Ardiles
 * @version 1.0
 */
public class Socio {
    //  ------------ Atributos (Solo se acceden con setters y getters) ------------
    private String rut;
    private String nombre;
    private int edad;
    private double cuotaBase;

    //Constante que vive en la clase y no se puede modificar
    public static final double CUOTA_BASE_DEFECTO = 30000;

    // ------------ CONSTRUCTORES ------------
    //Constructor por defecto (sobrecarga) crea un socio vacio con la cuota base sugerida
    public Socio() {
        this("205045678", "Martin", 26, CUOTA_BASE_DEFECTO);
    }

    /**
     * Constructor con parámetros.
     *
     * @param rut       RUT del socio
     * @param nombre    nombre completo del socio
     * @param edad      edad del socio en años
     * @param cuotaBase monto base de la mensualidad
     */

    public Socio(String rut, String nombre, int edad, double cuotaBase){
        this.rut = rut;
        this.nombre = nombre;
        this.edad = edad;
        this.cuotaBase = cuotaBase;
    }

    // ------------ GETTERS Y SETTERS ------------
    /**
     * Obtiene el RUT del socio.
     *
     * @return RUT del socio
     */
    public String getRut(){
        return rut;
    }
    /**
     * Modifica el RUT del socio.
     *
     * @param rut nuevo RUT
     */
    public void setRut(String rut){
        this.rut = rut;
    }

    /**
     * Obtiene el nombre del socio.
     *
     * @return nombre del socio
     */
    public String getNombre(){
        return nombre;
    }
    /**
     * Modifica el Nombre del socio.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /**
     * Obtiene la edad del socio.
     *
     * @return edad del socio
     */
    public int getEdad(){
        return edad;
    }
    /**
     * Modifica la edad del socio.
     *
     * @param edad nueva edad
     */
    public void setEdad(int edad){
        this.edad = edad;
    }

    /**
     * Obtiene la cuotaBase del socio.
     *
     * @return cuotaBase del socio
     */
    public double getCuotaBase(){
        return cuotaBase;
    }
    /**
     * Modifica la cuotaBase del socio.
     *
     * @param cuotaBase nueva cuotaBase
     */
    public void setCuotaBase(double cuotaBase){
        this.cuotaBase = cuotaBase;
    }

    // ------------ METODOS DEL NEGOCIO ------------

     /**
     * Calcula la cuota mensual final del socio.
     * En la clase base es simplemente la cuota base; las subclases
     * sobrescriben este método para aplicar descuentos o recargos.
     *
     * @return cuota mensual final
     */
    public double calcularCuotaFinal(){
        return cuotaBase;
    }

    /**
     * Genera un resumen en texto con los datos del socio.
     *
     * @return cadena con RUT, nombre, edad, cuota base y cuota final
     */
    public String obtenerResumen() {
        return "RUT: " + rut + "\n"
            + "Nombre: " + nombre + "\n"
            + "Edad: " + edad + " años\n"
            + "Cuota base: $" + cuotaBase + "\n"
            + "Cuota final: $" + calcularCuotaFinal() + "\n";
    }
}
