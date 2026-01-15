package com.lucastexeira.authuser.adapters.out.user;

import com.lucastexeira.authuser.adapters.out.persistence.entity.user.UserEntity;
import com.lucastexeira.authuser.adapters.out.persistence.entity.user.mapper.UserPersistenceMapper;
import com.lucastexeira.authuser.adapters.out.persistence.repository.UserRepository;
import com.lucastexeira.authuser.core.domain.User;
import com.lucastexeira.authuser.core.port.out.user.FetchUsersOutputPort;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FetchUserAdapter implements FetchUsersOutputPort {

  private final UserRepository userRepository;

  public FetchUserAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public PageGenericResult<User> execute(
      int page,
      int size,
      LocalDate startDate,
      LocalDate endDate
  ) {
    Pageable pageable = PageRequest.of(page, size);

    Page<UserEntity> result =
        userRepository.findAllWithFilter(startDate, endDate, pageable);

    List<User> users = result.getContent()
        .stream()
        .map(UserPersistenceMapper.INSTANCE::toDomain)
        .toList();
    return new PageGenericResult<User>(
        users,
        result.getNumber(),
        result.getSize(),
        result.getTotalElements(),
        result.getTotalPages(),
        result.hasNext(),
        result.hasPrevious()
    );
  }
}
