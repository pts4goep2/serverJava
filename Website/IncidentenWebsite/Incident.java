/**
 *
 * @author Indra
 */
public class Incident {
    private double latitude;
    private double longitude;
    private String naam;
    private String beschrijving;
    
    public Incident(double latitude, double longitude, String naam, String beschrijving){
        this.latitude = latitude;
        this.longitude = longitude;
        this.naam = naam;
        this.beschrijving = beschrijving;
    }
}
