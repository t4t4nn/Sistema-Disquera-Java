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
import disquera.exception.DiscoException;
import disquera.beans.Disco;
import java.util.ArrayList;
import java.util.List;
import disquera.db.dataBase;
/**
 *
 * @author asus
 */
public class DiscoDAO 
{
    private Connection con;

    private void abrirConexion() throws SQLException {
        if (con == null || con.isClosed()) {
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
        } catch (SQLException e) 
        {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }

    public void agregarDisco(Disco disco) throws DiscoException, SQLException 
    {
        try 
        {
            buscarDisco(disco.getID());
            throw new DiscoException("El ID ingresado ya existe.");
        } 
        catch (DiscoException e) 
        {}

        abrirConexion();

        String sql = "INSERT INTO disco (Id, Nombre, Artista, Anio, Duracion, Ranking, Genero) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, disco.getID());
            ps.setString(2, disco.getNombre());
            ps.setString(3, disco.getArtista());
            ps.setInt(4, disco.getAno());
            ps.setInt(5, disco.getDuracion());
            ps.setInt(6, disco.getRanking());
            ps.setString(7, disco.getGenero());
            
            ps.executeUpdate();
        } 
        finally 
        {
            cerrarConexion();
        }
    }

    public List<Disco> listarDisco() throws DiscoException, SQLException 
    {
        abrirConexion();
        List<Disco> lista = new ArrayList<>();

        String sql = "SELECT Id, Nombre, Artista, Anio, Duracion, Ranking, Genero FROM disco;";
        try 
        (
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) 
        {
            while (rs.next())
            {
                Disco disco = new Disco
                    (
                        rs.getInt("Id"),
                        rs.getString("Nombre"),
                        rs.getString("Artista"),
                        rs.getInt("Anio"),
                        rs.getInt("Duracion"),
                        rs.getInt("Ranking"),
                        rs.getString("Genero")
                    );
                    lista.add(disco);
            }
        }
        
        finally 
        {
            cerrarConexion();
        }

        return lista;
    }

    public Disco buscarDisco(int Id) throws DiscoException, SQLException 
    {
        if (Id <= 0) 
        {
            return null;
        }

        abrirConexion();
        String sql = "SELECT Id, Nombre, Artista, Anio, Duracion, Ranking, Genero FROM disco WHERE Id = (?)";
        Disco encontrada = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, Id);

            try (ResultSet rs = ps.executeQuery()) 
            {
                if (rs.next()) 
                {
                    encontrada = new Disco(
                        rs.getInt("Id"),
                        rs.getString("Nombre"),
                        rs.getString("Artista"),
                        rs.getInt("Anio"),
                        rs.getInt("Duracion"),
                        rs.getInt("Ranking"),
                        rs.getString("Genero")
                    );
                }
            }
        }
        finally 
        {
            cerrarConexion();
        }
        return encontrada;
    }
    
    public boolean eliminarDisco(int Id) throws DiscoException, SQLException 
    {
        buscarDisco(Id);
        abrirConexion();
        String sql = "DELETE FROM disco WHERE Id = (?)";
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
    
    public boolean actualizarDisco(Disco disco) throws DiscoException, SQLException 
    {
        abrirConexion();
        String sql = "UPDATE disco SET Nombre = (?), Artista = (?), Anio = (?), Duracion = (?), Ranking = (?), Genero = (?) WHERE Id = (?)";

        int resultado = 0;
        try (PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setString(1, disco.getNombre());
            ps.setString(2, disco.getArtista());
            ps.setInt(3, disco.getAno());
            ps.setInt(4, disco.getDuracion());
            ps.setInt(5, disco.getRanking());
            ps.setString(6, disco.getGenero());
            ps.setInt(7, disco.getID());
            resultado = ps.executeUpdate();
        }
    
        finally 
        {
            cerrarConexion();
        }

        return resultado > 0;
    }
    
    public boolean existeId(int Id) throws SQLException 
    {
        String sql = "SELECT 1 FROM disco WHERE Id = ?";
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
