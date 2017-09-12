package com.textbookvalet.services;

import java.util.List;

public interface BaseService<E, ID> {

	public List<E> getAll();

	public E save(E e);

	public E getById(ID id);

	public void delete(ID id);
}
