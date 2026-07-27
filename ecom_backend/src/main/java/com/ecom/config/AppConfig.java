package com.ecom.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // to declare java config class - to declare spring beans
public class AppConfig {
	/*
	 * configure ModelMapper as spring bean
	 * - to transfer the matching props (name + data type) entity <-> dto
	 */
	@Bean
	ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT)
				// configure mapper - not to transfer nulls from src -> dest
				.setPropertyCondition(Conditions.isNotNull());
		return mapper; // Method rets configured ModelMapper bean to SC
	}

}
