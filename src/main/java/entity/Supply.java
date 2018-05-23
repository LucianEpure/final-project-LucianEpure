package entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static application.Constants.INITIAL_AMMUNITION;
import static application.Constants.INITIAL_EQUIPMENT;
import static application.Constants.INITIAL_FOOD;

@Entity
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int ammunition;
    private int food;
    private int equipment;

    public Supply(){
        this.ammunition = INITIAL_AMMUNITION;
        this.food = INITIAL_FOOD;
        this.equipment = INITIAL_EQUIPMENT;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getEquipment() {
        return equipment;
    }

    public void setEquipment(int equipment) {
        this.equipment = equipment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
