package ru.sabirzyanov.springtest.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import ru.sabirzyanov.springtest.domain.History;
import ru.sabirzyanov.springtest.domain.Role;
import ru.sabirzyanov.springtest.domain.User;
import ru.sabirzyanov.springtest.repos.HistoryRepository;
import ru.sabirzyanov.springtest.repos.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Marselius on 12.12.2018.
 */

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user.getActivationCode() != null ) {
            throw new UsernameNotFoundException("email is not activated");
        }

        return user;
    }

    public String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean addUser(User user, Model model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        User userEmail = userRepository.findByEmail(user.getEmail());

        if (userFromDb != null) {
            model.addAttribute("usernameError", "User exists");
            return false;
        }

        if (userEmail != null) {
            model.addAttribute("emailError", "Email exists");
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(RandomStringUtils.randomAlphanumeric(6));
        sendMessage(user, user.getPassword());
        user.setPassword(encodedPassword(user.getPassword()));

        userRepository.save(user);

        //sendMessage(user);

        return true;
    }

    private void sendMessage(User user, String password) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
              "Hello, %s! \n" +
                      "Welcome. Please visit this link for the activation your account: http://localhost:8080/activate/%s " +
                    "This is your password: %s",
                    user.getUsername(),
                    user.getActivationCode(),
                    password
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome. Please visit this link for the activation your account: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findUser(String username){
        if (userRepository.findByUsername(username) == null)
            return null;
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user,
                         String username,
                         String email,
                         User admin,
                         Map<String, String> form,
                         Model model
    ) {
            String oldUsername = user.getUsername();
            if (!username.equals(user.getUsername())) {
                user.setUsername(username);
                model.addAttribute("usernameSuccess", "Username successfully changed");
            }

            if (!email.equals(user.getEmail())) {
                user.setEmail(email);
                user.setActivationCode(UUID.randomUUID().toString());
                user.setPassword(RandomStringUtils.randomAlphanumeric(6));
                sendMessage(user, user.getPassword());
                user.setPassword(encodedPassword(user.getPassword()));
                model.addAttribute("emailSuccess", "Email successfully changed, please activate your email and change password");
            }

            Set<String> roles = Arrays.stream(Role.values())
                    .map(Role::name)
                    .collect(Collectors.toSet());

            user.getRoles().clear();

            for (String key : form.keySet()) {
                if (roles.contains(key)) {
                    user.getRoles().add(Role.valueOf(key));
                }
            }

            userRepository.save(user);


            if (!oldUsername.equals(username)) {
                Date date = new Date();
                History history = new History(date, user.getScore(), user, admin);
                history.setOp("Username was changed from " + oldUsername + " to " + username);
                historyRepository.save(history);
            }

    }

    public void userListCreator(Model model, Pageable pageable, String username) {
        Page<User> page;
        if (username != null && !username.isEmpty()) {
            if (findUser(username) != null ) {
                List<User> userList = new ArrayList<>();
                userList.add(findUser(username));
                model.addAttribute("usersList", userList);
            }
            else {
                page = findAll(pageable);
                model.addAttribute("errorMessage", "User not found");
                model.addAttribute("page", page);
            }
        } else {
            page = findAll(pageable);
            model.addAttribute("page", page);
        }

        model.addAttribute("username", username);
        model.addAttribute("url", "/user");
    }

    public void updateProfile(User user, String password, String email, Model model) {

        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.isEmpty() && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
            model.addAttribute("emailSuccess", "Email successfully updated");

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (!password.equals("")) {
            user.setPassword(encodedPassword(password));
            model.addAttribute("passwordSuccess", "Password successfully updated");
        }

        //TODO успешно сохранено

        userRepository.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }

    public void addPoints(String username, Long discount, Long points, User admin) {
        if (points > 0) {
            Long oldScore;
            oldScore = userRepository.findByUsername(username).getScore();
            userRepository.findByUsername(username).setScore(points*discount/100 + oldScore); //20% sale
            userRepository.save(userRepository.findByUsername(username));

            Date date = new Date();
            History history = new History(date, userRepository.findByUsername(username).getScore(), userRepository.findByUsername(username), admin);
            history.setOp("+" + (points*discount/100));
            historyRepository.save(history);
        }
    }

    public void activatePoints(User user, User admin) {
        if (user.getScore() >= 500) {
            user.setScore(user.getScore()-500);
            userRepository.save(user);

            Date date = new Date();
            History history = new History(date, user.getScore(), user, admin);
            history.setOp("-" + 500);
            historyRepository.save(history);
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
