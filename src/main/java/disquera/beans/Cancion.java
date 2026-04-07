/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package disquera.beans;
import disquera.exception.CancionException;

/**
 *
 * @author Sebastian Cornejo
 */
public class Cancion 
{
    private int Id;
    private String Nombre;
    private int Duracion;
    
    public Cancion()
    {
        this.Nombre = new String();
        this.Id = this.Duracion = 0;
    }
    
    /**
     * 
     * @param Id
     * @param Nombre
     * @param Duracion
     * @throws CancionException 
     */
    
    
    public Cancion(int Id, String Nombre, int Duracion) throws CancionException{
    this.Id = Id;
    this.Nombre = Nombre;
    this.Duracion = Duracion;
}

    public int getID() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) throws CancionException {
        if(Nombre != null && Nombre.trim().length() > 0) 
        {
            this.Nombre = Nombre;
        }
        else 
        {
            throw new CancionException("Debes ingresar el nombre.");
        }    
    }

    public int getDuracion() {
        return Duracion;
    }

    public void setDuracion(int Duracion) {
        this.Duracion = Duracion;
    }

    
    
}
