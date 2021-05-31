import java.io.IOException;
import java.util.Scanner;

public class MainMenu {

    public static void menu() throws IOException {

        displayElegirRollUsuari();
        elegirRollUsuari();
    }

    public static void displayElegirRollUsuari(){
        System.out.println("Qui ets?\n");
        System.out.println("1-Administrador");
        System.out.println("2-Client");
    }

    /**Per elegir el rol, l'usuari ha de entroduit un numero (1 = admin) (2 = client)
     * Si el numero que ha introduit no es valid, el metode retornara un 0
     * Si el numero es valid, el metode retornara el numero que ha introduit l'usuari*/
    public static void elegirRollUsuari() throws IOException{

        System.out.print("\nTipus d'usuari: ");
        Scanner elegirRoll = new Scanner(System.in);
        switch (elegirRoll.nextInt()){
            case 1:
                MenuAdmin.menuAdmin();
                break;
            case 2:
                MenuClient.menuClient();
                break;
            default:
                System.out.println("\nIntrodueix un valor valid\n");
                elegirRollUsuari();
                break;
        }
    }
}
