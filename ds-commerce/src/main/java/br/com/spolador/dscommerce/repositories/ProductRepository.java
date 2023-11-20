package br.com.spolador.dscommerce.repositories;

import br.com.spolador.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
