package talentsync.moseshunsu.service.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import talentsync.moseshunsu.dto.security.AuthResponse;
import talentsync.moseshunsu.dto.security.LoginDto;

import java.io.IOException;

public interface AuthService {
    AuthResponse login(LoginDto loginDto);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
