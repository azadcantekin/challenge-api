package com.techcareer.challenge.controller;

import com.techcareer.challenge.data.dto.RoleDto;
import com.techcareer.challenge.exception.BadRequestException;
import com.techcareer.challenge.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/role/")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("add-role")
    public ResponseEntity<Boolean> addRole(@RequestBody RoleDto roleDto){
        return ResponseEntity.ok( roleService.addRole(roleDto));
    }
    @GetMapping("get-role")
    public ResponseEntity<RoleDto> getRole(@RequestParam Integer roleId){
        if (roleId < 0 ){
            throw new BadRequestException("Invalid role ID");
        }
        return ResponseEntity.ok(roleService.getRole(roleId));
    }

    @GetMapping("get-all-role")
    public ResponseEntity<List<RoleDto>> getAllRole(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @DeleteMapping("delete-role")
    public void deleteRole(@RequestParam Integer roleId){
        if (roleId < 0 ){
            throw new BadRequestException("Invalid role ID");
        }
        roleService.deleteRole(roleId);
    }

}
