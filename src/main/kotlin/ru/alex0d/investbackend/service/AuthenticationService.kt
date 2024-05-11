package ru.alex0d.investbackend.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.alex0d.investbackend.config.JwtService
import ru.alex0d.investbackend.dto.AuthenticationRequest
import ru.alex0d.investbackend.dto.AuthenticationResponse
import ru.alex0d.investbackend.dto.RefreshTokenRequest
import ru.alex0d.investbackend.dto.RegisterRequest
import ru.alex0d.investbackend.model.Portfolio
import ru.alex0d.investbackend.model.RefreshToken
import ru.alex0d.investbackend.model.Role
import ru.alex0d.investbackend.model.User
import ru.alex0d.investbackend.repository.RefreshTokenRepository
import ru.alex0d.investbackend.repository.UserRepository

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {
    fun register(request: RegisterRequest): AuthenticationResponse {
        val portfolio = Portfolio()
        val user = User(
            firstname = request.firstname,
            lastname = request.lastname,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.USER,
            portfolio = portfolio
        )
        portfolio.user = user
        userRepository.save(user)

        val jwtRefreshToken = jwtService.generateRefreshToken(user)
        val refreshToken = RefreshToken(
            token = jwtRefreshToken,
            expiration = jwtService.extractExpiration(jwtRefreshToken)!!.time,
            user = user
        )
        refreshTokenRepository.save(refreshToken)

        val accessToken = jwtService.generateAccessToken(user)
        return AuthenticationResponse(accessToken, jwtRefreshToken)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        val user = userRepository.findByEmail(request.email)!!

        val jwtRefreshToken = jwtService.generateRefreshToken(user)
        val refreshToken = RefreshToken(
            token = jwtRefreshToken,
            expiration = jwtService.extractExpiration(jwtRefreshToken)!!.time,
            user = user
        )
        refreshTokenRepository.save(refreshToken)

        val accessToken = jwtService.generateAccessToken(user)
        return AuthenticationResponse(accessToken, jwtRefreshToken)
    }

    fun refresh(request: RefreshTokenRequest): AuthenticationResponse? {
        val refreshToken = refreshTokenRepository.findByToken(request.refreshToken) ?: return null
        if (jwtService.isTokenExpired(refreshToken.token)) {
            refreshTokenRepository.delete(refreshToken)
            return null
        }

        val user = refreshToken.user
        val jwtRefreshToken = jwtService.generateRefreshToken(user)
        refreshToken.token = jwtRefreshToken
        refreshToken.expiration = jwtService.extractExpiration(jwtRefreshToken)!!.time
        refreshTokenRepository.save(refreshToken)

        val accessToken = jwtService.generateAccessToken(user)
        return AuthenticationResponse(accessToken, jwtRefreshToken)
    }
}