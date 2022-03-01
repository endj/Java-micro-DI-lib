package example;

import example.stuff.Example;
import example.stuff.OtherService;
import example.stuff.Repository;
import example.stuff.Service;
import library.Bean;
import library.Config;

@Config
public class Configuration {

    @Bean
    public Service someService(OtherService otherService,
                               Repository repository,
                               Example example) {
        return new Service(otherService, repository, example);
    }

    @Bean
    public Repository someRepository() {
        return new Repository();
    }

    @Bean
    public OtherService someOtherService(Example example) {
        return new OtherService(example);
    }

    @Bean
    public Example someExample() {
        return new Example();
    }
}
