package org.lessons.pizzery.springlamiapizzeriacrud.service;

import org.lessons.pizzery.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.lessons.pizzery.springlamiapizzeriacrud.model.Pizza;
import org.lessons.pizzery.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;


    public Pizza updatePizza(Pizza formPizza, Integer id) throws PizzaNotFoundException {
        Pizza pizzaUpdate = getById(id);
        pizzaUpdate.setNome(formPizza.getNome());
        pizzaUpdate.setDescrizione(formPizza.getDescrizione());
        pizzaUpdate.setPrezzo(formPizza.getPrezzo());
        pizzaUpdate.setIngredients(formPizza.getIngredients());
        return pizzaRepository.save(pizzaUpdate);
    }

    public Pizza createPizza(Pizza formPizza) {
        Pizza pizzaToPersist = new Pizza();
        pizzaToPersist.setNome(formPizza.getNome());
        pizzaToPersist.setDescrizione(formPizza.getDescrizione());
        pizzaToPersist.setPrezzo(formPizza.getPrezzo());
        pizzaToPersist.setCreatedAt(LocalDateTime.now());
        pizzaToPersist.setIngredients(formPizza.getIngredients());
        return pizzaRepository.save(pizzaToPersist);
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll(Sort.by("nome"));
    }

    public List<Pizza> getFilteredPizzas(String keyword) {
        return pizzaRepository.findByNomeContainingIgnoreCase(keyword);
    }

    public Pizza getById(Integer id) throws PizzaNotFoundException {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException(Integer.toString(id));
        }
    }

    public boolean deleteById(Integer id) {
        pizzaRepository.findById(id).orElseThrow(() -> new PizzaNotFoundException(Integer.toString(id)));
        try {
            pizzaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

