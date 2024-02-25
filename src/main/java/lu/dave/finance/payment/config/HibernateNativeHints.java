package lu.dave.finance.payment.config;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.GeneratorCreationContext;
import org.hibernate.generator.internal.CurrentTimestampGeneration;
import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;

// https://stackoverflow.com/questions/77791514/creationtimestamp-with-graalvm-triggers-java-lang-nosuchmethodexception-org-hi
public class HibernateNativeHints  implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(@NonNull RuntimeHints hints, @Nullable ClassLoader classLoader) {
        try {
            Constructor<CurrentTimestampGeneration> constructor1 =
                    ReflectionUtils.accessibleConstructor(CurrentTimestampGeneration.class,
                            CurrentTimestamp.class,
                            Member.class,
                            GeneratorCreationContext.class);
            Constructor<CurrentTimestampGeneration> constructor2 =
                    ReflectionUtils.accessibleConstructor(CurrentTimestampGeneration.class,
                            CreationTimestamp.class,
                            Member.class,
                            GeneratorCreationContext.class);
            Constructor<CurrentTimestampGeneration> constructor3 =
                    ReflectionUtils.accessibleConstructor(CurrentTimestampGeneration.class,
                            UpdateTimestamp.class,
                            Member.class,
                            GeneratorCreationContext.class);
            hints.reflection().registerConstructor(constructor1, ExecutableMode.INVOKE);
            hints.reflection().registerConstructor(constructor2, ExecutableMode.INVOKE);
            hints.reflection().registerConstructor(constructor3, ExecutableMode.INVOKE);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}