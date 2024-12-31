package com.example.BACKAppLiv.service;

import com.example.BACKAppLiv.model.OrderLocation;
import com.example.BACKAppLiv.model.LocationUpdate;
import com.example.BACKAppLiv.repository.OrderLocationRepository;
import org.springframework.stereotype.Service;

@Service
public class TrackingService {

    private final OrderLocationRepository repository;

    public TrackingService(OrderLocationRepository repository) {
        this.repository = repository;
    }

    public void updateLocation(Long orderId, LocationUpdate locationUpdate) {
        // Vérifier si une position existe déjà
        OrderLocation location = repository.findByOrderId(orderId)
                .orElse(new OrderLocation(orderId, locationUpdate.getLatitude(), locationUpdate.getLongitude()));

        // Mettre à jour la position
        location.setLatitude(locationUpdate.getLatitude());
        location.setLongitude(locationUpdate.getLongitude());
        repository.save(location);

        System.out.printf("Commande %d : Position mise à jour (Lat: %.6f, Lon: %.6f)%n",
                orderId, locationUpdate.getLatitude(), locationUpdate.getLongitude());
    }

    public LocationUpdate getLocation(Long orderId) {
        // Récupérer la position à partir de la base de données
        return repository.findByOrderId(orderId)
                .map(loc -> new LocationUpdate(loc.getLatitude(), loc.getLongitude()))
                .orElse(null);
    }
}
