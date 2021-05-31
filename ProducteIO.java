import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ProducteIO {

    private final RandomAccessFile fitxer;

    /**CONSTRUCTOR */
    public ProducteIO(String nomFitxer, String mode) throws IOException{
        fitxer = new RandomAccessFile(nomFitxer, mode);
    }










    /**Metode per INSERIR un producte nou al final del fitxer
     * Si no hi ha cap producte eliminat, escriurem el següent al final del fitxer,
     * Si hi ha un producte eliminat, el sobreescrivim*/

    public void inserirProducte(Producte producte) throws IOException {

        fitxer.seek(getIndexPerEscriure());
        escriureProductes(producte);
    }

    /**Metode que escriu les dades al fitxer*/
    public void escriureProductes(Producte producte) throws IOException {

        //Escriure l'id del producte (int)
        fitxer.writeInt(producte.getId());

        //Escriure el nom del producte (String de 20 caracters com a maxim)
        fitxer.writeChars(llargariaStringAjustada(producte.getNom(), Constants.LLARGARIA_MAX_NOM));

        //Escriure la descripcio del producte (String de 60 caracteres com a maxim)
        fitxer.writeChars(llargariaStringAjustada(producte.getDescripcio(), Constants.LLARGARIA_MAX_DESCRIPCIO));

        //Escriure el preu del producte (double)
        fitxer.writeDouble(producte.getPreu());

        //Escriure l'stock del producte (int)
        fitxer.writeInt(producte.getStock());

        //Escriure la categoria del producte (int)
        fitxer.writeInt(producte.getCategoria());

        //Escriure la disponibilitat del producte (boolean)
        fitxer.writeBoolean(producte.getEstaDisponible());

        //Escriure si el producte esta eliminat (boolean)
        fitxer.writeBoolean(producte.getEstaEliminat());
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










    /**Instanciar un producte amb les dades que llegim
     * I l'imprimim*/

    public void llegirProducteAdmins() throws IOException{

        if ((fitxer.getFilePointer() % Constants.LLARGARIA_MAX_PRODUCTE) == 0) {
            Producte producte = new Producte(fitxer.readInt(), llegirString(Constants.LLARGARIA_MAX_NOM), llegirString(Constants.LLARGARIA_MAX_DESCRIPCIO),
                    fitxer.readDouble(), fitxer.readInt(), fitxer.readInt(), fitxer.readBoolean(), fitxer.readBoolean() );

            System.out.println(producte.toStringAdmins());
        }
    }public void llegirProducteClients() throws IOException{

        if ((fitxer.getFilePointer() % Constants.LLARGARIA_MAX_PRODUCTE) == 0) {
            Producte producte = new Producte(fitxer.readInt(), llegirString(Constants.LLARGARIA_MAX_NOM), llegirString(Constants.LLARGARIA_MAX_DESCRIPCIO),
                    fitxer.readDouble(), fitxer.readInt(), fitxer.readInt(), fitxer.readBoolean(), fitxer.readBoolean() );

            System.out.println(producte.toStringClients());
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










    /**Metode que ens diu si un producte en concret esta eliminat o no.
     * Si el producte esta eliminat, retorna TRUE
     * Si NO esta eliminat retorna FALSE*/
    public boolean getElProducteEstaEliminat(int indexProducte) throws IOException{

        fitxer.seek(indexProducte + Constants.INDEX_ESTA_ELIMINAT);
        if (fitxer.readBoolean()){
            return true;
        }
        return false;
    }

    /**Metode que ens diu si un producte en concret esta disponible o no.
     * Si el producte esta dispnible, retorna TRUE
     * Si NO esta disponible retorna FALSE*/
    public boolean getElProducteEstaDisponible(int indexProducte) throws IOException{

        fitxer.seek(indexProducte + Constants.INDEX_ESTA_DISPONIBLE);
        if (fitxer.readBoolean()){
            return true;
        }
        return false;
    }

    /**Metode que retorna la categoria del producte que hi ha a l'index que li passam*/

    public int getCategoriaDelProducte(int indexProducte) throws IOException {

        fitxer.seek(indexProducte + Constants.INDEX_CATEGORIA);
        return fitxer.readInt();
    }










    /**Metode que llegeix tot el fitxer
     *
     *Colocam el punter a l'inici del fitxer
     * Recorrem el fitxer i miram si el producte que estam mirant esta eliminat amb el metode (getElProducteEstaEliminat)
     * Si esta eliminat, el bucle continua fora llegir el producte
     * Si NO esta eliminat, colocam el punter al principi del producte i el llegim*/

    public void llistarFitxerAdmins() throws IOException {

        fitxer.seek(0);
        for (int i = 0; i < fitxer.length() / Constants.LLARGARIA_MAX_PRODUCTE; i++){

            if (!getElProducteEstaEliminat(i * Constants.LLARGARIA_MAX_PRODUCTE)){
                fitxer.seek((long) i * Constants.LLARGARIA_MAX_PRODUCTE);
                llegirProducteAdmins();
            }
        }
    }

    /**Colocam el punter a l'inici del fitxer
     * Recorrem el fitxer i miram si el producte que estam mirant esta eliminat i disponible amb el metode (getElProducteEstaEliminat, getElProducteEstaDisponible)
     * Si esta eliminat, el bucle continua fora llegir el producte
     * Si NO esta eliminat, colocam el punter al principi del producte i el llegim*/

    public void llistarFitxerClients() throws IOException {

        fitxer.seek(0);
        for (int i = 0; i < fitxer.length() / Constants.LLARGARIA_MAX_PRODUCTE; i++){

            if (!getElProducteEstaEliminat(i * Constants.LLARGARIA_MAX_PRODUCTE) && getElProducteEstaDisponible(i * Constants.LLARGARIA_MAX_PRODUCTE)){
                fitxer.seek((long) i * Constants.LLARGARIA_MAX_PRODUCTE);
                llegirProducteClients();
            }
        }
    }










    /**Metode que imprimeix la llista dels productes ordenats segons el preu
     * Li passam un ArrayList amb els indexosDelsProductes dels productes ja ordenats
     * Recorrem l'ArrayList i si el producte no esta eliminat, colocam el punter sobre l'index del producte, el llegim i imprimim
     * Si el producte esta eliminat el metode no fa res i no l'imprimeix
     *
     * Per obtenir l'ArrayList utilitzam el metode (ordenarIndexosPreuAscendent)*/

    public void llistarProductesSegonsPreuAdmins(ArrayList<Integer> indexosDelsProductes) throws IOException{

        fitxer.seek(0);

        for (int i = 0; i < indexosDelsProductes.size(); i++){
            if (!getElProducteEstaEliminat(indexosDelsProductes.get(i))) { //Si el producte NO esta eliminat
                fitxer.seek((long) indexosDelsProductes.get(i));
                llegirProducteAdmins();
            }
        }
    }

    /**Metode que imprimeix la llista dels productes ordenats segons el preu
     * Li passam un ArrayList amb els indexosDelsProductes dels productes ja ordenats
     * Recorrem l'ArrayList i si el producte no esta eliminat, colocam el punter sobre l'index del producte, el llegim i imprimim
     * Si el producte esta eliminat el metode no fa res i no l'imprimeix
     *
     * Per obtenir l'ArrayList utilitzam el metode (ordenarIndexosPreuAscendent)*/

    public void llistarProductesSegonsPreuClients(ArrayList<Integer> indexosDelsProductes) throws IOException{

        fitxer.seek(0);

        for (int i = 0; i < indexosDelsProductes.size(); i++){
            if (!getElProducteEstaEliminat(indexosDelsProductes.get(i)) && getElProducteEstaDisponible(indexosDelsProductes.get(i))) { //Si el producte NO esta eliminat i ademes esta disponible
                fitxer.seek((long) indexosDelsProductes.get(i));
                llegirProducteClients();
            }
        }
    }

    /**Metode que ordena els indexos dels productes segons el seu preu de forma ascendent amb l'algoritme (selectionSort)
     *
     * Primer cream l'ArrayList que retornarem mes tard i li afegim els INDEXOS DELS PRODUCTES que hi ha al fitxer (0 , 302, 604, 906 ...).
     *
     * PRIMER BUCLE
     * Despres el recorrem per poder llegir els preus corresponents a cada producte.
     * A cada iteracio del primer bucle guardam el PREU i l'INDEX DEL PRODUCTE amb la seva posicio dins l'ArrayList
     * dins les variables (preuMesPetit, indexProductePreuMesPetit i posicioDelIndex).
     *
     * SEGON BUCLE
     * Al segon bucle utilitzarem la variable preuMesPetit per compararla amb els preus de TOTS els productes del fitxer per trobar el preu mes petit,
     * si trobam un preu mes petit que el que estam comparant, substiruirem el valor de les variables temporals que hem guardat abans
     * pels valors asociats al nou preu mes baix (preuMesPetit, indexProductePreuMesPetit, posicioDelIndex).
     *
     *
     * Una vegada hem trobat el preu mes petit intercanviam les seves posicions dins l'ArrayList i tornam a repetir el primer bucle*/

    public ArrayList<Integer> ordenarIndexosPreuAscendent() throws IOException{

        ArrayList<Integer> indexos = guardarPreus();

        for (int i = 0; i < indexos.size(); i++){

            fitxer.seek(indexos.get(i) + Constants.INDEX_PREU);

            double preuMesPetit = fitxer.readDouble();
            int indexProductePreuMesPetit = indexos.get(i);
            int posicioDelIndex = i;

            for (int j = i; j < fitxer.length() / Constants.LLARGARIA_MAX_PRODUCTE; j++){
                fitxer.seek((indexos.get(j)) + Constants.INDEX_PREU);
                if (fitxer.readDouble() < preuMesPetit){
                    fitxer.seek((indexos.get(j)) + Constants.INDEX_PREU);
                    preuMesPetit = fitxer.readDouble();
                    indexProductePreuMesPetit = indexos.get(j);
                    posicioDelIndex = j;
                }
            }
            indexos.set(posicioDelIndex, indexos.get(i));
            indexos.set(i, indexProductePreuMesPetit);
        }
        return indexos;
    }

    /**Metode que ordena els indexos dels productes segons el seu preu de forma ascendent amb l'algoritme (selectionSort)
     *
     * Primer cream l'ArrayList que retornarem mes tard i li afegim els INDEXOS DELS PRODUCTES que hi ha al fitxer (0 , 302, 604, 906 ...).
     *
     * PRIMER BUCLE
     * Despres el recorrem per poder llegir els preus corresponents a cada producte.
     * A cada iteracio del primer bucle guardam el PREU i l'INDEX DEL PRODUCTE amb la seva posicio dins l'ArrayList
     * dins les variables (preuMesGran, indexProductePreuMesGran i posicioDelIndex).
     *
     * SEGON BUCLE
     * Al segon bucle utilitzarem la variable preuMesGran per compararla amb els preus de TOTS els productes del fitxer per trobar el preu mes gran,
     * si trobam un preu mes gran que el que estam comparant, substiruirem el valor de les variables temporals que hem guardat abans
     * pels valors asociats al nou preu mes baix (preuMesGran, indexProductePreuMesGran, posicioDelIndex).
     *
     *
     * Una vegada hem trobat el preu mes gran intercanviam les seves posicions dins l'ArrayList i tornam a repetir el primer bucle*/

    public ArrayList<Integer> ordenarIndexosPreuDescendent() throws IOException{

        ArrayList<Integer> indexos = guardarPreus();

        for (int i = 0; i < indexos.size(); i++){

            fitxer.seek(indexos.get(i) + Constants.INDEX_PREU);

            double preuMesGran = fitxer.readDouble();
            int indexProductePreuMesGran = indexos.get(i);
            int posicioDelIndex = i;

            for (int j = i; j < fitxer.length() / Constants.LLARGARIA_MAX_PRODUCTE; j++){

                fitxer.seek((indexos.get(j)) + Constants.INDEX_PREU);

                if (fitxer.readDouble() > preuMesGran){
                    fitxer.seek((indexos.get(j)) + Constants.INDEX_PREU);
                    preuMesGran = fitxer.readDouble();
                    indexProductePreuMesGran = indexos.get(j);
                    posicioDelIndex = j;
                }
            }
            indexos.set(posicioDelIndex, indexos.get(i));
            indexos.set(i, indexProductePreuMesGran);
        }
        return indexos;
    }

    /**Metode que retorna un ArrayList amb els preus de tots els productes que llegeix*/

    public ArrayList<Integer> guardarPreus() throws IOException{
        ArrayList<Integer> preus = new ArrayList<>();

        fitxer.seek(0);
        for (int i = 0; i < fitxer.length() / Constants.LLARGARIA_MAX_PRODUCTE; i++){
            preus.add(i * Constants.LLARGARIA_MAX_PRODUCTE);
        }
        return preus;
    }

    /**Metode que recorre tots els productes del fitxer.
     * Si el producte que estam mirant pertany a la categoria que li hem passat per paramtre i no esta eliminat el mostram per pantalla*/

    public void llistarPerCategoriaAdmins(int categoria) throws IOException{

        fitxer.seek(0);

        for (int i = 0; i < fitxer.length() / Constants.LLARGARIA_MAX_PRODUCTE; i++){
            //si la categoria del producte coincideix amb la que volem i no esta eliminat el llegim i l'imprimim
            if ((getCategoriaDelProducte(i * Constants.LLARGARIA_MAX_PRODUCTE) == categoria) && (!getElProducteEstaEliminat(i * Constants.LLARGARIA_MAX_PRODUCTE))){
                fitxer.seek((long) i * Constants.LLARGARIA_MAX_PRODUCTE);
                llegirProducteAdmins();
            }
        }
    }

    /**Metode que recorre tots els productes del fitxer.
     * Si el producte que estam mirant pertany a la categoria que li hem passat per parametre, no esta eliminat i esta desponible el mostram per pantalla*/

    public void llistarPerCategoriaClients(int categoria) throws IOException{

        fitxer.seek(0);

        for (int i = 0; i < fitxer.length() / Constants.LLARGARIA_MAX_PRODUCTE; i++){
            //si la categoria del producte coincideix amb la que volem, no esta eliminat i esta disponible, el llegim i l'imprimim
            if ((getCategoriaDelProducte(i * Constants.LLARGARIA_MAX_PRODUCTE) == categoria) && (!getElProducteEstaEliminat(i * Constants.LLARGARIA_MAX_PRODUCTE)) && (getElProducteEstaDisponible(i * Constants.LLARGARIA_MAX_PRODUCTE))){
                fitxer.seek((long) i * Constants.LLARGARIA_MAX_PRODUCTE);
                llegirProducteClients();
            }
        }
    }










    /**Metode que retorna l'index del producte amb la id que li passem per parametre
     *
     * Repetim el bucle tantes vegades com productes hi ha la fitxer
     * A cada iteracio situam el punter a l'inici de cada producte
     * Comparam si l'ID que llegim és igual al ID que li hem passat per parametre
     * Si coincideixen, multiplicam i * la llargaria del producte i retornem el resultat (i * LLARGARIA_MAX_PRODUCTE)
     * Si no coincideixen retornem 0 per colocar el punter al incici del fitxer*/

    public int getIndexProducte(int idParametre) throws IOException {

        //Situam el punter a la posicio 0 del producte
        fitxer.seek(0);

        for (int i = 0; i < (fitxer.length() / Constants.LLARGARIA_MAX_PRODUCTE); i++){
            fitxer.seek((long) i * Constants.LLARGARIA_MAX_PRODUCTE);
            int llegirId = fitxer.readInt();
            if (llegirId == idParametre) {
                return i * Constants.LLARGARIA_MAX_PRODUCTE;
            }
        }
        return (int) fitxer.length();
    }

     /**Metode que ens retorna la posicio on podem escriure el següent producte
      *
      * Repetim el bucle tantes vegades com productes hi ha la fitxer
      * A cada iteracio situam el punter al boolean (estaEliminat) de cada producte
      * Si es true (si es pot sobreescriure), retornem el seu index
      * Si ha acabat el bucle i no ha trobat cap false, retornarà la llargaria del fitxer*/


    public int getIndexPerEscriure() throws IOException/*, NullPointerException */{

        //Situam el punter a la posicio 0 del producte
        fitxer.seek(0);

        for (int i = 0; i < (fitxer.length() / Constants.LLARGARIA_MAX_PRODUCTE); i++){
            fitxer.seek((long) i * Constants.LLARGARIA_MAX_PRODUCTE + Constants.INDEX_ESTA_ELIMINAT);
            if (fitxer.readBoolean()) {
                return i * Constants.LLARGARIA_MAX_PRODUCTE;
            }
        }
        return (int)fitxer.length();
    }










    /**Incrementar l'stock dels productes
     * Primer situam el punter sobre l'stock del producte que al que li volem afegir l'stock
     * A la variable (stockActual) li guardam l'stock que tenim actualment
     * Tornam a situar el punter sobre l'stock del producte i el sobreescrivim amb la suma de l'stockAcual + l'stock que volem afegir*/

    public void incrementarStock( int idProducte, int stockSumat) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_STOCK);
        int stockActual = fitxer.readInt();

        fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_STOCK);
        fitxer.writeInt(stockActual + stockSumat);
    }


    /**Disminuir l'stock dels productes
     * Colocam el punter sobre l'stock del nostre producte
     * Llegim l'stock que tenim i el guardam a la variable (stockActual)
     * Si l'stock que tenim es menor al que volem retirar, imprimim un missatge d'error
     * Pel contrari colocam el punter sobre l'stock del producte
     * I el sobreescrivim amb la resta de l'stock actual menos el que volem retirar*/

    public void disminuirStock(int idProducte, int stockRestat) throws IOException {

        fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_STOCK);
        int stockActual = fitxer.readInt();

        if (stockActual < stockRestat){
            System.out.println("Nomes hi ha " + stockActual + " unitats del producte, no en pots retirar " + stockRestat + ".");
        }
        else {
            fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_STOCK);
            fitxer.writeInt(stockActual - stockRestat);
        }
    }

    /*MODIFICAR DADES*/

    /**Canviar l'id*/
    public void modificarID(int idAntiga, int idNova) throws IOException{

        fitxer.seek(getIndexProducte(idAntiga));
        fitxer.writeInt(idNova);
    }

    /**Canviar el nom*/
    public void modificarNom(int idProducte, String nomNou) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_NOM);
        fitxer.writeChars(nomNou);

    }

    /**Canviar la descripcio*/
    public void modificarDescripcio(int idProducte, String descripcioNova) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_DESCRIPCIO);
        fitxer.writeChars(descripcioNova);
    }

    /**Canviar el preu*/
    public void modificarPreu(int idProducte, double nouPreu) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_PREU);
        fitxer.writeDouble(nouPreu);
    }

    /**Canviar la disponibilitat
     * Si el producte esta disponible, el posam en NO DISPONIBLE
     * Si el producte NO esta disponible, el posam en SI DISPONIBLE*/
    public void modificarDisponibilitat(int idProducte) throws IOException{

        if (getElProducteEstaDisponible(getIndexProducte(idProducte))){
            fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_ESTA_DISPONIBLE);
            fitxer.writeBoolean(false);
        }
        else {
            fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_ESTA_DISPONIBLE);
            fitxer.writeBoolean(true);
        }
    }

    /**Canviar la categoria*/
    public void modificarCategoria(int idProducte, int novaCategoria) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_CATEGORIA);
        fitxer.writeInt(novaCategoria);
    }

    /**Canviar boolean estaEliminat*/
    public void modificarEstaEliminat(int idProducte, boolean nouEstaEliminat) throws IOException{

        fitxer.seek(getIndexProducte(idProducte) + Constants.INDEX_ESTA_ELIMINAT);
        fitxer.writeBoolean(nouEstaEliminat);
    }
}
