package de.vispiron.carsync.classical.service;

import de.vispiron.carsync.classical.domain.CarsyncDomain;
import de.vispiron.carsync.classical.dto.PaginationRangeRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface RestService<T extends CarsyncDomain> {

	T save(T item);

	T get(Integer id);

	T get(String id);

	T save(T item, Map<String, Serializable> params);

	T get(Integer id, Map<String, Serializable> params);

	T get(String id, Map<String, Serializable> params);

	Page<T> getList();

	Page<T> getList(Map<String, Serializable> params);

	Page<T> getList(PaginationRangeRequest range);

	Page<T> getList(Map<String, Serializable> params, PaginationRangeRequest range);

	T update(T item);

	T update(T item, Integer id);

	T update(T item, String id);

	T update(T item, Map<String, Serializable> params);

	T update(T item, Integer id, Map<String, Serializable> params);

	T update(T item, String id, Map<String, Serializable> params);

	void delete(T item);

	void delete(Integer id);

	void delete(String id);

	void delete(T item, Map<String, Serializable> params);

	void delete(Integer id, Map<String, Serializable> params);

	void delete(String id, Map<String, Serializable> params);
}
