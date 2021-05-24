import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


        Producte producte1 = new Producte(1, "coca cola", "refresc de la marca coca cola", 0.99, 24, true, 2, false);
        Producte producte2 = new Producte(2, "pizza", "pizza de la marca casa tarradellas", 2.99, 120, true, 4, false);
        Producte producte3 = new Producte(3, "scottex", "paper des cul marca scottex", 1.99, 200, true, 1, true);
        Producte producte4 = new Producte(4, "formatge", "peça de formatge", 6.99, 16, false,  2, false);

        ProducteIO producteIO = new ProducteIO("C:\\Users\\Joan\\IdeaProjects\\P3FitxersAccesAleatori\\FitxersCategories\\ElMeuFitxer.txt", "rw");



        ProducteIO.inserirProducte(producte1);
        ProducteIO.inserirProducte(producte2);
        ProducteIO.inserirProducte(producte3);
        ProducteIO.inserirProducte(producte4);

        System.out.println(ProducteIO.getProducte(1));
        System.out.println(ProducteIO.getProducte(2));
        System.out.println(ProducteIO.getProducte(3));
        System.out.println(ProducteIO.getProducte(4));

        producteIO.imprimirLlargaria();

    }
}
