package guro.spring.kiosk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import guro.spring.kiosk.entity.Option;
import guro.spring.kiosk.exception.CustomEEnum;
import guro.spring.kiosk.exception.CustomException;
import guro.spring.kiosk.repository.OptionRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class OptionService {
    private final OptionRepo optionRepo;

    public Option getOptionById(Long id) {
        return optionRepo.findById(id).orElseThrow(() -> new CustomException(CustomEEnum.OPTION_NOT_FOUND));
    }

    public List<Option> getAllOptions() {
        return optionRepo.findAll();
    }
}

