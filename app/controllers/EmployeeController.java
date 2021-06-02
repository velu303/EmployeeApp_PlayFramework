package controllers;

import controllers.Auth.Secured;
import models.Employee;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.employee.create;

import views.html.errors._404;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class EmployeeController extends Controller{

    @Inject
    FormFactory formFactory;


    public Result index(){
        List<Employee> employees = Employee.find.all();

        if(Secured.CHECK()){
            return ok(views.html.employee.index.render(employees,""));
        }else{
            return redirect(routes.AuthController.login());
        }

    }


    @Security.Authenticated(Secured.class)
    public Result save() {

        Form<Employee> employeeForm = formFactory.form(Employee.class).bindFromRequest();

        if(employeeForm.hasErrors()){
            flash("danger","Input validation failed.");
            return badRequest(views.html.employee.create.render(employeeForm));
        }

        Map<String,String> input = employeeForm.rawData();
        String employeeName = input.get("employeeName");
        String employeeRole = input.get("employeeRole");
        Employee employee = employeeForm.get();

        employee.employeeName=employeeName;
        employee.employeeRole=employeeRole;
        employee.save();

        flash("success","Book Save Successfully");

        return redirect(routes.EmployeeController.index());
    }


    @Security.Authenticated(Secured.class)
    public Result create(){
        Form<Employee> bookForm = formFactory.form(Employee.class);
        return ok(create.render(bookForm));
    }


    @Security.Authenticated(Secured.class)
    public Result edit(Integer id){

        Employee employee = Employee.find.byId(id);
        if(employee==null){
            return notFound(_404.render());
        }

        Form<Employee> employeeForm = formFactory.form(Employee.class).fill(employee);
        return ok(views.html.employee.edit.render(employeeForm));
    }


    @Security.Authenticated(Secured.class)
    public Result update(Integer id){

        Form<Employee> employeeForm = formFactory.form(Employee.class).bindFromRequest();

        if (employeeForm.hasErrors()){

            flash("danger","Input Validation failed.");
            return badRequest(views.html.employee.edit.render(employeeForm));
        }

        Employee employee = employeeForm.get();
        Employee oldEmployee = Employee.find.byId(id);

        if(oldEmployee == null){
            flash("danger","Book Not Found");
            return redirect(routes.EmployeeController.edit(id));
        }
        Map<String,String> input = employeeForm.rawData();
        oldEmployee.employeeName = employee.employeeName;
        oldEmployee.employeeRole=employee.employeeRole;

        oldEmployee.update();

        flash("success","Employee Updated Successfully.");

        return redirect(routes.EmployeeController.index());
    }

    public Result destroy(Integer id){

        Employee employee = Employee.find.byId(id);
        if(employee == null){
            flash("danger","Employee Not Found");
            return notFound();
        }
        employee.delete();
        flash("success","Employee Deleted Successfully");

        return ok();
    }
}
