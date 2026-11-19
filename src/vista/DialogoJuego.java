/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import modelo.Juego;
import java.awt.*;
import javax.swing.*;
public class DialogoJuego extends JDialog {
// ── Campos del formulario (declarados aquí para acceder desde varios métodos) ──
private JTextField txtTitulo;
private JComboBox<String> cmbConsolaDlg;
private JComboBox<String> cmbGeneroDlg;
private JSpinner spnAnio;
private JComboBox<String> cmbPuntuacionDlg;
private JComboBox<String> cmbEstadoDlg;
private JButton btnGuardar;
private JButton btnCancelar;
private Juego juego;
private boolean guardado = false;
/**
* @param parent ventana padre (VentanaPrincipal)
* @param juegoAEditar null = nuevo juego | !null = editar existente
*/
public DialogoJuego(Frame parent, Juego juegoAEditar) {
super(parent, true); // true = modal
initUI(); // construimos la UI por código
setLocationRelativeTo(parent);
if (juegoAEditar != null) {
this.juego = juegoAEditar;
setTitle("Editar juego");
rellenarCampos(); // precarga los datos del juego en los campos
} else {
this.juego = new Juego();
setTitle("Añadir juego");
}
}
// ── Construye toda la UI por código ────────────────────────
private void initUI() {
// Panel central: 6 filas × 2 columnas, 8px entre celdas
// Columna 0 = etiqueta | Columna 1 = campo de entrada
JPanel pCampos = new JPanel(new GridLayout(6, 2, 8, 8));
pCampos.setBorder(BorderFactory.createEmptyBorder(14, 16, 8, 16));
// Fila 0 — Título

txtTitulo = new JTextField(25);
pCampos.add(new JLabel("Título:"));
pCampos.add(txtTitulo);
// Fila 1 — Consola
cmbConsolaDlg = new JComboBox<>(new String[]{
"NES","SNES","Mega Drive","Game Boy",
"PlayStation","Nintendo 64","Game Gear","Arcade"});
pCampos.add(new JLabel("Consola:"));
pCampos.add(cmbConsolaDlg);
// Fila 2 — Género
cmbGeneroDlg = new JComboBox<>(new String[]{
"Acción","Aventura","RPG","Plataformas","Puzzle",
"Deportes","Lucha","Disparos","Estrategia","Simulación"});
pCampos.add(new JLabel("Género:"));
pCampos.add(cmbGeneroDlg);
// Fila 3 — Año (JSpinner con rango 1970–2010)
spnAnio = new JSpinner(new SpinnerNumberModel(1990, 1970, 2010, 1));
pCampos.add(new JLabel("Año:"));
pCampos.add(spnAnio);
// Fila 4 — Puntuación
cmbPuntuacionDlg = new JComboBox<>(new String[]{
"Sin puntuar","★","★★","★★★","★★★★","★★★★★"});     
pCampos.add(new JLabel("Puntuación:"));
pCampos.add(cmbPuntuacionDlg);
// Fila 5 — Estado
cmbEstadoDlg = new JComboBox<>(new String[]{
"Pendiente","Jugando","Completado","Abandonado"});
pCampos.add(new JLabel("Estado:"));
pCampos.add(cmbEstadoDlg);
// Panel inferior: botones alineados a la derecha
btnGuardar = new JButton("Guardar");
btnCancelar = new JButton("Cancelar");
JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
pBotones.add(btnCancelar);
pBotones.add(btnGuardar);
// Listeners de los botones
btnGuardar .addActionListener(e -> guardar());
btnCancelar.addActionListener(e -> dispose());
// Ensamblar el diálogo
add(pCampos, BorderLayout.CENTER);
add(pBotones, BorderLayout.SOUTH);
pack(); // ajusta el tamaño al contenido
}
// ── Lógica del botón Guardar ────────────────────────────────
private void guardar() {
String titulo = txtTitulo.getText().trim();
if (titulo.isEmpty()) {
JOptionPane.showMessageDialog(this,
"El título no puede estar vacío.", "Error",
JOptionPane.ERROR_MESSAGE);
return;
}
juego.setTitulo (titulo);
juego.setConsola ((String) cmbConsolaDlg.getSelectedItem());
juego.setGenero ((String) cmbGeneroDlg.getSelectedItem());
juego.setAnio ((Integer) spnAnio.getValue());
juego.setPuntuacion(cmbPuntuacionDlg.getSelectedIndex());
juego.setEstado ((String) cmbEstadoDlg.getSelectedItem());
guardado = true;
dispose();
}
// ── Pre-rellena los campos al editar un juego existente ─────
private void rellenarCampos() {
txtTitulo.setText (juego.getTitulo());
cmbConsolaDlg.setSelectedItem(juego.getConsola());
cmbGeneroDlg .setSelectedItem(juego.getGenero());
spnAnio.setValue (juego.getAnio());

cmbPuntuacionDlg.setSelectedIndex(juego.getPuntuacion());
cmbEstadoDlg .setSelectedItem(juego.getEstado());
}
public boolean isGuardado() { return guardado; }
public Juego getJuego() { return juego; }
}