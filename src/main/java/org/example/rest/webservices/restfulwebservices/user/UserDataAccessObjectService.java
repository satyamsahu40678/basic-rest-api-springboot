package org.example.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDataAccessObjectService {
    //JPA/Hibernate > Database
    //UserDataAccessObjectService > Static List - first impplement in static then change to database

    private static List<User> users = new ArrayList<User>();
    private static int userCount = 0;

    static {
        users.add(new User(++userCount, "Satyam", LocalDate.now().minusYears(22)));
        users.add(new User(++userCount, "Her", LocalDate.now().minusYears(23)));
        users.add(new User(++userCount, "RBG", LocalDate.now().minusYears(20)));
    }

    //public List<User> findAll()
    public List<User> findAll(){
        return users;
    }

    //public User save(User user)
    public User save(User user){
        user.setId(++userCount);
        users.add(user);
        return user;
    }

    //public use findOne(int id)
    public User findOne(int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public void deleteById(int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }




}
