package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.iD	WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, department);
				return seller;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			
		}
		
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Seller> findByDepartment(int departmentId) {
		List<Seller> sellers = new ArrayList<Seller>();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select seller.*, department.Name as DepName from seller inner join department "
										+ "on seller.DepartmentId = department.Id where DepartmentId = ? order by Name");
			st.setInt(1, departmentId);
			
			rs = st.executeQuery();
			Department department2 = null;
			
			while (rs.next()) {
				if (department2 == null) {
					department2 = instantiateDepartment(rs);					
				}
				sellers.add(instantiateSeller(rs, department2));	
			}

			
			return sellers;
			
			
		}catch(SQLException e) {
			throw new DbException (e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			
		}
		
	}
	

	private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		return new Seller(rs.getInt("Id"),
				rs.getString("Name"),
				rs.getString("Email"),
				rs.getDate("BirthDate"),
				rs.getDouble("BaseSalary"),
				department);
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
		
	}



	

}
