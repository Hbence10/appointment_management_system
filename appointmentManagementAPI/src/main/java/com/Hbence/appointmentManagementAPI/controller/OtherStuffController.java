package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.service.OtherStuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/otherStuff")
public class OtherStuffController {

    private OtherStuffService otherStuffService;

    @Autowired
    public OtherStuffController(OtherStuffService otherStuffService) {
        this.otherStuffService = otherStuffService;
    }
}

/*
 * Endpointok:
 *       - galleria lekerdezese
 *       - galleria szerkesztese
 *       - szabalyzat lekerdezese
 *       - szabalyzat szerkesztese
 *       - akcio lekerdezese
 *       - akcio letrehozasa
 *       - velemenyek lekerdezese
 *       - velemeny like/dislike
 * */
