package com.yoru.currency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yoru.currency.entity.Currency;

@Controller
public class HelloController {
	@RequestMapping("/index")
    public String hello(Model model){
		model.addAttribute("currency", new Currency());
        return "index";
    }
}
