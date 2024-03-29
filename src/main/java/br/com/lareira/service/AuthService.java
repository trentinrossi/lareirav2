package br.com.lareira.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lareira.model.Usuario;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

    @Autowired
    private UsuarioService usuarioService;

    // @Autowired
    // private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email) {

        Usuario u = usuarioService.findByEmail(email);
        if (u == null) {
            throw new ObjectNotFoundException("Usuário não encontrado com este e-mail.");
        }

        u.setSenha(newPassword());
        usuarioService.update(u);
        
        // TODO implementar a rotina de e-mail
        // emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) { // gera um digito
            return (char) (rand.nextInt(10) + 48);
        } else if (opt == 1) { // gera letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        } else { // gera letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }
}