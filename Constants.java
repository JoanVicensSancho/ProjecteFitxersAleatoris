public class Constants {

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


    public int getLlargariaMaxNom(){
        return LLARGARIA_MAX_NOM;
    }
    public int getLlargariaMaxDescripcio(){
        return LLARGARIA_MAX_DESCRIPCIO;
    }
}
