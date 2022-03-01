package example.stuff;

public class OtherService {

    private final Example someExample;

    public OtherService(Example someExample) {
        this.someExample = someExample;
    }

    public void call() {
        System.out.println("Hello from " + this.getClass().getSimpleName());
        someExample.greetings();

    }
}
