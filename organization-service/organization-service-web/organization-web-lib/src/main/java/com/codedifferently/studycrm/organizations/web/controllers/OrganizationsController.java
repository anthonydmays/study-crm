package com.codedifferently.studycrm.organizations.web.controllers;

import com.codedifferently.studycrm.organizations.api.web.CreateOrganizationRequest;
import com.codedifferently.studycrm.organizations.api.web.CreateOrganizationResponse;
import com.codedifferently.studycrm.organizations.api.web.GetOrganizationResponse;
import com.codedifferently.studycrm.organizations.api.web.GetOrganizationsResponse;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationService;
import com.codedifferently.studycrm.organizations.sagas.OrganizationSagaService;
import jakarta.validation.Valid;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/organizations")
public class OrganizationsController {

  @Autowired private OrganizationSagaService organizationSagaService;

  @Autowired private OrganizationService organizationService;

  @PostMapping
  public CreateOrganizationResponse createOrganization(
      @Valid @RequestBody CreateOrganizationRequest createOrganizationRequest) {
    var newOrg =
        Organization.builder().name(createOrganizationRequest.getOrganizationName()).build();
    UserDetails userDetails = createOrganizationRequest.getUserDetails();
    Organization organization = organizationSagaService.createOrganization(newOrg, userDetails);
    return new CreateOrganizationResponse(organization.getId());
  }

  @GetMapping
  public ResponseEntity<GetOrganizationsResponse> getAll(final JwtAuthenticationToken auth) {
    return ResponseEntity.ok(
        new GetOrganizationsResponse(
            StreamSupport.stream(organizationService.findAllOrganizations().spliterator(), false)
                .map(c -> new GetOrganizationResponse(c.getId(), c.getName()))
                .collect(Collectors.toList())));
  }

  @GetMapping(value = "/{organizationId}")
  public ResponseEntity<GetOrganizationResponse> getOrganization(
      @PathVariable("organizationId") @Param("organizationId") UUID organizationId) {
    return organizationService
        .findOrganizationById(organizationId)
        .map(
            c ->
                new ResponseEntity<>(
                    new GetOrganizationResponse(c.getId(), c.getName()), HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}