package com.example.domain.port.input;

import com.example.core.dto.CreateMemberCommand;
import com.example.core.dto.CreateMemberResponse;


public interface MemberApplicationService {
    CreateMemberResponse createMember(CreateMemberCommand createMemberCommand);
}
