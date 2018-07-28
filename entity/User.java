package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
	//Constructor to allocate orderList
	public User() {
		this.orderList = new ArrayList();
	}
	// Attributes of User 
	public String name;
	public String mobileNo;
	public List<Order> orderList;
	@Override
	public String toString() {
		return "User [name=" + name + ", mobileNo=" + mobileNo + "]";
	}

}
