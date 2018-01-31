package demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService)context.getBean("userService");
        User user = new User();
        user.setId(5);
        user.setName("gxh19");
        user.setAge(13);
        user.setSchool("sspu");
        userService.update(user);
    }
}
