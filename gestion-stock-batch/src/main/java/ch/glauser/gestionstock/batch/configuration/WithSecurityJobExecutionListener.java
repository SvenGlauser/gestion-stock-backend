package ch.glauser.gestionstock.batch.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WithSecurityJobExecutionListener implements JobExecutionListener {

    private final AuthenticationManager authenticationManager;
    private final BatchProperties batchProperties;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(batchProperties.getUsername(), batchProperties.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContext runAsContext = SecurityContextHolder.createEmptyContext();
        runAsContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(runAsContext);
    }
}
