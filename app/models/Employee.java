package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "employees")
public class Employee extends Model {

    @Id
    @GeneratedValue
    public Integer id;

    @Constraints.Required
    public String employeeName;

    @Constraints.Required
    public String employeeRole;

    public static Finder<Integer, Employee> find = new Finder<>(Employee.class);

}
