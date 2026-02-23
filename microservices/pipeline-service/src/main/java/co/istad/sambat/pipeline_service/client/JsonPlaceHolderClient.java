package co.istad.sambat.pipeline_service.client;

import co.istad.sambat.pipeline_service.client.dto.UserResponse;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface JsonPlaceHolderClient {
    // https://jsonplaceholder.typicode.com/users
    @GetExchange("/users")
    List<UserResponse> getUsers();
}
