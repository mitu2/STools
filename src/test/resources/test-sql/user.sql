INSERT INTO user (id, create_time, update_time, account, email, nickname, password, status) VALUE (1, now(), now(), 'user', 'null@null.null', 'user', '{bcrypt}$2a$10$mZaNhpZr/bX0o5RSQa40eOXqdJ85r5hDIcI76Un43HyVCvAHK2LAC', 1);

INSERT INTO user_authority(id, create_time, update_time, authority_id, user_id, valid_period) VALUE (1, now(), now(), 1, 1, null);