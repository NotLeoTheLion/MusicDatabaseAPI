package lt.viko.eif.groupproject.musicapi.controller;

import lt.viko.eif.groupproject.musicapi.model.User;
import lt.viko.eif.groupproject.musicapi.model.Track;
import lt.viko.eif.groupproject.musicapi.service.TrackService;
import lt.viko.eif.groupproject.musicapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private TrackService trackService;

    /**
     * Rodomas pagrindinis puslapis.
     *
     * @return Puslapio pavadinimas "main".
     */
    @GetMapping("/")
    public String showMainPage() {
        return "main";
    }

    /**
     * Rodomas prisijungimo puslapis.
     *
     * @return Puslapio pavadinimas "login".
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    /**
     * Vartotojo prisijungimo apdorojimas.
     *
     * @param username Vartotojo vardas.
     * @param password Vartotojo slaptažodis.
     * @param model Modelio objektas duomenų perdavimui į vaizdą.
     * @return Jei prisijungimas sėkmingas, grąžinamas "dashboard" puslapis, kitaip "login" puslapis.
     */
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            model.addAttribute("error", "Vartotojas neegzistuoja");
            return "login";
        } else if (!userService.checkCredentials(username, password)) {
            model.addAttribute("error", "Neteisingas slaptažodis");
            return "login";
        } else {
            model.addAttribute("username", username);
            model.addAttribute("userId", user.getId());
            List<Track> tracks = trackService.getAllTracks();
            model.addAttribute("tracks", tracks);
            return "dashboard";
        }
    }

    /**
     * Rodomas registracijos puslapis.
     *
     * @return Puslapio pavadinimas "register".
     */
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    /**
     * Vartotojo registracijos apdorojimas.
     *
     * @param username Vartotojo vardas.
     * @param password Vartotojo slaptažodis.
     * @return Jei registracija sėkminga, nukreipiama į "register" puslapį su parametru `success=true`, kitaip lieka "register" puslapyje.
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.saveUser(user);
            return "redirect:/register?success=true";
        }
        return "register";
    }

    /**
     * Rodomas vartotojo panelės puslapis.
     *
     * @param model Modelio objektas duomenų perdavimui į vaizdą.
     * @return Puslapio pavadinimas "dashboard".
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Track> tracks = trackService.getAllTracks();
        model.addAttribute("tracks", tracks);
        return "dashboard";
    }

    /**
     * Takelių paieška pagal pavadinimą.
     *
     * @param searchTerm Paieškos terminas.
     * @param model Modelio objektas duomenų perdavimui į vaizdą.
     * @return Puslapio pavadinimas "dashboard" su paieškos rezultatais.
     */
    @PostMapping("/search")
    public String searchTracks(@RequestParam String searchTerm, Model model) {
        List<Track> tracks = trackService.searchTracksByTitle(searchTerm);
        model.addAttribute("tracks", tracks);
        return "dashboard";
    }

    /**
     * Naujo takelio įkėlimas.
     *
     * @param title Takelio pavadinimas.
     * @param file Takelio failas.
     * @param trackPhoto Takelio nuotrauka.
     * @param userId Vartotojo ID.
     * @param model Modelio objektas duomenų perdavimui į vaizdą.
     * @return Nukreipimas į "dashboard" puslapį.
     */
    @PostMapping("/upload")
    public String uploadTrack(@RequestParam("title") String title,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("trackPhoto") MultipartFile trackPhoto,
                              @RequestParam("userId") Long userId,
                              Model model) {
        try {
            trackService.saveTrack(userId, title, file, trackPhoto);
            model.addAttribute("successMessage", "Takelis sėkmingai įkeltas!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Nepavyko įkelti takelio. Bandykite dar kartą.");
        }
        return "redirect:/dashboard";
    }

    /**
     * Vartotojo atsijungimas.
     *
     * @param request HttpServletRequest objektas sesijos valdymui.
     * @param response HttpServletResponse objektas nukreipimui po atsijungimo.
     * @throws IOException Jei atsiranda I/O klaida.
     */
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("/");
    }
}
