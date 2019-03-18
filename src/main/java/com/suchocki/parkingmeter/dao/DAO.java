package com.suchocki.parkingmeter.dao;

import java.util.Optional;

public interface DAO<T, T2> { // T is object type, T2 is id type, this interface exists to avoid boilerplate
								// creating DAO layer (other DAO interfaces will be able to extend this)
	public void save(T object);

	public Optional<T> get(T2 id);

	public void update(T object);

	public void delete(T2 id);
}
