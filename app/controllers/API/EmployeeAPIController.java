package controllers.API;

import com.fasterxml.jackson.databind.JsonNode;
import models.Employee;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.HashMap;
import java.util.List;

public class EmployeeAPIController extends Controller {

    public Result index() {
        List<Employee> employees = Employee.find.all();
        return ok(Json.toJson(employees));
    }


    public Result save(){

        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();

        HashMap<String,Object> res = new HashMap<>();

        if(json == null){
            res.put("code","Bad Request");
            res.put("message","We Only Accept Json");
            return badRequest(Json.toJson(res));
        }

        Employee employee = Json.fromJson(json, Employee.class);
        employee.save();

        res.put("code","success");
        res.put("message","Employee Saved Successfully.");

        return ok(Json.toJson(res));
    }

    public Result update(Integer id){

        HashMap<String,Object> res = new HashMap<>();
        res.put("code","Bad Request");

        Http.RequestBody body = request().body();
        JsonNode json = body.asJson();

        if(json == null){
            res.put("message","We Only Accept Json");
            return badRequest(Json.toJson(res));
        }

        Employee employee = Employee.find.byId(id);

        if(employee == null){
            res.put("message","Employee does not exists");
            return badRequest(Json.toJson(res));
        }



        Employee newEmployee = Json.fromJson(json, Employee.class);

        employee.employeeName = newEmployee.employeeName;
        employee.employeeRole = newEmployee.employeeRole;

        employee.update();

        res.put("code","success");
        res.put("message","Employee Updated Successfully.");

        return ok(Json.toJson(res));
    }

    public Result destroy(Integer id){

        HashMap<String,Object> res = new HashMap<>();

        Employee employee = Employee.find.byId(id);

        if(employee == null){
            res.put("code","Bad Request");
            res.put("message","Employee does not exists");
            return badRequest(Json.toJson(res));
        }


        employee.delete();

        res.put("code","success");
        res.put("message","Employee deleted Successfully.");

        return ok(Json.toJson(res));
    }


}
