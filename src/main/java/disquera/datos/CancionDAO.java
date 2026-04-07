/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package disquera.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import disquera.exception.CancionException;
import disquera.beans.Cancion;
import java.util.ArrayList;
import java.util.List;
import disquera.db.dataBase;

/**
 *
 * @author asus
 */
public class CancionDAO {

    private Connection con;

    private void abrirConexion() throws SQLException 
    {
        if (con == null || con.isClosed()) 
        {
            con = dataBase.getConnection();
        }
    }

    private void cerrarConexion() 
    {
        try 
        {
            if (con != null && !con.isClosed()) 
            {
                con.close();
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }

    public void agregarCancion(Cancion cancion) throws CancionException, SQLException 
    {
        try 
        {
            buscarCancion(cancion.getID());
            throw new CancionException("El ID ingresado ya existe.");
        } 
        catch (CancionException e) 
        {}

        abrirConexion();

        String sql = "INSERT INTO cancion (Id, Nombre, Duracion) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, cancion.getID());
            ps.setString(2, cancion.getNombre());
            ps.setInt(3, cancion.getDuracion());
            ps.executeUpdate();
        } finally 
        {
            cerrarConexion();
        }
    }

    public List<Cancion> listarCancion() throws CancionException, SQLException 
    {
        abrirConexion();
        List<Cancion> lista = new ArrayList<>();

        String sql = "SELECT Id, Nombre, Duracion FROM cancion";

        try 
        (
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) 
        {
            while (rs.next())
                {
                    Cancion cancion = new Cancion
                    (
                        rs.getInt("Id"),
                        rs.getString("Nombre"),
                        rs.getInt("Duracion")
                    );
                    lista.add(cancion);
                }
        }
                
        finally 
        {
            cerrarConexion();
        }

        return lista;
    }

    public Cancion buscarCancion(int Id) throws CancionException, SQLException 
    {
        if (Id <= 0) {
            throw new CancionException("Debe ingresar un ID válido.");
        }

        abrirConexion();

        String sql = "SELECT Id, Nombre, Duracion FROM cancion WHERE Id = (?)";
        Cancion encontrada = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, Id);

            try (ResultSet rs = ps.executeQuery()) 
            {
                if (rs.next()) 
                {
                    encontrada = new Cancion(
                        rs.getInt("Id"),
                        rs.getString("Nombre"),
                        rs.getInt("Duracion")
                    );
                }
            }
        }
        finally 
        {
            cerrarConexion();
        }

        if (encontrada == null) 
        {
            throw new CancionException("Canción no encontrada.");
        }

        return encontrada;
    }
    
    public boolean eliminarCancion(int Id) throws CancionException, SQLException 
    {
        buscarCancion(Id);
        abrirConexion();
        String sql = "DELETE FROM cancion WHERE Id = ?";
        int resultado = 0;

        try (PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, Id);
            resultado = ps.executeUpdate();
        } 
        finally 
        {
            cerrarConexion();
        }

        return resultado > 0;
    }
    
    public boolean actualizarCancion(Cancion cancion) throws CancionException, SQLException
    {

        abrirConexion();
        String sql = "UPDATE cancion SET Nombre = ?, Duracion = ? WHERE Id = ?";

        int resultado = 0;

        try (PreparedStatement ps = con.prepareStatement(sql)) 
        {

            ps.setString(1, cancion.getNombre());
            ps.setInt(2, cancion.getDuracion());
            ps.setInt(3, cancion.getID());

            resultado = ps.executeUpdate();
            System.out.println("Filas modificadas: " + resultado);

        } 
        finally 
        {
            cerrarConexion();
        }

        return resultado > 0;
    }    
    
    public boolean existeId(int Id) throws SQLException 
    {
        String sql = "SELECT 1 FROM cancion WHERE Id = ?";
        abrirConexion(); 
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, Id);
        ResultSet rs = ps.executeQuery();

        boolean existe = rs.next();
        rs.close();
        ps.close();

        return existe;
    }

}