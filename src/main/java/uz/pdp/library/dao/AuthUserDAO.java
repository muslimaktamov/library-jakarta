package uz.pdp.library.dao;

import uz.pdp.library.entity.AuthUser;

public class AuthUserDAO extends BaseDao<AuthUser, String> {
    public AuthUser findByEmail(String email) {
        begin();
        AuthUser authUser = em.createQuery(
                        "select t from AuthUser t where t.email ilike :email", AuthUser.class)
                .setParameter("email", email)
                .getSingleResultOrNull();
        commit();
        return authUser;

    }

}
