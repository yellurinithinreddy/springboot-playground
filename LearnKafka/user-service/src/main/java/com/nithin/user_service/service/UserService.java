package com.nithin.user_service.service;

import com.nithin.event.UserCreatedEvent;
import com.nithin.user_service.dto.CreateUserRequest;
import com.nithin.user_service.entity.User;

import com.nithin.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;

    public String create(CreateUserRequest request) {
        User user = modelMapper.map(request,User.class);
        user = userRepository.save(user);

        UserCreatedEvent userCreatedEvent = modelMapper.map(user, UserCreatedEvent.class);
        kafkaTemplate.send("user-created-topic",user.getId(),userCreatedEvent);
        return "User Created";
    }
}
