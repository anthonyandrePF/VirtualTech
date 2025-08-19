package com.example.VirtualTech.web;

import com.example.VirtualTech.domain.Product;
import com.example.VirtualTech.repository.BrandRepository;
import com.example.VirtualTech.repository.CategoryRepository;
import com.example.VirtualTech.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService service;
  private final CategoryRepository categoryRepo;
  private final BrandRepository brandRepo;

  // --- Cargar catálogos SIEMPRE (GET, POST, con/ sin errores) ---
  @ModelAttribute("categories")
  public Object loadCategories() {
    return categoryRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
  }

  @ModelAttribute("brands")
  public Object loadBrands() {
    return brandRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
  }

  @GetMapping
  public String list(@RequestParam(required = false) String q, Model model) {
    model.addAttribute("products", service.list(q));
    model.addAttribute("q", q);
    model.addAttribute("title", "Productos");
    return "products/list";
  }

  @GetMapping("/new")
  public String form(Model model) {
    model.addAttribute("product", new Product());
    return "products/form";
  }

  @PostMapping   // POST a /products
  public String save(@Valid @ModelAttribute("product") Product product,
                     BindingResult br,
                     RedirectAttributes ra) {

    // Validar selects (ids obligatorios)
    Long categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
    Long brandId    = product.getBrand()    != null ? product.getBrand().getId()    : null;

    if (categoryId == null) br.rejectValue("category", "NotNull", "Selecciona una categoría");
    if (brandId == null)    br.rejectValue("brand",    "NotNull", "Selecciona una marca");

     // Validar SKU único
    if (product.getSku() == null || product.getSku().isBlank()) {
      br.rejectValue("sku", "NotBlank", "El SKU es obligatorio");
    } else {
      boolean duplicated = (product.getId() == null)
          ? service.skuExists(product.getSku())
          : service.skuExistsForOther(product.getSku(), product.getId());
      if (duplicated) br.rejectValue("sku", "Duplicate", "El SKU ya existe");
    }

    if (br.hasErrors()) {
      return "products/form";  // @ModelAttribute ya repone listas
    }

    // Adjuntar referencias JPA reales (evita insertar NULL)
    product.setCategory(categoryRepo.getReferenceById(categoryId));
    product.setBrand(brandRepo.getReferenceById(brandId));

    service.save(product);
    ra.addFlashAttribute("ok", "Producto guardado");
    return "redirect:/products";
  }

  @GetMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes ra) {
    service.delete(id);
    ra.addFlashAttribute("ok", "Producto eliminado");
    return "redirect:/products";
  }

  
  @GetMapping("/{id}/edit")
  public String formEdit(@PathVariable Long id, Model model){
    model.addAttribute("product", service.get(id)); // carga el producto
    return "products/form";                         // MISMA vista que “nuevo”
}



}

