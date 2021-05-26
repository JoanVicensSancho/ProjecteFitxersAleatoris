import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


        Producte producte1 = new Producte(1, "coca cola", "refresc de la marca coca cola", 100.99, 24, true, 2, false);
        Producte producte2 = new Producte(2, "pizza", "pizza de la marca casa tarradellas", 25.99, 120, true, 4, false);
        Producte producte3 = new Producte(3, "scottex", "paper des cul marca scottex", 21.99, 200, true, 1, false);
        Producte producte4 = new Producte(4, "formatge", "peça de formatge", 66.99, 16, false,  2, true);

        ProducteIO producteIO = new ProducteIO("C:\\Users\\Joan\\IdeaProjects\\P3FitxersAccesAleatori\\ElMeuFitxer.txt", "rw");


/*
        producteIO.inserirProducte(producte1);
        producteIO.inserirProducte(producte2);
        producteIO.inserirProducte(producte3);
        producteIO.inserirProducte(producte4);
*/
        producteIO.modificarEstaEliminat(4, false);

        //producteIO.llistarFitxer();

        producteIO.llistarProductesSegonsPreu(producteIO.ordenarIndexosPreuAscendent(producteIO.guardarPreus()));

        producteIO.imprimirLlargaria();

    }
}
