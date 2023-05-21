package com.fmt;

public class Node<T>  {

	private Node<T> next;
	T item;
	
	public Node(T item) {
		this.item = item;
		this.next = null;
	}
	
	public T getValue() {
		return item;
	}
	
	public Node<T> getNext() {
		return next;
	}
	
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	
}
