/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class ConsolaColorRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        // Llamamos primero al renderer padre para que prepare el JLabel
        JLabel label = (JLabel) super.getTableCellRendererComponent(
                           table, value, isSelected, hasFocus, row, column);
        if (!isSelected) {
            // Leemos la consola del modelo (columna 2) para esta fila
            Object val = table.getModel().getValueAt(row, 2);
            String consola = val != null ? val.toString() : "";
            // Color de fondo según la consola (tonos oscuros para FlatLaf Dark)
            Color bg = switch (consola) {
                case "NES"         -> new Color(70, 20, 20);   // rojo oscuro
                case "SNES"        -> new Color(45, 20, 65);   // morado oscuro
                case "Mega Drive"  -> new Color(20, 30, 75);   // azul oscuro
                case "Game Boy"    -> new Color(20, 55, 30);   // verde oscuro
                case "PlayStation" -> new Color(15, 40, 70);   // azul marino
                case "Nintendo 64" -> new Color(55, 20, 55);   // morado medio
                case "Game Gear"   -> new Color(65, 35, 10);   // naranja oscuro
                case "Arcade"      -> new Color(60, 50, 10);   // dorado oscuro
                default            -> new Color(35, 35, 35);   // gris oscuro
            };
            label.setBackground(bg);
            label.setForeground(Color.WHITE);
        }
        return label;
    }
}