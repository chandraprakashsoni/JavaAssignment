import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import entity.Order;
import entity.User;

public class CSVFile {


	public static void main(String[] args) throws ParseException {
		String fileName = null;
		if(args.length == 0) {
			System.out.println("Please enter the file name as argument");
			System.exit(0);
		}
		else
			fileName = args[0];
		String eachLine = null;
		List<User> userList = new ArrayList<User>();
		List <String> userOnce = new ArrayList<String>();
		List <String> userTwice = new ArrayList<String>();
		List <String> userThree = new ArrayList<String>();
		List <String> userFour = new ArrayList<String>();
		List <String> userFiveabove = new ArrayList<String>();
		int totalOrders = 0;
		float totalAmount = 0;
		
		try {
			FileReader fileReader = new FileReader(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			
			while((eachLine = bufferedReader.readLine()) != null) {
				String[] splittedArray = eachLine.split(",");
				
				//Skip first and blank rows
				if(splittedArray[0].equals("Date") || splittedArray[0].equals("") || splittedArray[0] == null)
					continue;
				
				//Create Order Object
				Order order = new Order();
				order.amount = Float.valueOf(splittedArray[3]);
				order.date = new SimpleDateFormat("yyyy-MM-dd").parse(splittedArray[0]);
				totalAmount += order.amount;
				totalOrders++;
				
				//Find i fuser already there in userlist
				User user = null;
				for(int i = 0; i < userList.size(); i++) {
					if (userList.get(i).mobileNo.equals(splittedArray[1]) ) {
						user = userList.get(i);
						break;
					}
				}
				
				//if no user found then create new user
				if(user == null) {
					user = new User();
					user.name = splittedArray[2];
					user.mobileNo = splittedArray[1];
				}
				
				//Add order to User
				user.orderList.add(order);
				userList.add(user);
			}
			System.out.println("Total Amout = " + totalAmount);
			System.out.println("Total Orders = " + totalOrders);
				
			for( int i =0; i < userList.size(); i ++ ) {
				User user = userList.get(i);
				if (user.orderList.size() == 1) {
					userOnce.add(user.name);
				} 
				else if (user.orderList.size() == 2) {
					userTwice.add(user.name);
				} 
				else if (user.orderList.size() == 3) {
					userThree.add(user.name);
				} 
				else if (user.orderList.size() == 4) {
					userFour.add(user.name);
				} 
				else {
					userFiveabove.add(user.name);
				}
			}
			
			System.out.println("Number of customers ordered exactly Once = " + userOnce.size());
			System.out.println("Number of customers ordered exactly Twice = " + userTwice.size());
			System.out.println("Number of customers ordered exactly 3 times = " + userThree.size());
			System.out.println("Number of customers ordered exactly 4 times = " + userFour.size());
			System.out.println("Number of customers ordered exactly 5 and more = " + userFiveabove.size());
			
			fileReader.close();
		}
		
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file");
		}
		
		catch(IOException ex) {
			System.out.println("Error reading file");
		}
		
		CSVFile object = new CSVFile();
		object.HTML(totalOrders, totalAmount, userOnce, userTwice, userThree, userFour, userFiveabove);

	}
	
	public void HTML(int totalOrders, float totalAmount, List<String> userOnce, List<String> userTwice, List<String> userThree, List<String> userFour, List<String> userFiveabove) {
		File file = new File("Report.html");
		
		if(file.exists()){
			System.out.println("File already exist");
		}
		else{
			FileWriter fileWriter = null;
			BufferedWriter bufferedWriter = null;
			String user1 = String.join("<br>", userOnce);
			try {
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			String htmlPage = "<html>\r\n" + 
					"	<head>\r\n" + 
					"		<title>\r\n" + 
					"			Report of Customer Orders\r\n" + 
					"		</title>\r\n" + 
					"	</head>\r\n" + 
					"	<body>\r\n" + 
					"		<h1> Report of Customer Orders </h1>\r\n" + 
					"		<h4> How many orders site receive = " +totalOrders+ "</h4>\r\n" + 
					"		<h4> Total amount of oders = " +totalAmount+ "</h4>\r\n" +
					"		<h4> List of names of the customers who ordered once only. </h4>\r\n" +user1+ 
					"		<h4> Distribution of customers who ordered exactly once, exactly twice and so on up to 4 orders and group the rest as 5 orders and above. </h4> \r\n" + 
					"		<table border = \"1\">\r\n" + 
					"			<tr>\r\n" + 
					"				<th> Number of Orders </th>\r\n" + 
					"				<th> Count of customers </th>\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td> 1 </td>\r\n" + 
					"				<td>"+userOnce.size()+"</td>\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td> 2 </td>\r\n" + 
					"				<td>"+userTwice.size()+"</td>\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td> 3 </td>\r\n" + 
					"				<td> "+userThree.size()+" </td>\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td> 4 </td>\r\n" + 
					"				<td> "+userFour.size()+"</td>\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td> 5 and more</td>\r\n" + 
					"				<td> "+userFiveabove.size()+"</td>\r\n" + 
					"			</tr>\r\n" + 
					"		</table>\r\n" + 
					"\r\n" + 
					"	</body>\r\n" + 
					"</html>" ;
			
			fileWriter.write(htmlPage);
			fileWriter.close();
			System.out.println("Report.html file created");
			}
			
					
			catch(IOException ex) {
				System.out.println("Error Writing File");
			}
		}

	}
}
