package lt.viko.eif.groupproject.musicapi.service;

import lt.viko.eif.groupproject.musicapi.model.User;
import lt.viko.eif.groupproject.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Paslaugų klasė, skirta vartotojų susijusioms operacijoms tvarkyti.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Grąžina visų vartotojų sąrašą.
     *
     * @return vartotojų sąrašas
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Grąžina vartotoją pagal ID.
     *
     * @param id vartotojo ID
     * @return vartotojas pagal ID arba null, jei nerasta
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Grąžina vartotoją pagal vartotojo vardą.
     *
     * @param username vartotojo vardas
     * @return vartotojas pagal vartotojo vardą arba null, jei nerasta
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Išsaugo naują vartotoją arba atnaujina esamą.
     *
     * @param user naujas vartotojas
     * @return išsaugotas vartotojas
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Ištrina vartotoją pagal ID.
     *
     * @param id vartotojo ID
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Tikrina vartotojo prisijungimo duomenis.
     *
     * @param username vartotojo vardas
     * @param password vartotojo slaptažodis
     * @return true, jei vartotojo vardas ir slaptažodis sutampa, kitaip false
     */
    public boolean checkCredentials(String username, String password) {
        User user = getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Registruoja naują vartotoją.
     *
     * @param user naujas vartotojas
     * @return true, jei registracija sėkminga, false, jei vartotojo vardas jau egzistuoja
     */
    public boolean registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false; // Vartotojo vardas jau egzistuoja
        }
        userRepository.save(user);
        return true;
    }
}
