package demo;

import export.annotation.Export;
import export.annotation.Import;

import java.util.Date;

public class UserData {

    private String name;
    private int age;
    private String school;
    private Date birthday;

    public UserData() {
    }

    public UserData(String name, int age, String school, Date birthday) {
        this.name = name;
        this.age = age;
        this.school = school;
        this.birthday = birthday;
    }

    @Export(description="姓名", index="0")
    public String getName() {
        return name;
    }

    @Import(index="0")
    public void setName(String name) {
        this.name = name;
    }

    @Export(description="年龄", index="1")
    public int getAge() {
        return age;
    }

    @Import(index="1")
    public void setAge(int age) {
        this.age = age;
    }

    @Export(description="学校", index="2")
    public String getSchool() {
        return school;
    }

    @Import(index="2")
    public void setSchool(String school) {
        this.school = school;
    }

    @Export(description="生日", index="3")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
