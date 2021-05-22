import java.io.IOException;
import java.io.RandomAccessFile;

public class ProducteIO {

    private RandomAccessFile fitxer;
    private Producte producte;

    private final int LLARGARIA_MAX_NOM = 20;  //20 caracters * 2 bytes = 40 bytes
    private final int LLARGARIA_MAX_DESCRIPCIO = 120;  // 120 caracters * 2 bytes = 240 bytes
    private final int LLARGARIA_MAX_PRODUCTE = 302;  //TOTAL BYTES 4(id) + 40(nom) + 240(descripcio) + 8(preu) + 4(stock) + 1(estaDisponible) + 4(categoria) + 1(estaEliminat) = 302 bytes


    /**CONSTRUCTOR */
    public ProducteIO(String fileName, String mode) throws IOException{
        fitxer = new RandomAccessFile("ElMeuFitxer", "rw");
    }


    /**Metode per INSERIR un producte nou al final del fitxer
     * Si no hi ha cap producte eliminat, escriurem el següent al final del fitxer,
     * Si hi ha un producte eliminat, el sobreescrivim*/

    public void inserirProducte(Producte producte) throws IOException {

        if (!esPotSobreescriure()) {
            fitxer.seek(fitxer.length());
            escriureProductes(producte);
        }
        else {
            fitxer.seek(getIndexPerSobreescriure());
            escriureProductes(producte);
        }
    }


    /**Metode que escriu les dades al fitxer*/
    public void escriureProductes(Producte producte) throws IOException {

        //Escriure l'id del producte (int)
        fitxer.writeInt(producte.getId());

        //Escriure el nom del producte (String de 20 caracters com a maxim)
        fitxer.writeChars(producte.llargariaStringAjustada(producte.getNom(), LLARGARIA_MAX_NOM));

        //Escriure la descripcio del producte (String de 60 caracteres com a maxim)
        fitxer.writeChars(producte.llargariaStringAjustada(producte.getDescripcio(), LLARGARIA_MAX_DESCRIPCIO));

        //Escriure el preu del producte (double)
        fitxer.writeDouble(producte.getPreu());

        //Escriure l'stock del producte (int)
        fitxer.writeInt(producte.getStock());

        //Escriure la disponibilitat del producte (boolean)
        fitxer.writeBoolean(producte.getEstaDisponible());

        //Escriure la categoria del producte (int)
        fitxer.writeInt(producte.getCategoria());

        //Escriure si el producte esta eliminat (boolean)
        fitxer.writeBoolean(producte.getEstaEliminat());
    }




    /**Metode que RETORNA un producte determinat del fitxer
     * @param idProducte*/

    public Producte getProducte(int idProducte) throws IOException{

        /**Cercam el producte que volem amb el metode (getIndexProducte) que ens retorna el seu index.
         * Colocam el punter sobre l'index que ens ha retornat
         * Li passam l'index al metode (llegirProducte) i ens retorna el producte  */
        fitxer.seek(getIndexProducte(idProducte));
        return llegirProducte();

    }

    /**Metode que retorna l'index del producte amb la id que li passem per parametre
     * @param idParametre*/

    public int getIndexProducte(int idParametre) throws IOException {

        //Situam el punter a la posicio 0 del producte
        fitxer.seek(0);

        /**Repetim el bucle tantes vegades com productes hi ha la fitxer
         * A cada iteracio situam el punter a l'inici de cada producte
         * Comparam si l'ID que llegim és igual al ID que li hem passat per parametre
         * Si coincideixen, multiplicam i * la llargaria del producte i retornem el resultat (i * LLARGARIA_MAX_PRODUCTE)
         * Si no coincideixen retornem -1*/

        for (int i = 0; i < (fitxer.length() / LLARGARIA_MAX_PRODUCTE); i++){
            fitxer.seek(i * LLARGARIA_MAX_PRODUCTE);
            if (fitxer.readInt() == idParametre) {
                return i * LLARGARIA_MAX_PRODUCTE;
            }
        }
        return -1;
    }

    /**Retorna un producte */

    public Producte llegirProducte() throws IOException{

        Producte producte = new Producte(0, "", "", 0, 0, false, 0, false );

        if ((fitxer.getFilePointer() % LLARGARIA_MAX_PRODUCTE) == 0) {
            producte.setId(fitxer.readInt());
            producte.setNom(llegirString(LLARGARIA_MAX_NOM));
            producte.setDescripcio(llegirString(LLARGARIA_MAX_DESCRIPCIO));
            producte.setPreu(fitxer.readDouble());
            producte.setStock(fitxer.readInt());
            producte.setEstaDisponible(fitxer.readBoolean());
            producte.setCategoria(fitxer.readInt());
        }
        return producte;
    }

    /**Llegeix un String i el retorna*/

    public String llegirString(int llargariaString) throws IOException{
        String string = "";
        for (int i = 0; i < llargariaString; i++){
            string += fitxer.readChar();
        }
        return string;
    }




    /**Metode que retorna true si hi ha un producte que es pugui sobreescriure i false si no
     * Recorrem tot el fitxer cercant productes que es puguin sobreescriure, si no en trobam cap retorna false*/

    public boolean esPotSobreescriure() throws IOException{

        //Colocam el punter a al principi del fitxer
        fitxer.seek(0);

        /**Repetim el bucle tantres vegades com productes hi ha dins el fitxer.
         * Colocam el punter a la posicio exacta on es troba el boolean que indica si un producte esta eliminat, es a dir es pot reescriure
         * Si el boolean que he llegit es false, significa que el producte NO es pot reescriure i retornara FALSE*/
        for (int i = 0; i < fitxer.length() / LLARGARIA_MAX_PRODUCTE; i++){
            fitxer.seek((i * LLARGARIA_MAX_PRODUCTE) + 301);
            if (fitxer.readBoolean()){
                return true;
            }
        }
        return false;
    }


    public int getIndexPerSobreescriure() throws IOException {

        //Situam el punter a la posicio 0 del producte
        fitxer.seek(0);

        /**Repetim el bucle tantes vegades com productes hi ha la fitxer
         * A cada iteracio situam el punter al boolean (estaEliminat) de cada producte
         * Si es true (si es pot sobreescriure), retornem el seu index
         * Si ha acabat el bucle i no ha trobat cap false, retorà -1*/

        for (int i = 0; i < (fitxer.length() / LLARGARIA_MAX_PRODUCTE); i++){
            fitxer.seek(i * LLARGARIA_MAX_PRODUCTE + 301);
            if (fitxer.readBoolean()) {
                return i * LLARGARIA_MAX_PRODUCTE;
            }
        }
        return -1;
    }




    /**Metode per ELIMINAR un producte del fitxer*/

    public void eliminarProducte (int posicio){

    }
}
