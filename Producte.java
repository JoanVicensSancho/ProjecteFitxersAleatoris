public class Producte {

    private int id; //4 bytes
    private String nom; //20 * 2 = 40 bytes
    private String descripcio; //50 * 2 = 100 bytes
    private double preu; //8 bytes
    private int stock; //4 bytes
    private boolean estaDisponible; //1 byte
    private Categories categoria;

    // TOTAL BYTES 4 + 40 + 120 + 8 + 4 + 1 + = 177 bytes

    private final int LLARGARIA_MAX_NOM = 20;
    private final int LLARGARIA_MAX_DESCRIPCIO = 120;
    private final int LLARGARIA_MAX_PRODUCTE = 177;  //TOTAL BYTES 4 + 40 + 120 + 8 + 4 + 1 + = 177 bytes

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


    /**Metode que ajusta la llargaria dels Strings, si son massa curts, els allarga amb espais fins que
     * arribin a la seva màxima llargaria, (si es un nom 20 caracters, si es una descripcio 120 caracters)*/

    public String llargariaStringAjustada(String string, int llargariaMax){

        String stringAjustat = string;

        if (string.length() > llargariaMax){
            stringAjustat = stringAjustat.substring(0, llargariaMax);
            return stringAjustat;
        }

        for (int i = string.length(); i < llargariaMax; i++){
            stringAjustat += " ";
        }

        return stringAjustat;

    }

    @Override
    public String toString() {
        return "Producte {" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", preu=" + preu +
                ", stock=" + stock +
                ", estaDisponible=" + estaDisponible +
                '}';
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
