/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retrovault;

import com.formdev.flatlaf.FlatDarkLaf;
import datos.DatabaseManager;
import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
public class Main {
public static void main(String[] args) {
// 1. Aplicar FlatLaf ANTES de crear ningún componente Swing
try {
UIManager.setLookAndFeel(new FlatDarkLaf());
} catch (Exception e) {
System.err.println("No se pudo cargar FlatLaf: " + e.getMessage());
}
// 2. Crear la tabla SQLite si todavía no existe
DatabaseManager.inicializar();
// 3. Abrir la ventana en el hilo de eventos de Swing
SwingUtilities.invokeLater(() -> {
new VentanaPrincipal().setVisible(true);
});
}
}
