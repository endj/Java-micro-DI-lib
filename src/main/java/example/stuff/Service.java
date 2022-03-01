package example.stuff;

public class Service {

    private final OtherService someOtherService;
    private final Repository someRepository;

    public Service(OtherService someOtherService, Repository someRepository, Example someExample) {
        this.someOtherService = someOtherService;
        this.someRepository = someRepository;
    }

    public void call() {
        System.out.println("Hello from " + this.getClass().getSimpleName());
        someOtherService.call();
        someRepository.call();
    }
}
