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

        // ------------ EVENTOS ------------
        btnRegistrar.addActionListener(e -> registrarSocio());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Escuchamos si el choice cambia - Si cambia a vip (osea index = 1) chkSpa.setEnabled(true) habilitamos el check de spa
        chCategoria.addItemListener(e -> {
            boolean esVip = chCategoria.getSelectedIndex() == 1;
            chkSpa.setEnabled(esVip);
            if(!esVip){
                chkSpa.setEnabled(false);
            }
        });

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

    private void registrarSocio(){
        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String textoEdad = txtEdad.getText().trim();
        
        if(nombre.isEmpty() || rut.isEmpty() || textoEdad.isEmpty()){
            mostrarAlerta("Error: Debe completar todos los campos.");
            return;
        }

        int edad;

        try {
            edad = Integer.parseInt(textoEdad);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error: La edad debe ser un numero entero.");
            return;
        }

        if(edad < 18){
            txtReporte.setText("Error: El socio debe ser mayor de edad.\n" + "No se realizo el registro.");
            mostrarAlerta("Error: El socio debe ser mayor de edad.");
            return;
        }
        int tipo = chCategoria.getSelectedIndex() + 1;
        Socio socio;

        switch (tipo) {
            case 1:
                double descuento = 10;
                socio = new SocioRegular(rut, nombre, edad, Socio.CUOTA_BASE_DEFECTO, descuento);
                break;
            case 2:
                boolean spa = chkSpa.getState();
                socio = new SocioVIP(rut, nombre, edad, Socio.CUOTA_BASE_DEFECTO, spa);
                break;
            default:
                mostrarAlerta("Error: Categoria no valida");
                return;
        }

        double cuotaMensual = socio.calcularCuotaFinal();
        if(chkCasillero.getState()){
            cuotaMensual += MONTO_CASILLERO;
        }

        int meses = Integer.parseInt(chMeses.getSelectedItem());

        String reporte = "------- SOCIO REGISTRADO -------\n";
        reporte += socio.obtenerResumen();
        reporte += "Casillero/Toalla: "
                    + (chkCasillero.getState() ? "Si (+$" + MONTO_CASILLERO + ")" : "No")
                    + "\n";
        reporte += "----------------------------";
        reporte += "CUOTA MENSUAL TOTAL: $" + cuotaMensual + "\n\n";
        reporte += generarProyeccion(cuotaMensual, meses);

        txtReporte.setText(reporte);
    }

    /**
     * Calcula la proyección de pagos mes a mes
     * usando un bucle for, acumulando el total pagado.
     *
     * @param cuotaMensual monto que se paga cada mes
     * @param meses        cantidad de meses a proyectar (6 o 12)
     * @return texto con el desglose mes a mes y el total
     */
    private String generarProyeccion(double cuotaMensual, int meses){
        String texto = "------- PROYECCION A " + meses + " -------\n";
        double acumulado = 0;

        for(int mes=1; mes<=meses; mes++){
            acumulado += cuotaMensual;
            texto += String.format("Mes %2d: $%,9.0f | Acumulado: $%,10.0f\n", mes, cuotaMensual, acumulado);
        }
        texto += "---------------------\n";
        texto += "TOTAL A PAGAR EN " + meses + " MESES: $" + acumulado + "\n";
        return texto;
    }
    private void limpiarFormulario(){
        txtNombre.setText("");
        txtRut.setText("");
        txtEdad.setText("");
        chCategoria.select(0);   // vuelve a "Regular"
        chMeses.select(0);       // vuelve a "6"
        chkCasillero.setState(false);
        chkSpa.setState(false);
        chkSpa.setEnabled(false);
        txtReporte.setText("");
        txtNombre.requestFocus(); // deja el cursor en el nombre
    }
    /**
     * Muestra una ventana de alerta con un mensaje.
     * AWT no tiene JOptionPane (eso es de Swing), así que se arma con Dialog.
     *
     * @param mensaje texto a mostrar en la alerta
     */
    private void mostrarAlerta(String mensaje){
        Dialog dialogo = new Dialog(this, "Alerta", true);
        dialogo.setLayout(new FlowLayout());
        dialogo.add(new Label(mensaje));

        Button btnAceptar = new Button("Aceptar");
        btnAceptar.addActionListener(e -> dialogo.dispose());
        dialogo.add(btnAceptar);

        dialogo.addWindowListener(new WindowAdapter(){
            @Override 
            public void windowClosing(WindowEvent e){
                dialogo.dispose();
            }
        });

        dialogo.setSize(380, 120);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }
}
