package org.lessons.pizzery.springlamiapizzeriacrud.controllers;

import jakarta.validation.Valid;
import org.lessons.pizzery.springlamiapizzeriacrud.exceptions.OfferNotFoundException;
import org.lessons.pizzery.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.lessons.pizzery.springlamiapizzeriacrud.model.AlertMessage;
import org.lessons.pizzery.springlamiapizzeriacrud.model.Offer;
import org.lessons.pizzery.springlamiapizzeriacrud.model.Pizza;
import org.lessons.pizzery.springlamiapizzeriacrud.service.OfferService;
import org.lessons.pizzery.springlamiapizzeriacrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private PizzaService pizzaService;


    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId") Optional<Integer> id, Model model) {
        Offer offer = new Offer();
        offer.setOfferStartDate(LocalDate.now());
        offer.setOfferEndDate(LocalDate.now().plusMonths(1));
        if (id.isPresent()) {
            try {
                Pizza pizza = pizzaService.getById(id.get());
                offer.setPizza(pizza);
            } catch (PizzaNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        model.addAttribute("offer", offer);
        return "/offers/create";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute Offer formOffer,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/offers/create";
        }

        Offer createdOffer = offerService.create(formOffer);
        return "redirect:/pizzas/" + Integer.toString(createdOffer.getPizza().getId());
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        try {
            Offer offer = offerService.getById(id);
            model.addAttribute("offer", offer);
            return "/offers/create";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute Offer formOffer,
         BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/offers/create";
        }
        try {
            Offer updatedOffer = offerService.update(id, formOffer);
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Offers successfully updated"));
            return "redirect:/pizzas/" + updatedOffer.getPizza().getId();
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id,
                         @RequestParam("pizzaId") Optional<Integer> pizzaIdParam,
                         RedirectAttributes redirectAttributes) {
        // faccio la delete
        Integer pizzaId = pizzaIdParam.get();
        try {
            offerService.delete(id);
            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.SUCCESS.SUCCESS,
                    "Offers successfully deleted"));
        } catch (OfferNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.ERROR.ERROR,
                    "Borrowing with id " + e.getMessage() + " not found"));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.ERROR.ERROR,
                    "Unable to delete borrowing"));
        }
        if (pizzaId == null) {
            return "redirect:/pizzas";
        }
        return "redirect:/pizzas/" + Integer.toString(pizzaId);
    }

}
