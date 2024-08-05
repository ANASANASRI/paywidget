package ma.m2t.paywidget.controller;


import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.validation.Valid;
import ma.m2t.paywidget.dto.MarchandDTO;
import ma.m2t.paywidget.service.serviceImpl.EmailService;
import ma.m2t.paywidget.enums.ERole;
import ma.m2t.paywidget.enums.UserStatus;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;
import ma.m2t.paywidget.model.Role;
import ma.m2t.paywidget.model.User;
import ma.m2t.paywidget.payload.request.LoginRequest;
import ma.m2t.paywidget.payload.request.SignupRequest;
import ma.m2t.paywidget.payload.request.UpdateProfileRequest;
import ma.m2t.paywidget.payload.response.JwtResponse;
import ma.m2t.paywidget.payload.response.MessageResponse;
import ma.m2t.paywidget.repository.RoleRepository;
import ma.m2t.paywidget.repository.UserRepository;
import ma.m2t.paywidget.security.jwt.JwtUtils;
import ma.m2t.paywidget.security.jwt.services.UserDetailsImpl;
import ma.m2t.paywidget.security.jwt.services.UserService;
import ma.m2t.paywidget.service.MarchandService;
import ma.m2t.paywidget.service.serviceImpl.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MarchandService marchandService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        // Check if user exists and its status
        if (userOptional.isEmpty() || userOptional.get().getStatus().equals(UserStatus.Inactive)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is inactive or does not exist");
        }

        User user = userOptional.get();

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        System.out.println(jwt + "   " + userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                roles));
    }


    @PutMapping("/users/{userId}/password")
    public ResponseEntity<?> updatePassword(
            @PathVariable Long userId, @Valid @RequestBody ChangePasswordRequest updatePasswordRequest) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        User user = entityManager.find(User.class, userId);
        if (user != null && encoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(updatePasswordRequest.getNewPassword()));
            entityManager.persist(user);
        } else {
            throw new BadCredentialsException("Invalid old password");
        }

        transaction.commit();
        entityManager.close();

        return ResponseEntity.ok(new MessageResponse("Password updated successfully"));
    }


//    @PutMapping("/users/{userId}/password")
//    public ResponseEntity<?> updatePassword(
//            @PathVariable Long userId, @Valid @RequestBody ChangePasswordRequest updatePasswordRequest) {
//
//        User user = userRepository.getReferenceById(userId);
//
//        if (!encoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
//            throw new BadCredentialsException("Invalid old password");
//        }
//
//        user.setPassword(encoder.encode(updatePasswordRequest.getNewPassword()));
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("Password updated successfully"));
//    }

    @GetMapping("/users")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userRepository.findAllUsersSortedById();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/admin")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<User>> findAdmin() {
        List<User> users = userRepository.findAdmin();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/mod")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<User>> findMod() {
        List<User> users = userRepository.findMod();
        return ResponseEntity.ok(users);
    }

    // Importez la classe List si ce n'est pas déjà fait

    @GetMapping("/users/roles/{username}") // Modifiez le chemin de l'endpoint pour refléter qu'il peut renvoyer plusieurs rôles
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ERole>> findRoles(@PathVariable String username) {
        List<ERole> roles = userRepository.findRolesByUsername(username); // Assurez-vous que la méthode findRoles de votre repository retourne une liste de rôles
        return ResponseEntity.ok(roles);
    }


    @GetMapping("/users/user")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<User>> findUser() {
        List<User> users = userRepository.findUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/userbymarchandid/{marchandId}")
    @PreAuthorize("permitAll()")
    public Long findUserByMarchandId(@PathVariable Long marchandId) throws MarchandNotFoundException {
        MarchandDTO marchandDTO = marchandService.findMarchandById(marchandId);

        String marchandName =marchandDTO.getMarchandName();

        Optional<User> user = userRepository.findByUsername(marchandName);

        return user.get().getId();
    }



    @PutMapping("/updateprofil/{username}")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UpdateProfileRequest signupRequest, @PathVariable String username) {
        // Retrieve the current authenticated user
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = username;
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + currentUsername));

        // Update user information
        currentUser.setFirstName(signupRequest.getFirstName());
        currentUser.setLastName(signupRequest.getLastName());
        currentUser.setUsername(signupRequest.getUsername());

        if(!encoder.matches(signupRequest.getOldPassword(), currentUser.getPassword())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Old password is incorrect."));
        }
        // Update password if provided
        if (!signupRequest.getNewPassword().equals("")) {

            if (encoder.matches(signupRequest.getOldPassword(), currentUser.getPassword())) {
                currentUser.setPassword(encoder.encode(signupRequest.getNewPassword()));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Old password is incorrect."));
            }
        }

        // Save the updated user
        userRepository.save(currentUser);

        return ResponseEntity.ok(new MessageResponse("User profile updated successfully!"));
    }



    //update by id
    @PutMapping("/updateprofile/{id}")
    public ResponseEntity<?> updateUserProfileById(@Valid @RequestBody UpdateProfileRequest updateProfileRequest, @PathVariable Long id) {
        // Rechercher l'utilisateur par ID dans la base de données
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Mettre à jour les informations de l'utilisateur
        currentUser.setFirstName(updateProfileRequest.getFirstName());
        currentUser.setLastName(updateProfileRequest.getLastName());
        currentUser.setUsername(updateProfileRequest.getUsername());
        currentUser.setProfilLogoUrl(updateProfileRequest.getProfilLogoUrl());

        //  currentUser.setProfilLogoUrl(signupRequest.getProfilLogoUrl());
        currentUser.setEmail(updateProfileRequest.getEmail());
        currentUser.setStatus(UserStatus.valueOf(updateProfileRequest.getStatus())); // Mettre à jour le statut

        // Mettre à jour les rôles de l'utilisateur
        Set<Role> roles = new HashSet<>(updateProfileRequest.getRoles());
        currentUser.setRoles(roles);

        // Enregistrer l'utilisateur mis à jour dans la base de données
        userRepository.save(currentUser);

        return ResponseEntity.ok(new MessageResponse("User profile updated successfully!"));
    }


//    @PostMapping("/signup")
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//
//        // Créer un nouvel utilisateur avec le statut par défaut "Active"
//        User user = new User(
//                signUpRequest.getUsername(),
//                signUpRequest.getFirstName(),
//                signUpRequest.getLastName(),
//                UserStatus.Active,
//                signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()), // Utilisez le mot de passe fourni
//                signUpRequest.getProfilLogoUrl(),
//                new HashSet<>() // Créez un ensemble vide pour les rôles
//        );
//
//        // Define the roles of the user
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles != null) {
//            strRoles.forEach(role -> {
//                switch (role.toUpperCase()) {
//                    case "ROLE_ADMIN":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_ADMIN is not found."));
//                        roles.add(adminRole);
//                        break;
//                    case "ROLE_MARCHAND":
//                        Role marchandRole = roleRepository.findByName(ERole.ROLE_MARCHAND)
//                                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_MARCHAND is not found."));
//                        roles.add(marchandRole);
//                        break;
//                    case "ROLE_COMMERCIAL":
//                        Role commercialRole = roleRepository.findByName(ERole.ROLE_COMMERCIAL)
//                                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_COMMERCIAL is not found."));
//                        roles.add(commercialRole);
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_USER is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//
//        // Définir les rôles de l'utilisateur
////        setRoles(signUpRequest.getRoles(), user.getRoles());
//
//        user.setRoles(roles);
//        userRepository.save(user);
//        // Envoyer l'email basé sur le rôle de l'utilisateur
//        sendRoleBasedEmail(user, signUpRequest.getPassword());
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        System.out.println("Received SignupRequest: " + signUpRequest);

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                UserStatus.Active,
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getProfilLogoUrl(),
                new HashSet<>()
        );

        // Définir les rôles de l'utilisateur
        setRoles(signUpRequest.getRoles(), user.getRoles());

        userRepository.save(user);


        // Extract and print role names
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .map(Enum::name)
                .collect(Collectors.toSet());
        System.out.println("Assigned Role Names: " + roleNames);


        System.out.println("Final User: " + user);

        sendRoleBasedEmail(user, signUpRequest.getPassword());

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private void setRoles(Set<String> strRoles, Set<Role> roles) {
        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role foundRole = roleRepository.findByName(ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(foundRole);
            });
        }
    }







    //Cette  méthode est utiliser pour envoyer des emails basés sur le rôle
    private void sendRoleBasedEmail(User user, String password) {
        boolean isMarchand = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(ERole.ROLE_MARCHAND));

        boolean isCommercial = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(ERole.ROLE_COMMERCIAL));

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN));

        try {
            if (isMarchand) {
                emailService.sendPasswordMarchandEmail(user.getEmail(), user.getUsername(), password);
            } else if (isCommercial) {
                emailService.sendPasswordCommercialEmail(user.getEmail(), user.getUsername(), password);
            } else if (isAdmin) {
                emailService.sendPasswordAdminEmail(user.getEmail(), user.getUsername(), password);
            }
        } catch (MessagingException e) {
            // Gérer les exceptions d'envoi d'email
            throw new RuntimeException("User registered but failed to send email: " + e.getMessage());
        }
    }


        // Méthode pour générer un mot de passe aléatoire
    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        byte[] passwordBytes = new byte[8]; // Longueur du mot de passe
        random.nextBytes(passwordBytes);
        return Base64.getEncoder().encodeToString(passwordBytes);
    }


    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')") // Assurez-vous que l'utilisateur a le rôle approprié
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/findMarchandIdByUserId/{userId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Long> findMarchandIdByUserId(@PathVariable Long userId) {
        // Retrieve the user by userId
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String userName = user.getUsername(); // Assuming the username is the same as the marchand name

            // Get the marchandId
            Long marchandId = (long) marchandService.findMarchandIdbyMarchandName(userName);

            // Check if marchandId is not null or handle case if not found
            if (marchandId != null) {
                return ResponseEntity.ok(marchandId);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




    @GetMapping("/findbyid/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

}