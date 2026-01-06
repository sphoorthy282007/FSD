package com.fsd;

class Student {
    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

public class Encapsulation_Student {

    public static void main(String[] args) {

        Student s = new Student();
        s.setId(101);
        s.setName("Sphoorthy");

        System.out.println(s.getId());
        System.out.println(s.getName());
    }
}
