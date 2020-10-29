package by.cniitu.virtualexhibition.repository.user;

import by.cniitu.virtualexhibition.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaUserRepository implements UserRepository {

    @Autowired
    private JpaUserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User get(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
