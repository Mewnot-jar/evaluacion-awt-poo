import java.awt.*;
import java.awt.event.*;

/**
 * Ventana principal del sistema de registro de socios del club Leo Rey.
 * Construida con AWT: permite ingresar los datos del socio, elegir su
 * categoría y beneficios, y muestra el reporte con la proyección de pagos.
 *
 * @author Martin Ardiles
 * @version 1.0
 */
public class VentanaClub extends Frame{
    // ------------ CONSTANTES ------------
    public static final double MONTO_CASILLERO = 5000; // Monto fijo mensual por casillero y toalla

    // ------------ COMPONENTES DE LA INTERFAZ GRAFICA ------------
    private TextField txtNombre;
    private TextField txtRut;
    private TextField txtEdad;
    private Choice chCategoria;
    private Choice chMeses;
    private Checkbox chkCasillero;
    private Checkbox chkSpa;
    private Button btnRegistrar;
    private Button btnLimpiar;
    private TextArea txtReporte;

    // Construye la ventana, crea y ubica a todos los componentes y los muestra
    public VentanaClub(){
        // ------------ CONFIGURACION DE LA VENTANA  ------------
        super("Club Deportivo Leo Rey - Registro de Socios"); // Titulo de la ventana
        setSize(650, 600); // Tamaño de la ventana
        setLayout(new BorderLayout(10, 10)); // Utilizamos borderlayout para la organizacion de los componentes

        // ------------ PANEL DEL FORMULARIO (NORTH)------------
        // GridLayout(filas, columnas, espcioH, espacioV): una grilla de 6 x 2
        Panel panelFormulario = new Panel(new GridLayout(6, 2, 5, 5));

        //Fila 1: Nombre
        panelFormulario.add(new Label("Nombre:"));
        txtNombre = new TextField();
        panelFormulario.add(txtNombre);

        //Fila 2: Rut
        panelFormulario.add(new Label("Rut:"));
        txtRut = new TextField();
        panelFormulario.add(txtRut);

        //Fila 3: Edad
        panelFormulario.add(new Label("Edad:"));
        txtEdad = new TextField();
        panelFormulario.add(txtEdad);

        //Fila 4: Categoria (Lista desplegable)
        panelFormulario.add(new Label("Categoria:"));
        chCategoria = new Choice();
        chCategoria.add("Regular");// indice 0
        chCategoria.add("VIP");// indice 1
        panelFormulario.add(chCategoria);

        //Fila 5: Meses de la proyeccion
        panelFormulario.add(new Label("Proyeccion (meses):"));
        chMeses = new Choice();
        chMeses.add("6");
        chMeses.add("12");
        panelFormulario.add(chMeses);

        //Fila 6: Beneficios
        chkCasillero = new Checkbox("Incluye casillero/toalla");
        chkSpa = new Checkbox("Acceso total spa (solo VIP)");
        chkSpa.setEnabled(false);
        panelFormulario.add(chkCasillero);
        panelFormulario.add(chkSpa);

        add(panelFormulario, BorderLayout.NORTH);

        // ------------ AREA DE REPORTE (CENTER) ------------
        txtReporte = new TextArea("", 15, 60, TextArea.SCROLLBARS_VERTICAL_ONLY);
        txtReporte.setEditable(false);
        txtReporte.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(txtReporte, BorderLayout.CENTER);

        // ------------ PANEL DE BOTONES (SOUTH) ------------
        Panel panelBotones = new Panel(new FlowLayout());
        btnRegistrar = new Button("Registrar y Calcular");
        btnLimpiar = new Button("Limpiar");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);

        // ------------ CIERRE DE VENTANA ------------
        addWindowListener(new WindowAdapter() {
            @Override 
            public void windowClosing(WindowEvent e){
                dispose();
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
