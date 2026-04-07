/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package disquera.beans;
//import disquera.exception.*;
import disquera.exception.DiscoException;

/**
 *
 * @author Sebastian Cornejo
 */
public class Disco 
{
    private int ID;
    private String Nombre;
    private String Artista;
    private int Ano;
    private int Duracion;
    private int Ranking;
    private String Genero;
    
    public Disco()
    {
        this.ID = this.Ano = this.Duracion = this.Ranking = 0;
        this.Nombre = this.Artista = this.Genero = new String();
    }
    
    /**
     * 
     * @param ID
     * @param Nombre
     * @param Artista
     * @param Ano
     * @param Duracion
     * @param Ranking
     * @param Genero
     * @throws DiscoException 
     */
    
    
    public Disco(int ID, String Nombre, String Artista, int Ano, int Duracion, int Ranking, String Genero) throws DiscoException
    {
        this.setID(ID);
        this.setNombre(Nombre);
        this.setArtista(Artista);
        this.setAno(Ano);
        this.setDuracion(Duracion);
        this.setRanking(Ranking);
        this.setGenero(Genero);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre)  throws DiscoException {
        if(Nombre != null && Nombre.trim().length() > 0) 
        {
            this.Nombre = Nombre;
        }
        else 
        {
            throw new DiscoException("Debes ingresar el nombre.");
        }    
    }


    public String getArtista() {
        return Artista;
    }

    public void setArtista(String Artista) throws DiscoException {
        if(Artista != null && Artista.trim().length() > 0) 
        {
            this.Artista = Artista;
        }
        else 
        {
            throw new DiscoException("Debes ingresar el nombre del Artista.");
        }    
    }

    public int getAno() {
        return Ano;
    }

    public void setAno(int Ano) throws DiscoException{
        if(Ano >1900 && Ano <= 2025) {
            this.Ano = Ano;
        }
        else {
            throw new DiscoException("Ingresa un año de 1900 a 2025");
        }
    }

    public int getDuracion() {
        return Duracion;
    }

    public void setDuracion(int Duracion) {
        this.Duracion = Duracion;
    }

    public int getRanking() {
        return Ranking;
    }

    public void setRanking(int Ranking) {
        this.Ranking = Ranking;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String Genero) throws DiscoException{
        if(Genero != null && Genero.trim().length() > 0) 
        {
            this.Genero = Genero;
        }
        else 
        {
            throw new DiscoException("Debes ingresar un Genero Musical.");
        }    
    }
    
}