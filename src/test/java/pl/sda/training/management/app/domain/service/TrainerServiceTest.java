package pl.sda.training.management.app.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.training.management.app.domain.model.Login;
import pl.sda.training.management.app.domain.model.Trainer;
import pl.sda.training.management.app.domain.model.User;
import pl.sda.training.management.app.domain.repository.TrainerRepo;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static pl.sda.training.management.app.domain.model.UserRole.ROLE_TRAINER;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {
    private TrainerService sut;

    @Mock
    private TrainerRepo trainerRepo;

    @Mock
    private UserService userService;

    @BeforeEach
    void setup() {
        sut = new TrainerService(trainerRepo, userService);
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

    @Test
    @DisplayName("Should create new trainer based on user.")
    void setAsTrainerByLogin() {
        //given
        Login testLogin = Login.of("testLogin");
        User testUser = User.builder()
                .login(testLogin)
                .roles(new HashSet<>())
                .build();

        when(userService.getUserByLogin(testLogin))
                .thenReturn(testUser);

        //when
        sut.setAsTrainerByLogin(testLogin);

        //then
        verify(userService, times(1)).getUserByLogin(testLogin);
        assertThat(testUser.getRoles()).contains(ROLE_TRAINER);
        verify(trainerRepo, times(1)).save(any(Trainer.class));
    }
}
