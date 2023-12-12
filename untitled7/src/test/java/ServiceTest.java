import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import org.junit.Test;
import service.UsuarioService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class ServiceTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testInsercaoListagemUsuario() throws IOException {
        // Teste de Inserção (Passo 17)
        UsuarioService usuarioService = new UsuarioService();
        UsuarioDTOInput usuarioDTO = new UsuarioDTOInput();
        usuarioDTO.setNome("Usuario Teste");
        usuarioDTO.setSenha("senha123");

        usuarioService.inserir(usuarioDTO);


        assertEquals(1, usuarioService.listar().size());

        // Teste de Listagem (Passo 18)
        // Faz uma requisição para a API local (listagem de usuários)
        URL urlLocal = new URL("http://localhost:4567/usuarios"); // Ajuste conforme sua configuração local
        HttpURLConnection connectionLocal = (HttpURLConnection) urlLocal.openConnection();
        connectionLocal.setRequestMethod("GET");

        // Verifica o código de resposta
        assertEquals(HttpURLConnection.HTTP_OK, connectionLocal.getResponseCode());

        // Converte a resposta para um objeto JsonNode usando o ObjectMapper
        JsonNode jsonResponseLocal = objectMapper.readTree(connectionLocal.getInputStream());

        // Verifica se a lista retornada pela API local possui pelo menos um usuário
        assertEquals(true, jsonResponseLocal.isArray());
        assertEquals(true, jsonResponseLocal.size() >= 1);

        // Teste de Inserção com dados aleatórios (Passo 19)
        // Faz uma requisição para a API externa (randomuser.me)
        URL urlExterna = new URL("https://randomuser.me/api/");
        HttpURLConnection connectionExterna = (HttpURLConnection) urlExterna.openConnection();
        connectionExterna.setRequestMethod("GET");


        assertEquals(HttpURLConnection.HTTP_OK, connectionExterna.getResponseCode());


        JsonNode jsonResponseExterna = objectMapper.readTree(connectionExterna.getInputStream());

        // Faz uma requisição para a API local (inserção de usuário com dados aleatórios)
        URL urlInsercao = new URL("http://localhost:4567/usuarios");
        HttpURLConnection connectionInsercao = (HttpURLConnection) urlInsercao.openConnection();
        connectionInsercao.setRequestMethod("POST");
        connectionInsercao.setDoOutput(true);

        // Envia o JSON da resposta externa para a API local
        objectMapper.writeValue(connectionInsercao.getOutputStream(), jsonResponseExterna);

        // Verifica se a inserção foi bem-sucedida
        assertEquals(HttpURLConnection.HTTP_CREATED, connectionInsercao.getResponseCode());
    }
}
