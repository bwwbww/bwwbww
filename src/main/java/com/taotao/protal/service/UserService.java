package com.taotao.protal.service;

import com.taotao.pojo.TbUser;

public interface UserService {
TbUser getUserByToken(String token);
}
