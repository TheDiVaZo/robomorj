# Конфигурация
Для создания и работы с конфигурацией используется фреймворк https://github.com/SpongePowered/Configurate
## Создание и загрузка
Для создания класса-конфига нужно создать класс наследуемый от ConfigHolder, добавить на класс аннотацию `@ConfigSerializeable` и создать пустой и `super` конструктор:

```java
@ConfigSerializable
@FieldDefaults(level = AccessLevel.PUBLIC)
public class MainConfig extends ConfigHolder<MainConfig> {

    public MainConfig(File baseFilePath) {
        super(baseFilePath);
    }

    public MainConfig() {
        this(null);
    }
}
```
Пример как его можно загружать:

```java
public void loadConfigs(File folder) {
File configFile = new File(folder.getAbsolutePath() + File.separatorChar + "config.yml");
MainConfig config = new MainConfig(configFile).loadOrCreateConfig();
}
```
## О модификаторах
Рассмотрим важные замечания по поводу модификаторов на полях:
```java
@ConfigSerializable
@FieldDefaults(level = AccessLevel.PUBLIC)
public class MainConfig extends ConfigHolder<MainConfig> {

    String coffee = "Американо";

    final String blackTea = "Цейлонский"; // кинет ошибку

    transient String greenTea = "Сентя"; // не сериализуется

    public MainConfig(File baseFilePath) {
        super(baseFilePath);
    }

    public MainConfig() {
        this(null);
    }
}
```
## Другие типы. Сериализаторы
Конфиги могут по умолчанию сериализовывать/десериализовывать примитивные виды, а также их обёртки, в java, также в список по умолчанию входят: `Enum`, `ArrayList` (включая `List` и другие), массивы, `HashMap` (включая `Map` и другие).

Для использования других типов вы можете использовать сериализатор. Их нужно писать вручную.

Напишем собственный сериализатор, например для `Duration` (`java.time.Duration`). Для этого создадим новый класс и имплементируем интерфейс `TypeSerializer<Duration>`:
```java
public class DurationSerializer implements TypeSerializer<Duration> {

    @Override
    public Duration deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return null;
    }

    @Override
    public void serialize(Type type, @Nullable Duration obj, ConfigurationNode node) throws SerializationException {

    }
}
```
Как мы видим, тут есть методы для десериализации и сериализации, но также есть методы и для значений по умолчанию - emptyValue. Заполним методы и переопределим emptyValue:
```java
public class DurationSerializer implements TypeSerializer<Duration> {

    @Override
    public Duration deserialize(Type type, ConfigurationNode node) throws SerializationException {
        if (node.empty()) {
            throw new SerializationException("Node is empty");
        }

        Duration duration = Duration.parse(node.getString(""));
        if (duration.isNegative() || duration.isZero()) {
            throw new SerializationException("Duration must be > 0");
        }
        return duration;
    }

    @Override
    public void serialize(Type type, @Nullable Duration obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.raw(null);
            return;
        }
        node.set(obj.toString());
    }

    @Override
    public @Nullable Duration emptyValue(Type specificType, ConfigurationOptions options) {
        return Duration.of(1, ChronoUnit.DAYS);
    }
}
```
## Сериализуем по-другому
Мы можем сериализовывать классы по-другому, рассмотрим на примере:
```java
@ConfigSerializable
@FieldDefaults(level = AccessLevel.PUBLIC)
public class MainConfig extends ConfigHolder<MainConfig> {

    Coffee americano = new Coffee("Американо");
    Coffee latte = new Coffee("Латте");

    public MainConfig(File baseFilePath) {
        super(baseFilePath);
    }

    public MainConfig() {
        this(null);
    }

    @ConfigSerializable
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PUBLIC)
    public static class Coffee {
        String name;
    }
}
```
Обратим внимание, что на этот другой класс обязательно должен быть статичен, у него должен быть конструктор без аргументов, а также он должен быть помечен как аннотацией `@ConfigSerializable`.

## Пример RawConfigContainer
```java
public class ConfigLoader extends RawConfigContainer {
public ConfigLoader(File path) {
super(path);
}

    public void loadConfigs() {
        loadConfigs(
                new DatabaseConfig(toFile("database.yml")),
                new MainConfig(toFile("config.yml")),
                new LanguageConfig(toFile("lang.yml"))
        );
    }

    public DatabaseConfig getDatabaseConfig() {
        return get(DatabaseConfig.class);
    }

    public MainConfig getMainConfig() {
        return get(MainConfig.class);
    }

    public LanguageConfig getLanguageConfig() {
        return get(LanguageConfig.class);
    }
}
```