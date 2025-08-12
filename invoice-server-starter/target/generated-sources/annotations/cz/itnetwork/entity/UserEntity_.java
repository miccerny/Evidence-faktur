package cz.itnetwork.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserEntity.class)
public abstract class UserEntity_ {

	public static volatile ListAttribute<UserEntity, PersonEntity> persons;
	public static volatile SingularAttribute<UserEntity, String> password;
	public static volatile SingularAttribute<UserEntity, Boolean> admin;
	public static volatile SingularAttribute<UserEntity, Long> userId;
	public static volatile SingularAttribute<UserEntity, String> email;

	public static final String PERSONS = "persons";
	public static final String PASSWORD = "password";
	public static final String ADMIN = "admin";
	public static final String USER_ID = "userId";
	public static final String EMAIL = "email";

}

