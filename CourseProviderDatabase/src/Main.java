import Controller.AssignmentController;
import Controller.CourseUserController;
import Exceptions.BusinessException;
import Exceptions.EntityNotFoundException;
import Exceptions.ValidationException;
import Models.Admin;
import Models.Assignment;
import Repository.FileRepository;
import Ui.Ui;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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