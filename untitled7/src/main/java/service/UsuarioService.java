package service;

import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final List<Usuario> listaUsuarios = new ArrayList<>();
    private final ModelMapper modelMapper = new ModelMapper();

    public List<UsuarioDTOOutput> listar() {
        List<UsuarioDTOOutput> listaDTO = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            listaDTO.add(modelMapper.map(usuario, UsuarioDTOOutput.class));
        }
        return listaDTO;
    }

    public void inserir(UsuarioDTOInput usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        listaUsuarios.add(usuario);
    }

    public void alterar(UsuarioDTOInput usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        listaUsuarios.removeIf(u -> u.getId().equals(usuario.getId()));
        listaUsuarios.add(usuario);
    }

    public UsuarioDTOOutput buscar(int id) {
        Usuario usuario = listaUsuarios.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        return (usuario != null) ? modelMapper.map(usuario, UsuarioDTOOutput.class) : null;
    }

    public void excluir(int id) {
        listaUsuarios.removeIf(u -> u.getId().equals(id));
    }
}
