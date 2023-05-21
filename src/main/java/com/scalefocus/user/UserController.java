package com.scalefocus.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {


  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }


  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUser(@PathVariable int id) {
    User ourUser = userService.getUserById(id);

    return ResponseEntity.ok(ourUser);
  }



  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest userRequest) {
    User user = userService.addUser(userRequest);

      URI location = UriComponentsBuilder.fromUriString("/users/{userid}")
                                       .buildAndExpand(user.getId())
                                       .toUri();
    return ResponseEntity.created(location).build();
  }


  @PutMapping("users/{id}")
  public String editUser(@PathVariable("id")   int id, @RequestParam("email")    String email, @RequestParam("fName") String fName,
                            @RequestParam("lName")  String lName){


    int n= userService.updateUser(id,email,fName,lName);
    if(n>0) {
      return "updated successful";
    }
    else {
      return "updating failed";
    }
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<UserDto> deleteUser(@PathVariable int id,
    @RequestParam(required = false) boolean returnOld) {

    UserDto userDto = userService.removeUser(id);
    if (returnOld) {
      return ResponseEntity.ok(userDto);
    } else {
      return ResponseEntity.noContent().build();
    }
  }
}


