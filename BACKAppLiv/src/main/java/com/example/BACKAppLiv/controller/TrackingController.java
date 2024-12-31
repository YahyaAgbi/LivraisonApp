package com.example.BACKAppLiv.controller;

import com.example.BACKAppLiv.model.LocationUpdate;
import com.example.BACKAppLiv.service.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/track")
public class TrackingController {

    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<String> updateLocation(
            @PathVariable Long orderId,
            @RequestBody LocationUpdate locationUpdate) {
        trackingService.updateLocation(orderId, locationUpdate);
        return ResponseEntity.ok("Position mise à jour avec succès");
    }
}
