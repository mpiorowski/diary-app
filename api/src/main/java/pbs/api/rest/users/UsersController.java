package pbs.api.rest.users;

import org.mapstruct.factory.Mappers;
import pbs.api.domain.user.UserEntity;
import pbs.api.domain.user.UsersService;
import pbs.api.rest.users.dto.UserAddRequestDto;
import pbs.api.rest.users.dto.UserEditRequestDto;
import pbs.api.rest.users.dto.UserResponseDto;
import pbs.api.rest.users.dto.UserTableDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersController {

  private final UsersService service;

  private UserMapper mapper = Mappers.getMapper(UserMapper.class);

  public UsersController(UsersService service) {
    this.service = service;
  }

  @GetMapping()
  public ResponseEntity getAllUsers() {

    List<UserResponseDto> allUsers = service.findAll().stream().map(mapper::entityToDto).collect(Collectors.toList());

    return ResponseEntity.ok(allUsers);
  }

//  // CHECK FOR ROLE_SUPER
//  @RolesAllowed("ROLE_ADMIN")
//  @PostMapping()
//  public ResponseEntity addUser(@Valid @RequestBody UserAddRequestDto addRequest) {
//
//    int id = userDao.addUser(addRequest);
//    return ResponseEntity.ok(id);
//  }
//
//  @RolesAllowed("ROLE_ADMIN")
//  @PutMapping("/{id}")
//  public ResponseEntity updateUser(
//      @PathVariable("id") int id, @Valid @RequestBody UserEditRequestDto editRequest) {
//
//    if (id == editRequest.getUserId()) {
//      userDao.updateUser(editRequest);
//    }
//    return ResponseEntity.ok(true);
//  }
//
//  @RolesAllowed("ROLE_ADMIN")
//  @DeleteMapping("/{id}")
//  public ResponseEntity deleteUser(@PathVariable("id") int id) {
//    userDao.deleteUser(id);
//    return ResponseEntity.ok(true);
//  }
}
