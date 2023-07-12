package com.example.batchexample.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j  // log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor    // Constructor DI를 위한 lombok 어노테이션
@Configuration  // Spring Batch 의 모든 Job 은 @Configuration 으로 등록해서 사용한다.
public class SimpleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * Batch Job 을 생성하는 simpleJob 은 simpleStep1 을 품고 있다.
     */

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")   // simpleJob 이란 이름의 Batch Job을 생성한다. job 의 이름을 별도로 지정하지 않고 Builder 를 통해 지정한다.
                .start(simpleStep1())
                .build();
    }

    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")    // simpleStep1 이란 이름의 Batch Step 을 생선한다. jobBuilderFactory 와 동일하게 Builder를 통해 이름을 지정한다.
                .tasklet((contribution, chunkContext) -> {  // Step 안에서 수행될 기능들을 명시한다. Tasklet Step 안에서 단일로 수행될 커스텀한 기능들을 선언할 때 사용한다.
                    log.info(">>>>>>>>>> This is Step1");   // Batch 가 수행되면 log.info 가 출력한다.
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
