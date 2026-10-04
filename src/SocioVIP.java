/**
 * Representa a un socio de categoría VIP del club Leo Rey.
 * Hereda de Socio y suma recargos por servicios premium a la cuota base.
 *
 * @author Martin Ardiles
 * @version 1.0
 */
public class SocioVIP extends Socio{
    // ------------ CONSTANTES ------------
    public static final double RECARGO_PREMIUM = 15000; // Recargo por servicios premium
    public static final double RECARGO_SPA = 10000; // Recargo por acceso total al SPA

    // ------------ ATRIBUTOS PROPIOS ------------
    private boolean accesoTotalSpa;

    // ------------ CONSTRUCTOR ------------
    /**
     * Crea un socio VIP inicializando los datos del padre con super(...).
     *
     * @param rut            RUT del socio
     * @param nombre         nombre completo del socio
     * @param edad           edad del socio en años
     * @param cuotaBase      monto base de la mensualidad
     * @param accesoTotalSpa true si tiene acceso total al spa
     */
    public SocioVIP(String rut, String nombre, int edad, double cuotaBase, boolean accesoTotalSpa){
        super(rut, nombre, edad, cuotaBase); // Inicializa los atributos de la clase padre "Socio"
        this.accesoTotalSpa = accesoTotalSpa; // Atributo propio de la subclase SocioVIP
    }

    // ------------ GETTERS Y SETTERS ------------
    /**
     * Indica si el socio tiene acceso total al spa.
     * Nos trae el accesoTotalSpa
     * @return true si tiene acceso total al spa
     */
    public boolean isAccesoTotalSpa(){
        return accesoTotalSpa;
    }
    /**
     * Modifica el acceso total al spa.
     *
     * @param accesoTotalSpa true para dar acceso, false para quitarlo
     */
    public void setAccesoTotalSpa(boolean accesoTotalSpa){
        this.accesoTotalSpa = accesoTotalSpa;
    }

    // ------------ METODOS SOBRESCRITOS ------------
    /**
     * Calcula la cuota final sumando el recargo premium y,
     * si corresponde, el recargo por acceso al spa.
     * Ejemplo: base $30.000 + premium $15.000 + spa $10.000 = $55.000.
     *
     * @return cuota mensual con recargos aplicados
     */
    @Override 
    public double calcularCuotaFinal(){
        double total = getCuotaBase() + RECARGO_PREMIUM;
        if (accesoTotalSpa){
            total += RECARGO_SPA;
        }
        return total;
    }
    /**
     * Genera el resumen del socio agregando la categoría y los recargos.
     *
     * @return cadena con los datos del socio VIP
     */
    @Override 
    public String obtenerResumen(){
        return "CATEGORIA: VIP\n"
            + super.obtenerResumen()
            + "Recargo Premium: $" + RECARGO_PREMIUM + "\n"
            + "Acceso Total al spa: " + (accesoTotalSpa ? "Si ($" + RECARGO_SPA + ")" : "No") + "\n";
    }
}
