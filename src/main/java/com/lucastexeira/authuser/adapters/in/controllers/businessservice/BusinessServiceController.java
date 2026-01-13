package com.lucastexeira.authuser.adapters.in.controllers.businessservice;

import com.lucastexeira.authuser.adapters.in.controllers.businessservice.dto.request.CreateBusinessServiceRequest;
import com.lucastexeira.authuser.adapters.in.controllers.businessservice.dto.request.PatchBusinessServiceRequest;
import com.lucastexeira.authuser.adapters.in.controllers.businessservice.dto.response.BusinessServiceResponse;
import com.lucastexeira.authuser.adapters.in.controllers.businessservice.mapper.BusinessServiceHTTPMapper;
import com.lucastexeira.authuser.adapters.in.controllers.businessservice.mapper.BusinessServicePaginationMapper;
import com.lucastexeira.authuser.common.dto.PaginationResponse;
import com.lucastexeira.authuser.core.port.in.businessservice.CreateBusinessServiceInputPort;
import com.lucastexeira.authuser.core.port.in.businessservice.DeleteBusinessServiceByIdInputPort;
import com.lucastexeira.authuser.core.port.in.businessservice.FetchBusinessServicesInputPort;
import com.lucastexeira.authuser.core.port.in.businessservice.PatchBusinessServiceInputPort;
import com.lucastexeira.authuser.core.usecase.command.CreateBusinessServiceCommand;
import com.lucastexeira.authuser.core.usecase.command.PatchBusinessServiceCommand;
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
@RequestMapping("api/business-services")
public class BusinessServiceController {

  private final CreateBusinessServiceInputPort createBusinessServiceInputPort;

  private final FetchBusinessServicesInputPort fetchBusinessServicesInputPort;

  private final PatchBusinessServiceInputPort patchBusinessServiceInputPort;

  private final DeleteBusinessServiceByIdInputPort deleteBusinessServiceByIdInputPort;

  private final BusinessServicePaginationMapper paginationMapper;

  private final BusinessServiceHTTPMapper businessServiceMapper;

  public BusinessServiceController(
      CreateBusinessServiceInputPort createBusinessServiceInputPort,
      FetchBusinessServicesInputPort fetchBusinessServicesInputPort,
      PatchBusinessServiceInputPort patchBusinessServiceInputPort,
      DeleteBusinessServiceByIdInputPort deleteBusinessServiceByIdInputPort,
      BusinessServicePaginationMapper paginationMapper,
      BusinessServiceHTTPMapper businessServiceMapper
  ) {
    this.createBusinessServiceInputPort = createBusinessServiceInputPort;
    this.fetchBusinessServicesInputPort = fetchBusinessServicesInputPort;
    this.patchBusinessServiceInputPort = patchBusinessServiceInputPort;
    this.deleteBusinessServiceByIdInputPort = deleteBusinessServiceByIdInputPort;
    this.paginationMapper = paginationMapper;
    this.businessServiceMapper = businessServiceMapper;
  }

  @PostMapping()
  public ResponseEntity<BusinessServiceResponse> create(
      @Valid
      @RequestBody CreateBusinessServiceRequest request,
      Authentication authentication
  ) {

    UUID userId = UUID.fromString(authentication.getName());
    var command = new CreateBusinessServiceCommand(
        userId,
        request.name(),
        request.price(),
        request.duration()
    );
    var result = this.createBusinessServiceInputPort.execute(command);
    return ResponseEntity.status(HttpStatus.CREATED).body(businessServiceMapper.toResponse(result));

  }

  @PatchMapping("{id}")
  public ResponseEntity<BusinessServiceResponse> patch(
      @Valid @RequestBody PatchBusinessServiceRequest request,
      @PathVariable UUID id,
      Authentication authentication
  ) {
    UUID userId = UUID.fromString(authentication.getName());
    var command = new PatchBusinessServiceCommand(
        id,
        request.name(),
        request.price(),
        request.duration()
    );
    var result = this.patchBusinessServiceInputPort.execute(userId, command);
    return ResponseEntity.ok(businessServiceMapper.toResponse(result));
  }

  @GetMapping()
  public ResponseEntity<PaginationResponse<BusinessServiceResponse>> fetchServices(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size,
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso =
          DateTimeFormat.ISO.DATE) LocalDate endDate,
      Authentication authentication
  ) {
    UUID userId = UUID.fromString(authentication.getName());
    var result = this.fetchBusinessServicesInputPort.execute(userId,
        new FetchQuery(
            page,
            size,
            startDate,
            endDate
        ));
    var response = this.paginationMapper.toPaginationResponse(result);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(
      Authentication authentication,
      @PathVariable UUID id
  ) {
    UUID userId = UUID.fromString(authentication.getName());
    this.deleteBusinessServiceByIdInputPort.execute(userId, id);
    return ResponseEntity.noContent().build();
  }
}
