package by.cniitu.virtualexhibition.repository.user;

import by.cniitu.virtualexhibition.entity.user.User;

public interface UserRepository {

    User save(User user);

    void delete(int id);

    User get(int id);

    User getByEmail(String email);

}
