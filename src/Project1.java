import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class Project1 {
	public static void main(String[] args)  {
		Scanner myReader;
		FactoryImpl line = new FactoryImpl(); 
		
		PrintStream exitFile = null;
		try {
			exitFile = new PrintStream(args[1]);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.setOut(exitFile);


		try {
			File myObj = new File(args[0]);
			myReader = new Scanner(myObj);
		}
		catch(Exception e ){
			System.out.println(e);
			myReader = null;
			
		}
			while(myReader.hasNextLine()) {
				String data = myReader.nextLine();
				//System.out.println();
				String[] parts = data.split(" ");     
		        String name = parts[0];
		        int x;
		        int y;
		        int z;
		        int fd;
		        Product temp;
		        Product prod;
		        try {
		        	
		        switch(name) {
		        	case("AF"):
		        		x = Integer.parseInt(parts[1]);
	        			y = Integer.parseInt(parts[2]);
	        			temp =  new Product(x,y);
	        			line.addFirst(temp);
	        			break;
		        	case("AL"):
		        		x = Integer.parseInt(parts[1]);
        				y = Integer.parseInt(parts[2]);
        				temp = new Product(x,y);
        				line.addLast(temp);
        				break;
		        	case("A"):
		        		x = Integer.parseInt(parts[1]);
		        		y = Integer.parseInt(parts[2]);
		        		z = Integer.parseInt(parts[3]);
		        		temp = new Product(y,z);
		        		line.add(x, temp);
		        		break;
		        	case("RF"):
		        		prod = line.removeFirst();
		        		System.out.println(prod);
		        		break;
		        	case("RL"):
		        		prod = line.removeLast();
		        		System.out.println(prod);
		        		break;
		        	case("RI"):
		        		x = Integer.parseInt(parts[1]);
		        		prod = line.removeIndex(x);
		        		System.out.println(prod);
		        		break;
		        	case("RP"):
		        		x = Integer.parseInt(parts[1]);
		        		prod = line.removeProduct(x);
		        		System.out.println(prod);
		        		break;
		        	case("F"):
		        		x = Integer.parseInt(parts[1]);
		        		prod = line.find(x);
		        		System.out.println(prod);
		        		break;
		        	case("G"):
		        		x = Integer.parseInt(parts[1]);
		        		prod = line.get(x);
		        		System.out.println(prod);
		        		break;
		        	case("U"):
		        		x = Integer.parseInt(parts[1]);
		        		y = Integer.parseInt(parts[2]);
		        		temp = new Product(x,y);
		        		prod = line.update(x,y);
		        		System.out.println(prod);
		        		break;
		        	case("FD"):
		        		fd = line.filterDuplicates();
	        			System.out.println(fd);
		        		break;
		        	case("R"):
		        		line.reverse();
		        		line.print();
		        		break;
		        	case("P"):
		        		line.print();
		        		break;

		        }
		        }catch (Exception e) {
					System.out.println(e.getMessage());
				}   
		        
			}
			myReader.close();
		} 
		
		
		
		
	}
