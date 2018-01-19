/*
    Łukasz Lepak, 277324
    AAL 17Z, projekt
    Tytuł projektu: Generacja spirali ze zbioru punktów
    prowadzący: dr inż. Tomasz Gambin
 */
package AAL;

import exceptions.WrongArgumentException;
import javafx.application.Application;
import javafx.stage.Stage;
import utilities.ArgumentParser;
import view.View;

import java.util.InputMismatchException;

public class AAL extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        View.setStage(primaryStage);
        ArgumentParser ap = new ArgumentParser(getParameters().getRaw());
        try {
            ap.parseArgs();
            if (!View.getStage().isShowing())
                System.exit(0);
        }
        catch (WrongArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println();
            ap.printHelp();
            System.exit(-1);
        }
        catch (NumberFormatException e) {
            System.out.println("Could not parse an integer from one of the arguments!");
            System.out.println();
            ap.printHelp();
            System.exit(-2);
        }
        catch (InputMismatchException e) {
            System.out.println("Error while reading data!");
            System.exit(-3);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
