# Small Dependency-Injection lib

Does not support cyclic dependencies by design ( unhandled SO ) 🥱

Example: 

```
@Config
public class C {

    @Bean
    public A a(B b) {
        return new A(b);
    }
    @Bean
    public B b() {
        return new B();
     }
}

class A {
    private final B b;
    public A(B b) {
        this.b = b;
    } 
    public void call() { System.out.print(b); }
}

class B {}

```
```

public class Main {
        var app = ApplicationContext.fromConfig(C.class);
        A a = app.getBean(A.class);
        a.call();
}
```
