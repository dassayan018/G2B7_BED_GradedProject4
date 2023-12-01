package com.gl.empMgmt.Service_;

import com.gl.empMgmt.dto.UserRegistrationDto;
import com.gl.empMgmt.entity.User;

public interface UserService {
	public User save(UserRegistrationDto registrationDto);

}
