package models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table (name = "role")
public class RoleModel {

    @Id
    @Column (name = "roleId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(name = "name")
    private String name;

    @ManyToMany (mappedBy = "roles")
    private Set<UserModel> users;

}
