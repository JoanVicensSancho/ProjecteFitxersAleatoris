import java.io.IOException;
import java.util.Scanner;

public class MainMenu {

    public boolean vullAcabarExecucio;


    //CONSTRUCTOR
    public MainMenu(boolean vullAcabarExecucio) throws IOException {
        this.vullAcabarExecucio = vullAcabarExecucio;
    }

    MenuAdmin menuAdmin = new MenuAdmin(1);
    MenuClient menuClient = new MenuClient(2);

    public void menu() throws IOException {

        //ProducteIO producteIO = new ProducteIO("C:\\Users\\Joan\\IdeaProjects\\P3FitxersAccesAleatori\\ElMeuFitxer.txt", "rw");

        displayElegirRollUsuari();
        elegirRollUsuari();
    }


    /**Per elegir el rol, l'usuari ha de entroduit un numero (1 = admin) (2 = client)
     * Si el numero que ha introduit no es valid, el metode retornara un 0
     * Si el numero es valid, el metode retornara el numero que ha introduit l'usuari*/
    public void elegirRollUsuari() throws IOException{
        Scanner elegirRoll = new Scanner(System.in);
        switch (elegirRoll.nextInt()){
            case 1:
                menuAdmin.menuAdmin();
                break;
            case 2:
                menuClient.menuClient();
                break;
            default:
                System.out.println("Introdueix un valor valid\n");
                elegirRollUsuari();
                break;
        }
    }

    public void displayElegirRollUsuari(){
        System.out.println("Qui ets?\n");
        System.out.println("1-Administrador");
        System.out.println("2-Client");
    }

}