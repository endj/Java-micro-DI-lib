package example;

import example.stuff.Example;
import example.stuff.Service;
import library.ApplicationContext;

public class Application {

    public static void main(String[] args) {
        var applicationContext = ApplicationContext.fromConfig(Configuration.class);
        Service service = applicationContext.getBean(Service.class);
        service.call();

        Example example = applicationContext.getBean(Example.class);
        example.greetings();
    }
}
