package br.com.intelliapps.envers.audit.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@MappedSuperclass
public class CustomRevisionEntity implements Serializable {

	private static final long serialVersionUID = 296532230077066360L;
	
	@Id
	@RevisionNumber
	@GeneratedValue
	@Column(name="revision_number")
	private int revisionNumber;
	
	@RevisionTimestamp
	@Column(name="revision_timestamp")
	private long revisionTimestamp;

	public int getRevisionNumber() {
		return revisionNumber;
	}
	
	@Transient
	public Date getRevisionDate() {
		return new Date(this.revisionTimestamp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + revisionNumber;
		result = prime * result + (int) (revisionTimestamp ^ (revisionTimestamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomRevisionEntity other = (CustomRevisionEntity) obj;
		if (revisionNumber != other.revisionNumber)
			return false;
		if (revisionTimestamp != other.revisionTimestamp)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomRevisionEntity [revisionNumber=" + revisionNumber + ", revisionTimestamp=" + revisionTimestamp + "]";
	}
	
}
