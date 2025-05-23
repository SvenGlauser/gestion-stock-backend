package ch.glauser.gestionstock.batch.configuration;

import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.pays.model.Pays;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {

    @Bean("jobPays")
    public Job jobPays(JobRepository jobRepository,
                       @Qualifier("stepPays") Step stepPays,
                       @Qualifier("stepLocaliteCH") Step stepLocaliteCH,
                       WithSecurityJobExecutionListener jobExecutionListener) {
        return new JobBuilder("job-pays", jobRepository)
                .start(stepPays)
                .next(stepLocaliteCH)
                .listener(jobExecutionListener)
                .build();
    }

    @Bean("stepPays")
    public Step stepPays(JobRepository jobRepository,
                         PlatformTransactionManager transactionManager,
                         @Qualifier("paysItemReader") ItemReader<Object> reader,
                         @Qualifier("paysItemProcessor") ItemProcessor<Object, Pays> processor,
                         @Qualifier("paysItemWriter") ItemWriter<Pays> writer) {
        return new StepBuilder("step-pays", jobRepository)
                .<Object, Pays>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean("stepLocaliteCH")
    public Step stepLocaliteCH(JobRepository jobRepository,
                         PlatformTransactionManager transactionManager,
                         @Qualifier("localiteItemReader") ItemReader<Object> reader,
                         @Qualifier("localiteItemProcessor") ItemProcessor<Object, Localite> processor,
                         @Qualifier("localiteItemWriter") ItemWriter<Localite> writer) {
        return new StepBuilder("step-localite-CH", jobRepository)
                .<Object, Localite>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
