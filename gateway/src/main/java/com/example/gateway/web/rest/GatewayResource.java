package com.example.gateway.web.rest;

import com.example.gateway.security.AuthoritiesConstants;
import com.example.gateway.service.UserService;
import com.example.gateway.service.dto.UserDTO;
import com.example.gateway.web.rest.vm.RouteVM;
import io.github.jhipster.web.util.ResponseUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Gateway configuration.
 */
@RestController
@RequestMapping("/api/gateway")
public class GatewayResource {
    private final RouteLocator routeLocator;

    private final DiscoveryClient discoveryClient;

    private final UserService userService;

    public GatewayResource(RouteLocator routeLocator, DiscoveryClient discoveryClient, UserService userService) {
        this.routeLocator = routeLocator;
        this.discoveryClient = discoveryClient;
        this.userService = userService;
    }

    /**
     * {@code GET  /routes} : get the active routes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of routes.
     */
    @GetMapping("/routes")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<RouteVM>> activeRoutes() {
        List<Route> routes = routeLocator.getRoutes();
        List<RouteVM> routeVMs = new ArrayList<>();
        routes.forEach(
            route -> {
                RouteVM routeVM = new RouteVM();
                routeVM.setPath(route.getFullPath());
                routeVM.setServiceId(route.getId());
                routeVM.setServiceInstances(discoveryClient.getInstances(route.getLocation()));
                routeVMs.add(routeVM);
            }
        );
        return ResponseEntity.ok(routeVMs);
    }

    /**
     * {@code GET /user/:id} : get the "id" user.
     *
     * @param id the id of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser(@RequestParam Long id) {
        return ResponseUtil.wrapOrNotFound(userService.getUserWithoutAuthoritiesById(id).map(UserDTO::new));
    }
}
