package com.example.todolistii.services;

import com.example.todolistii.domain.User;
import com.example.todolistii.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserSevice implements IUserService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserSevice(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS usr");
        jdbcTemplate.execute("CREATE TABLE usr (id LONG, email VARCHAR(30), password VARCHAR(30))");
    }

    @Override
    public int create(User user) {
        String query = String.format("INSERT INTO usr VALUES (%d, '%s', '%s')",
                user.getId(),
                user.getEmail(),
                user.getPassword());
        int result = jdbcTemplate.update(query);
        return result;
    }

    @Override
    public User get(long id) {
        String query = "SELECT * FROM usr WHERE Id=?";
        User user = jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
        return user;
    }

    @Override
    public int update(long id, User user) {
        String query = String.format("UPDATE usr SET email='%s', password='%s' WHERE id=%d",
                user.getEmail(),
                user.getPassword(),
                id);
        int result = jdbcTemplate.update(query);
        return result;
    }

    @Override
    public int delete(long id) {
        String query = String.format("DELETE FROM usr WHERE id=%d", id);
        int result = jdbcTemplate.update(query);
        return result;
    }
}
