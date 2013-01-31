package third.model;

import javax.persistence.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"firstName","lastName"}))
public abstract class Human {

	@Id
	@GeneratedValue
	@Column
	protected Integer id;

	@Column
	protected String firstName;

	@Column
	protected String lastName;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
