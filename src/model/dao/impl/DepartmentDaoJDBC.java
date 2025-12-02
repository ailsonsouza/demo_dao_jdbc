package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("insert into department (Name) values (?)", PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected >0) {
				ResultSet rs = st.getGeneratedKeys();
				
				while(rs.next()) {
					int Id = rs.getInt(1);
					obj.setId(Id);
				}
				
				DB.closeResultSet(rs);
			}else {
				throw new DbException("No rows affected!");
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department obj) {
		
		try(PreparedStatement st = conn.prepareStatement("update Department set Name = ? where Id=?")) {
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			st.executeUpdate();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("delete from Department where id=?");
			st.setInt(1, id);
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DbException("No rows deleted!");
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		ResultSet rs = null;
		try(PreparedStatement st = conn.prepareStatement("select * from Department where Id=?")){
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department departmentReturned = new Department(rs.getInt(1), rs.getString(2));
				return departmentReturned;
			}else {
				return null;
			}
		}catch(SQLException e) {
			throw new DbException (e.getMessage());
		}finally {
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Department> departments = new ArrayList<Department>();
		
		try {
			st = conn.prepareStatement("select * from Department");
			rs = st.executeQuery();
			
			while(rs.next()) {
				Department department = new Department(rs.getInt(1), rs.getString(2));
				departments.add(department);
			}
			return departments;
		}catch(SQLException e) {
			throw new DbException (e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

}
