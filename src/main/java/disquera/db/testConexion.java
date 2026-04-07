/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package disquera.db;

import disquera.db.dataBase;
import java.sql.Connection;
/**
 *
 * @author asus
 */
public class testConexion 
{
     public static void main(String[] args) 
     {
        try 
        {
            Connection con = dataBase.getConnection();
            System.out.println("Conexión exitosa!");
        }
        catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}