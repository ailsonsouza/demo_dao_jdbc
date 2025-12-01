package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao(); 
		
		System.out.println("=== TEST 1: seller findById ===");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println();
		
		System.out.println("=== TEST 2: seller findByDepartment ===");
		List<Seller> sellers = sellerDao.findByDepartment(4);
		sellers.forEach(System.out::println);
		
		System.out.println("=== TEST 3: seller findAll ===");
		sellers = sellerDao.findAll();
		sellers.forEach(System.out::println);

	}

}
