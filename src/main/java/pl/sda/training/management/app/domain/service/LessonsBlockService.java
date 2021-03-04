package pl.sda.training.management.app.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sda.training.management.app.domain.model.LessonsBlock;
import pl.sda.training.management.app.domain.repository.LessonsBlockRepo;
import pl.sda.training.management.app.exception.LessonsBlockNotFoundException;

@Service
@RequiredArgsConstructor
public class LessonsBlockService {
    private final LessonsBlockRepo lessonsBlockRepo;

    public Page<LessonsBlock> getPage(Pageable pageable) {
        return lessonsBlockRepo.findAll(pageable);
    }

    public LessonsBlock getById(Long id) {
        return lessonsBlockRepo.findById(id)
                .orElseThrow(() -> new LessonsBlockNotFoundException("LessonsBlock with id: " + id + " not found."));
    }
}
