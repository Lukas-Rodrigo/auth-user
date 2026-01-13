package com.lucastexeira.authuser.adapters.in.controllers.client;

import com.lucastexeira.authuser.adapters.in.controllers.client.dto.request.CreateClientRequest;
import com.lucastexeira.authuser.adapters.in.controllers.client.dto.request.PatchClientRequest;
import com.lucastexeira.authuser.adapters.in.controllers.client.dto.response.ClientResponse;
import com.lucastexeira.authuser.adapters.in.controllers.client.mapper.ClientHTTPMapper;
import com.lucastexeira.authuser.adapters.in.controllers.client.mapper.ClientPaginationMapper;
import com.lucastexeira.authuser.common.dto.PaginationResponse;
import com.lucastexeira.authuser.core.port.in.client.CreateClientInputPort;
import com.lucastexeira.authuser.core.port.in.client.DeleteClientByIdInputPort;
import com.lucastexeira.authuser.core.port.in.client.FetchClientInputPort;
import com.lucastexeira.authuser.core.port.in.client.PatchClientInputPort;
import com.lucastexeira.authuser.core.usecase.command.CreateClientCommand;
import com.lucastexeira.authuser.core.usecase.command.PatchClientCommand;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("api/clients")
public class ClientController {

  private final FetchClientInputPort fetchClientInputPort;

  private final ClientHTTPMapper clientMapper;

  private final ClientPaginationMapper paginationMapper;

  private final CreateClientInputPort createClientInputPort;

  private final PatchClientInputPort patchClientInputPort;

  private final DeleteClientByIdInputPort deleteClientByIdInputPort;


  public ClientController(
      FetchClientInputPort fetchClientInputPort,
      CreateClientInputPort createClientInputPort,
      ClientHTTPMapper clientMapper,
      ClientPaginationMapper paginationMapper,
      PatchClientInputPort patchClientInputPort,
      DeleteClientByIdInputPort deleteClientByIdInputPort
  ) {
    this.fetchClientInputPort = fetchClientInputPort;
    this.createClientInputPort = createClientInputPort;
    this.clientMapper = clientMapper;
    this.paginationMapper = paginationMapper;
    this.patchClientInputPort = patchClientInputPort;
    this.deleteClientByIdInputPort = deleteClientByIdInputPort;
  }

  @PostMapping
  public ResponseEntity<ClientResponse> create(
      @Valid @RequestBody CreateClientRequest request,
      Authentication authentication
  ) {
    UUID userId = UUID.fromString(authentication.getName());

    var command = new CreateClientCommand(
        request.name(),
        request.phoneNumber(),
        request.profileUrl(),
        request.observations()
    );
    var result = createClientInputPort.execute(userId, command);
    var response = clientMapper.toResponse(result);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PatchMapping("{id}")
  public ResponseEntity<ClientResponse> patch(
      @Valid @RequestBody PatchClientRequest request,
      @PathVariable UUID id,
      Authentication authentication
  ) {
    UUID userId = UUID.fromString(authentication.getName());

    var command = new PatchClientCommand(
        id,
        request.name(),
        request.phoneNumber(),
        request.observations(),
        request.profileUrl()
    );

    System.out.println(command);
    var result = this.patchClientInputPort.execute(
        userId,
        command
    );
    return ResponseEntity.ok(clientMapper.toResponse(result));
  }

  @GetMapping
  public ResponseEntity<PaginationResponse<ClientResponse>> fetchAllClients(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size,
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso =
          DateTimeFormat.ISO.DATE) LocalDate endDate,
      Authentication authentication
  ) {

    UUID userId = UUID.fromString(authentication.getName());

    var fetchQuery = new FetchQuery(
        page,
        size,
        startDate,
        endDate
    );
    var paginationResult = fetchClientInputPort.execute(
        userId,
        fetchQuery
    );

    var response = paginationMapper.toPaginationResponse(paginationResult);
    return ResponseEntity.ok(response);
  }


  @DeleteMapping("{id}")
  public ResponseEntity<Void>delete(
      Authentication authentication,
      @PathVariable UUID id
  ) {
    UUID userId = UUID.fromString(authentication.getName());
    deleteClientByIdInputPort.execute(userId, id);
    return ResponseEntity.noContent().build();
  }
}
