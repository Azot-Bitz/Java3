import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class MainClass {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clazz = Test1.class;
        Object testObj = clazz.newInstance();
        ArrayList<Method> al = new ArrayList<>();
        Method beforeMethod = null;
        Method afterMethod = null;
        for (Method o : clazz.getDeclaredMethods()) {
            if (o.isAnnotationPresent(MyTest.class)) {
                al.add(o);
            }
            if (o.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeMethod == null) beforeMethod = o;
                else throw new RuntimeException("Больше одного метода с аннотацией BeforeSuite");
            }
            if (o.isAnnotationPresent(AfterSuite.class)) {
                if (afterMethod == null) afterMethod = o;
                else throw new RuntimeException("Больше одного метода с аннотацией AfterSuite");
            }
                al.sort(new Comparator<Method>() {
                    @Override
                    public int compare(Method o1, Method o2) {
                        return o2.getAnnotation(MyTest.class).priority()-o1.getAnnotation(MyTest.class).priority();
                    }
                });
            }
                if (beforeMethod != null) beforeMethod.invoke(testObj, null);
                for (Method o : al) o.invoke(testObj, null);
                if (afterMethod != null) afterMethod.invoke(testObj, null);

            }


        }

