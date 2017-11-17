package org.javers.mongo.javersmongoproblem;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.MappingStyle;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.mongo.javersmongoproblem.domain.Permission;
import org.javers.mongo.javersmongoproblem.domain.Role;
import org.javers.repository.mongo.MongoRepository;
import org.javers.spring.boot.mongo.JaversProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableMongoRepositories
public class JaversMongoProblemApplication {

	public static void main(String[] args) {
		SpringApplication.run(JaversMongoProblemApplication.class, args);
	}

	private static final String MONGO_DB_URL = "localhost";
	private static final String MONGO_DB_NAME = "embeded_db";

	@Bean
	public MongoTemplate mongoTemplate() throws IOException {
		EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
		mongo.setBindIp(MONGO_DB_URL);
		MongoClient mongoClient = mongo.getObject();
		return new MongoTemplate(mongoClient, MONGO_DB_NAME);
	}

	@DependsOn("mongoTemplate")
	@Bean
	public Javers javers(MongoTemplate mongoTemplate, JaversProperties javersProperties) {
		return JaversBuilder.javers()
				.withListCompareAlgorithm(ListCompareAlgorithm.valueOf(javersProperties.getAlgorithm().toUpperCase()))
				.withMappingStyle(MappingStyle.valueOf(javersProperties.getMappingStyle().toUpperCase()))
				.withNewObjectsSnapshot(javersProperties.isNewObjectSnapshot())
				.withPrettyPrint(javersProperties.isPrettyPrint())
				.withTypeSafeValues(javersProperties.isTypeSafeValues())
				.registerJaversRepository(new MongoRepository(mongoTemplate.getDb()))
				.withPackagesToScan(javersProperties.getPackagesToScan())
				.registerEntities(Role.class, Permission.class) // <-- added line
				.build();
	}
}
