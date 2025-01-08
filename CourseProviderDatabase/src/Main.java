import Exceptions.BusinessException;
import Exceptions.EntityNotFoundException;
import Exceptions.ValidationException;
import Ui.Ui;


public class Main {
    public static void main(String[] args){


        Ui ui = new Ui();
        try {
            ui.start();
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }


    }
}