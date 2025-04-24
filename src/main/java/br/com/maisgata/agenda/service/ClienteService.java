package br.com.maisgata.agenda.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.maisgata.agenda.dto.ClienteDTO;
import br.com.maisgata.agenda.entity.Cliente;
import br.com.maisgata.agenda.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Converter DTO para Entity
    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setObs(dto.getObs());
        return cliente;
    }

    public List<String> gerarLinksWhatsapp(List<Long> ids, String mensagem) {
    List<Cliente> clientes = clienteRepository.findAllById(ids);
    String msg = URLEncoder.encode(mensagem, StandardCharsets.UTF_8);
    return clientes.stream()
            .map(c -> {
                String telefone = c.getTelefone().replaceAll("[^0-9]", "");
                return "https://wa.me/55" + telefone + "?text=" + msg;
            })
            .collect(Collectors.toList());
}


    // Converter Entity para DTO
    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setTelefone(cliente.getTelefone());
        dto.setObs(cliente.getObs());
        return dto;
    }

    public ClienteDTO salvar(ClienteDTO dto) {
        Cliente cliente = toEntity(dto);
        cliente = clienteRepository.save(cliente);
        return toDTO(cliente);
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(this::toDTO);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    public Optional<ClienteDTO> atualizar(Long id, ClienteDTO dto) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(dto.getNome());
            cliente.setTelefone(dto.getTelefone());
            cliente.setObs(dto.getObs());
            return toDTO(clienteRepository.save(cliente));
        });
    }
}
