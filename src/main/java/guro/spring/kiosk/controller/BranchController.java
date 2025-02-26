package guro.spring.kiosk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guro.spring.kiosk.dto.req.UpdatePasswordReq;
import guro.spring.kiosk.service.BranchService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BranchController {
    private final BranchService branchService;

    @GetMapping("/{id}/check-device-pw")
    public ResponseEntity<Boolean> checkDevicePw(@PathVariable Long id, @RequestParam String password) {
        return ResponseEntity.ok(branchService.checkDevicePw(id, password));
    }

    @PatchMapping("/{id}/device-pw")
    public ResponseEntity<?> updateDevicePw(@PathVariable Long id, @Valid @RequestBody UpdatePasswordReq req) {
        branchService.updateDevicePw(id, req.getPassword());
        return ResponseEntity.ok().build();
    }
}
