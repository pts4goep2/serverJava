/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;

/**
 *
 * @author Indra
 */
public class WebController {
    private WebPage webpage;
    
    public WebController(){
        webpage = new WebPage();
        
        try{
        webpage.doGet(null, null, 5);
        }
        catch(ServletException e){
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(WebController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
