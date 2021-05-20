import java.io.IOException;
import java.io.RandomAccessFile;

public class ProducteIO {

    private RandomAccessFile fitxer;
    private Producte producte;

    private final int LLARGARIA_MAX_NOM = 20;
    private final int LLARGARIA_MAX_DESCRIPCIO = 120;
    private final int LLARGARIA_MAX_PRODUCTE = 177;




    /**Metode per INSERIR un producte nou al final del fitxer*/

    public void inserirProducte(Producte producte) throws IOException {

        //Col·loquem el punter al final del fitxer
        fitxer.seek(fitxer.length());

        //Escriure l'id del producte (int)
        fitxer.writeInt(producte.getId());

        //Escriure el nom del producte (String de 20 caracters com a maxim)
        fitxer.writeChars(producte.llargariaStringAjustada(producte.getNom(), LLARGARIA_MAX_NOM));

        //Escriure la descripcio del producte (String de 120 caracteres com a maxim)
        fitxer.writeChars(producte.llargariaStringAjustada(producte.getDescripcio(), LLARGARIA_MAX_DESCRIPCIO));

        //Escriure el preu del producte (double)
        fitxer.writeDouble(producte.getPreu());

        //Escriure l'stock del producte (int)
        fitxer.writeInt(producte.getStock());

        //Escriure la disponibilitat del producte (boolean)
        fitxer.writeBoolean(producte.getEstaDisponible());
    }




    /**Metode per LLEGIR tots el productes del fitxer*/

    public Producte llegirTotElDocument() throws IOException{

        //Col·loquem el punter al principi del fitxer
        fitxer.seek(0);

        //Dividim la llargaria del fitxer entre la llargaria dels productes per saber
        // cuants de porductes hi ha i saber cuantes vegades s'ha de repetir el bucle
        for (int i = 0; i < fitxer.length() / LLARGARIA_MAX_PRODUCTE; i++ ){

            //Llegim l'identificador del prducte
            fitxer.readInt();

            //Llegim el nom del producte
            fitxer.readFully();
        }
    }




    /**Metode per ELIMINAR un producte del fitxer*/

    public void eliminarProducte (int posicio){

    }
}
