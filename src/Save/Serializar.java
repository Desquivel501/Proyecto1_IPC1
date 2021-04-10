package Save;

import Models.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Derek
 */
public class Serializar {
    public void Serializar(Profesores[] prof, Cursos[] curso, Alumnos[] alumno){
        try {
            ObjectOutputStream profSave = new ObjectOutputStream(new FileOutputStream("Saves\\profesores.ipc"));
            profSave.writeObject(prof);
            profSave.close();
            
            ObjectOutputStream cursoSave = new ObjectOutputStream(new FileOutputStream("Saves\\cursos.ipc"));
            cursoSave.writeObject(curso);
            cursoSave.close();
            
            ObjectOutputStream alumnoSave = new ObjectOutputStream(new FileOutputStream("Saves\\alumnos.ipc"));
            alumnoSave.writeObject(alumno);
            alumnoSave.close();
            
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Serializar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(Serializar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
