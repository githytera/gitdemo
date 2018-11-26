package com.ensat.repositories;

import com.ensat.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
//    @Query(value = "from Product  s where s.cityNo=?1")
}
