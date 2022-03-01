package library;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.function.Predicate.not;

public class ApplicationContext {

    private final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();
    private final Map<Class<?>, Method> FACTORY_METHODS = new HashMap<>();

    public static <T> ApplicationContext fromConfig(Class<T> configClass) {
        if (!configClass.isAnnotationPresent(Config.class))
            throw new IllegalArgumentException();

        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.instantiateBeans(configClass);
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        return (T) BEAN_MAP.get(clazz);
    }

    private  <T> void instantiateBeans(Class<T> config) {
        var beanMethods = Arrays.stream(config.getMethods())
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .toList();

        beanMethods.forEach(method -> FACTORY_METHODS.put(method.getReturnType(), method));

        T instance = newInstance(config);

        for (var beanMethod : beanMethods) {
            initBean(instance, beanMethod);
        }
    }

    private  <T> void initBean(T instance, Method method) {
        Class<?> returnType = method.getReturnType();
        if (BEAN_MAP.containsKey(returnType)) return;

        Class<?>[] parameterTypes = method.getParameterTypes();
        Arrays.stream(parameterTypes).filter(not(BEAN_MAP::containsKey))
                .map(FACTORY_METHODS::get)
                .forEach(m -> initBean(instance, m));

        Object[] constructorParams = Arrays.stream(parameterTypes).map(BEAN_MAP::get).toArray();
        try {
            Object invoke = method.invoke(instance, constructorParams);
            BEAN_MAP.put(returnType, invoke);
        } catch (Exception e) {
            throw new InstantiationError(e.getMessage());
        }
    }

    private  <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new InstantiationError(e.getMessage());
        }
    }
}
