import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuAdmin {

    public static void menuAdmin() throws IOException {
        ProducteIO producteIO = new ProducteIO("C:\\Users\\Joan\\IdeaProjects\\P3FitxersAccesAleatori\\ElMeuFitxer.txt", "rw");

        boolean vullAcabarExecucio = false;
        while (!vullAcabarExecucio) {

            displayMenuPrincipal();

            Scanner accio = new Scanner(System.in);
            switch (accio.nextInt()) {
                case 1:
                    producteIO.inserirProducte(menuInserirProducte());
                    break;

                case 2:
                    producteIO.modificarEstaEliminat(menuEliminarProducte(), true);
                    break;

                case 3:
                    displayMenuModificarDades();
                    menuModificarDades();
                    break;

                case 4:
                    displayMenuLlistarProductes();
                    menuLlistarProductes();
                    break;

                case 5: vullAcabarExecucio = true;
                break;

                default:
                    System.out.println("\nIntrodueix un valor valid");
                    break;

            }
        }
    }

    //Imprimir el menu principal
    public static void displayMenuPrincipal(){
        System.out.println("\nAccions possibles:\n");
        System.out.println("1- Inserir producte.");
        System.out.println("2- Eliminar producte.");
        System.out.println("3- Modificar producte.");
        System.out.println("4- Llistar productes.");
        System.out.println("5- Sortir.");
        System.out.print("\nQue vols fer? ");
    }

    /**Cream un objecte nou, el modificam amb les dades que introdueix l'usuari i el retornam*/
    public static Producte menuInserirProducte(){

        Producte producte = new Producte(0, "", "", 0, 0, 0, true, false);

        try {
            System.out.print("ID: ");
            Scanner id = new Scanner(System.in);
            producte.setId(id.nextInt());
        }
        catch (InputMismatchException noEsInteger){
            System.out.println("\nLa ID ha de ser un numero enter\n");
            System.out.print("ID: ");
            Scanner id = new Scanner(System.in);
            producte.setId(id.nextInt());
        }

        System.out.print("Nom: ");
        Scanner nom = new Scanner(System.in);
        producte.setNom(nom.nextLine());

        System.out.print("Descripcio: ");
        Scanner descripcio = new Scanner(System.in);
        producte.setDescripcio(descripcio.nextLine());

        try {
            System.out.print("Preu: ");
            Scanner preu = new Scanner(System.in);
            producte.setPreu(preu.nextDouble());
        }
        catch (InputMismatchException noEsDouble) {
            System.out.println("\nEls double s'escriuen amb una coma (,)\n");
            System.out.print("Preu: ");
            Scanner preu = new Scanner(System.in);
            producte.setPreu(preu.nextDouble());
        }

        try {
            System.out.print("Stock: ");
            Scanner stock = new Scanner(System.in);
            producte.setStock(stock.nextInt());
        }
        catch (InputMismatchException noEsInteger){
            System.out.println("\nL'stock ha de ser un numero enter\n");
            System.out.print("Stock: ");
            Scanner stock = new Scanner(System.in);
            producte.setStock(stock.nextInt());
        }

        try {
            System.out.print("Categoria: ");
            Scanner teclatCategoria = new Scanner(System.in);
            int categoria = teclatCategoria.nextInt();
            if (categoria > 4){
                categoria = 4;
            }
            else if (categoria < 1) {
                categoria = 1;
            }
            producte.setCategoria(categoria);
        }
        catch (InputMismatchException noEsInteger){
            System.out.println("\nLa categoria ha de ser un numero enter\n");
            System.out.print("Categoria: ");
            Scanner categoria = new Scanner(System.in);
            producte.setCategoria(categoria.nextInt());
        }

        return producte;
    }

    /**L'usuari intordueix l'id del producte que vol eliminar i el retornam per poder eliminar-lo amb el metode (modificarEstaEliminat())*/
    public static int menuEliminarProducte(){
        System.out.print("\nID del producte que vols eliminar: ");
        Scanner idEliminarProducte = new Scanner(System.in);
        return idEliminarProducte.nextInt();
    }

    /**Imprimir menu modificar dades*/
    public static void displayMenuModificarDades(){
        System.out.println("\nQue vols modificar?\n");
        System.out.println("1- Canviar ID");
        System.out.println("2- Canviar nom");
        System.out.println("3- Canviar descripcio");
        System.out.println("4- Canviar preu");
        System.out.println("5- Incrementar stock");
        System.out.println("6- Disminuir stock");
        System.out.println("7- Canviar categoria");
        System.out.println("8- Canviar disponibilitat");
    }

    /**Modificar dades*/
    public static void menuModificarDades()throws IOException{

        ProducteIO producteIO = new ProducteIO("C:\\Users\\Joan\\IdeaProjects\\P3FitxersAccesAleatori\\ElMeuFitxer.txt", "rw");

        Scanner eleccio = new Scanner(System.in);
        System.out.print("\nEleccio: ");
        switch (eleccio.nextInt()){
            case 1:
                try {
                    System.out.print("\nID actual del producte: ");
                    Scanner teclatIdAntic = new Scanner(System.in);
                    int idAntic = teclatIdAntic.nextInt();

                    System.out.print("\nID nou del producte: ");
                    Scanner teclatIdNou = new Scanner(System.in);
                    int idNou = teclatIdNou.nextInt();

                    producteIO.modificarID(idAntic, idNou);
                }
                catch (InputMismatchException noEsInteger){
                    System.out.println("\nHas de introduir un numero enter.");
                }
                break;

            case 2:
                try {
                    System.out.print("\nID del producte: ");
                    Scanner teclatIdNom = new Scanner(System.in);
                    int idNom = teclatIdNom.nextInt();

                    System.out.print("\nNom nou: ");
                    Scanner teclatNomNou = new Scanner(System.in);
                    String nomNou = teclatNomNou.nextLine();

                    producteIO.modificarNom(idNom, producteIO.llargariaStringAjustada(nomNou, Constants.LLARGARIA_MAX_NOM));
                }
                catch (InputMismatchException noEsInteger){
                    System.out.println("\nHas de introduir un numero enter.");
                }
                break;

            case 3:
                try {
                    System.out.print("\nID del producte: ");
                    Scanner teclatIdDescripcio = new Scanner(System.in);
                    int idDescripcio = teclatIdDescripcio.nextInt();

                    System.out.print("\nNova descripcio: ");
                    Scanner teclatDescripcioNova = new Scanner(System.in);
                    String descripcioNova = teclatDescripcioNova.nextLine();

                    producteIO.modificarDescripcio(idDescripcio, producteIO.llargariaStringAjustada(descripcioNova, Constants.LLARGARIA_MAX_DESCRIPCIO));
                }
                catch (InputMismatchException noEsInteger){
                    System.out.println("\nHas de introduir un numero enter.");
                }
                break;

            case 4:
                try {
                    System.out.print("\nID del producte: ");
                    Scanner teclatIdPreu = new Scanner(System.in);
                    int idPreu = teclatIdPreu.nextInt();

                    System.out.print("\nNou preu ");
                    Scanner teclatPreuNou = new Scanner(System.in);
                    double preuNou = teclatPreuNou.nextDouble();

                    producteIO.modificarPreu(idPreu, preuNou);
                }
                catch (InputMismatchException noEsInteger){
                    System.out.println("\nHas de introduir un numero enter.");
                }
                break;

            case 5:
                try {
                    System.out.print("\nID del producte: ");
                    Scanner teclatIdIncrementarStock = new Scanner(System.in);
                    int idIncrementarStock = teclatIdIncrementarStock.nextInt();

                    System.out.print("\nNumero d'unitats: ");
                    Scanner teclatStockSumat = new Scanner(System.in);
                    int stockSumat = teclatStockSumat.nextInt();

                    producteIO.incrementarStock(idIncrementarStock, stockSumat);
                }
                catch (InputMismatchException noEsInteger){
                    System.out.println("\nHas de introduir un numero enter.");
                }
                break;

            case 6:
                try {
                    System.out.print("\nID del producte: ");
                    Scanner teclatIdDisminuirStock = new Scanner(System.in);
                    int idDisminuirStock = teclatIdDisminuirStock.nextInt();

                    System.out.print("\nNumero d'unitats: ");
                    Scanner teclatStockRestat = new Scanner(System.in);
                    int stockRestat = teclatStockRestat.nextInt();

                    producteIO.disminuirStock(idDisminuirStock, stockRestat);
                }
                catch (InputMismatchException noEsInteger){
                    System.out.println("\nHas de introduir un numero enter.");
                }
                break;

            case 7:
                try {
                    System.out.print("\nID del producte: ");
                    Scanner teclatIdCategoria = new Scanner(System.in);
                    int idCategoria = teclatIdCategoria.nextInt();

                    System.out.print("\nNova categoria: ");
                    Scanner teclatNovaCategoria = new Scanner(System.in);
                    int novaCategoria = teclatNovaCategoria.nextInt();
                    if (novaCategoria > 4){
                        novaCategoria = 4;
                    }
                    else if (novaCategoria < 1) {
                        novaCategoria = 1;
                    }

                    producteIO.modificarCategoria(idCategoria, novaCategoria);
                }
                catch (InputMismatchException noEsInteger){
                    System.out.println("\nHas de introduir un numero enter.");
                }
                break;

            case 8:
                try {
                    System.out.print("\nID del producte: ");
                    Scanner teclatIdDisponibilitat = new Scanner(System.in);
                    int idDisponibilitat = teclatIdDisponibilitat.nextInt();

                    producteIO.modificarDisponibilitat(idDisponibilitat);
                }
                catch (InputMismatchException noEsInteger){
                    System.out.println("\nHas de introduir un numero enter.");
                }
                break;

            default:
                System.out.println("\nHas de introduir un valor valid.");
                menuModificarDades();
        }

    }

    /**Imprimir menu llistar dades*/
    public static void displayMenuLlistarProductes(){
        System.out.println("\nCom vols llistar els productes?\n");
        System.out.println("1- Cap criteri d'ordenacio");
        System.out.println("2- Preu (ascendent)");
        System.out.println("3- Preu (descencent)");
        System.out.println("4- Per categoria");
    }

    /**Llistar productes*/
    public static void menuLlistarProductes() throws IOException {

        ProducteIO producteIO = new ProducteIO("C:\\Users\\Joan\\IdeaProjects\\P3FitxersAccesAleatori\\ElMeuFitxer.txt", "rw");

        Scanner eleccio = new Scanner(System.in);
        System.out.print("\nEleccio: ");
        switch (eleccio.nextInt()){
            case 1:
                producteIO.llistarFitxerAdmins();
                break;

            case 2:
                producteIO.llistarProductesSegonsPreuAdmins(producteIO.ordenarIndexosPreuAscendent());
                break;

            case 3:
                producteIO.llistarProductesSegonsPreuAdmins(producteIO.ordenarIndexosPreuDescendent());
                break;

            case 4:
                System.out.print("\nCategoria de productes que vols llistar: ");
                Scanner categoria = new Scanner(System.in);
                producteIO.llistarPerCategoriaAdmins(categoria.nextInt());
                break;

            default:
                System.out.println("\nIntrodueix un valor valid");
                menuLlistarProductes();
                break;
        }
    }

}
