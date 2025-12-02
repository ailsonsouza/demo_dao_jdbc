package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		List<Department> departments = new ArrayList<>();
		
		
		System.out.println("--- TEST 1: Insert Department");
		Department department = new Department(null, "GRCP");
		departmentDao.insert(department);
		System.out.println("Department inserted sucessful");
		
		System.out.println("--- TEST 2: Update Department");
		department = new Department(6, "GRCP INICIAL");
		departmentDao.update(department);
		System.out.println("Department updated sucessful");
		
		System.out.println("=== TEST 3: Department findById ===");
		department = departmentDao.findById(2);
		System.out.println(department);
		
		System.out.println("=== TEST 4: department delete ===");
		//departmentDao.deleteById(7);
		System.out.println("delete completed!");
		
		System.out.println("=== TEST 6: department findAll ===");
		departments = departmentDao.findAll();
		departments.forEach(System.out::println);

	}

}
