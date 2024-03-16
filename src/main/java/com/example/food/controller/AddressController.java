package com.example.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.food.entity.Address;
import com.example.food.service.AddressService;

@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public String listAddresses(Model model) {
        model.addAttribute("addresses", addressService.findAll());
        return "address/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("address", new Address());
        return "address/add";
    }

    @PostMapping("/add")
    public String addAddress(Address address, RedirectAttributes redirectAttributes) {
        addressService.insert(address);
        redirectAttributes.addFlashAttribute("message", "주소가 성공적으로 추가되었습니다.");
        return "redirect:/address";
    }

    // 주소 상세 정보 조회 및 지도에 표시
    @GetMapping("/map/{bid}")
    public String showAddressMap(@PathVariable("bid") Long bid, Model model) {
        Address address = addressService.findById(bid);
        if (address != null) {
            model.addAttribute("roadAddress", address.getAddress());
            return "map";
        } else {
            return "redirect:/address";
        }
    }
}