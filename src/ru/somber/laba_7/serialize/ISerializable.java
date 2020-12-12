package ru.somber.laba_7.serialize;

/**
 * Интерфейс для сериализируемых объектов.
 */
public interface ISerializable {

    /**
     * Производит сохранение сериализируемого объекта.
     * Возвращается строка с данными объекта.
     */
    String save();

    /**
     * Производит загрузку данных объекта из переданной строки.
     */
    void load(String record);

}
