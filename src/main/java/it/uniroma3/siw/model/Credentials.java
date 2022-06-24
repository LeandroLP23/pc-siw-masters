package it.uniroma3.siw.model;

import javax.persistence.*;

//Credentials only refers to admin users
@Entity
public class Credentials {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String role;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	public static final String ADMIN_ROLE = "ADMIN";
	public static final String DEFAULT_ROLE = "USER";

	public Credentials(String username, String password, User user) {
		this.username = username;
		this.password = password;
		this.user = user;
		this.role = DEFAULT_ROLE;
	}

	public Credentials() {
		this.role = DEFAULT_ROLE;
	}

	public Long getId() {
		return this.id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public void setAdminRole()
	{
		this.role = ADMIN_ROLE;
	}
	
	public void setDefaultRole()
	{
		this.role = DEFAULT_ROLE;
	}

	public User getUser()
	{
		return this.user;
	}
	
	public String getRole() {
		return this.role;
	}
}

