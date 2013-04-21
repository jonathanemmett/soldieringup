package org.soldieringup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Class represents a user in the database
 * 
 * @author Jake
 * 
 */
public class User extends SoldierUpAccount implements UserDetails
{
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 7885033133143889220L;
	private String					first_name;
	private String					last_name;
	private Business				business;
	private Veteran					veteran;
	@DBRef
	private War					war;
	@DBRef
	private List<Tag>				tag;
	private String					password;
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
		// TODO Auto-generated constructor stub
	}

	public String getFirstName ()
	{
		return first_name;
	}

	public String getLastName ()
	{
		return last_name;
	}

	public void setFirstName (String first_name)
	{
		this.first_name = first_name;
	}

	public void setLastName (String last_name)
	{
		this.last_name = last_name;
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
	public List<Tag> getTag ()
	{
		return tag;
	}

	/**
	 * @param tag
	 *                the tag to set
	 */
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

	public void setPassword (String password)
	{
		this.password = password;
	}

	@Override
	public String getPassword ()
	{
		return this.password;
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
			sb.append (delim).append (r.getName ());
			delim = ",";
		}
		return sb.toString ();
	}

	public void setStatus (UserAccountStatus status)
	{
		this.status = status;
	}
}
