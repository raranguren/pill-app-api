package com.pillapp.api.configuration;

import com.pillapp.api.domain.entities.*;
import com.pillapp.api.repository.CareActionRepository;
import com.pillapp.api.repository.PatientRepository;
import com.pillapp.api.repository.ReminderRepository;
import com.pillapp.api.repository.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;

@ConditionalOnProperty(value = "spring.datasource.driver-class-name", havingValue = "org.h2.Driver")
@Configuration
public class InitialDataConfiguration {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final ReminderRepository reminderRepository;
    private final CareActionRepository actionRepository;

    public InitialDataConfiguration(UserRepository userRepository, PasswordEncoder passwordEncoder, PatientRepository patientRepository, ReminderRepository reminderRepository, CareActionRepository actionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientRepository = patientRepository;
        this.reminderRepository = reminderRepository;
        this.actionRepository = actionRepository;
    }


    @PostConstruct
    public void populateDatabase() {

        // USUARIOS
        var password = passwordEncoder.encode("Cev.1234");
        var padre = new User("cev@cev.com", "Arístides_padre", password);
        var madre = new User("cev2@cev.com", "Damaris_madre", password);
        var nanny = new User("cev3@cev.com", "Super_nanny", password);
        userRepository.saveAll(List.of(padre, madre, nanny));

        // PACIENTES
        var dani = new Patient("Dani", "Sangre A+\nAlergia a penicilina", 54, List.of(padre, madre, nanny));
        var anabel = new Patient("Anabel", "Sangre B-\nAlergia a penicilina", 42, List.of(nanny));
        var carmen = new Patient("Carmen", "Sangre A+\nAlergia a penicilina", 34, List.of(nanny));
        var anita = new Patient("Anita", "Sangre O+\nAlergia a penicilina", 44, List.of(padre, madre, nanny));
        var esme = new Patient("Esmeralda", "Sangre A+\nAlergia a penicilina", 24, List.of(nanny));
        var laura = new Patient("Laura", "Sangre A+\nAlergia a penicilina", 14, List.of(nanny));
        var juan = new Patient("Juanito", "Sangre O+\nAlergia a penicilina", 25, List.of(padre, madre, nanny));
        var gabriel = new Patient("Gabriel", "Sangre A+\nAlergia a penicilina", 37, List.of(nanny));
        patientRepository.saveAll(List.of(dani, anabel, carmen, anita, esme, laura, juan, gabriel));

        // HORAS
        var TODAY = LocalDate.now();
        var TODAY16 = LocalDateTime.of(TODAY, LocalTime.of(16, 0));
        var TODAY20 = LocalDateTime.of(TODAY, LocalTime.of(20, 0));
        var TOMORROW11 = LocalDateTime.of(TODAY.plusDays(1), LocalTime.of(11, 0));
        var LATER9 = LocalDateTime.of(TODAY.plusDays(4), LocalTime.of(9, 0));
        var NEXTMONTH12 = LocalDateTime.of(TODAY.plusDays(40), LocalTime.of(12, 0));
        var PAST2H = LocalDateTime.now().minusHours(2);

        // CITA MÉDICA
        var meeting = new MeetingPlan();
        meeting.appointmentTimestamp = LATER9.toEpochSecond(UTC);
        meeting.patient = dani;
        meeting.description = "Dentista";
        reminderRepository.save(meeting);

        // CITA MÉDICA
        meeting = new MeetingPlan();
        meeting.appointmentTimestamp = NEXTMONTH12.toEpochSecond(UTC);
        meeting.patient = dani;
        meeting.description = "Otorrino";
        reminderRepository.save(meeting);

        // CITA MÉDICA
        meeting = new MeetingPlan();
        meeting.appointmentTimestamp = NEXTMONTH12.toEpochSecond(UTC);
        meeting.patient = dani;
        meeting.description = "Oculista";
        reminderRepository.save(meeting);

        // CITA MÉDICA
        meeting = new MeetingPlan();
        meeting.appointmentTimestamp = TODAY16.toEpochSecond(UTC);
        meeting.patient = juan;
        meeting.description = "Dentista";
        reminderRepository.save(meeting);

        // CITA MÉDICA
        meeting = new MeetingPlan();
        meeting.appointmentTimestamp = TOMORROW11.toEpochSecond(UTC);
        meeting.patient = anita;
        meeting.description = "Oftalmólogo";
        reminderRepository.save(meeting);

        // CITA MÉDICA
        meeting = new MeetingPlan();
        meeting.appointmentTimestamp = TODAY16.toEpochSecond(UTC);
        meeting.patient = laura;
        meeting.description = "Pediatra";
        reminderRepository.save(meeting);

        // CITA MÉDICA
        meeting = new MeetingPlan();
        meeting.appointmentTimestamp = NEXTMONTH12.toEpochSecond(UTC);
        meeting.patient = esme;
        meeting.description = "Quitar escayola";
        reminderRepository.save(meeting);

        // CITA MÉDICA
        meeting = new MeetingPlan();
        meeting.appointmentTimestamp = LATER9.toEpochSecond(UTC);
        meeting.patient = laura;
        meeting.description = "Gine";
        reminderRepository.save(meeting);

        // PRESCRIPCIÓN
        var plan = new DrugPlan();
        plan.reminderTimestamp = TODAY16.toEpochSecond(UTC);
        plan.description = "Eutirox 25";
        plan.dosePerIntake = "3 comprimidos";
        plan.period = 86400L;
        plan.patient = juan;
        reminderRepository.save(plan);

        // TOMAS
        for (int i = 0; i<5; i++) {
            var action = new PlannedDrugAction();
            action.plan = plan;
            action.patient = plan.patient;
            action.doneTimestamp = PAST2H.toEpochSecond(UTC) - plan.period * i;
            action.doseNote = plan.dosePerIntake;
            actionRepository.save(action);
        }

        // PRESCRIPCIÓN
        plan = new DrugPlan();
        plan.reminderTimestamp = TODAY20.toEpochSecond(UTC);
        plan.description = "anti piojos";
        plan.dosePerIntake = "3 gotas";
        plan.period = 86400L;
        plan.patient = dani;
        reminderRepository.save(plan);

        // TOMAS
        for (int i = 0; i<4; i++) {
            var action = new PlannedDrugAction();
            action.plan = plan;
            action.patient = plan.patient;
            action.doneTimestamp = PAST2H.toEpochSecond(UTC) - plan.period * i;
            action.doseNote = plan.dosePerIntake;
            actionRepository.save(action);
        }

        // PRESCRIPCIÓN
        plan = new DrugPlan();
        plan.reminderTimestamp = TODAY20.toEpochSecond(UTC);
        plan.description = "Dercutane 20";
        plan.dosePerIntake = "1 cápsula";
        plan.period = 86400L;
        plan.patient = dani;
        reminderRepository.save(plan);

        // TOMAS
        for (int i = 0; i<3; i++) {
            var action = new PlannedDrugAction();
            action.plan = plan;
            action.patient = plan.patient;
            action.doneTimestamp = PAST2H.toEpochSecond(UTC) - plan.period * i;
            action.doseNote = plan.dosePerIntake;
            actionRepository.save(action);
        }

        // PRESCRIPCIÓN
        plan = new DrugPlan();
        plan.reminderTimestamp = TODAY16.toEpochSecond(UTC);
        plan.description = "Crema hongos";
        plan.dosePerIntake = "1";
        plan.period = 86400L;
        plan.patient = anita;
        reminderRepository.save(plan);

        // TOMAS
        for (int i = 0; i<3; i++) {
            var action = new PlannedDrugAction();
            action.plan = plan;
            action.patient = plan.patient;
            action.doneTimestamp = PAST2H.toEpochSecond(UTC) - plan.period * i;
            action.doseNote = plan.dosePerIntake;
            actionRepository.save(action);
        }

        // PRESCRIPCIÓN
        plan = new DrugPlan();
        plan.reminderTimestamp = TOMORROW11.toEpochSecond(UTC);
        plan.description = "anti piojos";
        plan.dosePerIntake = "2 gotas";
        plan.period = 86400L;
        plan.patient = esme;
        reminderRepository.save(plan);

        // TOMAS
        for (int i = 0; i<3; i++) {
            var action = new PlannedDrugAction();
            action.plan = plan;
            action.patient = plan.patient;
            action.doneTimestamp = PAST2H.toEpochSecond(UTC) - plan.period * i;
            action.doseNote = plan.dosePerIntake;
            actionRepository.save(action);
        }

        // PRESCRIPCIÓN
        plan = new DrugPlan();
        plan.reminderTimestamp = TOMORROW11.toEpochSecond(UTC);
        plan.description = "anti piojos";
        plan.dosePerIntake = "2 gotas";
        plan.period = 86400L;
        plan.patient = carmen;
        reminderRepository.save(plan);

        // TOMAS
        for (int i = 0; i<2; i++) {
            var action = new PlannedDrugAction();
            action.plan = plan;
            action.patient = plan.patient;
            action.doneTimestamp = PAST2H.toEpochSecond(UTC) - plan.period * i;
            action.doseNote = plan.dosePerIntake;
            actionRepository.save(action);
        }

    }

}
