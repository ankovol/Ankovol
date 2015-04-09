package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;


public class Controller {
    private Main main;
    private ArrayList<Man> mans;
    private int number;
    private String path = "phantom";
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private Label numberLabel;

    public Controller () {
        number = 0;
        mans = new ArrayList<Man>();
        mans.add(new Man("Вася",15));
        mans.add(new Man("Артём",20));
        mans.add(new Man("Саша", 12));
        mans.add(new Man("Роман", 24));
        mans.add(new Man("Василий", 42));
    }

    @FXML
    private  void initialize() {
        String name = mans.get(0).getName();
        nameTextField.setText(name);
        ageTextField.setText(Integer.toString(mans.get(0).getAge()));
        numberLabel.setText(Integer.toString(number));
    }
    @FXML
    private void handleCreateButton (){
        String name = nameTextField.getText();
        int age = Integer.parseInt(ageTextField.getText());
        Man man = new Man(name,age);
        mans.add(man);
        number = mans.size()-1;
        showMan(number);
    }
    private void showMan (int number){
        Man man = mans.get(number);
        String name = man.getName();
        nameTextField.setText(name);
        int age = man.getAge();
        ageTextField.setText(Integer.toString(age));
        numberLabel.setText(Integer.toString(number));
    }
    @FXML
    private void handlePrevButton (){
        if (number > 0){
            number = number - 1;
            showMan(number);
        }
    }
    @FXML
    private void handleNextButton (){
        if (number < mans.size()-1){
            number = number + 1;
            showMan(number);
        }
    }
    @FXML
    private void handleMinButton (){
        int minAge = mans.get(0).getAge();
        int number = 0;
        for (int i = 1; i < mans.size(); i++){
            if (mans.get(i).getAge() < minAge){  //mans[i] ~ mans.get(i)
                minAge = mans.get(i).getAge();
                number = i;
            }
        }
        this.number = number;
        showMan(number);
        System.out.println();
    }

    @FXML
    private void handleSaveButton (){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Files with data", "*.data"
        );
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file.getAbsolutePath());
        }catch (FileNotFoundException ex){
            System.out.println("File not found");
        }
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(fos);
            oos.writeObject(mans);
            oos.close();
        } catch (IOException ex) {
            System.out.println("Error");
        }
        try {
            fos.close();
        }catch (IOException ex) {
            System.out.println("Error");
        }
    }

    @FXML
    private void handleLoadButton(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Files with data", "*.data"
        );
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        Man man = Man.load(file.getAbsolutePath());
        mans.set(number, man);
        showMan(number);

        //TODO
        //Изменить этот метод так что-бы он загружал не одного текущего человека из файла,
        // а весь списочный массив mans
    }

    public void setMain(Main main){
        this.main = main;
    }
}