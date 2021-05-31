import java.io.IOException;
import java.util.Scanner;

public class MenuClient {


    public static void menuClient() throws IOException {

        ProducteIO producteIO = new ProducteIO("C:\\Users\\Joan\\IdeaProjects\\P3FitxersAccesAleatori\\ElMeuFitxer.txt", "rw");

        boolean vullAcabarExecucio = false;
        while (!vullAcabarExecucio) {

            displayMenuPrincipalClients();

            Scanner accio = new Scanner(System.in);
            System.out.print("\nEleccio: ");

            switch (accio.nextInt()) {

                case 1:
                    producteIO.llistarFitxerClients();
                    break;

                case 2:
                    producteIO.llistarProductesSegonsPreuClients(producteIO.ordenarIndexosPreuAscendent());
                    break;

                case 3:
                    producteIO.llistarProductesSegonsPreuClients(producteIO.ordenarIndexosPreuDescendent());
                    break;

                case 4:
                    System.out.print("\nCategoria de productes que vols llistar: ");
                    Scanner categoria = new Scanner(System.in);
                    producteIO.llistarPerCategoriaClients(categoria.nextInt());
                    break;

                case 5: vullAcabarExecucio = true;
                    break;

            }
        }
    }

    public static void displayMenuPrincipalClients(){
        System.out.println("\nCom vols llistar els productes?\n");
        System.out.println("1- Cap criteri d'ordenacio");
        System.out.println("2- Preu (ascendent)");
        System.out.println("3- Preu (descencent)");
        System.out.println("4- Per categoria");
        System.out.println("5- Acabar execucio");

    }
}
