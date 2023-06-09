package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;
import modelo.AccesoBBDD;
import modelo.ProyectoIntegrador;
import vista.S_ConsulProyect;
import vista.AyudaProyect;
import vista.ConsulProyect;
import vista.VentanaPrincipal;

/**
 * Clase que implementa la interfaz ActionListener para el botón de inicio de sesión.
 * Realiza la validación del inicio de sesión y muestra la interfaz correspondiente.
 */
public class LoginBtnListener implements ActionListener {
    private VentanaPrincipal v; // Ventana principal
    private AccesoBBDD programa = new AccesoBBDD(); // Objeto para acceder a la base de datos
    private ConsulProyect consProy; // Ventana de consulta de proyectos

    /**
     * CONSTRUCTOR
     * @param vista La ventana principal
     */
    public LoginBtnListener(VentanaPrincipal vista) {
        v = vista;
    }

    /**
     * Método que se ejecuta al hacer clic en el botón de inicio de sesión.
     * Realiza la validación del inicio de sesión y muestra la interfaz correspondiente.
     * @param e El evento de acción generado por el botón de inicio de sesión.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (initConsul()) {
            ArrayList<ProyectoIntegrador> proyectos = mostrarProys();
            String studentInput1 = v.getStudResp().getText().trim();

            if (studentInput1.equals("")) {
                consProy = new ConsulProyect();
                MenuListener Menulist = new MenuListener(consProy);
                consProy.showProys(proyectos);
                consProy.hacerVisible();
            } else {
                // Estamos en la vista de alumno
                S_ConsulProyect s_consProy = new S_ConsulProyect();
                s_consProy.hacerVisible();
            }
            v.dispose();
        } else {
            JOptionPane.showMessageDialog(v, "Usuario y contraseña incorrectos");
        }
    }

    /**
     * Método que realiza la inicialización de la consulta.
     * Verifica el usuario y la contraseña y establece la conexión con la base de datos.
     * @return true si la consulta se inicializa correctamente, false en caso contrario.
     */
    public boolean initConsul() {
        boolean check = false;
        String studentInput1 = v.getStudResp().getText().trim();
        programa.setUserAlumno(studentInput1);
        String username = v.getAdminRes1().getText().trim();
        programa.setUsrname(username);
        programa.setPassword(new String(v.getAdminRes3().getPassword()).trim());

        programa.getConexion();

        boolean resultado = programa.login();
        if (resultado) {
            // Damos acceso
            check = true;
        } else {
            JOptionPane.showMessageDialog(v, "Usuario y contraseña incorrectos");
        }
        return check;
    }

    /**
     * Método que carga los proyectos desde la base de datos.
     * @return Una lista de objetos ProyectoIntegrador que representa los proyectos cargados desde la base de datos.
     */
    public ArrayList<ProyectoIntegrador> mostrarProys() {
        String query = "SELECT * FROM proyectoIntegrador";
        return programa.cargarProyectos(query); // Carga los proyectos desde la base de datos
    }
}
