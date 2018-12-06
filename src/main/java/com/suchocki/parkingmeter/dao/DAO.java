package com.suchocki.parkingmeter.dao;

import java.util.List;

public interface DAO<T, T2> { // T is object type, T2 is id type, this interface exists to avoid boilerplate
								// creating DAO layer (other DAO interfaces will be able to extend this)
	public void save(T object);

	public T get(T2 id);

	public List<T> getAll();

	public void update(T object);

	public void delete(T2 id);
}
