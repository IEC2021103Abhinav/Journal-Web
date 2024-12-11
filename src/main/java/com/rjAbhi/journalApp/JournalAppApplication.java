package com.rjAbhi.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JournalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalAppApplication.class, args);
	}

//	@Bean
//	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory)
//	{
////		MongoDatabaseFactory ek interface hai jo ki coonection banaeyaga database se
//		return new MongoTransactionManager(dbFactory);
//	}
}
