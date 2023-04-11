package org.lessons.pizzery.springlamiapizzeriacrud.controllers;

import jakarta.validation.Valid;
import org.lessons.pizzery.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.pizzery.springlamiapizzeriacrud.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("list", ingredientService.getAll());
        model.addAttribute("ingredientObj", new Ingredient());
        return "/ingredients/index";
    }

    @PostMapping("/save")
    public String doSave(@Valid @ModelAttribute(name = "ingredientObj") Ingredient ingredient,
        BindingResult bindingResult,
        Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("list", ingredientService.getAll());
            return "/ingredients/index";
        }
        ingredientService.create(ingredient);
        return "redirect:/ingredient";
    }
}
