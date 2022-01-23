package com.skt.shms.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cafe")
public class CafeController {
    @GetMapping(value="/info")
    public String info() {
        System.out.println("cafe info~~");
        return "cafeInfo";
    }
}