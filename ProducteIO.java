import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class ProducteIO {

    private RandomAccessFile fitxer;
    private Producte producte;

    private static final int LLARGARIA_MAX_NOM = 20;  //20 caracters * 2 bytes = 40 bytes
    private static final int LLARGARIA_MAX_DESCRIPCIO = 120;  // 120 caracters * 2 bytes = 240 bytes

    //TOTAL BYTES 4(id) + 40(nom) + 240(descripcio) + 8(preu) + 4(stock) + 1(estaDisponible) + 4(categoria) + 1(estaEliminat) = 302 bytes
    private final int LLARGARIA_MAX_PRODUCTE = 4 + (LLARGARIA_MAX_NOM * 2) + (LLARGARIA_MAX_DESCRIPCIO * 2) + 8 + 4 + 1 + 4 + 1;
    private final int INDEX_NOM =  LLARGARIA_MAX_PRODUCTE - 298;
    private final int INDEX_DESCRIPCIO =  LLARGARIA_MAX_PRODUCTE - 258;
    private final int INDEX_PREU =  LLARGARIA_MAX_PRODUCTE - 18;
    private final int INDEX_STOCK =  LLARGARIA_MAX_PRODUCTE - 10;
    private final int INDEX_ESTA_DISPONIBLE =  LLARGARIA_MAX_PRODUCTE - 6;
    private final int INDEX_CATEGORIA =  LLARGARIA_MAX_PRODUCTE - 5;
    private final int INDEX_ESTA_ELIMINAT =  LLARGARIA_MAX_PRODUCTE - 1;

    /**CONSTRUCTOR */
    public ProducteIO(String fileName, String mode) throws IOException{
        fitxer = new RandomAccessFile(fileName, "rw");
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


    /**Metode que RETORNA un producte determinat del fitxer  */

    public void llistarFitxer() throws IOException{

        /**Colocam el punter a l'inici del fitxer
         * Recorrem el fitxer cercant el boolean on tenim guardat si el producte esta eliminat o no
         * Si esta eliminat, el bucle continua fora llegir el producte
         * Si NO esta eliminat, colocam el punter al principi del producte i el llegim*/

        fitxer.seek(0);
        for (int i = 0; i < fitxer.length() / LLARGARIA_MAX_PRODUCTE; i++){
            fitxer.seek((i * LLARGARIA_MAX_PRODUCTE) + INDEX_ESTA_ELIMINAT);
            if (fitxer.readBoolean()){
                continue;
            }
            else {
                fitxer.seek(i * LLARGARIA_MAX_PRODUCTE);
                llegirProducte();
            }
        }
    }

    public void llistarPerCAtegoria() throws IOException{

        for (int i = 0; i < (fitxer.length() / LLARGARIA_MAX_PRODUCTE); i++){
            for (int j = 0; j )
        }
    }

    public void llistarPerPreuAscendent(){
        List<Integer> preus = new ArrayList<>();

        for (int i )
    }

    /**Instanciar un producte amb les dades que llegim
     * I l'imprimim*/

    public void llegirProducte() throws IOException{

        if ((fitxer.getFilePointer() % LLARGARIA_MAX_PRODUCTE) == 0) {
            Producte producte = new Producte(fitxer.readInt(), llegirString(LLARGARIA_MAX_NOM), llegirString(LLARGARIA_MAX_DESCRIPCIO),
                    fitxer.readDouble(), fitxer.readInt(), fitxer.readBoolean(), fitxer.readInt(), fitxer.readBoolean() );

            System.out.println(producte.toString());
        }
    }

    /**Llegeix un String i el retorna*/

    public String llegirString(int llargariaString) throws IOException{
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < llargariaString; i++){
            string.append(fitxer.readChar());
        }
        return string.toString();
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
         * Si no coincideixen retornem 0 per colocar el punter al incici del fitxer*/

        for (int i = 0; i < (fitxer.length() / LLARGARIA_MAX_PRODUCTE); i++){
            fitxer.seek((long) i * LLARGARIA_MAX_PRODUCTE);
            int llegirId = fitxer.readInt();
            if (llegirId == idParametre) {
                return i * LLARGARIA_MAX_PRODUCTE;
            }
        }
        return -1;
    }



    /**Metode que retorna true si hi ha un producte que es pugui sobreescriure i false si no
     * Recorrem tot el fitxer cercant productes que es puguin sobreescriure, si no en trobam cap retorna false*/

    public boolean esPotSobreescriure() throws IOException, NullPointerException{

        //Colocam el punter a al principi del fitxer
        fitxer.seek(0);

        /**Repetim el bucle tantres vegades com productes hi ha dins el fitxer.
         * Colocam el punter a la posicio exacta on es troba el boolean que indica si un producte esta eliminat, es a dir es pot reescriure
         * Si el boolean que he llegit es false, significa que el producte NO es pot reescriure i retornara FALSE*/
        for (int i = 0; i < fitxer.length() / LLARGARIA_MAX_PRODUCTE; i++){
            long index = (long) i * LLARGARIA_MAX_PRODUCTE + 301;
            fitxer.seek(index);
            if (fitxer.readBoolean()){
                return true;
            }
        }
        return false;
    }


    public int getIndexPerSobreescriure() throws IOException, NullPointerException {

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

    public void incrementarStock( int idProducte, int stockSumat) throws IOException{

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

    public void disminuirStock(int idProducte, int stockRestat) throws IOException {

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
    public void modificarID(int idAntiga, int idNova) throws IOException{

        fitxer.seek(getIndexProducte(idAntiga));
        fitxer.writeInt(idNova);
    }

    /**Canviar el nom*/
    public void modificarNom(int idProducte, String nomNou) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_NOM);
        fitxer.writeChars(nomNou);
    }

    /**Canviar la descripcio*/
    public void modificarDescripcio(int idProducte, String descripcioNova) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_DESCRIPCIO);
        fitxer.writeChars(descripcioNova);
    }

    /**Canviar el preu*/
    public void modificarPreu(int idProducte, int nouPreu) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_PREU);
        fitxer.writeInt(nouPreu);
    }

    /**Canviar la disponibilitat*/
    public void modificarDisponibilitat(int idProducte, boolean novaDisponibilitat) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_ESTA_DISPONIBLE);
        fitxer.writeBoolean(novaDisponibilitat);
    }

    /**Canviar la categoria*/
    public void modificarCategoria(int idProducte, int novaCategoria) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_CATEGORIA);
        fitxer.writeInt(novaCategoria);
    }

    /**Canviar boolean estaEliminat*/
    public void modificarEstaEliminat(int idProducte, boolean nouEstaEliminat) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + INDEX_ESTA_ELIMINAT);
        fitxer.writeBoolean(nouEstaEliminat);
    }

    public void imprimirLlargaria() throws IOException{
        System.out.println(fitxer.length());
    }
}
