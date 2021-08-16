package com.entis.util;

import com.entis.data.Student;

public class StudentUtils {

    private static final String[] names = {"Леха", "Саня", "Егор", "Нурсултан", "Игорь", "Владислав", "Владимир", "Ярослав", "Петр", "Жека"};
    private static final String[] surnames = {"Рофланов", "Нурсултанов", "Казахстанов", "Иванов", "Кузнецов", "Хартронов", "Бочкоделов", "Борисов", "Петров", "Сидоров"};
    private static final String[] lastnames = {"Батькович", "Нурсултанович", "Абдылханович", "Алексеевич", "Борисович", "Владиславович", "Владимирович", "Ярославович", "Петрович", "Евгенович"};

    public static Student generateSimple() {
        Student student = new Student();
        student.setPhoneNumber("0682282281");
        student.setFullName("Ivanov Ivan Batkovich");
        student.setAge(16);
        return student;
    }

    public static int getRandomNumberFrom0to9() {
        return (int) (Math.random() * 10);
    }

    public static Student generateRandom() {
        Student student = new Student();
        StringBuilder phone = new StringBuilder("068");
        for (int i = 0; i < 7; i++) {
            phone.append(getRandomNumberFrom0to9());
        }
        student.setPhoneNumber(phone.toString());
        student.setFullName(names[getRandomNumberFrom0to9()] + " " + surnames[getRandomNumberFrom0to9()] + " " + lastnames[getRandomNumberFrom0to9()]);
        student.setAge((int) (Math.random() * 100 + 1));
        return student;
    }
}
