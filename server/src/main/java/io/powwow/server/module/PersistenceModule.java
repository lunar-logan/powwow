package io.powwow.server.module;

import io.powwow.core.dao.DefinitionDAO;
import io.powwow.core.dao.ExecutionDAO;
import io.powwow.dao.redis.RedisDefinitionDAO;
import io.powwow.dao.redis.RedisExecutionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class PersistenceModule {

    @Bean
    public ExecutionDAO getExecutionDAOBean(Jedis redis) {
        return new RedisExecutionDAO(redis);
    }

    @Bean
    public DefinitionDAO getDefinitionDAOBean(Jedis redis) {
        return new RedisDefinitionDAO(redis);
    }
}
