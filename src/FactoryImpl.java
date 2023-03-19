import java.util.HashSet;
import java.util.NoSuchElementException;


/*
 * @author Ahmet
 */



public class FactoryImpl implements Factory{
	private Holder first = null;
	private Holder last = null;
	private Integer size = 0;
	
	
	@Override
	public void addFirst(Product product) {
		
		Holder new_Holder = new Holder(null,product,null);
		if(size == 0) { //If line is empty
			first = new_Holder;
			last = new_Holder;
		}
		else {
			first.setPreviousHolder(new_Holder);
			new_Holder.setNextHolder(first);
		}
		first = new_Holder;
		size++;
	}
	
	
	@Override
	public void addLast(Product product) {
		
		Holder new_Holder = new Holder(null,product,null);		
		if(size == 0) { //If line is empty
			first = new_Holder;
			last = new_Holder;
			size++;
			return;
		}
		
		else {
			Holder curr = first;

			while(curr.getNextHolder()!= null) { //to find last element
				curr = curr.getNextHolder();
			}
			
			curr.setNextHolder(new_Holder);
			new_Holder.setPreviousHolder(curr);	
			last = new_Holder;
			size++;
		}
	}
	
	@Override
	public Product removeFirst() throws NoSuchElementException {
		
		if(size == 0) { //If line is empty
			throw new NoSuchElementException("Factory is empty.");
		}
		Product temp1;
		if(size == 1) {//change the first and last one to null
			temp1 = new Product(first.getProduct().getId(),first.getProduct().getValue());
			first = null;
			last = null;
			size--;
			return temp1;
		}
		temp1 = new Product(first.getProduct().getId(),first.getProduct().getValue());
		first = first.getNextHolder();
		first.setPreviousHolder(null);
		size--;
		return temp1;
	}
	
	@Override
	public Product removeLast() throws IndexOutOfBoundsException {
		Holder second_last = first;		
		if(size == 0 ) {//If line is empty
			throw new NoSuchElementException("Factory is empty.");
		}
		if(size == 1) { //if there is one element we should use remove first
			return removeFirst();
			
		}

		Product old_Product;
		
		while(second_last.getNextHolder().getNextHolder() != null) {
			second_last = second_last.getNextHolder();
		}
		//save the old product to return
		old_Product = new Product(second_last.getNextHolder().getProduct().getId(),second_last.getNextHolder().getProduct().getValue());
		second_last.setNextHolder(null);
		last = second_last;
		size--;
		return old_Product;
	}
	
	
	@Override
	public Product find(int id) throws IndexOutOfBoundsException {
		Holder currHolder = first;
		while(currHolder != null && currHolder.getProduct().getId()!= id) {
			currHolder = currHolder.getNextHolder();
		}
		
		if(currHolder == null) {
			throw new NoSuchElementException("Product not found.");
		}
		//if currHolder is not null,then we reach the correct one
		return currHolder.getProduct();

	}
	
	@Override
	public Product update(int id,Integer value) throws IndexOutOfBoundsException {
		
		if(size == 0 ) {//If line is empty
			throw new NoSuchElementException("Product not found.");
		}
		//save the old product to return
		Product old_Product = null;
		if(first.getProduct().getId() == id) {
			old_Product = new Product(first.getProduct().getId(), first.getProduct().getValue());
			first.getProduct().setValue(value);
			return old_Product;
		}
		Holder curr = first;
		
		while(curr != null ) {
			if(curr.getProduct().getId() == id) {
				old_Product = new Product(curr.getProduct().getId(), curr.getProduct().getValue());
				curr.getProduct().setValue(value);
				break;
			}
			curr = curr.getNextHolder();
		}
		
		
		if(old_Product == null) {
			
			throw new NoSuchElementException("Product not found.");
		}
		
		return old_Product;
		
	}
	
	@Override
	public Product get(int index) throws IndexOutOfBoundsException {
		//save the old product to return
		Product old_Product = null;
		if(index<0 || index >= size) {//If line is empty or index is larger than the size-1
			throw new NoSuchElementException("Index out of bounds.");
		}
		Holder currHolder1 = first;
		
		if(index == 0 && currHolder1 != null) {
			return first.getProduct();
		}
		if(index == size-1) {
			return last.getProduct();
		}
		int counter = 0;
		while(counter<size) {
			if(counter == index) {
				old_Product = currHolder1.getProduct();
				break;
			}
			else {
				currHolder1 = currHolder1.getNextHolder();
				counter++;
			}
			
		}
		return old_Product;
	}
	
	@Override
	public void add(int index, Product product) throws IndexOutOfBoundsException {
		if(index<0 || index > size) { //If line is empty or index is larger than the size
			throw new NoSuchElementException("Index out of bounds.");
		}
		
		Holder currHolder1 = first, prev = null;
		Holder new_Holder1 = new Holder(null,product,null);
		if(index == 0 && currHolder1 != null) {
			addFirst(product);
			return;
		}
		if(index == size ) {
			addLast(product);
			return;
		}
		int counter = 0;
		
		while(counter<size+1) {
			if(counter == index) {
				prev.setNextHolder(new_Holder1);
				new_Holder1.setPreviousHolder(prev);
				currHolder1.setPreviousHolder(new_Holder1);
				new_Holder1.setNextHolder(currHolder1);
				break;
			}
			else {
				prev = currHolder1;
				currHolder1 = currHolder1.getNextHolder();
				counter++;
			}
			
		}
		size++;
	}
	
	@Override
	public Product removeIndex(int index) throws IndexOutOfBoundsException {
		
		if(index<0 || index > size-1) { //If line is empty or index is larger than size-1
			throw new NoSuchElementException("Index out of bounds.");
		}
		Holder currHolder = first , prev = null;
		Product temp = null;
		if(index == 0) {
			return removeFirst();
		}
		if(index == size-1) {
			return removeLast();
		}
		else {
			int counter = 0;
			while(counter<size) {
				if(counter == index) { //find the element at the index and delete it
					temp = currHolder.getProduct();
					prev.setNextHolder(currHolder.getNextHolder());
					currHolder.getNextHolder().setPreviousHolder(prev);
					break;
				}
				else {
					prev = currHolder;
					currHolder = currHolder.getNextHolder();
					counter++;
				}
			}
			size--;
			return temp;
		}
		
	}
	
	@Override
	public Product removeProduct(int value) throws IndexOutOfBoundsException {
		
		if(size == 0) { //If line is empty
			throw new NoSuchElementException("Product not found.");
		}
		Holder curr = first , prev = null;
		//save the old product to return
		Product old_Product = null;
		
		if(curr.getProduct().getValue() == value) {
			return removeFirst();
		}
		
		while(curr != null  ) {
			if(curr.getProduct().getValue() == value) {
				if(curr.getNextHolder() == null) {
					return removeLast();
				}
				old_Product = new Product(curr.getProduct().getId(), curr.getProduct().getValue());
				prev.setNextHolder(curr.getNextHolder());
				curr.getNextHolder().setPreviousHolder(prev);
				size--;
				break;
			}
			else {
				prev = curr;
				curr = curr.getNextHolder();
			}
		}
		
		if(old_Product == null) {
			throw new NoSuchElementException("Product not found.");
		}
		
		return old_Product;
		

	}
	
	@Override
	public void reverse() {
		if(size == 0) { //If line is empty
			return;
		}
		Holder temp = null;
		Holder current = first;
		
		
		while(current != null) {
			temp = current.getPreviousHolder();
			current.setPreviousHolder(current.getNextHolder());
			current.setNextHolder(temp);
			current = current.getPreviousHolder();
		}
		
		if(temp != null) {
			first = temp.getPreviousHolder();
			
		}
		Holder curr = first;
		while(curr.getNextHolder() != null) {
			curr = curr.getNextHolder();
		}
		last = curr;
		last.setNextHolder(null);
		
		
	}
	
	@Override
	public int filterDuplicates() {
		int counter = 0;
		if(size == 0 || size == 1 ) { //If line is empty or there is one,there is no duplicate
			return 0;
		}
		Holder currentHolder = first, prev = null;
		HashSet<Integer> set = new HashSet<>();
		
		
		while(currentHolder != null && currentHolder.getNextHolder() != null) {
			if(set.contains(currentHolder.getProduct().getValue()) != true) {
				set.add(currentHolder.getProduct().getValue());
				prev  = currentHolder;
				currentHolder = currentHolder.getNextHolder();
				
			}
			else {
				prev.setNextHolder(currentHolder.getNextHolder());
				currentHolder.getNextHolder().setPreviousHolder(prev);
				currentHolder = currentHolder.getNextHolder();
				counter++;	
				size--;
			}
		}
		if(currentHolder != null) {
			if(set.contains(currentHolder.getProduct().getValue()) != true) {
				set.add(currentHolder.getProduct().getValue());
				prev  = currentHolder;
				currentHolder = currentHolder.getNextHolder();	
			}			
			else {
				counter++;
				removeLast();
			}
		}
		return counter;
	}
	
	public void print() {
		if (size== 0) {
			System.out.println("{}");
			return;
		}
		Holder curr = first;
		
		
		System.out.print("{");
		
		while (curr.getNextHolder() != null) {
			System.out.print(curr.getProduct());
			System.out.print(",");
			curr = curr.getNextHolder();
		}
		if(curr != null) {
			System.out.print(curr.getProduct());
		}
		System.out.println("}");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}