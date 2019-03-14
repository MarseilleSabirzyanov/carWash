package ru.sabirzyanov.springtest.service;

import org.springframework.security.authentication.LockedException;
import  ru.sabirzyanov.springtest.exceptions.*;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UserNotFoundExceptionCustom, LockedException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundExceptionCustom("");
        }

        if (user.getActivationCode() != null ) {
            throw new LockedException("email не активирован");
            //throw new UsernameNotFoundException("email is not activated");
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
            model.addAttribute("usernameError", "Пользователь с таким ID уже существует");
            return false;
        }

        if (userEmail != null) {
            model.addAttribute("emailError", "Email уже используется");
            return false;
        }

        if (user.getSurname() == null) {
            user.setSurname("");
        }
        if (user.getPhone() == null) {
            user.setPhone("");
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
              "Здравствуйте, %s! \n" +
                      "Пожалуйста, перейдите по данной ссылке, чтобы активировать свой аккаунт: http://localhost:8080/activate/%s " +
                    "Это ваш пароль: %s",
                    user.getName(),
                    user.getActivationCode(),
                    password
            );

            mailSender.send(user.getEmail(), "Код активации Сююмбике", message);
        }
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Пожалуйста, перейдите по данной ссылке, чтобы активировать свой аккаунт: http://localhost:8080/activate/%s",
                    user.getName(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Код активации Сююмбике", message);
        }
    }

    private void sendMessage(String email, String password) {
        if (!StringUtils.isEmpty(findUserByEmail(email).getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Это ваш новый пароль: %s",
                    findUserByEmail(email).getName(),
                    password
            );

            mailSender.send(email, "Восстановление пароля Сююмбике", message);
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
                         String email,
                         //User admin,
                         Map<String, String> form,
                         Model model,
                         String name,
                         String surname,
                         String phone
    ) {
            if (!email.equals(user.getEmail())) {
                user.setEmail(email);
                user.setActivationCode(UUID.randomUUID().toString());
                user.setPassword(RandomStringUtils.randomAlphanumeric(6));
                sendMessage(user, user.getPassword());
                user.setPassword(encodedPassword(user.getPassword()));
                model.addAttribute("emailSuccess", "Email успешно изменен, пожалуйста активируйте ваш email");
            }

            if (!name.equals(user.getName())) {
                user.setName(name);
                model.addAttribute("nameSuccess", "Имя успешно изменено");
            }
            if (surname != null && !surname.equals(user.getSurname())) {
                user.setSurname(surname);
                model.addAttribute("surnameSuccess", "Фамилия успешно изменена");
            }
            if (phone != null && !phone.equals(user.getPhone())) {
                user.setPhone(phone);
                model.addAttribute("phoneSuccess", "номер телефона успешно изменен");
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


            /*  if (!oldUsername.equals(username)) {
                Date date = new Date();
                History history = new History(date, user.getScore(), user, admin);
                history.setOp("Username was changed from " + oldUsername + " to " + username);
                historyRepository.save(history);
            }*/

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
                model.addAttribute("errorMessage", "Пользователь не найден");
                model.addAttribute("page", page);
            }
        } else {
            page = findAll(pageable);
            model.addAttribute("page", page);
        }

        model.addAttribute("username", username);
        model.addAttribute("url", "/user");
    }

    public void updateProfile(User user, String password, String email, Model model, String name, String surname, String phone) {

        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.isEmpty() && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
            model.addAttribute("emailSuccess", "Email успешно изменен");

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (!password.equals("")) {
            user.setPassword(encodedPassword(password));
            model.addAttribute("passwordSuccess", "Пароль успешно изменен");
        }

        if (name == null || name.isEmpty()){
            model.addAttribute("nameError", "Имя не может быть пустым");
        } else
        if (!name.equals(user.getName())) {
            user.setName(name);
            model.addAttribute("nameSuccess", "Имя успешно изменено");
        }
        if (surname != null && !surname.equals(user.getSurname())) {
            user.setSurname(surname);
            model.addAttribute("surnameSuccess", "Фамилия успешно изменена");
        }
        if (phone != null && !phone.equals(user.getPhone())) {
            user.setPhone(phone);
            model.addAttribute("phoneSuccess", "Номер телефона успешно изменен");
        }

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

    public void activatePoints(User user, User admin, Long activatedPoints, Model model) {
        if (user.getScore() < activatedPoints) {
            model.addAttribute("activatedPointsError", "У вас недостаточно баллов");
        }
        else if (activatedPoints < 0) {
            model.addAttribute("activatedPointsError", "Активируемые баллы не могут быть меньше 0");
        } else {
            user.setScore(user.getScore() - activatedPoints);
            userRepository.save(user);

            Date date = new Date();
            History history = new History(date, user.getScore(), user, admin);
            history.setOp("-" + activatedPoints);
            historyRepository.save(history);
            model.addAttribute("activatedPointsSuccess", "Баллы успешно активированы");
        }

        /*if (user.getScore() >= 500) {
            user.setScore(user.getScore()-500);
            userRepository.save(user);

            Date date = new Date();
            History history = new History(date, user.getScore(), user, admin);
            history.setOp("-" + 500);
            historyRepository.save(history);
        }*/
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void restoreAccount(String email, Model model) {
        if (findUserByEmail(email) != null) {
            User user = findUserByEmail(email);
            user.setPassword(RandomStringUtils.randomAlphanumeric(6));
            sendMessage(email, user.getPassword());
            user.setPassword(encodedPassword(user.getPassword()));
            model.addAttribute("emailSuccess", "Новый пароль отправлен на почту");

            userRepository.save(user);
        } else {
            model.addAttribute("emailError", "Email не найден");
        }
    }

    public boolean checkUsername(String username, Model model) {
        if (username.length() != 7) {
            model.addAttribute("usernameError", "Длина user ID должна быть равна 7");
            return false;
        }
        for (int i = 0; i < username.length(); i++) {
            if (!Character.isDigit(username.charAt(i))) {
                model.addAttribute("usernameError", "User ID может состоять только из цифр");
                return false;
            }
        }
        return true;
    }
}
