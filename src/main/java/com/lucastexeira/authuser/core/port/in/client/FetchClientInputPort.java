package com.lucastexeira.authuser.core.port.in.client;

import com.lucastexeira.authuser.core.domain.Client;
import com.lucastexeira.authuser.core.usecase.query.FetchQuery;
import com.lucastexeira.authuser.core.usecase.result.PageGenericResult;

import java.util.UUID;

public interface FetchClientInputPort {
  PageGenericResult<Client> execute(UUID userId, FetchQuery fetchQuery);
}
