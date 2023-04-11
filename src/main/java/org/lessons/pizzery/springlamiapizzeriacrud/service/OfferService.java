package org.lessons.pizzery.springlamiapizzeriacrud.service;

import org.lessons.pizzery.springlamiapizzeriacrud.exceptions.OfferNotFoundException;
import org.lessons.pizzery.springlamiapizzeriacrud.model.Offer;
import org.lessons.pizzery.springlamiapizzeriacrud.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public Offer create(Offer formOffer) {
        return offerRepository.save(formOffer);
    }

    public Offer getById(Integer id) throws OfferNotFoundException {
        return offerRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException(Integer.toString(id)));
    }

    public Offer update(Integer id, Offer formOffer) throws OfferNotFoundException {
        Offer offerToUpdate = getById(id);
        offerToUpdate.setOfferStartDate(formOffer.getOfferStartDate());
        offerToUpdate.setOfferEndDate(formOffer.getOfferEndDate());
        offerToUpdate.setOfferName(formOffer.getOfferName());
        return offerRepository.save(offerToUpdate);
    }

    public void delete(Integer borrowingId) throws OfferNotFoundException {
        Offer offerToDelete = getById(borrowingId);
        offerRepository.delete(offerToDelete);
    }
}
