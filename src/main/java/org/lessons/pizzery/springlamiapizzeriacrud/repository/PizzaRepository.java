package org.lessons.pizzery.springlamiapizzeriacrud.repository;

import java.util.List;
import org.lessons.pizzery.springlamiapizzeriacrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    public List<Pizza> findByNomeContainingIgnoreCase(String nome);
    }


