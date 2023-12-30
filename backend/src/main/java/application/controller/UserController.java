package application.controller;

import application.controller.observer.UserRegistrationObserver;
import application.entity.User;
import application.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:5173")
public class UserController{

private final UserRepository userRepository;
    private boolean setUserRegistered = false;
    private List<UserRegistrationObserver> observers = new ArrayList<>();

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping
    public List<User> listUsers() {
        return  userRepository.findAll();
    }
    @GetMapping("/{id}")
    public User getDepartment(@PathVariable long id) {
        return userRepository.getReferenceById(id);
    }
    @PostMapping
    public void insertUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/registration-status")
    public void setUserRegistered(@RequestBody boolean value) {
        this.setUserRegistered = value;
        notifyAllObservers();
    }
    @GetMapping("/registration-status")
    public boolean getUserRegistrationStatus() {
        return setUserRegistered;
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        User existingUser = userRepository.getReferenceById(user.getId());
        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setEmail(user.getEmail());
        existingUser.setId(user.getId());
        existingUser.setPassword(user.getPassword());
        existingUser.setDescription(user.getDescription());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(existingUser);
    }

    private void notifyAllObservers() {
        observers.forEach(observer -> observer.onUserRegistrationStatusChanged(setUserRegistered));
    }
    public void registerObserver(UserRegistrationObserver observer) {
        observers.add(observer);
    }
}
