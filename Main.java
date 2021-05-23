import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Producte producte = new Producte(1, "paper", "paper de cuina", 2.99, 20, true, 2, true);
        ProducteIO producteIO = new ProducteIO("filename", "rw");

        ProducteIO.inserirProducte(producte);
        ProducteIO.getProducte(1);

    }
}
