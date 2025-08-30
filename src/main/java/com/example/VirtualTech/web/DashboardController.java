// DashboardController.java
package com.example.VirtualTech.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.VirtualTech.repository.OrderItemRepository;

import java.time.*;

@Controller
@RequiredArgsConstructor
public class DashboardController {
  private final OrderItemRepository repo;

  @GetMapping({ "/dashboard"})
  public String dashboard(Model model){
    // Inicio y fin del día local
    ZoneId zone = ZoneId.systemDefault(); // America/Lima en tu caso
    LocalDateTime start = LocalDate.now(zone).atStartOfDay();
    LocalDateTime end   = start.plusDays(1);

    var salesToday = repo.salesBetween(start, end);
    var byCat      = repo.salesByCategoryBetween(start, end);
    var byBrand    = repo.salesByBrandBetween(start, end);
    var units      = repo.unitsBetween(start, end);

    model.addAttribute("salesToday", salesToday == null ? 0.0 : salesToday.doubleValue());
    model.addAttribute("byCatLabels",  byCat.stream().map(m -> (String)m.get("category")).toList());
    model.addAttribute("byCatTotals",  byCat.stream().map(m -> ((Number)m.get("total")).doubleValue()).toList());
    model.addAttribute("byBrandLabels",byBrand.stream().map(m -> (String)m.get("brand")).toList());
    model.addAttribute("byBrandTotals",byBrand.stream().map(m -> ((Number)m.get("total")).doubleValue()).toList());
    model.addAttribute("unitsToday", units == null ? 0L : units);

    return "dashboard";
  }
}
