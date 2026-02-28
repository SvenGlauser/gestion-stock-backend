package ch.glauser.gestionstock.batch.configuration;

import ch.glauser.utilities.exception.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("!test")
@Component
public class BatchLauncher implements ApplicationRunner {

    private final JobOperator jobOperator;
    private final Job jobPays;

    public BatchLauncher(final JobOperator jobOperator,
                         @Qualifier("jobPays") final Job jobPays) {
        this.jobOperator = jobOperator;
        this.jobPays = jobPays;
    }

    @Override
    public void run(@NonNull ApplicationArguments args) {
        try {
            jobOperator.start(jobPays, new JobParameters());
        } catch (JobInstanceAlreadyCompleteException _) {
            log.info("Le job d'importation des pays a déjà été exécuté");
        } catch (InvalidJobParametersException e) {
            throw new TechnicalException("Mauvaise configuration des paramètres pour l'exécution du job d'importation des pays", e);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new TechnicalException("Impossible de lancer 2 instances du job d'importation des pays", e);
        } catch (JobRestartException e) {
            throw new TechnicalException("Mauvaise configuration pour le restart du job d'importation des pays", e);
        }
    }
}
