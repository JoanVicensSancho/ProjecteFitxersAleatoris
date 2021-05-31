public class Constants {

    public static final int LLARGARIA_MAX_NOM = 20;  //20 caracters * 2 bytes = 40 bytes
    public static final int LLARGARIA_MAX_DESCRIPCIO = 120;  // 120 caracters * 2 bytes = 240 bytes

    //TOTAL BYTES 4(id) + 40(nom) + 240(descripcio) + 8(preu) + 4(stock) + 4(categoria) + 1(estaDisponible) + 1(estaEliminat) = 302 bytes
    public static final int LLARGARIA_MAX_PRODUCTE = 4 + (LLARGARIA_MAX_NOM * 2) + (LLARGARIA_MAX_DESCRIPCIO * 2) + 8 + 4 + 4 + 1 + 1;
    public static final int INDEX_NOM = LLARGARIA_MAX_PRODUCTE - 298;
    public static final int INDEX_DESCRIPCIO = LLARGARIA_MAX_PRODUCTE - 258;
    public static final int INDEX_PREU = LLARGARIA_MAX_PRODUCTE - 18;
    public static final int INDEX_STOCK = LLARGARIA_MAX_PRODUCTE - 10;
    public static final int INDEX_CATEGORIA = LLARGARIA_MAX_PRODUCTE - 6;
    public static final int INDEX_ESTA_DISPONIBLE = LLARGARIA_MAX_PRODUCTE - 2;
    public static final int INDEX_ESTA_ELIMINAT = LLARGARIA_MAX_PRODUCTE - 1;

    public static final String BLUE = "\033[34m";   //1 BLUE
    public static final String GREEN = "\033[32m";  //2 GREEN
    public static final String RESET = "\u001B[0m"; //RESET



}
