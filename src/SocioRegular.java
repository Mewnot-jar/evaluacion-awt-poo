/**
 * Representa a un socio de categoría Regular del club Leo Rey.
 * Hereda de Socio y aplica un descuento porcentual sobre la cuota base.
 *
 * @author Martin Ardiles
 * @version 1.0
 */
public class SocioRegular extends Socio{
    // ------------ ATRIBUTO PROPIO ------------
    private double descuentoPase;

    // ------------ CONSTRUCTOR ------------
    /**
     * Crea un socio regular inicializando los datos del padre con super(...).
     *
     * @param rut           RUT del socio
     * @param nombre        nombre completo del socio
     * @param edad          edad del socio en años
     * @param cuotaBase     monto base de la mensualidad
     * @param descuentoPase porcentaje de descuento (0 a 100)
     */
    public SocioRegular(String rut, String nombre, int edad, double cuotaBase, double descuentoPase){
        super(rut, nombre, edad, cuotaBase); // Inicializa los datos de la clase padre "Socio"
        this.descuentoPase = descuentoPase;
    }

    // ------------ GETTERS Y SETTERS ------------

    /**
     * Obtiene el porcentaje de descuento.
     *
     * @return porcentaje de descuento
     */
    public double getDescuentoPase(){
        return descuentoPase;
    }
    /**
     * Modifica el porcentaje de descuento.
     *
     * @param descuentoPase nuevo porcentaje de descuento (0 a 100)
     */
    public void setDescuentoPase(double descuentoPase){
        this.descuentoPase = descuentoPase;
    }

    // ------------ METODOS SOBRESCRITOS ------------
    /**
     * Calcula la cuota final aplicando el descuento sobre la cuota base.
     * Ejemplo: base $30.000 con 10% de descuento = $27.000.
     *
     * @return cuota mensual con descuento aplicado
     */
    @Override
    public double calcularCuotaFinal() {
        double descuento = getCuotaBase() * (descuentoPase / 100);
        return getCuotaBase() - descuento;
    }

    /**
     * Genera el resumen del socio agregando la categoría y el descuento.
     *
     * @return cadena con los datos del socio regular
     */
    public String obtenerResumen(){
        return "Categoria: REGULAR\n"
            + super.obtenerResumen()
            + "Descuento aplicado: " + descuentoPase + "%\n";
    }
}
