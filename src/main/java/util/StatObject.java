package util;

public class StatObject<T> {

	private T initial;
	private T current;


	public StatObject(T t) {
		this.initial = t;
		this.current = t;
	}

	public void reset() {
		current = initial;
	}

	public T getInitial() { return initial; }

	public T getCurrent() { return current; }

	public void setCurrent(T current) { this.current = current; }
}
