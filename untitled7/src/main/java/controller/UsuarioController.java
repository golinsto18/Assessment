package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import service.UsuarioService;

import static spark.Spark.*;

public class UsuarioController {
    private final UsuarioService usuarioService = new UsuarioService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void respostasRequisicoes() {
        get("/usuarios", (req, res) -> {
            res.type("application/json");
            return objectMapper.writeValueAsString(usuarioService.listar());
        });

        get("/usuarios/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            res.type("application/json");
            return objectMapper.writeValueAsString(usuarioService.buscar(id));
        });

        delete("/usuarios/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            usuarioService.excluir(id);
            res.status(204);
            return "";
        });

        post("/usuarios", (req, res) -> {
            UsuarioDTOInput usuarioDTO = objectMapper.readValue(req.body(), UsuarioDTOInput.class);
            usuarioService.inserir(usuarioDTO);
            res.status(201);
            return "";
        });

        put("/usuarios", (req, res) -> {
            UsuarioDTOInput usuarioDTO = objectMapper.readValue(req.body(), UsuarioDTOInput.class);
            usuarioService.alterar(usuarioDTO);
            res.status(204);
            return "";
        });
    }

    public static void main(String[] args) {
        UsuarioController usuarioController = new UsuarioController();
        usuarioController.respostasRequisicoes();
    }
}
