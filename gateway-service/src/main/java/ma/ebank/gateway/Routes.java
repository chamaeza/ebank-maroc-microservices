package ma.ebank.gateway;
import org.springframework.context.annotation.*;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
@Configuration public class Routes { @Bean RouteLocator routes(RouteLocatorBuilder b){return b.routes().route("customers",r->r.path("/api/customers/**").uri("lb://customer-service")).route("bank",r->r.path("/api/accounts/**").uri("lb://ebank-service")).build();}}