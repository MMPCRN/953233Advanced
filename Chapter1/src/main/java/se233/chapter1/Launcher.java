package se233.chapter1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import se233.chapter1.controller.GenCharacter;
import se233.chapter1.controller.GenItemList;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;
import se233.chapter1.view.CharacterPane;
import se233.chapter1.view.EquipPane;
import se233.chapter1.view.InventoryPane;

import java.lang.reflect.Array;
import java.util.ArrayList;

//1.25
public class Launcher extends Application {
    private static Scene mainScene;
    private static BasedCharacter mainCharacter = null;
//1.33
// private static BasedEquipment[] allEquipments = null;
    private static ArrayList<BasedEquipment> allEquipments = null;  //1.33

    private static Weapon equippedWeapon = null;
    private static Armor equippedArmor = null;
    private static CharacterPane characterPane = null;
    private static EquipPane equipPane = null;
    private static InventoryPane inventoryPane = null;

    public static void setEquippedWeapon(Weapon retrievedEquipment){
        equippedWeapon = retrievedEquipment;
    }

    public static void setEquippedArmor(Armor retrievedArmor){
        equippedArmor = retrievedArmor;
    }

    public static void setAllEquipments(ArrayList<BasedEquipment> allEquipments){
        Launcher.allEquipments = allEquipments;
    }

    public static ArrayList<BasedEquipment> getAllEquipments(){
        return allEquipments;
    }

    public static BasedEquipment getEquippedArmor(){
        return equippedArmor;
    }

    public static BasedEquipment getEquippedWeapon(){
        return equippedWeapon;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Intro to RPG");
        primaryStage.setResizable(true);
        primaryStage.show();
//1.29
        mainCharacter = GenCharacter.setUpCharacter();
        allEquipments = GenItemList.setUpItemList();

        Pane mainPane = getMainPane();
        mainScene = new Scene(mainPane);
        primaryStage.setScene(mainScene);
    }

    public Pane getMainPane() {
        BorderPane mainPane = new BorderPane();
        characterPane = new CharacterPane();
        equipPane = new EquipPane();
        inventoryPane = new InventoryPane();
        refreshPane();
        mainPane.setCenter(characterPane);
        mainPane.setLeft(equipPane);
        mainPane.setBottom(inventoryPane);
        return mainPane;
    }

    public static void refreshPane() {
        characterPane.drawPane(mainCharacter);
        equipPane.drawPane(equippedWeapon,equippedArmor);
        inventoryPane.drawPane(allEquipments);
    }
    public static BasedCharacter getMainCharacter() {
        return mainCharacter;
    }
    public static void setMainCharacter(BasedCharacter mainCharacter) {
        Launcher.mainCharacter = mainCharacter;
    }
    public static void main(String[] args) {
        launch(args);
    }


}
