public class Producte {

    private int id;                  //              4 bytes    0-3
    private String nom;              //  20 * 2 =   40 bytes    4-43
    private String descripcio;       //  120 * 2 = 240 bytes    44-283
    private double preu;             //              8 bytes    284-291
    private int stock;               //              4 bytes    292-295
    private int categoria;           //              4 bytes    296-299
    private boolean estaDisponible;  //              1 byte     300
    private boolean estaEliminat;    //              1 byte     301
                                     //TOTAL BYTES 302 bytes


    //CONSTRUCTOR
    public Producte(int id, String nom, String descripcio, double preu, int stock, int categoria, boolean estaDisponible, boolean estaEliminat){
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.preu = preu;
        this.stock = stock;
        this.categoria = categoria;
        this.estaDisponible = estaDisponible;
        this.estaEliminat = estaEliminat;
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
        return "\nProducte " +
                "\n_________________________________" +
                "\nID:" + id +
                "\nNom:" + nom +
                "\nDescripcio: " + descripcio +
                "\nPreu: " + preu +
                "\nStock: " + stock +
                "\nCategoria: " + categoria +
                "\nEstaDisponible: " + estaDisponible +
                "\nEstaEliminat: " + estaEliminat
                ;
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
    public int getCategoria(){
        return categoria;
    }
    public boolean getEstaDisponible(){
        return estaDisponible;
    }
    public boolean getEstaEliminat(){
        return estaEliminat;
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
    public void setCategoria(int categoria){
        this.categoria = categoria;
    }
    public void setEstaDisponible(boolean estaDisponible){
        this.estaDisponible = estaDisponible;
    }
    public void setEstaEliminat(boolean estaEliminat) {
        this.estaEliminat = estaEliminat;
    }

}
