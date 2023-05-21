package com.fmt;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class used to instantiate a sorted Linked List.
 * @param <T> object
 */

public class MyLinkedList<T> implements Iterable<T>{

	private int size;
	private Node<T> head;
    private Comparator<T> cmp;
    
    
	public MyLinkedList(List<T> list ,Comparator<T> cmp) {
		this.head = null;
		this.size = 0;
		this.cmp = cmp;
		for (T a : list) {
			this.addToList(a);
		}
	}
	/**
	 * 
	 * @return integer size
	 */
	public int getSize() {
		return this.size;
	}
	
	public void clear() {
		this.head = null;
		this.size = 0;
	}
	/**
	 * Add element to the linked list and maintains sort
	 * @param t
	 */
	
	public void addToList(T t) {
		Node<T> newNode = new Node<T>(t);
		Node<T> current = head; 
		Node<T> previous = null;
		
		if(this.head == null){
			this.head = newNode;
			this.size++;
			return;
		}
		
		while(current != null && cmp.compare(current.item, newNode.item) < 0) {
			previous = current;
			current = current.getNext();
		}
		
		if(previous == null) {
			this.head = newNode;
		}else {
			previous.setNext(newNode);
		}
		
		newNode.setNext(current);
		this.size++;
		
	}
	/**
	 * Removes an element from the linked list given the index of the element
	 * @param position
	 */
	public void remove(int position) {
		if(position < 0 || position > this.size) {
			throw new IllegalArgumentException("Invalid Index");
		}else if(position == 0) {
			this.head = this.head.getNext();
			this.size--;
		}else {
			Node<T> previous = this.getNode(position-1);
			Node<T> current = previous.getNext();
			previous.setNext(current.getNext());
			this.size--;
		}
	}
	
	private Node<T> getNode(int position){
		Node<T> current = this.head;
		for(int i = 0; i < position; i++) {
			current = current.getNext();
		}
		return current;
	}
	/**
	 * Method that takes an index and returns the value stored at that index
	 * @param position
	 * @return object
	 */
	public T getElement(int position) {
		if(position < 0 || position > this.size) {
			throw new IllegalArgumentException("Invalid Index");
		}
		
		Node<T> indexedElement = this.getNode(position);
		return indexedElement.getValue();
	}
	
	/**
	 * Prints the elements in the Linked List.
	 */
	public void display(){
	        Node<T> current = head;
	 
	        if (head == null) {
	            System.out.println("List is empty");
	            return;
	        }
	        while (current != null) {
	           
	            System.out.print(current.getValue() + " ");
	            current = current.getNext();
	        }
	 
	        System.out.println();
	    }
	
	private class LinkedListIterator implements Iterator<T>{
		
		private Node<T> current;
		
		public LinkedListIterator() {
			current = head;	
		}
		
		public boolean hasNext() {
			return current!= null;
		}
		
		public T next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			T value = current.getValue();
			current = current.getNext();
			return value;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

	public Iterator<T> iterator(){
		return new LinkedListIterator();
	}
}
	
		
	
