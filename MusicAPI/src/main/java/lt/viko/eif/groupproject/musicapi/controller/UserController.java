package lt.viko.eif.groupproject.musicapi.controller;

import lt.viko.eif.groupproject.musicapi.model.User;
import lt.viko.eif.groupproject.musicapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Šis kontroleris tvarko API užklausimus, susijusius su vartotojais.
 */
@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Grąžina visų vartotojų sąrašą.
     *
     * @return visų vartotojų sąrašas
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Grąžina vartotoją pagal ID.
     *
     * @param id vartotojo ID
     * @return rastas vartotojas arba null, jei vartotojas nerastas
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Sukuria naują vartotoją.
     *
     * @param user naujas vartotojo objektas
     * @return sukurtas vartotojas
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Ištrina vartotoją pagal ID.
     *
     * @param id vartotojo ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Registruoja naują vartotoją.
     *
     * @param username vartotojo vardas
     * @param password slaptažodis
     * @param model Model objektas, naudojamas perduoti duomenis į šabloną
     * @return peradresavimo URL į prisijungimo puslapį su sėkmės parametru arba likti registracijos puslapyje su klaidos pranešimu
     */
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        if (!userService.registerUser(user)) {
            model.addAttribute("error", "Vartotojas su šiuo vartotojo vardu jau egzistuoja.");
            return "register";
        }

        return "redirect:/login?success=true";
    }
}