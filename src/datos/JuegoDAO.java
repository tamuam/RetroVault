/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.Juego;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JuegoDAO {
    // ── OBTENER TODOS ─────────────────────────────────────────
    public List<Juego> obtenerTodos() {
        List<Juego> lista = new ArrayList<>();
        String sql = "SELECT * FROM juegos ORDER BY titulo";
        // try-with-resources cierra Connection, Statement y ResultSet automáticamente
        try (Connection c = DatabaseManager.getConnection();
             Statement  s = c.createStatement();
             ResultSet  r = s.executeQuery(sql)) {
            while (r.next()) lista.add(mapear(r));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    // ── FILTRAR POR CONSOLA ───────────────────────────────────
    public List<Juego> obtenerPorConsola(String consola) {
        List<Juego> lista = new ArrayList<>();
        // PreparedStatement: el ? se sustituye de forma segura por el valor
        String sql = "SELECT * FROM juegos WHERE consola = ? ORDER BY titulo";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, consola);   // posición 1 = primer ?
            try (ResultSet r = ps.executeQuery()) {
                while (r.next()) lista.add(mapear(r));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    // ── FILTRAR POR ESTADO ────────────────────────────────────
    public List<Juego> obtenerPorEstado(String estado) {
        List<Juego> lista = new ArrayList<>();
        String sql = "SELECT * FROM juegos WHERE estado = ? ORDER BY titulo";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet r = ps.executeQuery()) {
                while (r.next()) lista.add(mapear(r));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    // ── INSERTAR ──────────────────────────────────────────────
    public void insertar(Juego j) {
        String sql = "INSERT INTO juegos (titulo, consola, genero, anio, puntuacion, estado) VALUES(?,?,?,?,?,?)";
        // RETURN_GENERATED_KEYS: recuperar el id asignado por AUTOINCREMENT
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, j.getTitulo());
            ps.setString(2, j.getConsola());
            ps.setString(3, j.getGenero());
            ps.setInt   (4, j.getAnio());
            ps.setInt   (5, j.getPuntuacion());
            ps.setString(6, j.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) j.setId(rs.getInt(1));  // guardamos el id en el objeto
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
    // ── ACTUALIZAR ────────────────────────────────────────────
    public void actualizar(Juego j) {
        String sql = "UPDATE juegos SET titulo=?,consola=?,genero=?,anio=?,puntuacion=?,estado=? WHERE id=?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, j.getTitulo());
            ps.setString(2, j.getConsola());
            ps.setString(3, j.getGenero());
            ps.setInt   (4, j.getAnio());
            ps.setInt   (5, j.getPuntuacion());
            ps.setString(6, j.getEstado());
            ps.setInt   (7, j.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    // ── ELIMINAR ──────────────────────────────────────────────
    public void eliminar(int id) {
        String sql = "DELETE FROM juegos WHERE id = ?";
        try (Connection c = DatabaseManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    // ── ESTADÍSTICAS ──────────────────────────────────────────
    public int contarTotal() {
        return contarSQL("SELECT COUNT(*) FROM juegos");
    }
    public int contarCompletados() {
        return contarSQL("SELECT COUNT(*) FROM juegos WHERE estado='Completado'");
    }
    public double puntuacionMedia() {
        String sql = "SELECT AVG(puntuacion) FROM juegos WHERE puntuacion > 0";
        try (Connection c = DatabaseManager.getConnection();
             Statement  s = c.createStatement();
             ResultSet  r = s.executeQuery(sql)) {
            if (r.next()) return Math.round(r.getDouble(1) * 10.0) / 10.0;
        } catch (SQLException e) { e.printStackTrace(); }
        return 0.0;
    }
    private int contarSQL(String sql) {
        try (Connection c = DatabaseManager.getConnection();
             Statement  s = c.createStatement();
             ResultSet  r = s.executeQuery(sql)) {
            if (r.next()) return r.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
    // ── MAPEAR ResultSet → Juego ──────────────────────────────
    // Método privado reutilizado por todos los métodos de consulta.
    // Lee las columnas del ResultSet por nombre y rellena un objeto Juego.
    private Juego mapear(ResultSet r) throws SQLException {
        Juego j = new Juego();
        j.setId        (r.getInt   ("id"));
        j.setTitulo    (r.getString ("titulo"));
        j.setConsola   (r.getString ("consola"));
        j.setGenero    (r.getString ("genero"));
        j.setAnio      (r.getInt    ("anio"));
        j.setPuntuacion(r.getInt    ("puntuacion"));
        j.setEstado    (r.getString ("estado"));
        return j;
    }
}