import javax.imageio.IIOException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.SortedMap;

public class ProducteIO {

    private static RandomAccessFile fitxer;
    private Producte producte;

    private static final int LLARGARIA_MAX_NOM = 20;  //20 caracters * 2 bytes = 40 bytes
    private static final int LLARGARIA_MAX_DESCRIPCIO = 120;  // 120 caracters * 2 bytes = 240 bytes

    //TOTAL BYTES 4(id) + 40(nom) + 240(descripcio) + 8(preu) + 4(stock) + 1(estaDisponible) + 4(categoria) + 1(estaEliminat) = 302 bytes
    private static final int LLARGARIA_MAX_PRODUCTE = 4 + (LLARGARIA_MAX_NOM * 2) + (LLARGARIA_MAX_DESCRIPCIO * 2) + 8 + 4 + 1 + 4 + 1;
    private static final int INDEX_NOM =  LLARGARIA_MAX_PRODUCTE - 298;
    private static final int INDEX_DESCRIPCIO =  LLARGARIA_MAX_PRODUCTE - 258;
    private static final int INDEX_PREU =  LLARGARIA_MAX_PRODUCTE - 18;
    private static final int INDEX_STOCK =  LLARGARIA_MAX_PRODUCTE - 10;
    private static final int INDEX_ESTA_DISPONIBLE =  LLARGARIA_MAX_PRODUCTE - 6;
    private static final int INDEX_CATEGORIA =  LLARGARIA_MAX_PRODUCTE - 5;
    private static final int INDEX_ESTA_ELIMINAT =  LLARGARIA_MAX_PRODUCTE - 1;

    /**CONSTRUCTOR */
    public ProducteIO(String fileName, String mode) throws IOException{
        fitxer = new RandomAccessFile("C:\\Users\\Joan\\IdeaProjects\\P3FitxersAccesAleatori\\FitxersCategories\\ElMeuFitxer.txt", "rw");
    }


    /**Metode per INSERIR un producte nou al final del fitxer
     * Si no hi ha cap producte eliminat, escriurem el següent al final del fitxer,
     * Si hi ha un producte eliminat, el sobreescrivim*/

    public static void inserirProducte(Producte producte) throws IOException {

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
    public static void escriureProductes(Producte producte) throws IOException {

        fitxer.seek(fitxer.length());
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

    public static void getProducte(int idProducte) throws IOException{

        /**Cercam el producte que volem amb el metode (getIndexProducte) que ens retorna el seu index.
         * Colocam el punter sobre el boolean que ens diu si el producte esta eliminat o no.
         * Si esta eliminat, ens imprimira un misstage per pantalla diguent que el producte esta eliminat,
         * Si no esta eliminat, utilitzam el metode (llegirProducte) que ens llegeix el producte  */

        fitxer.seek(getIndexProducte(idProducte) + INDEX_ESTA_ELIMINAT);
        if (fitxer.readBoolean()){
            System.out.println("Aquest producte no existeix.");
        }
        else {
            fitxer.seek(getIndexProducte(idProducte));
            llegirProducte();
        }
    }

    /**Retorna un producte */

    public static Producte llegirProducte() throws IOException{

        Producte producte = new Producte(0, "", "", 0, 0, false, 0, false );

        fitxer.seek(0);

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

    public static String llegirString(int llargariaString) throws IOException{
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < llargariaString; i++){
            string.append(fitxer.readChar());
        }
        return string.toString();
    }


    /**Metode que retorna l'index del producte amb la id que li passem per parametre
     * @param idParametre*/

    public static int getIndexProducte(int idParametre) throws IOException {

        //Situam el punter a la posicio 0 del producte
        fitxer.seek(0);

        /**Repetim el bucle tantes vegades com productes hi ha la fitxer
         * A cada iteracio situam el punter a l'inici de cada producte
         * Comparam si l'ID que llegim és igual al ID que li hem passat per parametre
         * Si coincideixen, multiplicam i * la llargaria del producte i retornem el resultat (i * LLARGARIA_MAX_PRODUCTE)
         * Si no coincideixen retornem 0 per colocar el punter al incici del fitxer*/

        for (int i = 0; i < (fitxer.length() / LLARGARIA_MAX_PRODUCTE); i++){
            fitxer.seek((long) i * LLARGARIA_MAX_PRODUCTE);
            if (fitxer.readInt() == idParametre) {
                return i * LLARGARIA_MAX_PRODUCTE;
            }
        }
        return 0;
    }



    /**Metode que retorna true si hi ha un producte que es pugui sobreescriure i false si no
     * Recorrem tot el fitxer cercant productes que es puguin sobreescriure, si no en trobam cap retorna false*/

    public static boolean esPotSobreescriure() throws IOException, NullPointerException{

        //Colocam el punter a al principi del fitxer
        fitxer.seek(1);

        /**Repetim el bucle tantres vegades com productes hi ha dins el fitxer.
         * Colocam el punter a la posicio exacta on es troba el boolean que indica si un producte esta eliminat, es a dir es pot reescriure
         * Si el boolean que he llegit es false, significa que el producte NO es pot reescriure i retornara FALSE*/
        for (int i = 0; i < fitxer.length() / LLARGARIA_MAX_PRODUCTE; i++){
            fitxer.seek(((long) i * LLARGARIA_MAX_PRODUCTE) + 301);
            if (fitxer.readBoolean()){
                return true;
            }
        }
        return false;
    }


    public static int getIndexPerSobreescriure() throws IOException, NullPointerException {

        //Situam el punter a la posicio 0 del producte
        fitxer.seek(0);

        /**Repetim el bucle tantes vegades com productes hi ha la fitxer
         * A cada iteracio situam el punter al boolean (estaEliminat) de cada producte
         * Si es true (si es pot sobreescriure), retornem el seu index
         * Si ha acabat el bucle i no ha trobat cap false, retorà 0*/

        for (int i = 0; i < (fitxer.length() / LLARGARIA_MAX_PRODUCTE); i++){
            fitxer.seek((long) i * LLARGARIA_MAX_PRODUCTE + 301);
            if (fitxer.readBoolean()) {
                return i * LLARGARIA_MAX_PRODUCTE;
            }
        }
        return 0;
    }



    /**Incrementar l'stock dels productes
     * @param idProducte
     * @param stockSumat
     * Primer situam el punter sobre l'stock del producte que al que li volem afegir l'stock
     * A la variable (stockActual) li guardam l'stock que tenim actualment
     * Tornam a situar el punter sobre l'stock del producte i el sobreescrivim amb la suma de l'stockAcual + l'stock que volem afegir*/

    public static void incrementarStock( int idProducte, int stockSumat) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_STOCK);
        int stockActual = fitxer.readInt();

        fitxer.seek(getIndexProducte(idProducte) + INDEX_STOCK);
        fitxer.writeInt(stockActual + stockSumat);
    }


    /**Disminuir l'stock dels productes
     * @param idProducte
     * @param stockRestat
     * Colocam el punter sobre l'stock del nostre producte
     * Llegim l'stock que tenim i el guardam a la variable (stockActual)
     * Si l'stock que tenim es menor al que volem retirar, imprimim un missatge d'error
     * Pel contrari colocam el punter sobre l'stock del producte
     * I el sobreescrivim amb la resta de l'stock actual menos el que volem retirar*/

    public static void disminuirStock(int idProducte, int stockRestat) throws IOException {

        fitxer.seek(getIndexProducte(idProducte) + INDEX_STOCK);
        int stockActual = fitxer.readInt();

        if (stockActual < stockRestat){
            System.out.println("Nomes hi ha " + stockActual + " unitats del producte, no en pots retirar " + stockRestat);
        }
        else {
            fitxer.seek(getIndexProducte(idProducte) + INDEX_STOCK);
            fitxer.writeInt(stockActual - stockRestat);
        }
    }


    /**MODIFICAR DADES*/

    /**Canviar l'id*/
    public static void modificarID(int idAntiga, int idNova) throws IOException{

        fitxer.seek(getIndexProducte(idAntiga));
        fitxer.writeInt(idNova);
    }

    /**Canviar la descripcio*/
    public static void modificarDescripcio(int idProducte, String descripcioNova) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_DESCRIPCIO);
        fitxer.writeChars(descripcioNova);
    }

    /**Canviar el preu*/
    public static void modificarPreu(int idProducte, int nouPreu) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_PREU);
        fitxer.writeInt(nouPreu);
    }

    /**Canviar la disponibilitat*/
    public static void modificarDisponibilitat(int idProducte, boolean novaDisponibilitat) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_ESTA_DISPONIBLE);
        fitxer.writeBoolean(novaDisponibilitat);
    }

    /**Canviar la categoria*/
    public static void modificarCategoria(int idProducte, int novaCategoria) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_CATEGORIA);
        fitxer.writeInt(novaCategoria);
    }

    /**Canviar boolean estaEliminat*/
    public static void modificarEstaEliminat(int idProducte, boolean nouEstaEliminat) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_ESTA_ELIMINAT);
        fitxer.writeBoolean(nouEstaEliminat);
    }
    /**Canviar el nom*/
    public static void modificarNom(int idProducte, String nomNou) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_NOM);
        fitxer.writeChars(nomNou);
    }
}
