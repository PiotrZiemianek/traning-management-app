package pl.sda.training.management.app.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.training.management.app.domain.model.Trainer;
import pl.sda.training.management.app.domain.model.User;
import pl.sda.training.management.app.domain.repository.TrainerRepo;
import pl.sda.training.management.app.exception.TrainerNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {
    private TrainerService sut;

    @Mock
    private TrainerRepo trainerRepo;

    @BeforeEach
    void setup() {
        sut = new TrainerService(trainerRepo);
    }

    @Test
    @DisplayName("Should thrown TrainerNotFoundException when trainer not found in DB.")
    void getException() {
        //given
        Long trainerId = 1L;

        when(trainerRepo.findById(trainerId))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(TrainerNotFoundException.class, () -> {
            Trainer trainer = sut.get(trainerId);
        });


    }

    @Test
    @DisplayName("Should set user.isActive=true. Should save trainer to repo.")
    void save() {
        //given
        User user = new User();
        Trainer trainer = new Trainer();
        trainer.setUser(user);
        when(trainerRepo.save(trainer))
                .thenReturn(trainer);

        //when
        trainer = sut.save(trainer);

        //then
        assertTrue(trainer.getUser().isActive());
        verify(trainerRepo, atLeastOnce()).save(notNull());

    }

    @Test
    @DisplayName("Should set user.isActive=false and save trainer to repo.")
    void delete() {
        //given
        Long trainerId = 1L;
        User user = new User();
        user.setActive(true);
        Trainer trainer = new Trainer();
        trainer.setUser(user);

        when(trainerRepo.findById(trainerId))
                .thenReturn(Optional.of(trainer));

        when(trainerRepo.save(trainer))
                .thenReturn(trainer);

        //when
        sut.delete(trainerId);

        //then
        assertFalse(trainer.getUser().isActive());
        verify(trainerRepo, atLeastOnce()).findById(trainerId);
        verify(trainerRepo, atLeastOnce()).save(notNull());
    }
}