/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientguitest;

import Database.*;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author Leo
 */
public class Administratie 
{
    private IDatabase database;
    private static Administratie admin = null;
    private Administratie()
    {
        //database = new SQL();
    }
    
    public boolean loginEmergencyService(String username, String password, int id)
    {
        if(id < 1 || id > 3)
        {
            return false;
        }
        return database.loginEmergencyService(username, password, id);
    }
    
    public static Administratie getInstance()
    {
        if(admin == null)
        {
            admin = new Administratie();
        }
        return admin;
    }
}
