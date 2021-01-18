package br.com.iblue.apicrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iblue.apicrud.entity.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {
}