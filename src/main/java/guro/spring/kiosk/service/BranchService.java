package guro.spring.kiosk.service;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Branch;
import guro.spring.kiosk.repository.BranchRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class BranchService {
    private final BranchRepo branchRepo;

    public Branch getBranchById(Long id) {
        return branchRepo.findById(id).orElseThrow(() -> new RuntimeException("Branch not found"));
    }

    public boolean checkDevicePw(Long id, String password) {
        Branch branch = getBranchById(id);
        return branch.getDevicePw().equals(password);
    }

    @Transactional
    public void updateDevicePw(Long id, String password) {
        Branch branch = getBranchById(id);
        branch.updateDevicePw(password);
        branchRepo.save(branch);
    }
}
