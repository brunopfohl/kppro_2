package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.Car;
import cz.uhk.kppro.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("")
    public String listAllCars(Model model) {
        model.addAttribute("cars", this.carService.getAllCars());
        return "cars_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable long id) {
        Car car = this.carService.getCarById(id);

        if (car == null) {
            return "redirect:/cars";
        }

        model.addAttribute("car", car);
        return "car_detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("edit", false);
        return "car_edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id) {
        Car car = this.carService.getCarById(id);

        if (car == null) {
            return "redirect:/cars";
        }

        car.setId(id);
        model.addAttribute("car", car);
        model.addAttribute("edit", true);
        return "car_edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        this.carService.deleteCarById(id);
        return "redirect:/cars";
    }

    @PostMapping("/save")
    public String save(@Valid Car car, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", car.getId() == 0);
            return "car_edit";
        }

        this.carService.saveCar(car);

        return "redirect:/cars";
    }
}
