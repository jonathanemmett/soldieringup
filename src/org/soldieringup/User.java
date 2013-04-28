package org.soldieringup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.gson.annotations.Expose;

/**
 * Class represents a user in the database
 * 
 * @author Jake
 *
 */
@Document(collection = "user")
public class User extends SoldierUpAccount implements UserDetails
{
	private static final long			serialVersionUID	= 7885033133143889220L;
	@Expose
	private String first_name;
	@Expose
	private String last_name;
	private long salt;
	@Expose(deserialize = false)
	private String password;
	@Expose
	private Business business;
	@Expose
	private Veteran veteran;
	@DBRef
	@Expose
	private War war;

	private List<Role>				roles;
	private boolean					enabled			= true;
	private UserAccountStatus			status;
	private Collection<? extends GrantedAuthority>	auths;

	public User (String email, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities)
	{
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.auths = authorities;
	}

	public User ()
	{
		super ();
	}

	public String getFirstName()
	{
		return first_name;
	}

	public String getLastName()
	{
		return last_name;
	}

	public void setFirstName( String first_name )
	{
		this.first_name = first_name;
	}

	public void setLastName( String last_name )
	{
		this.last_name = last_name;
	}

	/**
	 * Gets the salt for the account password
	 * @return The salt for the account password
	 */
	public long getSalt()
	{
		return this.salt;
	}

	/**
	 * Gets the password for the account
	 * @return The password for the account
	 */
	@Override
	public String getPassword()
	{
		return this.password;
	}

	/**
	 * Sets the salt for the account password
	 * @param salt The salt for the account password
	 */
	public void setSalt( long salt )
	{
		this.salt = salt;
	}

	/**
	 * Sets the password for the account
	 * @param The password for the account
	 */
	public void setPassword( String password )
	{
		this.password = password;
	}

	/**
	 * @return the business
	 */
	public Business getBusiness ()
	{
		return business;
	}

	/**
	 * @param business
	 *                the business to set
	 */
	public void setBusiness (Business business)
	{
		this.business = business;
	}

	/**
	 * @return the veteran
	 */
	public Veteran getVeteran ()
	{
		return veteran;
	}

	/**
	 * @return the war
	 */
	public War getWar ()
	{
		return war;
	}

	/**
	 * @param war
	 *                the war to set
	 */
	public void setWar (War war)
	{
		this.war = war;
	}

	/**

	 * @return the tag
	 */
	@Override
	public List<Tag> getTag ()
	{
		return tag;
	}

	/**
	 * @param tag
	 *                the tag to set
	 */
	@Override
	public void setTag (List<Tag> tag)
	{
		this.tag = tag;
	}

	/**
	 * @param veteran
	 *                the veteran to set
	 */
	public void setVeteran (Veteran veteran)
	{
		this.veteran = veteran;
	}

	@Override
	public String toString ()
	{
		return "User [id=" + id + ", " + "first_name=" + first_name + ", " + "last_name=" + last_name + ", " + "address=" + address + ", " + "zip="
				+ zip + "]";
	}

	public void addRole (Role role)
	{
		if (this.roles == null)
			this.roles = new ArrayList<Role> ();
		this.roles.add (role);
	}

	public Object getId ()
	{
		return this.id;
	}

	public List<Role> getRoles ()
	{
		return this.roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities ()
	{
		return auths;
	}

	@Override
	public String getUsername ()
	{
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired ()
	{
		return false;
	}

	@Override
	public boolean isAccountNonLocked ()
	{
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired ()
	{
		return false;
	}

	@Override
	public boolean isEnabled ()
	{
		return enabled;
	}

	public void setEnabled (boolean b)
	{
		this.enabled = b;
	}

	public UserAccountStatus getStatus ()
	{
		return status;
	}

	public String getRolesCSV ()
	{
		String delim = "";
		StringBuilder sb = new StringBuilder ();
		for (Role r : this.roles) {
			sb.append (delim).append (r.getRole ());
			delim = ",";
		}
		return sb.toString ();
	}

	public void setStatus (UserAccountStatus status)
	{
		this.status = status;
	}
}
