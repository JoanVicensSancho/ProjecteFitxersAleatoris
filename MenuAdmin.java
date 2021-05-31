import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.Scanner;

public class MenuAdmin {

    public static void menuAdmin() throws IOException {
        ProducteIO producteIO = new ProducteIO("C:\\Users\\Joan\\IdeaProjects\\P3FitxersAccesAleatori\\ElMeuFitxer.txt", "rw");

        boolean vullAcabarExecucio = false;
        while (!vullAcabarExecucio) {

            displayDemanarQueFer();

            Scanner accio = new Scanner(System.in);
            switch (accio.nextInt()) {
                case 1:
                    producteIO.inserirProducte(inserirProducteMenu());
                    break;

                case 2:
                    producteIO.modificarEstaEliminat(eliminarProducteMenu(), true);
                    break;

                case 3:
                    displayMenuModificarDades();


                case 8: vullAcabarExecucio = true;
                break;

            }
        }
    }

    //Imprimir el menu principal
    public static void displayDemanarQueFer(){
        System.out.println("Accions possibles:\n");
        System.out.println("1- Inserir producte.");
        System.out.println("2- Eliminar producte.");
        System.out.println("3- Modificar producte.");
        System.out.println("4- Llistar productes.");
        System.out.println("5- Llistar productes preu ascendent.");
        System.out.println("6- Llistar productes preu descendent.");
        System.out.println("7- Llistar productes per categoria.");
        System.out.println("8- Sortir.");
        System.out.print("Que vols fer? ");
    }

    /**Cream un objecte nou, el modificam amb les dades que introdueix l'usuari i el retornam*/
    public static Producte inserirProducteMenu(){

        Producte producte = new Producte(0, "", "", 0, 0, 0, true, false);
        System.out.print("ID: ");
        Scanner id = new Scanner(System.in);
        producte.setId(id.nextInt());

        System.out.print("Nom: ");
        Scanner nom = new Scanner(System.in);
        producte.setNom(nom.next());

        System.out.print("Descripcio: ");
        Scanner descripcio = new Scanner(System.in);
        producte.setDescripcio(descripcio.next());

        System.out.print("Preu: ");
        Scanner preu = new Scanner(System.in);
        producte.setPreu(preu.nextDouble());

        System.out.print("Stock: ");
        Scanner stock = new Scanner(System.in);
        producte.setStock(stock.nextInt());

        System.out.print("Categoria: ");
        Scanner categoria = new Scanner(System.in);
        producte.setCategoria(categoria.nextInt());

        return producte;
    }

    /**L'usuari intordueix l'id del producte que vol eliminar i el retornam per poder eliminar-lo amb el metode (modificarEstaEliminat())*/
    public static int eliminarProducteMenu(){
        System.out.print("ID del producte que vols eliminar: ");
        Scanner idEliminarProducte = new Scanner(System.in);
        return idEliminarProducte.nextInt();
    }

    /**Imprimir menu modificar dades*/

    public static void displayMenuModificarDades(){
        System.out.println("Que vols modificar?\n");
        System.out.println("1- Canviar ID");
        System.out.println("2- Canviar nom");
        System.out.println("3- Canviar descripcio");
        System.out.println("4- Canviar preu");
        System.out.println("5- Canviar stock");
        System.out.println("6- Canviar categoria");
        System.out.println("7- Canviar disponibilitat");
    }
    public static void menuModificarDades()throws IOException{

        ProducteIO producteIO = new ProducteIO("C:\\Users\\Joan\\IdeaProjects\\P3FitxersAccesAleatori\\ElMeuFitxer.txt", "rw");

        Scanner eleccio = new Scanner(System.in);
        System.out.print("Accio: ");
        switch (eleccio.nextInt()){
            case 1:
                System.out.print("ID actual del producte: ");
                Scanner idAntic = new Scanner(System.in);
                System.out.print("ID nou del producte: ");
                Scanner idNou = new Scanner(System.in);
                producteIO.modificarID(idAntic.nextInt(), idNou.nextInt());
                break;

            case 2:
                System.out.print("ID del producte: ");
                Scanner idProducte = new Scanner(System.in);
                System.out.print("Nom nou: ");
                Scanner nomNou = new Scanner(System.in);
                producteIO.modificarNom(idProducte.nextInt(), nomNou.next());
                break;

            case 3:
        }

    }
}
