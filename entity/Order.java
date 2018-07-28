package entity;

import java.util.Date;

public class Order {
	public Date date;
	public float amount;
	@Override
	public String toString() {
		return "Order [date=" + date + ", amount=" + amount + "]";
	}
	
	

}
