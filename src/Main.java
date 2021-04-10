import Menu.*;
import Models.Profesores;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Derek
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        LoginGUI loginGUI = new LoginGUI();
//        Profesores[] prof = new Profesores[50];
//        AdminGUI adminGUI = new AdminGUI();
//        adminGUI.AdminGUI(prof,0);
//        adminGUI.AdminGUI();
    }
    
}
