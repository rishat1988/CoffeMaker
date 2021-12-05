package models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class UserModel {

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "name")
    @NotNull(message = "name must be filled in")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birth")
    private Date date_of_birth;

    @Column(name = "password")
    @NotBlank(message = "password can not be empty")
    @Size(min = 6, max = 10, message = "password can not be less then 6" +
            " and nore then 10 characters")
    private String password;

    @Transient
    @NotBlank(message = "password can not be empty")
    @Size(min = 6, max = 10, message = "password can not be less then 6" +
            " and nore then 10 characters")
    private String confirmPassword;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName="userId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName="roleId"))
    private Set<RoleModel> roles;

    @OneToMany(mappedBy = "user")
    private List<TicketModel> tickets;
}
