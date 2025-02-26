package guro.spring.kiosk.controller;

import guro.spring.kiosk.dto.req.ProductByCategoryReq;
import guro.spring.kiosk.dto.req.ProductUnavailableReq;
import guro.spring.kiosk.dto.resp.ProductDetailResp;
import guro.spring.kiosk.dto.resp.ProductSimpleResp;
import guro.spring.kiosk.entity.Branch;
import guro.spring.kiosk.entity.Product;
import guro.spring.kiosk.service.BranchService;
import guro.spring.kiosk.service.ProductService;
import guro.spring.kiosk.service.ProductUnavailableService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;
    private final BranchService branchService;

    private final ProductUnavailableService productUnavailableService;

    @GetMapping("/detail")
    public ResponseEntity<List<ProductDetailResp>> getProductDetailsByCategory(@Valid @ModelAttribute ProductByCategoryReq req) {
        log.info("getProductDetailsByCategory 호출: {}", req.toString());
        Branch branch = branchService.getBranchById(req.getBranchId());
        return ResponseEntity.ok(productService.getProductDetailsByCategory(branch, req.getCategory()));
    }

    @GetMapping("/simple")
    public ResponseEntity<List<ProductSimpleResp>> getProductSimplesByCategory(@Valid @ModelAttribute ProductByCategoryReq req) {
        Branch branch = branchService.getBranchById(req.getBranchId());
        return ResponseEntity.ok(productService.getProductSimplesByCategory(branch, req.getCategory()));
    }

    @PostMapping("/unavailable-toggle")
    public ResponseEntity<?> toggleProductUnavailable(@Valid @ModelAttribute ProductUnavailableReq req) {
        Branch branch = branchService.getBranchById(req.getBranchId());
        Product product = productService.getProductById(req.getProductId());
        productUnavailableService.toggleProductUnavailable(branch, product);
        return ResponseEntity.ok().build();
    }
}
