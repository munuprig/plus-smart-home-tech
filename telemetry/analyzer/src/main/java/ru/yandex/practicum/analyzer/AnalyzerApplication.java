package ru.yandex.practicum.analyzer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.*;

@SpringBootApplication
public class AnalyzerApplication {

	@Value("${threadPool.arrayBlockingQueue.capacity}")
	private final int arrayBlockingQueueCapacity = 2;
	@Value("${threadPool.corePoolSize}")
	private final int threadPoolCorePoolSize = 2;
	@Value("${threadPool.maximumPoolSize}")
	private final int threadPoolMaximumPoolSize = 2;
	@Value("${threadPool.keepAliveTime}")
	private final long threadPoolKeepAliveTime = 60L;

	public static void main(String[] args) {
		SpringApplication.run(AnalyzerApplication.class, args);
	}

	@Bean
	public ExecutorService getExecutorService() {
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(arrayBlockingQueueCapacity);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(threadPoolCorePoolSize, threadPoolMaximumPoolSize, threadPoolKeepAliveTime, TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());
		threadPoolExecutor.allowCoreThreadTimeOut(true);
		return threadPoolExecutor;
	}
}