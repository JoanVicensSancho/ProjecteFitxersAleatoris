import sun.applet.AppletResourceLoader;

public class Producte {

    private int id;
    private String nom;
    private String descripcio;
    private double preu;
    private int stock;
    private boolean estaDisponible;
    private Categories categoria;

    //CONSTRUCTOR
    public Producte(int id, String nom, String descripcio, double preu, int stock, boolean estaDisponible, Categories categoria){
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.preu = preu;
        this.stock = stock;
        this.estaDisponible = estaDisponible;
        this.categoria = categoria;
    }

    //GETTERS
    public int getId(){
        return id;
    }
    public String getNom(){
        return nom;
    }
    public String getDescripcio(){
        return descripcio;
    }
    public double getPreu(){
        return preu;
    }
    public int getStock(){
        return stock;
    }
    public boolean getEstaDisponible(){
        return estaDisponible;
    }
    public Categories getCategoria(){
        return categoria;
    }

    //SETTERS
    public void setId(int id){
        this.id = id;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
    public void setDescripcio(String descripcio){
        this.descripcio = descripcio;
    }
    public void setPreu(double preu){
        this.preu = preu;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public void setEstaDisponible(boolean estaDisponible){
        this.estaDisponible = estaDisponible;
    }
    public void setCategoria(Categories categoria){
        this.categoria = categoria;
    }
}
