package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.Car;
import cz.uhk.kppro.model.Driver;
import cz.uhk.kppro.service.CarService;
import cz.uhk.kppro.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/drivers")
public class DriverController {
    private final DriverService driverService;
    private final CarService carService;

    public DriverController(DriverService driverService, CarService carService) {
        this.driverService = driverService;
        this.carService = carService;
    }

    @GetMapping("")
    public String listAllCars(Model model) {
        model.addAttribute("drivers", this.driverService.getAllDrivers());
        return "drivers_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable long id) {
        Driver driver = this.driverService.getDriverById(id);

        if (driver == null) {
            return "redirect:/drivers";
        }

        model.addAttribute("drivers", driver);
        return "driver_detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("driver", new Driver());
        model.addAttribute("cars", this.carService.getAllCars());
        model.addAttribute("edit", false);
        return "driver_edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id) {
        Driver driver = this.driverService.getDriverById(id);

        if (driver == null) {
            return "redirect:/drivers";
        }

        driver.setId(id);
        model.addAttribute("driver", driver);
        model.addAttribute("cars", this.carService.getAllCars());
        model.addAttribute("edit", true);
        return "driver_edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        this.driverService.deleteDriverById(id);
        return "redirect:/drivers";
    }

    @PostMapping("/save")
    public String save(@Valid Driver driver, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", driver.getId() == 0);
            return "driver_edit";
        }

        this.driverService.saveDriver(driver);

        return "redirect:/drivers";
    }
}
