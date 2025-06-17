package com.libraryManagement.controller;

import com.libraryManagement.dto.requestDto.AuthRequestDto;
import com.libraryManagement.dto.requestDto.MemberUpdateDto;
import com.libraryManagement.dto.responseDto.MemberProfileDto;
import com.libraryManagement.dto.requestDto.RegisterRequestDto;
import com.libraryManagement.dto.responseDto.AuthResponseDto;
import com.libraryManagement.entities.Member;
import com.libraryManagement.repository.MemberRepository;
import com.libraryManagement.service.UserInfoService;
import com.libraryManagement.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Validated
@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AuthController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtUtil jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome! This endpoint is not secure.";
    }

    @PostMapping("/register")
    public String registerMember(@Valid @RequestBody RegisterRequestDto dto) {
        return service.registerUser(dto);
    }

    @PostMapping("/login")
    public AuthResponseDto authenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            Member member = memberRepository.findByEmail(authRequestDto.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new AuthResponseDto(jwtService.generateToken(authRequestDto.getEmail(), member.getUserRole().name()), member.getUserRole().name());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/profile")
    public MemberProfileDto getProfile(Authentication authentication) {
        String email = authentication.getName();  // always non-null when authenticated

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        MemberProfileDto profile = new MemberProfileDto();
        profile.setId(member.getMemberId());
        profile.setName(member.getName());
        profile.setEmail(member.getEmail());
        profile.setPhone(member.getPhone());
        profile.setAddress(member.getAddress());
        profile.setMembershipStatus(member.getMembershipStatus());
        return profile;
    }

    @PutMapping("/profile")
    public MemberProfileDto updateProfile(Authentication authentication,@Valid @RequestBody MemberUpdateDto dto){
        String email = authentication.getName();
        Member m=memberRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        m.setName(dto.getName());
        m.setPhone(dto.getPhone());
        m.setAddress(dto.getAddress());
        memberRepository.save(m);
        return new MemberProfileDto(m.getMemberId(),m.getName(),m.getEmail(),m.getPhone(),m.getAddress(),m.getMembershipStatus());
    }
    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            jwtService.invalidateToken(token); // Blacklist the token
            return "User logged out successfully!";
        } else {
            return "Invalid token!";
        }
    }
}
