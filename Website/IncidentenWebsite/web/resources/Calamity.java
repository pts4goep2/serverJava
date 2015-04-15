/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

/**
 *
 * @author Indra
 */
public class Calamity {

    private String latitude;
    private String longitude;
    private String name;
    private String description;

    public Calamity(String latitude, String longitude, String name, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
    }

}
