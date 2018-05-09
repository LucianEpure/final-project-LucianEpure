package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Role(){

    }
    public Role(String name){
        this.roleName = name;
    }
    @Column(unique = true)
    private String roleName;

    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }



}